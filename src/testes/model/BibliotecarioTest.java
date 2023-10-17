package testes.model;

import dao.DAO;

import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;

import model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BibliotecarioTest {

    private Bibliotecario bibliotecario1;

    @BeforeEach
    void setUp() {
        DAO.getBibliotecario().alteraParaPastaTeste();
        DAO.getEmprestimo().alteraParaPastaTeste();
        DAO.getReserva().alteraParaPastaTeste();
        DAO.getPrazos().alteraParaPastaTeste();
        DAO.getLeitor().alteraParaPastaTeste();
        DAO.getLivro().alteraParaPastaTeste();

        bibliotecario1 = DAO.getBibliotecario().criar(new Bibliotecario("BIBLIOTECARIO1","SENHABIB1"));
    }

    @AfterEach
    void tearDown() {
        DAO.getBibliotecario().removerTodos();
        DAO.getEmprestimo().removerTodos();
        DAO.getReserva().removerTodos();
        DAO.getPrazos().removerTodos();
        DAO.getLeitor().removerTodos();
        DAO.getLivro().removerTodos();

        DAO.getBibliotecario().alteraParaPastaPrincipal();
        DAO.getEmprestimo().alteraParaPastaPrincipal();
        DAO.getReserva().alteraParaPastaPrincipal();
        DAO.getPrazos().alteraParaPastaPrincipal();
        DAO.getLeitor().alteraParaPastaPrincipal();
        DAO.getLivro().alteraParaPastaPrincipal();
    }

    @Test
    void registrarLivro() {
        //não existe nenhum livro registrado
        assertTrue(DAO.getLivro().encontrarTodos().isEmpty());

        Livro livro1 = new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1");
        DAO.getLivro().criar(livro1);
        DAO.getLivro().encontrarTodos().get(0).setISBN(10);

        //livro registrado corretamente
        assertEquals(1,DAO.getLivro().encontrarTodos().size());
        assertEquals(10,DAO.getLivro().encontrarTodos().get(0).getISBN());
    }
}