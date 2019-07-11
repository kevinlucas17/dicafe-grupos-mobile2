package br.uerj.dicafe.medicamentos;

import java.util.Arrays;
import java.io.Serializable;

/**
 * Informações de um medicamento.
 */

public class Medicamento implements Serializable {

    private String nome;
    private String principioAtivo;
    private String marca;
    private double quantidadePorPorcao;
    private String bula;

    /**
     * Constrói uma instância com o nome, o princípio ativo e a bula do
     * medicamento, o limite do quanto se pode tomá-lo e suas
     * contraindicações.
     *
     * @param nome Nome do medicamento.
     * @param principioAtivo Princípio ativo do medicamento.
     * @param marca Marca do medicamento.
     * @param quantidadePorPorcao Quantidade, em mg, do princípio ativo
     * por porção (ex.: comprimido, injeção, etc).
     * @param bula Bula do medicamento.
     *
     * @throws IllegalArgumentException Se a quantidade não for finita
     * e positiva.
     */
    public Medicamento(String nome, String principioAtivo, String marca,
                       double quantidadePorPorcao, String bula) {
        if (quantidadePorPorcao <= 0 ||
            quantidadePorPorcao == Double.POSITIVE_INFINITY)
            //
            throw new IllegalArgumentException(
                "Quantidade por porção deve ser um número real finito"+
                " maior do que zero.");

        this.nome = nome;
        this.principioAtivo = principioAtivo;
        this.marca = marca;
        this.quantidadePorPorcao = quantidadePorPorcao;
        this.bula = bula;
    }

    /**
     * @return Nome do medicamento.
     */
    public String pegaNome() {
        return nome;
    }
    /**
     * @return Princípio ativo.
     */
    public String pegaPrincipioAtivo() {
        return principioAtivo;
    }
    /**
     * @return Marca do medicamento.
     */
    public String pegaMarca() {
        return marca;
    }
    /**
     * @return Quantidade por porção.
     */
    public double pegaQuantidadePorPorcao() {
        return quantidadePorPorcao;
    }
    /**
     * @return Bula do medicamento.
     */
    public String pegaBula() {
        return bula;
    }

    /**
     * @return Os dados do medicamento.
     */
    public String toString() {
        return (
            "Nome: "+nome+"\n"+
            "Princípio ativo: "+principioAtivo+"\n"+
            "Marca: "+marca+"\n"+
            "Quantidade por porção: "+quantidadePorPorcao+"\n"+
            "Bula: "+bula+"\n"
        );
    }
}