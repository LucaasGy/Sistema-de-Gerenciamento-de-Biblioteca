package testes.dao.adm;

import dao.DAO;

import model.Adm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdmImplTest {

    private Adm lucas, ana, gabriel, paulo, fernanda;

    @BeforeEach
    void setUp() {
        this.lucas = DAO.getAdm().criar(new Adm("Lucas","batata"));
        this.ana = DAO.getAdm().criar(new Adm("Ana","alface"));
        this.gabriel = DAO.getAdm().criar(new Adm("Gabriel","cenoura"));
        this.paulo = DAO.getAdm().criar(new Adm("Paulo","beterraba"));
        this.fernanda = DAO.getAdm().criar(new Adm("Fernanda","chuchu"));
    }

    @AfterEach
    void tearDown() {
        DAO.getAdm().removerTodos();
    }

    @Test
    void criar() {
        Adm esperado = new Adm("Silas", "macarrao");
        esperado.setID(20000);
        Adm atual =  DAO.getAdm().criar(esperado);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorId() {
        Adm esperado = this.gabriel;
        Adm atual = DAO.getAdm().encontrarPorId(1021);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorNome() {
        Adm esperado = this.fernanda;
        Adm esperado2 = DAO.getAdm().criar(new Adm("fernanda","portugal"));

        List<Adm> atual = DAO.getAdm().encontrarPorNome("Fernanda");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
    }

    @Test
    void remover() {
        assertNotNull(DAO.getAdm().encontrarPorId(1011));
        assertNotNull(DAO.getAdm().encontrarPorId(1031));

        DAO.getAdm().remover(1011);
        DAO.getAdm().remover(1031);

        assertNull(DAO.getAdm().encontrarPorId(1011));
        assertNull(DAO.getAdm().encontrarPorId(1031));

        assertEquals(3,DAO.getAdm().encontrarTodos().size());
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getAdm().encontrarTodos().size());
    }

    @Test
    void atualizar() {
        this.lucas.setSenha("novasenha");
        this.lucas.setNome("joaquim");
        Adm esperado = this.lucas;

        Adm atual = DAO.getAdm().atualizar(this.lucas);

        assertEquals(esperado,atual);
    }

    @Test
    void removerTodos() {
        DAO.getAdm().removerTodos();

        assertTrue(DAO.getAdm().encontrarTodos().isEmpty());
    }
}