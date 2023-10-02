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
        Livro livro1 = DAO.getLivro().criar(new Livro("As cronicas","Jorge","Sua paz",2000,"Fantasia"));
        Livro livro2 = DAO.getLivro().criar(new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura"));
        Livro livro3 = DAO.getLivro().criar(new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense"));

        Leitor leitor1 = DAO.getLeitor().criar(new Leitor("Lucas","Rua Alameda","75757575","batata"));
        Leitor leitor2 = DAO.getLeitor().criar(new Leitor("Gabriel","Rua Pitanga","86868686","cenoura"));
        Leitor leitor3 = DAO.getLeitor().criar(new Leitor("Jose","Rua Maca","97979797","chuchu"));

        this.emprestimo1 = DAO.getEmprestimo().criar(new Emprestimo(livro1,leitor1));
        this.emprestimo2 = DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor2));
        this.emprestimo3 = DAO.getEmprestimo().criar(new Emprestimo(livro3,leitor3));
    }

    @AfterEach
    void tearDown() {
        DAO.getEmprestimo().removerTodos();
        DAO.getLeitor().removerTodos();
        DAO.getLivro().removerTodos();
    }

    @Test
    void criar() {
        Livro livroEsperado = DAO.getLivro().criar(new Livro("Harry Potter","Leonardo","Seu desespero",2003,"Fantasia"));

        Leitor leitorEsperado = DAO.getLeitor().criar(new Leitor("Joaquim","Rua banana","01010101","sapato"));

        assertTrue(livroEsperado.getDisponivel());
        assertEquals(0,livroEsperado.getQtdEmprestimo());

        Emprestimo emprestimoEsperado = new Emprestimo(livroEsperado, leitorEsperado);

        Emprestimo atual =  DAO.getEmprestimo().criar(emprestimoEsperado);

        assertEquals(emprestimoEsperado, atual);

        assertFalse(livroEsperado.getDisponivel());
        assertEquals(1,livroEsperado.getQtdEmprestimo());
    }

    @Test
    void remover() {
        assertNotNull(DAO.getEmprestimo().encontrarPorId(1003));
        assertNotNull(DAO.getEmprestimo().encontrarPorId(1013));

        DAO.getEmprestimo().remover(1003);
        DAO.getEmprestimo().remover(1013);

        assertNull(DAO.getEmprestimo().encontrarPorId(1003));
        assertNull(DAO.getEmprestimo().encontrarPorId(1013));

        assertEquals(1,DAO.getEmprestimo().encontrarTodosAtuais().size());
    }

    @Test
    void removerTodosEmprestimosDeUmLivro() {
        Livro livro2 = DAO.getLivro().criar(new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura"));
        livro2.setISBN(11.00000);

        Leitor leitor4 = DAO.getLeitor().criar(new Leitor("Gabriel","Rua Pitanga","86868686","cenoura"));
        Leitor leitor5 = DAO.getLeitor().criar(new Leitor("Jose","Rua Maca","97979797","chuchu"));
        leitor4.setID(1300);
        leitor5.setID(1400);

        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor4));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor4));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor4));

        assertEquals(6, DAO.getEmprestimo().encontrarTodos().size());

        DAO.getEmprestimo().removerTodosEmprestimosDeUmLivro(11.00000);

        assertEquals(3, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void removerTodosEmprestimoDeUmLeitor() {
        Leitor leitor1 = DAO.getLeitor().criar(new Leitor("Jose","Rua Maca","97979797","chuchu"));
        leitor1.setID(1000);

        Livro livro4 = DAO.getLivro().criar(new Livro("Os jogos","Ronaldo","Sua alegria",2020,"Comedia"));
        Livro livro5 = DAO.getLivro().criar(new Livro("O grito","Percy","Seu medo",1991,"Horror"));
        livro4.setISBN(13.00000);
        livro5.setISBN(14.00000);

        DAO.getEmprestimo().criar(new Emprestimo(livro4,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));

        assertEquals(6, DAO.getEmprestimo().encontrarTodos().size());

        DAO.getEmprestimo().removerTodosEmprestimoDeUmLeitor(1000);

        assertEquals(3, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void encontrarPorId() {
        Emprestimo esperado = this.emprestimo3;
        Emprestimo atual = DAO.getEmprestimo().encontrarPorId(1023);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorISBN() {
        Emprestimo esperado = this.emprestimo3;
        Emprestimo atual = DAO.getEmprestimo().encontrarPorISBN(this.emprestimo3.getLivro().getISBN());

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarTodos() {
        assertEquals(3, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void encontrarTodosAtuais() {
        DAO.getEmprestimo().remover(1013);

        assertEquals(2, DAO.getEmprestimo().encontrarTodosAtuais().size());
        assertEquals(3, DAO.getEmprestimo().encontrarTodos().size());
    }

    @Test
    void encontrarHistoricoDeUmLeitor() {
        Leitor leitor1 = DAO.getLeitor().criar(new Leitor("Jose","Rua Maca","97979797","chuchu"));
        leitor1.setID(1000);

        Livro livro4 = DAO.getLivro().criar(new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense"));
        Livro livro5 = DAO.getLivro().criar(new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense"));
        livro4.setISBN(13.00000);
        livro5.setISBN(14.00000);

        DAO.getEmprestimo().criar(new Emprestimo(livro4,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));
        DAO.getEmprestimo().criar(new Emprestimo(livro5,leitor1));

        assertEquals(3, DAO.getEmprestimo().encontrarHistoricoDeUmLeitor(1000).size());
        assertEquals(1, DAO.getEmprestimo().encontrarHistoricoDeUmLeitor(1013).size());
    }

    @Test
    void encontrarHistoricoDeUmLivro() {
        Livro livro2 = DAO.getLivro().criar(new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura"));
        livro2.setISBN(11.00000);

        Leitor leitor4 = DAO.getLeitor().criar(new Leitor("Gabriel","Rua Pitanga","86868686","cenoura"));
        Leitor leitor5 = DAO.getLeitor().criar(new Leitor("Jose","Rua Maca","97979797","chuchu"));
        leitor4.setID(1300);
        leitor5.setID(1400);

        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor4));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor5));
        DAO.getEmprestimo().criar(new Emprestimo(livro2,leitor5));

        assertEquals(3, DAO.getEmprestimo().encontrarHistoricoDeUmLivro(11.00000).size());
        assertEquals(1, DAO.getEmprestimo().encontrarHistoricoDeUmLivro(this.emprestimo3.getLivro().getISBN()).size());
    }

    @Test
    void removerTodos() {
        assertEquals(1,DAO.getLivro().encontrarPorISBN(this.emprestimo1.getLivro().getISBN()).getQtdEmprestimo());
        assertEquals(1,DAO.getLivro().encontrarPorISBN(this.emprestimo2.getLivro().getISBN()).getQtdEmprestimo());
        assertFalse(DAO.getLivro().encontrarPorISBN(this.emprestimo1.getLivro().getISBN()).getDisponivel());
        assertFalse(DAO.getLivro().encontrarPorISBN(this.emprestimo2.getLivro().getISBN()).getDisponivel());

        DAO.getEmprestimo().removerTodos();

        assertTrue(DAO.getEmprestimo().encontrarTodos().isEmpty());
        assertEquals(0,DAO.getLivro().encontrarPorISBN(this.emprestimo1.getLivro().getISBN()).getQtdEmprestimo());
        assertEquals(0,DAO.getLivro().encontrarPorISBN(this.emprestimo2.getLivro().getISBN()).getQtdEmprestimo());
        assertTrue(DAO.getLivro().encontrarPorISBN(this.emprestimo1.getLivro().getISBN()).getDisponivel());
        assertTrue(DAO.getLivro().encontrarPorISBN(this.emprestimo2.getLivro().getISBN()).getDisponivel());
    }
}