package testes.model;

import dao.DAO;

import erros.objetos.ObjetoInvalido;
import erros.objetos.UsuarioSenhaIncorreta;

import model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {

    private Adm adm1, adm2, adm3, adm4, adm5;
    private Bibliotecario bibliotecario1, bibliotecario2, bibliotecario3, bibliotecario4, bibliotecario5;
    private Leitor leitor1, leitor2, leitor3, leitor4, leitor5;
    private Reserva reserva1, reserva2, reserva3, reserva4, reserva5;
    private Livro livro1, livro2, livro3, livro4, livro5;
    private Prazos prazo1, prazo2, prazo3, prazo4, prazo5;

    @BeforeEach
    void setUp() {
        adm1 = DAO.getAdm().criar(new Adm("Admin1", "SenhaAdm1"));
        adm2 = DAO.getAdm().criar(new Adm("Admin2", "SenhaAdm2"));
        adm3 = DAO.getAdm().criar(new Adm("Admin3", "SenhaAdm3"));
        adm4 = DAO.getAdm().criar(new Adm("Admin4", "SenhaAdm4"));
        adm5 = DAO.getAdm().criar(new Adm("Admin5", "SenhaAdm5"));

        bibliotecario1 = DAO.getBibliotecario().criar(new Bibliotecario("Bibliotecario1", "SenhaBib1"));
        bibliotecario2 = DAO.getBibliotecario().criar(new Bibliotecario("Bibliotecario2", "SenhaBib2"));
        bibliotecario3 = DAO.getBibliotecario().criar(new Bibliotecario("Bibliotecario3", "SenhaBib3"));
        bibliotecario4 = DAO.getBibliotecario().criar(new Bibliotecario("Bibliotecario4", "SenhaBib4"));
        bibliotecario5 = DAO.getBibliotecario().criar(new Bibliotecario("Bibliotecario5", "SenhaBib5"));

        leitor1 = DAO.getLeitor().criar(new Leitor("Leitor1","Rua Alameda","75999128840","SenhaLeitor1"));
        leitor2 = DAO.getLeitor().criar(new Leitor("Leitor2","Rua Pitanga","75912345678","SenhaLeitor2"));
        leitor3 = DAO.getLeitor().criar(new Leitor("Leitor3","Rua Pera","75998765432","SenhaLeitor3"));
        leitor4 = DAO.getLeitor().criar(new Leitor("Leitor4","Rua Banana","75987654321","SenhaLeitor4"));
        leitor5 = DAO.getLeitor().criar(new Leitor("Leitor5","Rua Laranja","75955555555","SenhaLeitor5"));

        livro1 = DAO.getLivro().criar(new Livro("LIVRO1","AUTOR1","EDITORA1",2001,"CATEGORIA1"));
        livro2 = DAO.getLivro().criar(new Livro("LIVRO2","AUTOR2","EDITORA2",2002,"CATEGORIA2"));
        livro3 = DAO.getLivro().criar(new Livro("LIVRO3","AUTOR3","EDITORA3",2003,"CATEGORIA3"));
        livro4 = DAO.getLivro().criar(new Livro("LIVRO4","AUTOR4","EDITORA4",2004,"CATEGORIA4"));
        livro5 = DAO.getLivro().criar(new Livro("LIVRO5","AUTOR5","EDITORA5",2005,"CATEGORIA5"));

        reserva1 = DAO.getReserva().criar(new Reserva(livro1,leitor1));
        reserva5 = DAO.getReserva().criar(new Reserva(livro1,leitor3));
        reserva2 = DAO.getReserva().criar(new Reserva(livro2,leitor1));
        reserva4 = DAO.getReserva().criar(new Reserva(livro2,leitor2));
        reserva3 = DAO.getReserva().criar(new Reserva(livro4,leitor2));

        prazo1 = DAO.getPrazos().criar(new Prazos(leitor1, livro1));
        prazo2 = DAO.getPrazos().criar(new Prazos(leitor1, livro2));
        prazo3 = DAO.getPrazos().criar(new Prazos(leitor2, livro4));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitor().removerTodos();
        DAO.getAdm().removerTodos();
        DAO.getBibliotecario().removerTodos();
        DAO.getEmprestimo().removerTodos();
        DAO.getReserva().removerTodos();
        DAO.getLivro().removerTodos();
        DAO.getPrazos().removerTodos();
    }

    @Test
    void checarLoginADM() throws UsuarioSenhaIncorreta, ObjetoInvalido {
        Adm esperado = this.adm1;

        assertThrows(ObjetoInvalido.class, ()-> Sistema.checarLoginADM(1000, "senhaadm1"));
        assertThrows(UsuarioSenhaIncorreta.class, ()-> Sistema.checarLoginADM(1001, "senha errada"));
        assertEquals(esperado, Sistema.checarLoginADM(1001,"senhaadm1"));
    }

    @Test
    void checarLoginBibliotecario() throws UsuarioSenhaIncorreta, ObjetoInvalido {
        Bibliotecario esperado = this.bibliotecario1;

        assertThrows(ObjetoInvalido.class, ()-> Sistema.checarLoginBibliotecario(2000, "senhabib1"));
        assertThrows(UsuarioSenhaIncorreta.class, ()-> Sistema.checarLoginBibliotecario(1002, "senha errada"));
        assertEquals(esperado, Sistema.checarLoginBibliotecario(1002,"senhabib1"));
    }

    @Test
    void checarLoginLeitor() throws UsuarioSenhaIncorreta, ObjetoInvalido {
        Leitor esperado = this.leitor1;

        assertThrows(ObjetoInvalido.class, ()-> Sistema.checarLoginLeitor(3000, "senhaleitor1"));
        assertThrows(UsuarioSenhaIncorreta.class, ()-> Sistema.checarLoginLeitor(1003, "senha errada"));
        assertEquals(esperado, Sistema.checarLoginLeitor(1003,"senhaleitor1"));
    }

    @Test
    void aplicarMulta() {
        Emprestimo emp1 = new Emprestimo(this.livro1,this.leitor1);
        Emprestimo emp2 = new Emprestimo(this.livro2,this.leitor2);
        Emprestimo emp3 = new Emprestimo(this.livro3,this.leitor3);

        emp1.setdataPrevista(LocalDate.now());
        emp2.setdataPrevista(LocalDate.now().minusDays(2));
        emp3.setdataPrevista(LocalDate.now().minusDays(3));

        DAO.getEmprestimo().criar(emp1);
        DAO.getEmprestimo().criar(emp2);
        DAO.getEmprestimo().criar(emp3);

        assertNull(this.leitor1.getDataMulta());
        assertNull(this.leitor2.getDataMulta());
        assertNull(this.leitor3.getDataMulta());

        Sistema.aplicarMulta(emp1);
        Sistema.aplicarMulta(emp2);
        Sistema.aplicarMulta(emp3);

        assertNull(this.leitor1.getDataMulta());
        assertEquals(this.leitor2.getDataMulta(), LocalDate.now().plusDays(4));
        assertEquals(this.leitor3.getDataMulta(), LocalDate.now().plusDays(6));
    }

    @Test
    void verificarMultasLeitores() {
        this.leitor1.setDataMulta(LocalDate.now().minusDays(1));
        this.leitor2.setDataMulta(LocalDate.now().minusDays(2));
        this.leitor3.setDataMulta(LocalDate.now().minusDays(3));

        //ps: trocar a data por um dia anterior à data da realização do teste
        assertEquals(this.leitor1.getDataMulta(), LocalDate.of(2023, 9, 17));
        assertEquals(this.leitor2.getDataMulta(), LocalDate.of(2023, 9, 16));
        assertEquals(this.leitor3.getDataMulta(), LocalDate.of(2023, 9, 15));

        Sistema.verificarMultasLeitores();

        assertNull(this.leitor1.getDataMulta());
        assertNull(this.leitor2.getDataMulta());
        assertNull(this.leitor3.getDataMulta());
    }

    @Test
    void verificarPrazosEReservas() {
        assertEquals(3, DAO.getPrazos().encontrarTodos().size());
        assertEquals(5, DAO.getReserva().encontrarTodos().size());

        this.prazo1.setDataLimite(LocalDate.now().minusDays(1));
        this.prazo2.setDataLimite(LocalDate.now().minusDays(1));
        this.prazo3.setDataLimite(LocalDate.now().minusDays(1));

        Sistema.verificarPrazosEReservas();

        assertEquals(2, DAO.getPrazos().encontrarTodos().size());
        assertEquals(2, DAO.getReserva().encontrarTodos().size());
    }

    @Test
    void adicionarPrazoParaTop2reserva() {
        assertNull(DAO.getPrazos().encontrarPorId(this.leitor3.getID()));
        assertEquals(2, DAO.getPrazos().prazosDeUmLeitor(this.leitor1.getID()).size());

        Sistema.adicionarPrazoParaTop2reserva(this.leitor1.getID());

        assertEquals(0, DAO.getPrazos().prazosDeUmLeitor(this.leitor1.getID()).size());
        assertEquals(LocalDate.now().plusDays(2),DAO.getPrazos().encontrarPorId(this.leitor3.getID()).getDataLimite());
    }
}