package testes;

import dao.DAO;
import model.Livro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LivroImplTest {

    private Livro livro1;
    private Livro livro2;
    private Livro livro3;
    private Livro livro4;
    private Livro livro5;

    @BeforeEach
    void setUp() {
        livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2001,"CATEGORIA1"));
        livro2 = DAO.getLivro().criar(new Livro("LIVRO2","AUTOR2","EDITORA2",2002,"CATEGORIA2"));
        livro3 = DAO.getLivro().criar(new Livro("LIVRO3","AUTOR3","EDITORA3",2003,"CATEGORIA3"));
        livro4 = DAO.getLivro().criar(new Livro("LIVRO4","AUTOR4","EDITORA4",2004,"CATEGORIA4"));
        livro5 = DAO.getLivro().criar(new Livro("LIVRO5","AUTOR5","EDITORA5",2005,"CATEGORIA5"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLivro().removerTodos();
    }

    @Test
    void criar() {
        Livro esperado = new Livro("As cronicas de eu","Xama","Viva mais",2002,"Terror");

        Livro atual = DAO.getLivro().criar(esperado);
        esperado.setISBN(atual.getISBN());

        assertEquals(esperado, atual);
    }

    @Test
    void removerPorISBN() {
        List<Livro> livros = DAO.getLivro().encontrarTodos();

        double isbn1 = livros.get(0).getISBN();
        double isbn3 = livros.get(2).getISBN();
        double isbn5 = livros.get(4).getISBN();

        DAO.getLivro().removerPorISBN(isbn1);
        DAO.getLivro().removerPorISBN(isbn3);
        DAO.getLivro().removerPorISBN(isbn5);

        Livro acharLivro1 = DAO.getLivro().encontrarPorISBN(isbn1);
        Livro acharLivro3 = DAO.getLivro().encontrarPorISBN(isbn3);
        Livro acharLivro5 = DAO.getLivro().encontrarPorISBN(isbn5);

        assertNull(acharLivro1);
        assertNull(acharLivro3);
        assertNull(acharLivro5);

        assertEquals(2, DAO.getLivro().encontrarTodos().size());
    }

    @Test
    void removerTodos() {
        DAO.getLivro().removerTodos();

        assertEquals(0, DAO.getLivro().encontrarTodos().size());
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getLivro().encontrarTodos().size());
    }

    @Test
    void encontrarPorTitulo() {
        Livro esperado = this.livro4;
        Livro esperado2 = DAO.getLivro().criar(new Livro("LIVRO4","AUTOR4.6","EDITORA4.6",2004,"CATEGORIA4.6"));

        List<Livro> atual = DAO.getLivro().encontrarPorTitulo("livro4");

        assertEquals(2,atual.size());
    }

    @Test
    void encontrarPorAutor() {
        Livro esperado = this.livro1;
        Livro esperado2 = DAO.getLivro().criar(new Livro("LIVRO1.2","AUTOR1","EDITORA1.2",2001,"CATEGORIA1.2"));

        List<Livro> atual = DAO.getLivro().encontrarPorAutor("autor1");

        assertEquals(2,atual.size());
    }

    @Test
    void encontrarPorCategoria() {
        Livro esperado = this.livro2;
        Livro esperado2 = DAO.getLivro().criar(new Livro("LIVRO2.1","AUTOR2.1","EDITORA2.1",2002,"CATEGORIA2"));

        List<Livro> atual = DAO.getLivro().encontrarPorCategoria("categoria2");

        assertEquals(2,atual.size());
    }

    @Test
    void encontrarPorISBN() {
        Livro esperado = this.livro5;
        double isbn5 = DAO.getLivro().encontrarPorTitulo("livro5").get(0).getISBN();

        Livro atual = DAO.getLivro().encontrarPorISBN(isbn5);

        assertEquals(esperado,atual);
    }

    @Test
    void atualizar() {
        this.livro4.setCategoria("TERROR");
        this.livro4.setAutor("EU MEMO");

        Livro esperado = this.livro4;

        Livro atual = DAO.getLivro().atualizar(this.livro4);

        assertEquals(esperado,atual);
    }
}