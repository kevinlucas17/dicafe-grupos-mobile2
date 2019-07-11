package com.app.speechtotext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessadorResultado{
    private static final List<String> excecoes = Arrays.asList("a","da","de","do");

    private List<String> resultadosAProcurar;
    private List<String> alimentosEncontrados;
    private List<String> catalogoAlimentos;


    public ProcessadorResultado(List<String> resultadosAProcurar, List<String> catalogoAlimentos){
        this.resultadosAProcurar = resultadosAProcurar;
        this.catalogoAlimentos = catalogoAlimentos;
        this.alimentosEncontrados = new ArrayList<>();
    }

    public Double obterQuantidadeDoAlimento(){
        String padraoQuantidade = "(\\d+)([.,]\\d+)?(\\s)?";
        Pattern pattern = Pattern.compile(padraoQuantidade);

        for(String resultado : resultadosAProcurar) {
            resultado = resultado.toLowerCase();

            Matcher matcher = pattern.matcher(resultado);

            if (matcher.find()) {
                try {
                    String quantidade = (matcher.group(1)+matcher.group(2))
                            .replace("null", "")
                            .replace(",",".");

                    return Double.parseDouble(quantidade);
                }
                catch (NumberFormatException e) {}
            }
        }
        return null;
    }

    public String obterUnidadeDeMedida(){
        String padraoUnidade = "(\\d+)([.,]\\d+)?(\\s)?(g|kg)";
        Pattern pattern = Pattern.compile(padraoUnidade);

        for(String resultado : resultadosAProcurar){
            Matcher matcher = pattern.matcher(resultado);
            if(matcher.find()) {
                String unidade = matcher.group(4).trim().toLowerCase();

                if(unidade.equals("kg")){ return "kg"; }
                if(unidade.equals("g")){ return "g"; }
            }
        }
        return null;
    }

    public List<String> obterDescricaoAlimento(){
        String padrao = "(\\d+)([.,]\\d+)?(\\s)?(g|kg|grama|grama)?(\\s)(d[aeo])";
        Pattern pattern = Pattern.compile(padrao);

        if(null != catalogoAlimentos) {
            for (String resultado : resultadosAProcurar) {
                Matcher matcher = pattern.matcher(resultado);
                List<String> resultadoBusca;
                if (matcher.find()) {
                    resultadoBusca = procurarNaLista(resultado.substring(matcher.end(6)).trim());
                    for(String alimento : resultadoBusca){
                        if(!alimentosEncontrados.contains(alimento)){
                            alimentosEncontrados.add(alimento);
                        }
                    }
                    if(resultadoBusca.size() > 0) break;
                }

            }
        }
        return alimentosEncontrados;
    }

    private List<String> procurarNaLista(final String alimentoFalado){
        List<String> copiaCatalogo = new ArrayList<>();
        copiaCatalogo.addAll(catalogoAlimentos);

        String[] palavrasDoResultado = alimentoFalado.split("\\s+");

        for(String palavra : palavrasDoResultado) {
            List<String> alimentosQueContemPalavra = new ArrayList<>();
            palavra = palavra.toLowerCase();

            for(String alimento : copiaCatalogo){
                alimento = alimento.toLowerCase();

                if(alimento.contains(palavra) && !excecoes.contains(palavra)){
                    alimentosQueContemPalavra.add(alimento);
                }
            }
            if(alimentosQueContemPalavra.size() > 0){
                copiaCatalogo = alimentosQueContemPalavra;
            }
        }
        return (copiaCatalogo.size() == catalogoAlimentos.size()) ?
                new ArrayList<String>() :
                copiaCatalogo;
    }
}
