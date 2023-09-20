package dao.bibliotecario;

import dao.CRUD;

import model.Bibliotecario;

import java.util.List;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Bibliotecario. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface BibliotecarioDAO extends CRUD<Bibliotecario> {

    /**
     * Método que encontra objetos Bibliotecario por meio do nome.
     *
     * @param nome o nome sobre os quais os bibliotecarios devem ser encontrados
     * @return retorna lista de bibliotecarios encontrados
     */

    public List<Bibliotecario> encontrarPorNome(String nome);
}