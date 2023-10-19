package testes.model;

import dao.DAO;

import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroNaoPossuiEmprestimo;
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

        Livro livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1"));

        //livro registrado corretamente
        assertEquals(1,DAO.getLivro().encontrarTodos().size());
        assertEquals(livro1.getISBN(),DAO.getLivro().encontrarTodos().get(0).getISBN());
    }

    @Test
    void fazerEmprestimo() throws ObjetoInvalido, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimo, LivroReservado{
        //não existe leitor
        assertThrows(ObjetoInvalido.class, ()-> Bibliotecario.fazerEmprestimo(33,33));

        Leitor leitor1 = new Leitor("LEITOR1", "RUA ALAMEDA","75999128840","SENHA1");
        DAO.getLeitor().criar(leitor1);

        Livro livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1"));

        //leitor ta bloqueado
        leitor1.setBloqueado(true);
        DAO.getLeitor().atualizar(leitor1);
        assertThrows(LeitorBloqueado.class, ()-> Bibliotecario.fazerEmprestimo(leitor1.getID(),livro1.getISBN()));
        leitor1.setBloqueado(false);
        DAO.getLeitor().atualizar(leitor1);

        //leitor ta multado
        leitor1.setDataMulta(LocalDate.now());
        DAO.getLeitor().atualizar(leitor1);
        assertThrows(LeitorMultado.class, ()-> Bibliotecario.fazerEmprestimo(leitor1.getID(),livro1.getISBN()));
        leitor1.setDataMulta(null);
        DAO.getLeitor().atualizar(leitor1);

        Leitor leitor2 = DAO.getLeitor().criar(new Leitor("LEITOR2", "RUA ALAMEDA2","74999128840","SENHA2"));

        //leitor ja possui um emprestimo ativo
        Livro livro2 = DAO.getLivro().criar(new Livro("LIVRO2","AUTOR2","EDITORA2",2000,"CATEGORIA2"));
        Emprestimo emp2 = new Emprestimo(livro2.getISBN(),leitor1.getID());
        DAO.getEmprestimo().criar(emp2);
        assertThrows(LeitorTemEmprestimo.class, ()-> Bibliotecario.fazerEmprestimo(leitor1.getID(),livro1.getISBN()));
        DAO.getEmprestimo().removerTodos();

        //livro possui uma reserva ativa e o top 1 da reserva não é o leitor que tá tentando realizar o emprestimo
        Reserva reserva1 = new Reserva(livro1.getISBN(),leitor2.getID());
        DAO.getReserva().criar(reserva1);
        assertThrows(LivroReservado.class, ()-> Bibliotecario.fazerEmprestimo(leitor1.getID(),livro1.getISBN()));
        DAO.getReserva().removerTodos();

        //livro possui uma reserva ativa e o top 1 da reserva é o leitor que tá tentando realizar o emprestimo
        Reserva reserva2 = new Reserva(livro1.getISBN(),leitor1.getID());
        DAO.getReserva().criar(reserva2);
        Prazos prazo1 = new Prazos(leitor1.getID(),livro1.getISBN());
        DAO.getPrazos().criar(prazo1);
        assertEquals(1, DAO.getReserva().encontrarTodos().size());
        assertEquals(1, DAO.getPrazos().encontrarTodos().size());

        //tudo correto, leitor possui reserva ativa do livro que ta tentando fazer o empréstimo
        Bibliotecario.fazerEmprestimo(leitor1.getID(),livro1.getISBN());

        assertTrue(DAO.getReserva().encontrarTodos().isEmpty());
        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());

        assertEquals(leitor1, DAO.getLeitor().encontrarPorId(DAO.getEmprestimo().encontrarTodos().get(0).getIDleitor()));
    }

    @Test
    void devolverLivro() throws LivroNaoPossuiEmprestimo {
        Livro livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1"));

        //leitor nao possui livro a devolver
        assertThrows(LivroNaoPossuiEmprestimo.class, ()-> Bibliotecario.devolverLivro(livro1.getISBN()));

        //leitor atrasou a devolução do livro
        Leitor leitor1 = new Leitor("LEITOR1", "RUA ALAMEDA","75999128840","SENHA1");
        DAO.getLeitor().criar(leitor1);
        Emprestimo emp1 = new Emprestimo(livro1.getISBN(),leitor1.getID());
        DAO.getEmprestimo().criar(emp1);
        emp1.setDataPrevista(LocalDate.now().minusDays(1));

        //livro devolvido tem reserva
        Leitor leitor2 = new Leitor("LEITOR2", "RUA ALAMEDA2","74999128840","SENHA2");
        DAO.getLeitor().criar(leitor2);
        Reserva reserva1 = new Reserva(livro1.getISBN(),leitor2.getID());
        DAO.getReserva().criar(reserva1);

        assertNull(leitor1.getDataMulta());
        assertFalse(DAO.getLivro().encontrarTodos().get(0).getDisponivel());
        assertEquals(1,DAO.getLivro().encontrarTodos().get(0).getQtdEmprestimo());
        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());
        assertEquals(1, DAO.getEmprestimo().encontrarTodos().size());

        //tudo correto, leitor atrasou a devolução do livro
        Bibliotecario.devolverLivro(livro1.getISBN());

        assertEquals(LocalDate.now().plusDays(2),leitor1.getDataMulta());
        assertTrue(DAO.getLivro().encontrarTodos().get(0).getDisponivel());
        assertEquals(0,DAO.getLeitor().encontrarTodos().get(0).getLimiteRenova());
        assertEquals(1,DAO.getPrazos().encontrarTodos().size());
        assertTrue(DAO.getEmprestimo().encontrarTodosAtuais().isEmpty());
        assertEquals(1, DAO.getEmprestimo().encontrarTodos().size());
    }
}