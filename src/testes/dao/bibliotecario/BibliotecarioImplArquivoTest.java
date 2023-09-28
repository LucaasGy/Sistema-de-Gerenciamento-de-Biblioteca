package testes.dao.bibliotecario;

import dao.DAO;
import model.Bibliotecario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ArmazenamentoArquivo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BibliotecarioImplArquivoTest {

    private Bibliotecario bibliotecario1, bibliotecario2, bibliotecario3, bibliotecario4, bibliotecario5;

    @BeforeEach
    void setUp() {
        this.bibliotecario1 = DAO.getBibliotecario().criar(new Bibliotecario("biblio1","senha1"));
        this.bibliotecario2 = DAO.getBibliotecario().criar(new Bibliotecario("biblio2","senha2"));
        this.bibliotecario3 = DAO.getBibliotecario().criar(new Bibliotecario("biblio3","senha3"));
        this.bibliotecario4 = DAO.getBibliotecario().criar(new Bibliotecario("biblio4","senha4"));
        this.bibliotecario5 = DAO.getBibliotecario().criar(new Bibliotecario("biblio5","senha5"));
    }

    @AfterEach
    void tearDown() {
        DAO.getBibliotecario().removerTodos();
    }

    @Test
    void criar() {
        Bibliotecario esperado = new Bibliotecario("bibliotecarioEsperado","senhaDele");
        esperado.setID(22222);
        Bibliotecario atual = DAO.getBibliotecario().criar(esperado);

        assertEquals(esperado,atual);
        assertEquals(1052,atual.getID());
    }

    @Test
    void remover() {
        assertNotNull(DAO.getBibliotecario().encontrarPorId(1012));
        assertNotNull(DAO.getBibliotecario().encontrarPorId(1032));

        DAO.getBibliotecario().remover(1012);
        DAO.getBibliotecario().remover(1032);

        assertNull(DAO.getBibliotecario().encontrarPorId(1012));
        assertNull(DAO.getBibliotecario().encontrarPorId(1032));

        assertEquals(3,DAO.getBibliotecario().encontrarTodos().size());
        assertEquals(3, ArmazenamentoArquivo.resgatar("bibliotecario.dat","Bibliotecario").size());

    }

    @Test
    void removerTodos() {
        DAO.getBibliotecario().removerTodos();

        assertTrue(DAO.getBibliotecario().encontrarTodos().isEmpty());
        assertTrue(ArmazenamentoArquivo.resgatar("bibliotecario.dat","Bibliotecario").isEmpty());
        assertEquals(1002,DAO.getBibliotecario().criar(new Bibliotecario("biblio1","senha1")).getID());
    }

    @Test
    void encontrarPorId() {
        Bibliotecario atual = DAO.getBibliotecario().encontrarPorId(1042);
        assertEquals(this.bibliotecario5,atual);
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getBibliotecario().encontrarTodos().size());
        assertEquals(this.bibliotecario1, DAO.getBibliotecario().encontrarTodos().get(0));
        assertEquals(this.bibliotecario2, DAO.getBibliotecario().encontrarTodos().get(1));
        assertEquals(this.bibliotecario3, DAO.getBibliotecario().encontrarTodos().get(2));
        assertEquals(this.bibliotecario4, DAO.getBibliotecario().encontrarTodos().get(3));
        assertEquals(this.bibliotecario5, DAO.getBibliotecario().encontrarTodos().get(4));
        assertEquals(5, ArmazenamentoArquivo.resgatar("bibliotecario.dat","Bibliotecario").size());
    }

    @Test
    void atualizar() {
        this.bibliotecario1.setSenha("novasenha");
        this.bibliotecario1.setNome("joaquim");
        Bibliotecario esperado = this.bibliotecario1;

        Bibliotecario atual = DAO.getBibliotecario().atualizar(this.bibliotecario1);

        assertEquals(esperado,atual);
    }

    @Test
    void encontrarPorNome() {
        Bibliotecario esperado = this.bibliotecario2;
        Bibliotecario esperado2 = DAO.getBibliotecario().criar(new Bibliotecario("biblio2","portugal"));

        List<Bibliotecario> atual = DAO.getBibliotecario().encontrarPorNome("biblio2");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
    }
}