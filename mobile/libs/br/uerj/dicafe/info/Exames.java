package br.uerj.dicafe.info;

import java.util.ArrayList;
import java.util.HashSet;
import org.json.JSONObject;
import java.io.IOException;
import java.io.File;
import br.uerj.dicafe.utils.JSONUtils;
import br.uerj.dicafe.utils.SerializacaoUtils;

public class Exames {

    private static final String ENDERECO_PASTA = "bd-local/";
    private static final String ENDERECO_EXAMES = ENDERECO_PASTA+"exames.ser";

    private String enderecoAPI;
    private String enderecoExames;
    private ArrayList<String[]> exames;

    /**
     * Registra endereço da API.
     * @param enderecoAPI Endereço da tabela do banco de dados onde os
     * exames serão guardados e pegos.
     * @param enderecoExames Endereço do arquivo local.
     */
    public Exames(String enderecoAPI, String enderecoExames) {
        this.enderecoAPI = enderecoAPI;
        this.enderecoExames = enderecoExames;
        this.exames = carregaExames(enderecoExames);
    }

    public Exames(String enderecoAPI) {
        this(enderecoAPI, ENDERECO_EXAMES);
    }

    public Exames() {
        this("");
    }

    /**
     * @param nome Nome do exame.
     * @param data Data de quando o exame aconteceu.
     * @param info Informação relevante ao exame.
     */    
    public void enviaExame(String nome, String data, String info) {
        /*HashMap<String, String> hashMapParaEnviar = new HashMap<>();
        hashMapParaEnviar.put("nome", nome);
        hashMapParaEnviar.put("data", data);
        hashMapParaEnviar.put("info", info);
        JSONUtils.enviaJSON(hashMapParaEnviar, enderecoAPI);*/

        exames.add(new String[] {nome, data, info});
        guardaExames(enderecoExames);
    }

    /**
     * @return Todos os exames do histórico.
     */
    public String[][] pegaExames() {
        // Lê dados da API.
        String[][] retorno = new String[exames.size()][3];
        int c = 0;
        for (String[] valores : exames) {
            for (int i = 0; i < 3; i++)
                retorno[c][i] = valores[i];
            c++;
        }
        return retorno;
    }

    /**
     * @return Os nomes de todos os exames disponíveis.
     */
    public String[] pegaExamesNomes() {
        // Lê dados da API.
        return new String[] {"Exame de sangue", "Exame de fundo de olho"};
    }



    /**
     * @param endereco Endereço do arquivo onde os exames estão guardados.
     * @return A ArrayList carregada do arquivo ou uma ArrayList vazia, se não for
     * possível carregá-la.
     */
    private ArrayList<String[]> carregaExames(String endereco) {
        try {
            return (ArrayList<String[]>) SerializacaoUtils.desserializa(endereco);
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Guarda os exames em um arquivo, se possível. Não levanta erros, caso
     * não consiga.
     * @param endereco Endereço do arquivo onde os exames serão guardados.
     */
    private void guardaExames(String endereco) {
        File pasta = new File(ENDERECO_PASTA);
        if (!pasta.isDirectory())
            pasta.mkdirs();
        try {
            SerializacaoUtils.serializa(exames, endereco);
        }
        catch (IOException e) {

        }
    }
}
