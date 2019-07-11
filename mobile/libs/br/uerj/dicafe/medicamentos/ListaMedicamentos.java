package br.uerj.dicafe.medicamentos;

import java.io.IOException;

/**
 * Lista de medicamentos.
 */

public interface ListaMedicamentos {

    /**
     * Adiciona um medicamento à lista.
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
        double quantidadePorPorcao, String bula);

    /**
     * Remove um medicamento da lista.
     *
     * @param nome Nome do medicamento a ser removido.
     * @throws IllegalStateException Se o medicamento não estiver na
     * lista.
     */
    public void removeMedicamento(String nome);

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
        String[] contraindicacoesLocais);

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
                                       int codigoRxCUI);

    /**
     * Remove um princípio ativo da lista.
     *
     * @param nome Nome do princípio ativo a ser removido.
     * @throws IllegalStateException Se o princípio ativo não estiver
     * na lista.
     */
    public void removePrincipioAtivo(String nome);

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
    public boolean podemJuntos(String nomeFora, String[] grupoNomes) throws IOException;

    /**
     * @param nome Nome do medicamento a ser consultado.
     * @return Verdadeiro, se o medicamento estiver na lista e falso,
     * caso contrário.
     */
    public boolean existeMedicamento(String nome);
    /**
     * @param nome Nome do princípio ativo a ser consultado.
     * @return Verdadeiro, se o princípio ativo estiver na lista e
     * falso, caso contrário.
     */
    public boolean existePrincipioAtivo(String nome);
}