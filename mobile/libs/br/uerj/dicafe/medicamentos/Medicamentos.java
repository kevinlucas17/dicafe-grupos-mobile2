package br.uerj.dicafe.medicamentos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import br.uerj.dicafe.utils.SerializacaoUtils;

/**
 * Lista de medicamentos e seus princípios ativos, que compara quais
 * medicamentos podem ser tomados juntos.
 */

public class Medicamentos implements ListaMedicamentos {

    private static final String ENDERECO_PASTA = "bd-local/";
    private static final String ENDERECO_PRINCIPIOS_ATIVOS = ENDERECO_PASTA+"principiosAtivos.ser";
    private static final String ENDERECO_MEDICAMENTOS = ENDERECO_PASTA+"medicamentos.ser";
    private static final String ENDERECO_HISTORICO = ENDERECO_PASTA+"historicoMedicamentos.ser";

    private HashMap<String, PrincipioAtivo> principiosAtivos;
    private HashMap<String, Medicamento> medicamentos;
    private ArrayList<String[]> historicoMedicamentos;
    private String enderecoPrincipiosAtivos;
    private String enderecoMedicamentos;
    private String enderecoHistoricoMedicamentos;

    /**
     * Constrói uma lista de medicamentos vazia e outra de princípios ativos.
     * @param enderecoPrincipiosAtivos Endereço do arquivo local de princípios ativos.
     * @param enderecoMedicamentos Endereço do arquivo local de medicamentos.
     */
    public Medicamentos(String enderecoPrincipiosAtivos, String enderecoMedicamentos,
                        String enderecoHistoricoMedicamentos) {
        principiosAtivos = carregaPrincipiosAtivos(enderecoPrincipiosAtivos);
        medicamentos = carregaMedicamentos(enderecoMedicamentos);
        historicoMedicamentos = carregaHistorico(enderecoHistoricoMedicamentos);

        this.enderecoPrincipiosAtivos = enderecoPrincipiosAtivos;
        this.enderecoMedicamentos = enderecoMedicamentos;
        this.enderecoHistoricoMedicamentos = enderecoHistoricoMedicamentos;
    }

    public Medicamentos() {
        this(ENDERECO_PRINCIPIOS_ATIVOS, ENDERECO_MEDICAMENTOS, ENDERECO_HISTORICO);
    }


    /**
     * Adiciona um medicamento ao histórico.
     * 
     * @param nome Nome do medicamento.
     * @param data Data de quando o medicamento foi tomado.
     * @param quantidade Quantidade tomada.
     *
     * @throws IllegalArgumentException Se o medicamento não está registrado no banco de
     * dados.
     */
    public void adicionaMedicamentoHistorico(String nome, String data, double quantidade) {
        if (!existeMedicamento(nome))
            throw new IllegalArgumentException("Medicamento não registrado no banco de dados.");
        historicoMedicamentos.add(new String[] {nome, data, String.valueOf(quantidade)});
        guardaHistorico(enderecoHistoricoMedicamentos);
    }

    /**
     * Adiciona um medicamento ao histórico. A quantidade é equivalente a uma porção.
     */
    public void adicionaMedicamentoHistorico(String nome, String data) {
        if (!existeMedicamento(nome))
            throw new IllegalArgumentException("Medicamento não registrado no banco de dados.");
        adicionaMedicamentoHistorico(nome, data, medicamentos.get(nome).pegaQuantidadePorPorcao());
    }

    /**
     * Checa se um medicamento pode ser tomado no dia de hoje. <br/>
     * Obs.: Os medicamentos só são considerados se tiverem sido adicionados na mesma data.
     *
     * @param nomeMedicamento Nome do medicamento sendo checado.
     *
     * @return Verdadeiro ou falso, dependendo se o medicamento pode ou não ser tomado hoje.
     *
     * @throws IOException Se não for possível acessar a API de medicamentos.
     */
    public boolean podeHoje(String nomeMedicamento) throws IOException{

        String[][] historicoHoje = pegaHistoricoDeHoje();
        HashSet<String> medicamentosHoje = new HashSet<>();
        for (String[] linha : historicoHoje)
            medicamentosHoje.add(linha[0]);
        try {
            return podemJuntos(nomeMedicamento, medicamentosHoje.toArray(new String[0]));
        }
        catch (IOException e) {
            throw e;
        }
    }


