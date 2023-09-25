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

    @Test
    void fazerEmprestimo() throws ObjetoInvalido, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimo, LivroEmprestado, LivroReservado, LivroNaoDisponivel {
        //não existe leitor
        assertThrows(ObjetoInvalido.class, ()-> this.bibliotecario1.fazerEmprestimo(33,33));

        Leitor leitor1 = new Leitor("LEITOR1", "RUA ALAMEDA","75999128840","SENHA1");
        DAO.getLeitor().criar(leitor1);

        //não existe livro
        assertThrows(ObjetoInvalido.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,33));

        Livro livro1 = new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1");
        DAO.getLivro().criar(livro1);
        DAO.getLivro().encontrarTodos().get(0).setISBN(10);

        //leitor ta bloqueado
        leitor1.setBloqueado(true);
        assertThrows(LeitorBloqueado.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,10));
        leitor1.setBloqueado(false);

        //leitor ta multado
        leitor1.setDataMulta(LocalDate.now());
        assertThrows(LeitorMultado.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,10));
        leitor1.setDataMulta(null);

        //livro nao ta disponivel porque está emprestado a outro leitor
        livro1.setDisponivel(false);
        Leitor leitor2 = DAO.getLeitor().criar(new Leitor("LEITOR2", "RUA ALAMEDA2","74999128840","SENHA2"));
        Emprestimo emp1 = new Emprestimo(livro1.getISBN(),leitor2.getID());
        DAO.getEmprestimo().criar(emp1);
        assertThrows(LivroEmprestado.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,10));

        //livro nao ta disponivel porque o adm alterou sua disponibilidade (livro em reparo ou algo do tipo)
        DAO.getEmprestimo().removerTodos();
        livro1.setDisponivel(false);
        assertThrows(LivroNaoDisponivel.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,10));
        livro1.setDisponivel(true);

        //leitor ja possui um emprestimo ativo
        Livro livro2 = new Livro("LIVRO2","AUTOR2","EDITORA2",2000,"CATEGORIA2");
        DAO.getLivro().criar(livro2);
        DAO.getLivro().encontrarTodos().get(1).setISBN(20);
        Emprestimo emp2 = new Emprestimo(livro2.getISBN(),leitor1.getID());
        DAO.getEmprestimo().criar(emp2);
        assertThrows(LeitorTemEmprestimo.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,10));
        DAO.getEmprestimo().removerTodos();

        //livro possui uma reserva ativa e o top 1 da reserva não é o leitor que tá tentando realizar o emprestimo
        Reserva reserva1 = new Reserva(livro1.getISBN(),leitor2.getID());
        DAO.getReserva().criar(reserva1);
        assertThrows(LivroReservado.class, ()-> this.bibliotecario1.fazerEmprestimo(1003,10));
        DAO.getReserva().removerTodos();

        ////livro possui uma reserva ativa e o top 1 da reserva é o leitor que tá tentando realizar o emprestimo
        Reserva reserva2 = new Reserva(livro1.getISBN(),leitor1.getID());
        DAO.getReserva().criar(reserva2);
        Prazos prazo1 = new Prazos(leitor1.getID(),livro1.getISBN());
        DAO.getPrazos().criar(prazo1);
        assertEquals(1, DAO.getReserva().encontrarTodos().size());
        assertEquals(1, DAO.getPrazos().encontrarTodos().size());

        //tudo correto, leitor possui reserva ativa do livro que ta tentando fazer o empréstimo
        this.bibliotecario1.fazerEmprestimo(1003,10);

        assertTrue(DAO.getReserva().encontrarTodos().isEmpty());
        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());

        assertEquals(leitor1, DAO.getLeitor().encontrarPorId(DAO.getEmprestimo().encontrarTodos().get(0).getLeitor()));
    }

    @Test
    void devolverLivro() throws ObjetoInvalido, LeitorNaoPossuiEmprestimo{
        //leitor nao existe
        assertThrows(ObjetoInvalido.class, ()-> this.bibliotecario1.devolverLivro(10));

        Leitor leitor1 = new Leitor("LEITOR1", "RUA ALAMEDA","75999128840","SENHA1");
        DAO.getLeitor().criar(leitor1);

        //leitor nao possui livro a devolver
        assertThrows(LeitorNaoPossuiEmprestimo.class, ()-> this.bibliotecario1.devolverLivro(1003));

        //leitor atrasou a devolução do livro
        Livro livro1 = new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1");
        DAO.getLivro().criar(livro1);
        Emprestimo emp1 = new Emprestimo(livro1.getISBN(),leitor1.getID());
        DAO.getEmprestimo().criar(emp1);
        emp1.setdataPrevista(LocalDate.now().minusDays(1));

        //livro devolvido tem reserva
        Leitor leitor2 = new Leitor("LEITOR2", "RUA ALAMEDA2","74999128840","SENHA2");
        Reserva reserva1 = new Reserva(livro1.getISBN(),leitor2.getID());
        DAO.getReserva().criar(reserva1);

        assertNull(leitor1.getDataMulta());
        assertFalse(DAO.getLivro().encontrarTodos().get(0).getDisponivel());
        assertEquals(1,DAO.getLivro().encontrarTodos().get(0).getQtdEmprestimo());
        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());
        assertEquals(1, DAO.getEmprestimo().encontrarTodos().size());

        //tudo correto, leitor atrasou a devolução do livro
        this.bibliotecario1.devolverLivro(1003);

        assertEquals(LocalDate.now().plusDays(2),leitor1.getDataMulta());
        assertTrue(DAO.getLivro().encontrarTodos().get(0).getDisponivel());
        assertEquals(0,DAO.getLeitor().encontrarTodos().get(0).getLimiteRenova());
        assertEquals(1,DAO.getPrazos().encontrarTodos().size());
        assertTrue(DAO.getEmprestimo().encontrarTodosAtuais().isEmpty());
        assertEquals(1, DAO.getEmprestimo().encontrarTodos().size());
    }
}