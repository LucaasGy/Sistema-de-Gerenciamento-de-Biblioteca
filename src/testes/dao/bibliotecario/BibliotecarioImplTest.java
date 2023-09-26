package testes.dao.bibliotecario;

import dao.DAO;

import model.Bibliotecario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BibliotecarioImplTest {

    private Bibliotecario lucas, ana, gabriel, paulo, fernanda;

    @BeforeEach
    void setUp() {
        this.lucas = DAO.getBibliotecario().criar(new Bibliotecario("Lucas","batata"));
        this.ana = DAO.getBibliotecario().criar(new Bibliotecario("Ana","alface"));
        this.gabriel = DAO.getBibliotecario().criar(new Bibliotecario("Gabriel","cenoura"));
        this.paulo = DAO.getBibliotecario().criar(new Bibliotecario("Paulo","beterraba"));
        this.fernanda = DAO.getBibliotecario().criar(new Bibliotecario("Fernanda","chuchu"));
    }

    @AfterEach
    void tearDown() {
        DAO.getBibliotecario().removerTodos();
    }

    @Test
    void criar() {
        Bibliotecario esperado = new Bibliotecario("Silas", "macarrao");
        esperado.setID(20000);
        Bibliotecario atual =  DAO.getBibliotecario().criar(esperado);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorId() {
        Bibliotecario esperado = this.gabriel;
        Bibliotecario atual = DAO.getBibliotecario().encontrarPorId(1022);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorNome() {
        Bibliotecario esperado = this.fernanda;
        Bibliotecario esperado2 = DAO.getBibliotecario().criar(new Bibliotecario("fernanda","portugal"));

        List<Bibliotecario> atual = DAO.getBibliotecario().encontrarPorNome("Fernanda");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
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
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getBibliotecario().encontrarTodos().size());
    }

    @Test
    void atualizar() {
        this.lucas.setSenha("novasenha");
        this.lucas.setNome("joaquim");
        Bibliotecario esperado = this.lucas;

        Bibliotecario atual = DAO.getBibliotecario().atualizar(this.lucas);

        assertEquals(esperado,atual);
    }

    @Test
    void removerTodos() {
        DAO.getBibliotecario().removerTodos();

        assertTrue(DAO.getBibliotecario().encontrarTodos().isEmpty());
    }
}