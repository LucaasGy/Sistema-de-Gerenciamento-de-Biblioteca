package model;

import dao.DAO;

import erros.objetos.ObjetoInvalido;

import java.util.List;

/**
 * Classe Convidado que representa um Convidade do sistema.
 * Como Convidado não precisa de nome e senha, ele não pode herdar
 * a Superclasse Usuário.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class Convidado {
    private TipoUsuario tipoUsuario;

    /**
     * Construtor de um Convidado do sistema.
     * O tipo de usuário é definido como Convidado diretamente.
     */

    public Convidado() {
        this.tipoUsuario = TipoUsuario.Convidado;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
