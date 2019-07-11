package br.uerj.dicafe.medicamentos;

import java.util.ArrayList;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import br.uerj.dicafe.utils.JSONUtils;

/**
 * Métodos de comparação de medicamentos através da API da Biblioteca
 * Nacional de Medicina dos Estados Unidos.
 *
 * API:
 *  https://rxnav.nlm.nih.gov/InteractionAPIs.html
 */

public class MedicamentosAPI {

    /**
     * Pega os códigos RxCUI das contraindicações do código passado
     * pela API.
     *
     * @param codigoRxCUI Código do medicamento.
     * @return Os códigos RxCUI de todas as contraindicações.
     * @throws IOException Se não for possível pegar o JSON da API.
     */
    public static Integer[] pegaContraindicacoes(int codigoRxCUI) throws IOException {

        String endereco =
            "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui="
            +codigoRxCUI+"&sources=ONCHigh";

        JSONObject objJSON;
        try {
            objJSON = JSONUtils.pegaJSON(endereco);
        }
        catch (IOException e) {
            throw e;
        }

        // Pega os subitens onde se encontram as contraindicações.
        JSONArray interacoesJSON;
        try {
            interacoesJSON = (JSONArray) objJSON.get("interactionTypeGroup");
            interacoesJSON = interacoesJSON
                .getJSONObject(0)
                .getJSONArray("interactionType")
                .getJSONObject(0)
                .getJSONArray("interactionPair");
        }
        catch (JSONException e) {
            // Não há contraindicações.
            return new Integer[0];
        }

        ArrayList<Integer> codigosRxCUI = new ArrayList<>();

        // Pega as contraindicações dos subitens.
        for (int i = 0, n = interacoesJSON.length(); i < n; i++) {
            try {
                String codigo = interacoesJSON
                    .getJSONObject(i)
                    .getJSONArray("interactionConcept")
                    .getJSONObject(1)
                    .getJSONObject("minConceptItem")
                    .getString("rxcui");
                codigosRxCUI.add(Integer.valueOf(codigo));
            }
            catch (JSONException|NumberFormatException e) {
                // Não foi possível extrair RxCUI.
                continue;
            }
        }

        // Passa ArrayList para int[].
        return codigosRxCUI.toArray(new Integer[0]);
    }
}