    /**
     * @return A parte do histórico referente ao dia de hoje.
     */
    public String[][] pegaHistoricoDeHoje() {
        ordenaHistorico();

        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        int primeiroDoDia;
        for (primeiroDoDia = historicoMedicamentos.size(); primeiroDoDia > 0; primeiroDoDia--)
            if (data.compareTo(historicoMedicamentos.get(primeiroDoDia-1)[1]) > 0)
                break;

        String[][] historicoHoje = new String[historicoMedicamentos.size()-primeiroDoDia][3];
        for (int i = 0; i < historicoHoje.length; i++)
            for (int j = 0; j < 3; j++)
                historicoHoje[i][j] = historicoMedicamentos.get(i+primeiroDoDia)[j];
        
        return historicoHoje;
    }

    /**
     * @return Os princípios ativos dos medicamentos registrados na data te "hoje".
     */
    public String[] pegaPrincipiosAtivosHoje() {

        String[][] historicoHoje = pegaHistoricoDeHoje();
        HashSet<String> principiosAtivosConjunto = new HashSet<>();

        for (int i = 0; i < historicoHoje.length; i++)
            principiosAtivosConjunto.add(medicamentos.get(historicoHoje[i][0]).pegaPrincipioAtivo());

        return principiosAtivosConjunto.toArray(new String[0]);
    }





    private void ordenaHistorico() {

        Comparator c = new Comparator<String[]>() {
            public int compare(String[] a, String[] b) {
                return a[1].compareTo(b[1]);
            }
            public boolean equals(Object obj) {
                return false;
            }
        };

        historicoMedicamentos.sort(c);
    }

    /**
     * Adiciona um medicamento à lista de medicamentos disponíveis.
     *
     * @param nome Nome do medicamento.
     * @param principioAtivo Princípio ativo do medicamento.
     * @param marca Marca do medicamento.
     * @param quantidadePorPorcao Quantidade, em mg, do princípio ativo
     * @param bula Bula do medicamento.
     *
     * @throws IllegalArgumentException Se a quantidade não for um
     * número finito positivo.
     * @throws IllegalStateException Se o medicamento já estiver na lista.
     */
    public void adicionaMedicamento(
        String nome, String principioAtivo, String marca,
        double quantidadePorPorcao, String bula) {
        
        if (!existePrincipioAtivo(principioAtivo))
            throw new IllegalStateException("Princípio ativo não existe na lista.");

        // Levanta IllegalArgumentException.
        Medicamento m = new Medicamento(nome, principioAtivo, marca,
                                        quantidadePorPorcao, bula);

        medicamentos.put(nome, m);

        guardaMedicamentos(enderecoMedicamentos);
    }

    /**
     * Remove um medicamento da lista.
     *
     * @param nome Nome do medicamento a ser removido.
     * @throws IllegalStateException Se o medicamento não estiver na
     * lista.
     */
    public void removeMedicamento(String nome) {
        if (!existeMedicamento(nome))
            throw new IllegalStateException("Medicamento não está na lista.");
        medicamentos.remove(nome);

        guardaMedicamentos(enderecoMedicamentos);
    }

    /**
     * Adiciona um princípio ativo à lista.
     *
     * @param nome Nome do princípio ativo.
     * @param limiteDiario O máximo que se pode tomar diariamente. Se 
     * não houver limite, é igual a infinito.
     * @param codigoRxCUI O código RxCUI do medicamento na API da
     * biblioteca nacional de medicina dos Estados Unidos.
     * @param contraindicacoesLocais Contraindicações que não dependem
     * da API.
     *
     * @throws IllegalArgumentException Se o limite diário ou o código
     * RxCUI não forem positivos.
     * @throws IllegalStateException Se o princípio ativo já está na
     * lista.
     */
    public void adicionaPrincipioAtivo(
        String nome, double limiteDiario, int codigoRxCUI,
        String[] contraindicacoesLocais) {

        if (existePrincipioAtivo(nome))
            throw new IllegalStateException("Princípio ativo já existe na lista.");

        PrincipioAtivo p;
        // Levantam IllegalArgumentException.
        if (contraindicacoesLocais.length == 0) 
            p = new PrincipioAtivo(nome, limiteDiario, codigoRxCUI);
        else
            p = new PrincipioAtivo(nome, limiteDiario, codigoRxCUI,
                                                  contraindicacoesLocais);

        principiosAtivos.put(nome, p);
        guardaPrincipiosAtivos(enderecoPrincipiosAtivos);
    }

