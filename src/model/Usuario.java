package model;

import java.io.Serializable;

/**
 * Superclasse Usuário, que representa possíveis utilizadores do sistema.
 * Contém um id, nome, senha e o tipo de usuário que irá herdar a classe.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public abstract class Usuario implements Serializable {
    private int ID;
    private String nome;
    private String senha;
    private TipoUsuario tipoUsuario;

    /**
     * Construtor de um Usuário do sistema.
     *
     * Contém os parâmetros que tanto ADM, Bibliotecario e Leitor devem ter.
     *
     * @param nome nome do Usuário
     * @param senha senha do Usuário
     * @param tipoUsuario tipo do Usuário ( ADM, Bibliotecario ou Leitor )
     */

    public Usuario(String nome, String senha, TipoUsuario tipoUsuario) {
        this.nome = nome.toLowerCase();
        this.senha = senha.toLowerCase();
        this.tipoUsuario = tipoUsuario;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
