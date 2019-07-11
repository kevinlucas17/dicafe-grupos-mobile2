package br.uerj.dicafe.utils;

import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * Utilitários para JSON.
 */

public class JSONUtils {


    /**
     * @param endereco Endereço do JSON na rede.
     * @return O JSON desejado.
     *
     * @throws IOException Se não for possível abrir URL ou um stream
     * a partir dele.
     *
     * Referência:
     *  http://theoryapp.com/parse-json-in-java/
     */
    public static JSONObject pegaJSON(String endereco) throws IOException {
        // build a URL
        //endereco = URLEncoder.encode(endereco, "UTF-8");
        URL url;
        try {
            url = new URL(endereco);
        }
        catch (MalformedURLException e) {
            System.out.println(endereco);
            throw new IOException("Protocolo indefinido ou não fornecido.");
        }
        // read from the URL
        Scanner scan;
        try {
            scan = new Scanner(url.openStream());
        }
        catch (IOException e) {
            throw new IOException("Não foi possível criar stream.");
        }
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();
     
        // build a JSON object
        JSONObject obj = new JSONObject(str);

        return obj;
    }

    /**
     * @param valores Mapa de chaves e valores que serão adicionados ao
     * JSON.
     * @param <V> Classe dos valores do HashMap.
     *
     * @return Um JSON com os valores inseridos.
     * @throws JSONException Se uma das chaves for 'null' ou um dos 
     * valores for um número inválido.
     */
    public static <V> JSONObject criaJSON(HashMap<String, V> valores) {
        JSONObject jo = new JSONObject();
        for (Map.Entry<String, V> par : valores.entrySet())
            jo.put(par.getKey(), par.getValue());
        return jo;
    }


    /**
     * Envia JSON para um servidor.
     * @param jsonParaEnviar O arquivo JSON que se deseja enviar.
     * @param enderecoServidor Endereço do servidor que receberá o JSON.
     */
    public static void enviaJSON(JSONObject jsonParaEnviar, String enderecoServidor) {
        // TODO
    }

    /**
     * Envia HashMap para um servidor como JSON.
     * @param hashMapParaEnviar O arquivo JSON que se deseja enviar.
     * @param enderecoServidor Endereço do servidor que receberá o JSON.
     * @param <V> Classe dos valores do HashMap.
     */
    public static <V> void enviaJSON(HashMap<String, V> hashMapParaEnviar,
                                     String enderecoServidor) {
        JSONObject jsonParaEnviar = criaJSON(hashMapParaEnviar);
        enviaJSON(jsonParaEnviar, enderecoServidor);
    }




}