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
     * @param nomePasta nome da pasta onde arquivo vai ser guardado
     * @param nomeArquivo nome do arquivo a ser guardado
     * @param <T> tipo do objeto a ser guardado
     */

    public static <T> void guardar(List<T> listaT, String nomeArquivo, String nomePasta){
        File pasta = new File("dadosArquivo/" + nomePasta);
        pasta.mkdirs();

        File arquivo = new File(pasta.getAbsolutePath(), nomeArquivo);

        try (ObjectOutputStream novoObjeto = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            novoObjeto.writeObject(listaT);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método responsável por resgatar as informações do sistema em um arquivo binário.
     *
     * @param nomeArquivo nome do arquivo a ser resgatado
     * @param nomePasta nome da pasta onde arquivo esta armazenado
     * @param <T> tipo do objeto a ser resgatado
     * @return retorna lista de objetos guardados no arquivo
     */

    public static <T> ArrayList<T> resgatar(String nomeArquivo, String nomePasta){
        File pasta = new File("dadosArquivo/" + nomePasta);
        File arquivo = new File(pasta.getAbsolutePath(), nomeArquivo);

        if (!arquivo.exists())
            return new ArrayList<T>();

        try(ObjectInputStream novoObjeto = new ObjectInputStream(new FileInputStream(arquivo))) {
            List<T> lista = (ArrayList<T>) novoObjeto.readObject();
            return (ArrayList<T>) lista;
        }
        catch (IOException e) {
            return new ArrayList<T>();
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Classe não encontrada");
        }
    }
}
