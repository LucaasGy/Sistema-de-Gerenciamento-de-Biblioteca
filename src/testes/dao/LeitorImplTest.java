package testes.dao;

import dao.DAO;

import model.Leitor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeitorImplTest {

    private Leitor lucas, ana, gabriel;

    @BeforeEach
    void setUp() {
        lucas = DAO.getLeitor().criar(new Leitor("Lucas","Rua Eldorado","7577772222","batata"));
        ana = DAO.getLeitor().criar(new Leitor("Ana","Rua Ofensa","7511115555","cenura"));
        gabriel = DAO.getLeitor().criar(new Leitor("Lucas","Rua Coca-Cola","7599993333","beterraba"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitor().removerTodos();
    }

    @Test
    void criar() {
        Leitor esperado = new Leitor("Silas", "Rua México","7987675436","eletricidade");
        esperado.setID(20000);
        Leitor atual =  DAO.getLeitor().criar(esperado);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorId() {
        Leitor esperado = this.gabriel;
        Leitor atual = DAO.getLeitor().encontrarPorId(1023);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarPorNome() {
        Leitor esperado = this.ana;
        Leitor esperado2 = DAO.getLeitor().criar(new Leitor("ANA","Rua Portugal","7665653434","tortuguita"));

        List<Leitor> atual = DAO.getLeitor().encontrarPorNome("ANA");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
    }

    @Test
    void encontrarPorTelefone() {
        Leitor esperado = this.gabriel;
        Leitor esperado2 = DAO.getLeitor().criar(new Leitor("pedro","Rua Portugal","7599993333","tortuguita"));

        List<Leitor> atual = DAO.getLeitor().encontrarPorTelefone("7599993333");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
    }

    @Test
    void remover() {
        assertNotNull(DAO.getLeitor().encontrarPorId(1013));
        assertNotNull(DAO.getLeitor().encontrarPorId(1023));

        DAO.getLeitor().remover(1013);
        DAO.getLeitor().remover(1023);

        assertNull(DAO.getLeitor().encontrarPorId(1013));
        assertNull(DAO.getLeitor().encontrarPorId(1023));

        assertEquals(1,DAO.getLeitor().encontrarTodos().size());
    }

    @Test
    void encontrarTodos() {
        assertEquals(3, DAO.getLeitor().encontrarTodos().size());
    }

    @Test
    void atualizar() {
        this.lucas.setSenha("novasenha");
        this.lucas.setNome("joaquim");
        this.lucas.setDataMulta(LocalDate.now());
        Leitor esperado = this.lucas;

        Leitor atual = DAO.getLeitor().atualizar(this.lucas);

        assertEquals(esperado,atual);
    }

    @Test
    void removerTodos() {
        DAO.getLeitor().removerTodos();

        assertTrue(DAO.getLeitor().encontrarTodos().isEmpty());
    }
}