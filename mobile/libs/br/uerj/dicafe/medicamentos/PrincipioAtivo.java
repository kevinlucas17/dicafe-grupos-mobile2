package br.uerj.dicafe.medicamentos;

import java.util.HashSet;
import java.util.Arrays;
import java.io.Serializable;

/**
 * Informações de um princípio ativo, incluindo seu código na API da
 * biblioteca nacional de medicina dos Estados Unidos.
 */

public class PrincipioAtivo implements Serializable {

    private String nome;
    private double limiteDiario;
    private int codigoRxCUI;
    private HashSet<String> contraindicacoesLocais;

    /**
     * Constrói uma instância de princípio ativo.
     *
     * @param nome Nome do princípio ativo.
     * @param limiteDiario O máximo que se pode tomar diariamente. Se 
     * não houver limite, é igual a infinito.
     * @param codigoRxCUI O código RxCUI do medicamento na API da
     * biblioteca nacional de medicina dos Estados Unidos.
     * @param contraindicacoesLocais Contraindicações além das da API.
     *
     * @throws IllegalArgumentException Se o limite diário ou o código
     * RxCUI não forem positivos.
     */
    public PrincipioAtivo(String nome, double limiteDiario, int codigoRxCUI,
                          String[] contraindicacoesLocais) {
        if (limiteDiario <= 0)
            throw new IllegalArgumentException(
                "Limite deve ser maior ou igual a zero.");
        if (codigoRxCUI <= 0)
            throw new IllegalArgumentException(
                "Código RxCUI deve ser um inteiro positivo.");
        this.nome = nome;
        this.limiteDiario = limiteDiario;
        this.codigoRxCUI = codigoRxCUI;
        this.contraindicacoesLocais = new HashSet<>(
            Arrays.asList(contraindicacoesLocais));
    }  

    /**
     * Contrói uma instância de princípio ativo sem contraindicações
     * locais.
     *
     * @param nome Nome do princípio ativo.
     * @param limiteDiario O máximo que se pode tomar diariamente. Se 
     * não houver limite, é igual a infinito.
     * @param codigoRxCUI O código RxCUI do medicamento na API da
     * biblioteca nacional de medicina dos Estados Unidos.
     *
     * @see #PrincipioAtivo(String, double, int, String[])
     */
    public PrincipioAtivo(String nome, double limiteDiario, int codigoRxCUI) {
        this(nome, limiteDiario, codigoRxCUI, new String[0]);
    }


    /**
     * Adiciona uma nova contraindicação local.
     * @param contraindicacao Contraindicação a ser adicionada.
     * @throws IllegalStateException Se a contraindicação já está na
     * lista.
     */
    public void adicionaContraindicacao(String contraindicacao) {
        if (contraindicacoesLocais.contains(contraindicacao))
            throw new IllegalStateException("Contraindicacao já está na lista.");
        contraindicacoesLocais.add(contraindicacao);
    }

    /**
     * remove uma contraindicação local.
     * @param contraindicacao Contraindicação a ser removida.
     * @throws IllegalStateException Se a contraindicação não está na
     * lista.
     */
    public void removeContraindicacao(String contraindicacao) {
        if (!contraindicacoesLocais.contains(contraindicacao))
            throw new IllegalStateException("Contraindicacao não está na lista.");
        contraindicacoesLocais.remove(contraindicacao);
    }


    /**
     * @return Todas as contraindicações locais.
     */
    public String[] pegaContraIndicacoesLocais() {
        return contraindicacoesLocais.toArray(new String[0]);
    }

    /**
     * @return Nome do princípio ativo.
     */
    public String pegaNome() {return nome;}
    /**
     * @return Máximo que se pode tomar do princípio ativo diariamente.
     */
    public double pegaLimiteDiario() {return limiteDiario;}
    /**
     * @return Código RxCUI do princípio ativo.
     */
    public int pegaCodigoRxCUI() {return codigoRxCUI;}

    /**
     * @return Nome, limite diário e código RxCUI.
     */
    public String toString() {
        String limiteString = (limiteDiario==Double.POSITIVE_INFINITY)
                              ? "sem limite" : Double.toString(limiteDiario);
        return (
            "Nome: "+nome+"\n"+
            "Limite diário: "+limiteString+"\n"+
            "Código RxCUI: "+codigoRxCUI
        );
    }
}