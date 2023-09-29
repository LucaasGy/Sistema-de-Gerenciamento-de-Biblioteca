package testes.dao.leitor;

import dao.DAO;

import model.Leitor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.ArmazenamentoArquivo;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeitorImplArquivoTest {

    private Leitor leitor1, leitor2, leitor3, leitor4, leitor5;

    @BeforeEach
    void setUp() {
        DAO.getLeitor().alteraParaPastaTeste();

        this.leitor1 = DAO.getLeitor().criar(new Leitor("Lucas", "Rua Alameda", "75757575", "batata"));
        this.leitor2 = DAO.getLeitor().criar(new Leitor("Ana", "Avenida Central", "12345678", "senha123"));
        this.leitor3 = DAO.getLeitor().criar(new Leitor("Pedro", "Rua das Flores", "98765432", "abcd1234"));
        this.leitor4 = DAO.getLeitor().criar(new Leitor("Mariana", "Avenida Principal", "55555555", "senhamariana"));
        this.leitor5 = DAO.getLeitor().criar(new Leitor("Carlos", "Rua dos Passarinhos", "11111111", "senha12345"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitor().removerTodos();

        DAO.getLeitor().alteraParaPastaPrincipal();
    }

    @Test
    void criar() {
        Leitor esperado = new Leitor("Silas", "Rua México","7987675436","eletricidade");
        esperado.setID(20000);
        Leitor atual =  DAO.getLeitor().criar(esperado);

        assertEquals(esperado, atual);
    }

    @Test
    void remover() {
        assertNotNull(DAO.getLeitor().encontrarPorId(1013));
        assertNotNull(DAO.getLeitor().encontrarPorId(1023));

        DAO.getLeitor().remover(1013);
        DAO.getLeitor().remover(1023);

        assertNull(DAO.getLeitor().encontrarPorId(1013));
        assertNull(DAO.getLeitor().encontrarPorId(1023));

        assertEquals(3,DAO.getLeitor().encontrarTodos().size());
        assertEquals(3,ArmazenamentoArquivo.resgatar("leitorTeste.dat","Leitor Teste").size());
    }

    @Test
    void removerTodos() {
        DAO.getLeitor().removerTodos();

        assertTrue(DAO.getLeitor().encontrarTodos().isEmpty());
        assertTrue(ArmazenamentoArquivo.resgatar("leitorTeste.dat","Leitor Teste").isEmpty());
        assertEquals(1003,DAO.getLeitor().criar(new Leitor("novoLeitor","rua","444","senhaNova")).getID());
    }

    @Test
    void encontrarPorId() {
        Leitor esperado = this.leitor3;
        Leitor atual = DAO.getLeitor().encontrarPorId(1023);

        assertEquals(esperado, atual);
    }

    @Test
    void encontrarTodos() {
        assertEquals(5, DAO.getLeitor().encontrarTodos().size());
        assertEquals(this.leitor1, DAO.getLeitor().encontrarTodos().get(0));
        assertEquals(this.leitor2, DAO.getLeitor().encontrarTodos().get(1));
        assertEquals(this.leitor3, DAO.getLeitor().encontrarTodos().get(2));
        assertEquals(this.leitor4, DAO.getLeitor().encontrarTodos().get(3));
        assertEquals(this.leitor5, DAO.getLeitor().encontrarTodos().get(4));
        assertEquals(5,ArmazenamentoArquivo.resgatar("leitorTeste.dat","Leitor Teste").size());
    }

    @Test
    void atualizar() {
        this.leitor1.setSenha("novasenha");
        this.leitor1.setNome("joaquim");
        this.leitor1.setDataMulta(LocalDate.now());
        Leitor esperado = this.leitor1;

        Leitor atual = DAO.getLeitor().atualizar(this.leitor1);

        assertEquals(esperado,atual);
    }

    @Test
    void encontrarPorNome() {
        Leitor esperado = this.leitor2;
        Leitor esperado2 = DAO.getLeitor().criar(new Leitor("ANA","Rua Portugal","7665653434","tortuguita"));

        List<Leitor> atual = DAO.getLeitor().encontrarPorNome("ANA");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
    }

    @Test
    void encontrarPorTelefone() {
        Leitor esperado = this.leitor5;
        Leitor esperado2 = DAO.getLeitor().criar(new Leitor("pedro","Rua Portugal","11111111","tortuguita"));

        List<Leitor> atual = DAO.getLeitor().encontrarPorTelefone("11111111");

        assertEquals(2,atual.size());

        assertEquals(esperado, atual.get(0));
        assertEquals(esperado2, atual.get(1));
    }
}