    /**
     * Adiciona um princípio ativo à lista, sem contra indicações locais.
     *
     * @param nome Nome do princípio ativo.
     * @param limiteDiario O máximo que se pode tomar diariamente. Se 
     * não houver limite, é igual a infinito.
     * @param codigoRxCUI O código RxCUI do medicamento na API da
     * biblioteca nacional de medicina dos Estados Unidos.
     *
     * @throws IllegalArgumentException Se o limite diário ou o código
     * RxCUI não forem positivos.
     * @throws IllegalStateException Se o princípio ativo já está na
     * lista.
     */
    public void adicionaPrincipioAtivo(String nome, double limiteDiario,
                                       int codigoRxCUI) {
        adicionaPrincipioAtivo(nome, limiteDiario, codigoRxCUI, new String[0]);
    }

    /**
     * Remove um princípio ativo da lista.
     *
     * @param nome Nome do princípio ativo a ser removido.
     * @throws IllegalStateException Se o princípio ativo não estiver
     * na lista.
     */
    public void removePrincipioAtivo(String nome) {
        if (!existePrincipioAtivo(nome))
            throw new IllegalStateException("Princípio ativo não está na lista.");
        principiosAtivos.remove(nome);

        guardaPrincipiosAtivos(enderecoPrincipiosAtivos);
    }

    /**
     * Checa se um medicamento pode ser tomado com qualquer medicamento
     * em um grupo individualmente.
     *
     * @param nomeFora Medicamento a ser testado.
     * @param grupoNomes Grupo de nomes de medicamentos.
     * @return Verdadeiro, se os medicamentos podem ser tomados juntos
     * e falso, se não.
     * @throws IllegalStateException Se ao menos um dos nomes for
     * inválido.
     * @throws IllegalArgumentException Se os nomes forem iguais.
     * @throws IOException Quando não se consegue acessar a API.
     */
    public boolean podemJuntos(String nomeFora, String[] grupoNomes) throws IOException {
        
        // Levanta IllegalStateException.
        int rxcuiNomeFora = pegaCodigoRxCUI(nomeFora);
        HashSet<String> contraindicacoesLocaisNomeFora =
            new HashSet<>(Arrays.asList(pegaContraIndicacoesLocais(nomeFora)));

        for (String grupoNome : grupoNomes) {
            String principioAtivo = medicamentos.get(grupoNome).pegaPrincipioAtivo();
            if (contraindicacoesLocaisNomeFora.contains(principioAtivo))
                return false;
        }

        int[] rxcuisGrupo = new int[grupoNomes.length];
        for (int i = 0; i < rxcuisGrupo.length; i++) {
            if (nomeFora == grupoNomes[i])
                throw new IllegalArgumentException(
                    "Não é permitida a repetição de medicamentos.");
            // Levanta IllegalStateException
            rxcuisGrupo[i] = pegaCodigoRxCUI(grupoNomes[i]);
        }
        HashSet<Integer> contraindicacoesNomeFora;
        try {
            contraindicacoesNomeFora = new HashSet<>(Arrays.asList(
                MedicamentosAPI.pegaContraindicacoes(rxcuiNomeFora)));
        }
        catch (IOException e) {
            throw new IOException("Não foi possível acessar a API.");
        }

        for (int rxcui : rxcuisGrupo)
            if (contraindicacoesNomeFora.contains(rxcui))
                return false;
        return true;
    }

    /**
     * @param nome Nome do medicamento a ser consultado.
     * @return Verdadeiro, se o medicamento estiver na lista e falso,
     * caso contrário.
     */
    public boolean existeMedicamento(String nome) {
        return medicamentos.containsKey(nome);
    }
    /**
     * @param nome Nome do princípio ativo a ser consultado.
     * @return Verdadeiro, se o princípio ativo estiver na lista e
     * falso, caso contrário.
     */
    public boolean existePrincipioAtivo(String nome) {
        return principiosAtivos.containsKey(nome);
    }

