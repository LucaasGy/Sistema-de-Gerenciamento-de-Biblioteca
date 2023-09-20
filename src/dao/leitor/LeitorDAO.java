package dao.leitor;

import dao.CRUD;

import model.Leitor;

import java.util.List;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Leitor. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface LeitorDAO extends CRUD<Leitor> {

    /**
     * Método que encontra objetos Leitor por meio do nome.
     *
     * @param nome o nome sobre os quais os leitores devem ser encontrados
     * @return retorna lista de leitores encontrados
     */

    public List<Leitor> encontrarPorNome(String nome);

    /**
     * Método que encontra objetos Leitor por meio do telefone.
     *
     * @param telefone o telefone sobre os quais os leitores devem ser encontrados
     * @return retorna lista de leitores encontrados
     */

    public List<Leitor> encontrarPorTelefone(String telefone);
}
