package testes.model;

import dao.DAO;

import erros.objetos.ObjetoInvalido;

import model.Leitor;
import model.Livro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    Livro livro1, livro2, livro3, livro4, livro5;
    Leitor leitor1;

    @BeforeEach
    void setUp() {
        livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2001,"CATEGORIA1"));
        livro2 = DAO.getLivro().criar(new Livro("LIVRO2","AUTOR2","EDITORA2",2002,"CATEGORIA2"));
        livro3 = DAO.getLivro().criar(new Livro("LIVRO3","AUTOR3","EDITORA3",2003,"CATEGORIA3"));
        livro4 = DAO.getLivro().criar(new Livro("LIVRO4","AUTOR4","EDITORA4",2004,"CATEGORIA4"));
        livro5 = DAO.getLivro().criar(new Livro("LIVRO5","AUTOR5","EDITORA5",2005,"CATEGORIA5"));

        leitor1 = DAO.getLeitor().criar(new Leitor("Lucas","Rua Alameda","75757575","batata"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLivro().removerTodos();
    }

    @Test
    void pesquisarLivroPorTitulo() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()-> this.leitor1.pesquisarLivroPorTitulo("naoexiste"));
        assertEquals(1, this.leitor1.pesquisarLivroPorTitulo("livro1").size());
    }

    @Test
    void pesquisarLivroPorAutor() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()-> this.leitor1.pesquisarLivroPorAutor("naoexiste"));
        assertEquals(1, this.leitor1.pesquisarLivroPorAutor("autor3").size());
    }

    @Test
    void pesquisarLivroPorCategoria() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()-> this.leitor1.pesquisarLivroPorCategoria("naoexiste"));
        assertEquals(1, this.leitor1.pesquisarLivroPorCategoria("categoria5").size());
    }

    @Test
    void pesquisarLivroPorISBN() throws ObjetoInvalido {
        double isbn5 = DAO.getLivro().encontrarTodos().get(4).getISBN();
        Livro esperado = this.livro5;

        assertThrows(ObjetoInvalido.class, ()-> this.leitor1.pesquisarLivroPorISBN(99999999));
        assertEquals(esperado, this.leitor1.pesquisarLivroPorISBN(isbn5));
    }
}