    /**
     * Pega o código RxCUI de um medicamento.
     *
     * @param nome Nome do medicamento.
     * @return Código RxCUI do medicamento.
     * @throws IllegalStateException Se o medicamento não estiver
     * registrado.
     */
    private int pegaCodigoRxCUI(String nome) {
        if (!existeMedicamento(nome))
            throw new IllegalStateException("Medicamento não registrado.");
        String principioAtivo = medicamentos.get(nome).pegaPrincipioAtivo();
        return principiosAtivos.get(principioAtivo).pegaCodigoRxCUI();
    }

    /**
     * Pega as contraindicações locais de um medicamento.
     *
     * @param nome Nome do medicamento.
     * @return Nomes das contraindicações do medicamento.
     * @throws IllegalStateException Se o medicamento não estiver
     * registrado.
     */
    private String[] pegaContraIndicacoesLocais(String nome) {
        if (!existeMedicamento(nome))
            throw new IllegalStateException("Medicamento não registrado.");
        String principioAtivo = medicamentos.get(nome).pegaPrincipioAtivo();
        return principiosAtivos.get(principioAtivo).pegaContraIndicacoesLocais();
    }


    /**
     * Guarda tanto os princípios ativos quanto os medicamentos, se possível.
     */
    public void guardaDados() {
        guardaPrincipiosAtivos(enderecoPrincipiosAtivos);
        guardaMedicamentos(enderecoMedicamentos);
    }

    /**
     * @param endereco Endereço do arquivo onde os princípios ativos estão guardados.
     * @return A HashMap carregada do arquivo ou uma HashMap vazia, se não for
     * possível carregá-la.
     */
    private HashMap<String, PrincipioAtivo> carregaPrincipiosAtivos(String endereco) {
        try {
            return (HashMap<String, PrincipioAtivo>) SerializacaoUtils.desserializa(endereco);
        }
        catch (Exception e) {
            return new HashMap<>();
        }
    }

    /**
     * @param endereco Endereço do arquivo onde os medicamentos estão guardados.
     * @return A HashMap carregada do arquivo ou uma HashMap vazia, se não for
     * possível carregá-la.
     */
    private HashMap<String, Medicamento> carregaMedicamentos(String endereco) {
        try {
            return (HashMap<String, Medicamento>) SerializacaoUtils.desserializa(endereco);
        }
        catch (Exception e) {
            return new HashMap<>();
        }
    }

    /**
     * @param endereco Endereço do arquivo onde o histórico está guardado.
     * @return Uma ArrayList com o histórico, se disponível, ou uma ArrayList
     * vazia.
     */
    private ArrayList<String[]> carregaHistorico(String endereco) {
        try {
            return (ArrayList<String[]>) SerializacaoUtils.desserializa(endereco);
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }
    

    /**
     * Guarda os princípios ativos em um arquivo, se possível. Não levanta erros, caso
     * não consiga.
     * @param endereco Endereço do arquivo onde os princípios ativos serão guardados.
     */
    private void guardaPrincipiosAtivos(String endereco) {
        guardaSerializable(endereco, principiosAtivos);
    }

    /**
     * Guarda os medicamentos em um arquivo, se possível. Não levanta erros, caso
     * não consiga.
     * @param endereco Endereço do arquivo onde os princípios ativos serão guardados.
     */
    private void guardaMedicamentos(String endereco) {
        guardaSerializable(endereco, medicamentos);
    }

    /**
     * Guarda o histórico em um arquivo, se possível. Não levanta erros, caso
     * não consiga.
     * @param endereco Endereço do arquivo onde o medicamento será guardado.
     */
    private void guardaHistorico(String endereco) {
        guardaSerializable(endereco, historicoMedicamentos);
    }


    /**
     * Guarda um Serializable em um arquivo, se possível. Não levanta erros, caso
     * não consiga.
     * @param endereco Endereço do arquivo onde o Serializable serão guardados.
     */
    private void guardaSerializable(String endereco, Serializable obj) {
        File pasta = new File(ENDERECO_PASTA);
        if (!pasta.isDirectory())
            pasta.mkdirs();
        try {
            SerializacaoUtils.serializa(obj, endereco);
        }
        catch (IOException e) {

        }
    }
}