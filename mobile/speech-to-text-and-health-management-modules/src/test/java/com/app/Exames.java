package br.uerj.dicafe.info;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import br.uerj.dicafe.utils.JSONUtils;

public class Exames {

    private String enderecoAPI;

    /**
     * Registra endereço da API.
     * @param enderecoAPI Endereço da tabela do banco de dados onde os
     * exames serão guardados e pegos.
     */
    public Exames(String enderecoAPI) {
        this.enderecoAPI = enderecoAPI;
    }

    /**
     * @param nome Nome do exame.
     * @param data Data de quando o exame aconteceu.
     * @param info Informação relevante ao exame.
     */    
    public void adicionaExames(String nome, String data, String info) {
        HashMap<String, String> hashMapParaEnviar = new HashMap<>();
        hashMapParaEnviar.put("nome", nome);
        hashMapParaEnviar.put("data", data);
        hashMapParaEnviar.put("info", info);
        JSONUtils.enviaJSON(hashMapParaEnviar, enderecoAPI);
    }

    /**
     * @return Todos os exames do histórico.
     */
    public String[][] pegaExames() {
        // Lê dados da API.
        String[][] ret = {
        	{"Exame de sangue", "2019-07-02", "Exame de sangue apresentou resultados normais."},
        	{"Exame de urina", "2019-07-02", "Exame de urina apresentou uréia acima da média."},
        	{"Exame de fundo de olho.", "2019-05-04", "Exame apresentou resultados normais."}
        };
        return ret;
    }

    /**
     * @return Os nomes de todos os exames disponíveis.
     */
    public String[] pegaExamesNomes() {
        // Lê dados da API.
        String[] ret = {"Exame de sangue", "Exame de fundo de olho", "Exame de urina", "Exame de sensibilidade."};
        return ret;
    }
}
