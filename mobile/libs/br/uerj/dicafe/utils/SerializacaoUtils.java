package br.uerj.dicafe.utils;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Classe com utilitários relacionados à serialização de objetos.
 *
 * <p>Referência: https://www.tutorialspoint.com/java/java_serialization.htm</p>
 */

public class SerializacaoUtils {

    /**
     * Serializa um objeto.
     *
     * @param obj Objeto a serializar.
     * @param arquivoCaminho Caminho onde o objeto serializado será guardado.
     *
     * @throws IOException Se ocorrer algum erro de serialização.
     */
    public static void serializa(Serializable obj, String arquivoCaminho) 
        throws IOException {

        try {
            FileOutputStream fileOut = new FileOutputStream(arquivoCaminho);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            throw new IOException("Não foi possível serializar o arquivo.");
        }
    }

    /**
     * Desserializa um objeto.
     *
     * @param arquivoCaminho Caminho onde o objeto serializado está guardado.
     *
     * @return O objeto desserializado.
     *
     * @throws IOException Se ocorrer algum erro de desserialização.
     * @throws ClassNotFoundException Se a classe do arquivo serializado for 
     * desconhecida.
     */
    public static Object desserializa(String arquivoCaminho) 
        throws IOException, ClassNotFoundException {

        Object objetoRetorno;
        try {
            FileInputStream fileIn = new FileInputStream(arquivoCaminho);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            objetoRetorno = in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException i) {
            throw new IOException("Não foi possível ler arquivo serializado.");
        }
        catch (ClassNotFoundException c) {
            throw new ClassNotFoundException("Classe do arquivo serializado desconhecida.");
        }

        return objetoRetorno;
    }
}