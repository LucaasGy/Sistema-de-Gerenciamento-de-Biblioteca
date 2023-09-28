package testes.dao.adm;

import dao.DAO;

import model.Adm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.ArmazenamentoArquivo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdmImplArquivoTest {

    private Adm adm1, adm2, adm3, adm4, adm5;

    @BeforeEach
    void setUp() {
        this.adm1 = DAO.getAdm().criar(new Adm("adm1","senha1"));
        this.adm2 = DAO.getAdm().criar(new Adm("adm2","senha2"));
        this.adm3 = DAO.getAdm().criar(new Adm("adm3","senha3"));
        this.adm4 = DAO.getAdm().criar(new Adm("adm4","senha4"));
        this.adm5 = DAO.getAdm().criar(new Adm("adm5","senha5"));
    }

    @AfterEach
    void tearDown() {
        DAO.getAdm().removerTodos();
    }

    @Test
    void criar() {
        Adm esperado = new Adm("admEsperado","senhaDele");
        esperado.setID(22222);
        Adm atual = DAO.getAdm().criar(esperado);

        assertEquals(esperado,atual);
        assertEquals(1051,atual.getID());
    }

    @Test
    void encontrarPorId() {
        Adm atual = DAO.getAdm().encontrarPorId(1041);
        assertEquals(this.adm5,atual);
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getAdm().encontrarTodos().size());
        assertEquals(this.adm1, DAO.getAdm().encontrarTodos().get(0));
        assertEquals(this.adm2, DAO.getAdm().encontrarTodos().get(1));
        assertEquals(this.adm3, DAO.getAdm().encontrarTodos().get(2));
        assertEquals(this.adm4, DAO.getAdm().encontrarTodos().get(3));
        assertEquals(this.adm5, DAO.getAdm().encontrarTodos().get(4));
        assertEquals(5,ArmazenamentoArquivo.resgatar("adm.dat","Adm").size());
    }

    @Test
    void atualizar() {
        this.adm1.setSenha("novasenha");
        this.adm1.setNome("joaquim");
        Adm esperado = this.adm1;

        Adm atual = DAO.getAdm().atualizar(this.adm1);

        assertEquals(esperado,atual);
    }

    @Test
    void encontrarPorNome() {
        Adm esperado = this.adm2;
        Adm esperado2 = DAO.getAdm().criar(new Adm("adm2","portugal"));

        List<Adm> atual = DAO.getAdm().encontrarPorNome("adm2");

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
        assertEquals(3,ArmazenamentoArquivo.resgatar("adm.dat","Adm").size());
    }

    @Test
    void removerTodos() {
        DAO.getAdm().removerTodos();

        assertTrue(DAO.getAdm().encontrarTodos().isEmpty());
        assertTrue(ArmazenamentoArquivo.resgatar("adm.dat","Adm").isEmpty());
        assertEquals(1001,DAO.getAdm().criar(new Adm("adm1","senha1")).getID());
    }
}