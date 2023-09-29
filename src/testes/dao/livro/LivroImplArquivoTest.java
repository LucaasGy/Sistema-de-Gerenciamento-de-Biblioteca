package testes.dao.livro;

import dao.DAO;

import model.Livro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.ArmazenamentoArquivo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LivroImplArquivoTest {

    private Livro livro1, livro2, livro3, livro4, livro5;

    @BeforeEach
    void setUp() {
        DAO.getLivro().alteraParaPastaTeste();

        this.livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2001,"CATEGORIA1"));
        this.livro2 = DAO.getLivro().criar(new Livro("LIVRO2","AUTOR2","EDITORA2",2002,"CATEGORIA2"));
        this.livro3 = DAO.getLivro().criar(new Livro("LIVRO3","AUTOR3","EDITORA3",2003,"CATEGORIA3"));
        this.livro4 = DAO.getLivro().criar(new Livro("LIVRO4","AUTOR4","EDITORA4",2004,"CATEGORIA4"));
        this.livro5 = DAO.getLivro().criar(new Livro("LIVRO5","AUTOR5","EDITORA5",2005,"CATEGORIA5"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLivro().removerTodos();

        DAO.getLivro().alteraParaPastaPrincipal();
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
        Livro esperado = this.livro5;
        double isbn5 = DAO.getLivro().encontrarPorTitulo("livro5").get(0).getISBN();

        Livro atual = DAO.getLivro().encontrarPorISBN(isbn5);

        assertEquals(esperado,atual);
    }

    @Test
    void removerTodos() {
        DAO.getLivro().removerTodos();

        assertTrue(DAO.getLivro().encontrarTodos().isEmpty());
        assertTrue(ArmazenamentoArquivo.resgatar("livroTeste.dat","Livro Teste").isEmpty());

        //apagou todos os isbn
        assertEquals(0,DAO.getLivro().checarListaISBN().size());

        DAO.getLivro().criar(new Livro("A origem","Ana","Seu medo",1994,"Suspense"));
        assertEquals(1,DAO.getLivro().checarListaISBN().size());
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getLivro().encontrarTodos().size());
        assertEquals(5, ArmazenamentoArquivo.resgatar("livroTeste.dat","Livro Teste").size());
    }

    @Test
    void atualizar() {
        this.livro4.setCategoria("TERROR");
        this.livro4.setAutor("EU MEMO");

        Livro esperado = this.livro4;

        Livro atual = DAO.getLivro().atualizar(this.livro4);

        assertEquals(esperado,atual);
    }

    @Test
    void encontrarPorAutor() {
        DAO.getLivro().criar(new Livro("LIVRO1.2","AUTOR1","EDITORA1.2",2001,"CATEGORIA1.2"));

        List<Livro> atual = DAO.getLivro().encontrarPorAutor("autor1");

        assertEquals(2,atual.size());
    }

    @Test
    void encontrarPorCategoria() {
        DAO.getLivro().criar(new Livro("LIVRO2.1","AUTOR2.1","EDITORA2.1",2002,"CATEGORIA2"));

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
    void checarListaISBN() {
        assertEquals(5,DAO.getLivro().checarListaISBN().size());
    }
}