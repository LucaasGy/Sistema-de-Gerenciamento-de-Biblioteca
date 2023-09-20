package testes.dao;

import dao.DAO;

import model.Emprestimo;
import model.Leitor;
import model.Livro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoImplTest {

    private Emprestimo emprestimo1, emprestimo2, emprestimo3;

    @BeforeEach
    void setUp() {
        Livro livro1 = new Livro("As cronicas","Jorge","Sua paz",2000,"Fantasia");
        Livro livro2 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        Livro livro3 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");

        livro1.setISBN(10.00000);
        livro2.setISBN(11.00000);
        livro3.setISBN(12.00000);

        Leitor leitor1 = new Leitor("Lucas","Rua Alameda","75757575","batata");
        Leitor leitor2 = new Leitor("Gabriel","Rua Pitanga","86868686","cenoura");
        Leitor leitor3 = new Leitor("Jose","Rua Maca","97979797","chuchu");

        leitor1.setID(1000);
        leitor2.setID(1100);
        leitor3.setID(1200);

        emprestimo1 = DAO.getEmprestimo().criar(new Emprestimo(livro1,leitor1));
        emprestimo2 = DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor2));
        emprestimo3 = DAO.getEmprestimo().criar(new Emprestimo(livro3,leitor3));
    }

    @AfterEach
    void tearDown() {
        DAO.getEmprestimo().removerTodos();
    }

    @Test
    void criar() {
        Livro livroEsperado = new Livro("Harry Potter","Leonardo","Seu desespero",2003,"Fantasia");
        livroEsperado.setISBN(13.00000);

        Leitor leitorEsperado = new Leitor("Joaquim","Rua banana","01010101","sapato");
        leitorEsperado.setID(1300);

        Emprestimo emprestimoEsperado = new Emprestimo(livroEsperado,leitorEsperado);

        Emprestimo atual =  DAO.getEmprestimo().criar(emprestimoEsperado);

        assertEquals(emprestimoEsperado, atual);
    }

    @Test
    void remover() {
        assertNotNull(DAO.getEmprestimo().encontrarPorId(1000));
        assertNotNull(DAO.getEmprestimo().encontrarPorId(1200));

        DAO.getEmprestimo().remover(1000);
        DAO.getEmprestimo().remover(1200);

        assertNull(DAO.getEmprestimo().encontrarPorId(1000));
        assertNull(DAO.getEmprestimo().encontrarPorId(1200));

        assertEquals(1,DAO.getEmprestimo().encontrarTodosAtuais().size());
    }

    @Test
    void removerTodosEmprestimosDeUmLivro() {
        Livro livro2 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        livro2.setISBN(11.00000);

        Leitor leitor4 = new Leitor("Gabriel","Rua Pitanga","86868686","cenoura");
        Leitor leitor5 = new Leitor("Jose","Rua Maca","97979797","chuchu");

        leitor4.setID(1300);
        leitor5.setID(1400);

        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor4));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor5));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor5));

        assertEquals(6, DAO.getEmprestimo().encontrarTodos().size());

        DAO.getEmprestimo().removerTodosEmprestimosDeUmLivro(11.00000);

        assertEquals(2, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void removerTodosEmprestimoDeUmLeitor() {
        Leitor leitor1 = new Leitor("Jose","Rua Maca","97979797","chuchu");
        leitor1.setID(1000);

        Livro livro4 = new Livro("Os jogos","Ronaldo","Sua alegria",2020,"Comedia");
        Livro livro5 = new Livro("O grito","Percy","Seu medo",1991,"Horror");

        livro4.setISBN(13.00000);
        livro5.setISBN(14.00000);

        DAO.getEmprestimo().criar(new Emprestimo(livro4,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));

        assertEquals(6, DAO.getEmprestimo().encontrarTodos().size());

        DAO.getEmprestimo().removerTodosEmprestimoDeUmLeitor(1000);

        assertEquals(2, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void encontrarPorId() {
        Emprestimo esperado = this.emprestimo3;
        Emprestimo atual = DAO.getEmprestimo().encontrarPorId(1200);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorISBN() {
        Emprestimo esperado = this.emprestimo3;
        Emprestimo atual = DAO.getEmprestimo().encontrarPorISBN(12.00000);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarTodos() {
        assertEquals(3, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void encontrarTodosAtuais() {
        DAO.getEmprestimo().remover(1200);

        assertEquals(2, DAO.getEmprestimo().encontrarTodosAtuais().size());
        assertEquals(3, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void encontrarHistoricoDeUmLeitor() {
        Leitor leitor1 = new Leitor("Jose","Rua Maca","97979797","chuchu");
        leitor1.setID(1000);

        Livro livro4 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");
        Livro livro5 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");

        livro4.setISBN(13.00000);
        livro5.setISBN(14.00000);

        DAO.getEmprestimo().criar(new Emprestimo(livro4,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));

        assertEquals(4, DAO.getEmprestimo().encontrarHistoricoDeUmLeitor(1000).size());
        assertEquals(1, DAO.getEmprestimo().encontrarHistoricoDeUmLeitor(1100).size());
    }

    @Test
    void encontrarHistoricoDeUmLivro() {
        Livro livro2 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        livro2.setISBN(11.00000);

        Leitor leitor4 = new Leitor("Gabriel","Rua Pitanga","86868686","cenoura");
        Leitor leitor5 = new Leitor("Jose","Rua Maca","97979797","chuchu");

        leitor4.setID(1300);
        leitor5.setID(1400);

        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor4));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor5));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor5));

        assertEquals(4, DAO.getEmprestimo().encontrarHistoricoDeUmLivro(11.00000).size());
        assertEquals(1, DAO.getEmprestimo().encontrarHistoricoDeUmLivro(12.00000).size());
    }

    @Test
    void removerTodos() {
        assertEquals(1,this.emprestimo1.getLivro().getQtdEmprestimo());
        assertEquals(1,this.emprestimo2.getLivro().getQtdEmprestimo());
        assertFalse(this.emprestimo1.getLivro().getDisponivel());
        assertFalse(this.emprestimo2.getLivro().getDisponivel());

        DAO.getEmprestimo().removerTodos();

        assertTrue(DAO.getEmprestimo().encontrarTodos().isEmpty());
        assertEquals(0,this.emprestimo1.getLivro().getQtdEmprestimo());
        assertEquals(0,this.emprestimo2.getLivro().getQtdEmprestimo());
        assertTrue(this.emprestimo1.getLivro().getDisponivel());
        assertTrue(this.emprestimo2.getLivro().getDisponivel());
    }
}