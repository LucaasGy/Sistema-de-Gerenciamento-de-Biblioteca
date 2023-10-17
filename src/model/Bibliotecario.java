package model;

import dao.DAO;
import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;

/**
 * Subclasse Bibliotecario que extende a Superclasse Usuário.
 *
 * Representa um Bibliotecario do sistema, podendo registrar novos dos livros.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class Bibliotecario extends Usuario {

    /**
     * Construtor de um Bibliotecario do sistema.
     *
     * Recebe como parâmetro a maioria dos atributos da classe para inseri-las diretamente.
     * A inserção é feita chamando o construtor da Superclasse.
     * O tipo de usuário é definido a depender do usuário em questão.
     *
     * @param nome nome do Bibliotecario
     * @param senha senha do Bibliotecario
     */

    public Bibliotecario(String nome, String senha){
        super(nome, senha, TipoUsuario.Bibliotecario);
    }

    /**
     * Método que cria um novo Livro e o adiciona no acervo da biblioteca
     *
     * @param livro objeto livro contendo todas as informações pré definidas
     */

    public void registrarLivro(Livro livro){
        DAO.getLivro().criar(livro);
    }
}
