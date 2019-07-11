package br.uerj.dicafe.info;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import org.json.JSONObject;

import br.uerj.dicafe.utils.JSONUtils;
import br.uerj.dicafe.utils.SerializacaoUtils;

public class Intercorrencias {

    private static final String ENDERECO_PASTA = "bd-local/";
    private static final String ENDERECO_INTERCORRENCIAS = ENDERECO_PASTA+"intercorrencias.ser";

    private String enderecoAPI;
    private String enderecoIntercorrencias;
    private ArrayList<String[]> intercorrencias;

    /**
     * Registra endereço da API.
     * @param enderecoAPI Endereço da tabela do banco de dados onde as
     * intercorrências serão guardadas e pegas.
     * @param enderecoIntercorrencias Endereço do arquivo local.
     */
    public Intercorrencias(String enderecoAPI, String enderecoIntercorrencias) {
        this.enderecoAPI = enderecoAPI;
        this.enderecoIntercorrencias = enderecoIntercorrencias;
        intercorrencias = carregaIntercorrencias(enderecoIntercorrencias);
    }

    public Intercorrencias(String enderecoAPI) {
        this(enderecoAPI, ENDERECO_INTERCORRENCIAS);
    }

    public Intercorrencias() {
        this("");
    }
    
    /**
     * @param nome Nome da intercorrência.
     * @param data Data de quando a intercorrência aconteceu.
     * @param info Informação relevante à intercorrência.
     */    
    public void enviaIntercorrencia(String nome, String data, String info) {
        /*HashMap<String, String> hashMapParaEnviar = new HashMap<>();
        hashMapParaEnviar.put("nome", nome);
        hashMapParaEnviar.put("data", data);
        hashMapParaEnviar.put("info", info);
        JSONUtils.enviaJSON(hashMapParaEnviar, enderecoAPI);*/

        intercorrencias.add(new String[] {nome, data, info});
        guardaIntercorrencias(enderecoIntercorrencias);
    }

    /**
     * @return Uma matriz bi-dimensional com cada linha contendo
     * {nome, data, info}, nesta ordem.
     */
    public String[][] pegaIntercorrencias() {
        // Lê dados da API.
        String[][] retorno = new String[intercorrencias.size()][3];
        int c = 0;
        for (String[] valores : intercorrencias) {
            for (int i = 0; i < 3; i++)
                retorno[c][i] = valores[i];
            c++;
        }
        return retorno;
    }


    /**
     * @param endereco Endereço do arquivo onde as intercorrências estão guardadas.
     * @return A ArrayList carregada do arquivo ou uma ArrayList vazia, se não for
     * possível carregá-la.
     */
    private ArrayList<String[]> carregaIntercorrencias(String endereco) {
        try {
            return (ArrayList<String[]>) SerializacaoUtils.desserializa(endereco);
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Guarda as intercorrências em um arquivo, se possível. Não levanta erros, caso
     * não consiga.
     * @param endereco Endereço do arquivo onde as intercorrências serão guardadas.
     */
    private void guardaIntercorrencias(String endereco) {
        File pasta = new File(ENDERECO_PASTA);
        if (!pasta.isDirectory())
            pasta.mkdirs();
        try {
            SerializacaoUtils.serializa(intercorrencias, endereco);
        }
        catch (IOException e) {

        }
    }
}