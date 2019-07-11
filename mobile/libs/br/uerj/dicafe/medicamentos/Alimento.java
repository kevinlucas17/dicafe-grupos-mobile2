package br.uerj.dicafe.medicamentos;

import java.util.HashSet;
import java.util.Arrays;
import java.io.*;
import org.json.simple.parser.*;
import org.json.simple.*;

import br.uerj.dicafe.utils.JSONFile;

public class Alimento{
    private String alimento;
    private HashSet<String> contraindicacoes;
    private String nome_arquivo="PA.json";

    public Alimento(String alimento)throws IOException,ParseException{
        this.alimento=alimento;
        this.contraindicacoes = new HashSet<String>(JSONFile.openJSON(nome_arquivo,alimento)); 
    }
    public Boolean temInteracao(String medicamento){
        return contraindicacoes.contains(medicamento);

    }
    public void adicionaContraindicacao(String medicamento){
        contraindicacoes.add(medicamento);
    }
  
}
