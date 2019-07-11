package br.uerj.dicafe.utils;

import java.util.HashSet;
import java.io.*;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONFile{

public static HashSet<String> openJSON(String nome_arquivo,String alimento)throws IOException, ParseException{
    HashSet<String> meds= new HashSet<String>();
    JSONParser parser=new JSONParser();
    FileReader jsonfile;
    try{
        jsonfile= new FileReader(nome_arquivo);
    }catch(FileNotFoundException e ){ return meds;}
    Object obj=parser.parse(jsonfile);
    JSONObject novo=(JSONObject)obj;
    JSONObject food=(JSONObject) novo.get(alimento);
    try{
    JSONArray drugs=(JSONArray) food.get("Principio_Ativo");
    Iterator<String> iterator=drugs.iterator();
    while(iterator.hasNext()){
        meds.add(iterator.next());
        }
    }catch(NullPointerException e){ return meds;}

    return meds;
    }
}
