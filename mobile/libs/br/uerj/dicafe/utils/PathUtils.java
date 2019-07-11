package br.uerj.dicafe.utils;

import java.io.File;

class PathUtils {

    public static boolean eCaminhoArquivoValido(String caminhoArquivo) {
        return new File(caminhoArquivo).isFile();
    }
    public static boolean temExtensaoCerta(String caminhoArquivo, String extensaoCerta) {
        return caminhoArquivo.endsWith(extensaoCerta);
    }
}