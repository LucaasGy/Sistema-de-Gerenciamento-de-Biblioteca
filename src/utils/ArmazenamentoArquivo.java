package utils;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela manipulação de todos os dados do sistema
 * armazenados em arquivos binários, mantendo a persistência das informações
 * e evitando a perda de dados.
 *
 * @author Lucas Gabriel.
 */

public class ArmazenamentoArquivo {

    /**
     * Método responsável por guardar as informações do sistema em um arquivo binário.
     *
     * @param listaT lista de determinados tipos de objetos
     * @param nomeArquivo nome do arquivo a ser guardado
     * @param <T> tipo do objeto a ser guardado
     */

    public static <T> void guardar(List<T> listaT, String nomeArquivo){
        try {
            FileOutputStream novoArquivo = new FileOutputStream(nomeArquivo);
            ObjectOutputStream novoObjeto = new ObjectOutputStream(novoArquivo);

            novoObjeto.writeObject(listaT);

            novoObjeto.close();
            novoArquivo.close();
        }
        catch ( java.io.FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado");
        }
        catch ( java.io.IOException e) {
            throw new RuntimeException("Erro ao acessar dados");
        }
    }

    /**
     * Método responsável por resgatar as informações do sistema em um arquivo binário.
     *
     * @param nomeArquivo nome do arquivo a ser resgatado
     * @param <T> tipo do objeto a ser resgatado
     * @return retorna lista de objetos guardados no arquivo
     */

    public static <T> ArrayList<T> resgatar(String nomeArquivo){
        try {
            File arquivo = new File(nomeArquivo);

            if (!arquivo.exists()) {
                return new ArrayList<T>();
            }

            FileInputStream novoArquivo = new FileInputStream(arquivo);
            ObjectInputStream novoObjeto = new ObjectInputStream(novoArquivo);

            ArrayList<T> lista = (ArrayList<T>) novoObjeto.readObject();

            novoArquivo.close();
            novoObjeto.close();

            return lista;
        }
        catch ( java.io.IOException e) {
            return new ArrayList<T>();
        }
        catch ( java.lang.ClassNotFoundException e) {
            throw new RuntimeException("Classe não encontrada");
        }
    }
}
