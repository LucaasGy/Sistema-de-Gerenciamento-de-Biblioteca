package dao.adm;

import dao.CRUD;

import model.Adm;

import java.util.List;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Administrador. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface AdmDAO extends CRUD<Adm> {

    /**
     * Método que encontra objetos Administrador por meio do nome.
     *
     * @param nome o nome sobre os quais os Administradores devem ser encontrados
     * @return retorna lista de Administradores encontrados
     */
    public List<Adm> encontrarPorNome(String nome);
}
