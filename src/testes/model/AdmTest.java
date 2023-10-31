package testes.model;

import dao.DAO;

import erros.leitor.LeitorNaoMultado;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.objetos.ObjetoInvalido;

import model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AdmTest {

    private Adm adm1;
    private Bibliotecario bibliotecario1;
    private Livro livro1, livro2, livro3, livro4, livro5, livro6, livro7, livro8,
            livro9, livro10, livro11, livro12, livro13, livro14, livro15;
    private Leitor leitor1, leitor2, leitor3, leitor4, leitor5;
    private Emprestimo emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8;
    private Reserva reserva1,reserva2,reserva3,reserva4,reserva5;

    @BeforeEach
    void setUp() {
        DAO.getAdm().alteraParaPastaTeste();
        DAO.getEmprestimo().alteraParaPastaTeste();
        DAO.getReserva().alteraParaPastaTeste();
        DAO.getLivro().alteraParaPastaTeste();
        DAO.getLeitor().alteraParaPastaTeste();
        DAO.getBibliotecario().alteraParaPastaTeste();
        DAO.getPrazos().alteraParaPastaTeste();

        adm1 = DAO.getAdm().criar(new Adm("ADM1","SENHAADM1"));

        bibliotecario1 = DAO.getBibliotecario().criar(new Bibliotecario("BIBLIOTECARIO1","SENHABIB1"));

        livro1 = DAO.getLivro().criar(new Livro("LIVRO1", "AUTOR1", "EDITORA1", 2000, "CATEGORIA1"));
        livro2 = DAO.getLivro().criar(new Livro("LIVRO2", "AUTOR2", "EDITORA2", 2001, "CATEGORIA2"));
        livro3 = DAO.getLivro().criar(new Livro("LIVRO3", "AUTOR3", "EDITORA3", 2002, "CATEGORIA3"));
        livro4 = DAO.getLivro().criar(new Livro("LIVRO4", "AUTOR4", "EDITORA4", 2003, "CATEGORIA4"));
        livro5 = DAO.getLivro().criar(new Livro("LIVRO5", "AUTOR5", "EDITORA5", 2004, "CATEGORIA5"));
        livro6 = DAO.getLivro().criar(new Livro("LIVRO6", "AUTOR6", "EDITORA6", 2005, "CATEGORIA6"));
        livro7 = DAO.getLivro().criar(new Livro("LIVRO7", "AUTOR7", "EDITORA7", 2006, "CATEGORIA7"));
        livro8 = DAO.getLivro().criar(new Livro("LIVRO8", "AUTOR8", "EDITORA8", 2007, "CATEGORIA8"));
        livro9 = DAO.getLivro().criar(new Livro("LIVRO9", "AUTOR9", "EDITORA9", 2008, "CATEGORIA9"));
        livro10 = DAO.getLivro().criar(new Livro("LIVRO10", "AUTOR10", "EDITORA10", 2009, "CATEGORIA10"));
        livro11 = DAO.getLivro().criar(new Livro("LIVRO11", "AUTOR11", "EDITORA11", 2010, "CATEGORIA11"));
        livro12 = DAO.getLivro().criar(new Livro("LIVRO12", "AUTOR12", "EDITORA12", 2011, "CATEGORIA12"));
        livro13 = DAO.getLivro().criar(new Livro("LIVRO13", "AUTOR13", "EDITORA13", 2012, "CATEGORIA13"));
        livro14 = DAO.getLivro().criar(new Livro("LIVRO14", "AUTOR14", "EDITORA14", 2013, "CATEGORIA14"));
        livro15 = DAO.getLivro().criar(new Livro("LIVRO15", "AUTOR15", "EDITORA15", 2014, "CATEGORIA15"));

        leitor1 = DAO.getLeitor().criar(new Leitor("Leitor1","Rua Alameda","75999128840","SenhaLeitor1"));
        leitor2 = DAO.getLeitor().criar(new Leitor("Leitor2","Rua Pitanga","75912345678","SenhaLeitor2"));
        leitor3 = DAO.getLeitor().criar(new Leitor("Leitor3","Rua Pera","75998765432","SenhaLeitor3"));
        leitor4 = DAO.getLeitor().criar(new Leitor("Leitor4","Rua Banana","75987654321","SenhaLeitor4"));
        leitor5 = DAO.getLeitor().criar(new Leitor("Leitor5","Rua Laranja","75955555555","SenhaLeitor5"));

        emp1 = DAO.getEmprestimo().criar(new Emprestimo(this.livro1.getISBN(), this.leitor1.getID()));
        emp2 = DAO.getEmprestimo().criar(new Emprestimo(this.livro2.getISBN(), this.leitor1.getID()));
        emp3 = DAO.getEmprestimo().criar(new Emprestimo(this.livro3.getISBN(), this.leitor5.getID()));
        emp4 = DAO.getEmprestimo().criar(new Emprestimo(this.livro4.getISBN(), this.leitor1.getID()));
        emp5 = DAO.getEmprestimo().criar(new Emprestimo(this.livro5.getISBN(), this.leitor1.getID()));
        emp6 = DAO.getEmprestimo().criar(new Emprestimo(this.livro6.getISBN(), this.leitor2.getID()));
        emp7 = DAO.getEmprestimo().criar(new Emprestimo(this.livro7.getISBN(), this.leitor1.getID()));
        emp8 = DAO.getEmprestimo().criar(new Emprestimo(this.livro8.getISBN(), this.leitor1.getID()));

        reserva1 = DAO.getReserva().criar(new Reserva(livro1.getISBN(),leitor1.getID()));
        reserva2 = DAO.getReserva().criar(new Reserva(livro1.getISBN(),leitor2.getID()));
        reserva3 = DAO.getReserva().criar(new Reserva(livro2.getISBN(),leitor3.getID()));
        reserva4 = DAO.getReserva().criar(new Reserva(livro3.getISBN(),leitor4.getID()));
        reserva5 = DAO.getReserva().criar(new Reserva(livro4.getISBN(),leitor5.getID()));
    }

    @AfterEach
    void tearDown() {
        DAO.getAdm().removerTodos();
        DAO.getEmprestimo().removerTodos();
        DAO.getReserva().removerTodos();
        DAO.getLivro().removerTodos();
        DAO.getLeitor().removerTodos();
        DAO.getBibliotecario().removerTodos();
        DAO.getPrazos().removerTodos();

        DAO.getAdm().alteraParaPastaPrincipal();
        DAO.getEmprestimo().alteraParaPastaPrincipal();
        DAO.getReserva().alteraParaPastaPrincipal();
        DAO.getLivro().alteraParaPastaPrincipal();
        DAO.getLeitor().alteraParaPastaPrincipal();
        DAO.getBibliotecario().alteraParaPastaPrincipal();
        DAO.getPrazos().alteraParaPastaPrincipal();
    }

    @Test
    void removerLeitor() throws LeitorTemEmprestimo {
        assertThrows(LeitorTemEmprestimo.class, ()->Adm.removerLeitor(1003));
        DAO.getEmprestimo().removerTodos();

        assertEquals(1, DAO.getReserva().encontrarReservasLeitor(1003).size());
        assertNotNull(DAO.getLeitor().encontrarPorId(1003));

        Adm.removerLeitor(1003);

        assertNull(DAO.getLeitor().encontrarPorId(1003));
        assertTrue(DAO.getReserva().encontrarReservasLeitor(1003).isEmpty());
    }

    @Test
    void atualizarDadosLeitor() throws ObjetoInvalido {
        Leitor leitor10 = new Leitor("Leitor10","Rua Alameda10","759991288410","SenhaLeitor10");

        assertThrows(ObjetoInvalido.class, ()->Adm.atualizarDadosLeitor(leitor10));

        leitor10.setID(1003);

        assertEquals("leitor1", DAO.getLeitor().encontrarPorId(1003).getNome());

        Adm.atualizarDadosLeitor(leitor10);

        assertEquals("leitor10", DAO.getLeitor().encontrarPorId(1003).getNome());
    }

    @Test
    void removerBibliotecario(){
        assertNotNull(DAO.getBibliotecario().encontrarPorId(1002));

        Adm.removerBibliotecario(1002);

        assertNull(DAO.getBibliotecario().encontrarPorId(1002));
    }

    @Test
    void atualizarDadosBibliotecario() throws ObjetoInvalido {
        Bibliotecario bibliotecario2 = new Bibliotecario("BIBLIOTECARIO2","SENHABIB2");

        assertThrows(ObjetoInvalido.class, ()->Adm.atualizarDadosBibliotecario(bibliotecario2));

        bibliotecario2.setID(1002);

        assertEquals("bibliotecario1", DAO.getBibliotecario().encontrarPorId(1002).getNome());

        Adm.atualizarDadosBibliotecario(bibliotecario2);

        assertEquals("bibliotecario2", DAO.getBibliotecario().encontrarPorId(1002).getNome());
    }

    @Test
    void removerAdm(){
        assertNotNull(DAO.getAdm().encontrarPorId(1001));

        Adm.removerAdm(1001);

        assertNull(DAO.getAdm().encontrarPorId(1001));
    }

    @Test
    void atualizarDadosAdministrador() throws ObjetoInvalido{
        Adm adm2 = new Adm("ADM2","SENHAADM2");

        assertThrows(ObjetoInvalido.class, ()->Adm.atualizarDadosAdministrador(adm2));

        adm2.setID(1001);

        assertEquals("adm1", DAO.getAdm().encontrarPorId(1001).getNome());

        Adm.atualizarDadosAdministrador(adm2);

        assertEquals("adm2", DAO.getAdm().encontrarPorId(1001).getNome());
    }

    @Test
    void bloquearLeitor(){
        assertEquals(1, DAO.getReserva().encontrarReservasLeitor(1003).size());
        assertFalse(DAO.getLeitor().encontrarPorId(1003).getBloqueado());

        Adm.bloquearLeitor(1003);

        assertTrue(DAO.getReserva().encontrarReservasLeitor(1003).isEmpty());
        assertTrue(DAO.getLeitor().encontrarPorId(1003).getBloqueado());
    }

    @Test
    void desbloquearLeitor(){
        this.leitor1.setBloqueado(true);
        DAO.getLeitor().atualizar(this.leitor1);
        assertTrue(DAO.getLeitor().encontrarPorId(1003).getBloqueado());

        Adm.desbloquearLeitor(1003);
        assertFalse(DAO.getLeitor().encontrarPorId(1003).getBloqueado());
    }

    @Test
    void tirarMulta() throws ObjetoInvalido, LeitorNaoMultado {
        assertThrows(ObjetoInvalido.class, ()->Adm.tirarMulta(666));

        assertThrows(LeitorNaoMultado.class, ()->Adm.tirarMulta(1003));

        this.leitor1.setDataMulta(LocalDate.now());
        DAO.getLeitor().atualizar(this.leitor1);
        assertNotNull(DAO.getLeitor().encontrarPorId(1003).getDataMulta());

        Adm.tirarMulta(1003);

        assertNull(DAO.getLeitor().encontrarPorId(1003).getDataMulta());
    }

    @Test
    void removerUmLivro()  throws LivroEmprestado {
        assertThrows(LivroEmprestado.class, ()->Adm.removerUmLivro(this.livro1.getISBN()));
        DAO.getEmprestimo().removerTodos();

        assertNotNull(DAO.getLivro().encontrarPorISBN(this.livro1.getISBN()));
        assertEquals(2, DAO.getReserva().encontrarReservasLivro(this.livro1.getISBN()).size());

        Adm.removerUmLivro(this.livro1.getISBN());

        assertNull(DAO.getLivro().encontrarPorISBN(this.livro1.getISBN()));
        assertTrue(DAO.getReserva().encontrarReservasLivro(this.livro1.getISBN()).isEmpty());
    }

    @Test
    void atualizarDadosLivro() throws LivroEmprestado {
        Livro livro20 = new Livro("LIVRO20","AUTOR20","EDITORA20",2020,"CATEGORIA20");
        livro20.setISBN(this.livro1.getISBN());

        //livro ta emprestado
        assertThrows(LivroEmprestado.class, ()->Adm.atualizarDadosLivro(livro20));

        DAO.getEmprestimo().removerTodos();
        assertEquals(2, DAO.getReserva().encontrarReservasLivro(this.livro1.getISBN()).size());
        assertTrue(DAO.getLivro().encontrarPorISBN(this.livro1.getISBN()).getDisponivel());
        livro20.setDisponivel(false);

        Adm.atualizarDadosLivro(livro20);

        assertTrue(DAO.getReserva().encontrarReservasLivro(this.livro1.getISBN()).isEmpty());
        assertFalse(DAO.getLivro().encontrarPorISBN(this.livro1.getISBN()).getDisponivel());

        assertEquals("livro20", DAO.getLivro().encontrarPorISBN(this.livro1.getISBN()).getTitulo());
    }

    @Test
    void historicoEmprestimoDeUmLeitor() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()->Adm.historicoEmprestimoDeUmLeitor(666));

        assertEquals(6, Adm.historicoEmprestimoDeUmLeitor(1003).size());
        Leitor teste = DAO.getLeitor().encontrarPorId(Adm.historicoEmprestimoDeUmLeitor(1003).get(0).getIDleitor());
        Leitor teste2 = DAO.getLeitor().encontrarPorId(Adm.historicoEmprestimoDeUmLeitor(1003).get(2).getIDleitor());
        Leitor teste3 = DAO.getLeitor().encontrarPorId(Adm.historicoEmprestimoDeUmLeitor(1003).get(4).getIDleitor());
        assertEquals(this.leitor1, teste);
        assertEquals(this.leitor1, teste2);
        assertEquals(this.leitor1, teste3);
    }

    @Test
    void livrosMaisPopulares() {
        livro5.setQtdEmprestimo(30);
        livro8.setQtdEmprestimo(25);
        livro10.setQtdEmprestimo(22);
        livro2.setQtdEmprestimo(20);
        livro9.setQtdEmprestimo(17);
        livro4.setQtdEmprestimo(15);
        livro15.setQtdEmprestimo(14);
        livro11.setQtdEmprestimo(13);
        livro1.setQtdEmprestimo(12);
        livro6.setQtdEmprestimo(11);
        livro13.setQtdEmprestimo(10);
        livro12.setQtdEmprestimo(9);
        livro14.setQtdEmprestimo(8);
        livro7.setQtdEmprestimo(7);
        livro3.setQtdEmprestimo(6);

        assertEquals(10,Adm.livrosMaisPopulares().size());
        assertEquals(this.livro5,Adm.livrosMaisPopulares().get(0));
        assertEquals(this.livro8,Adm.livrosMaisPopulares().get(1));
        assertEquals(this.livro10,Adm.livrosMaisPopulares().get(2));
        assertEquals(this.livro2,Adm.livrosMaisPopulares().get(3));
        assertEquals(this.livro9,Adm.livrosMaisPopulares().get(4));
        assertEquals(this.livro4,Adm.livrosMaisPopulares().get(5));
        assertEquals(this.livro15,Adm.livrosMaisPopulares().get(6));
        assertEquals(this.livro11,Adm.livrosMaisPopulares().get(7));
        assertEquals(this.livro1,Adm.livrosMaisPopulares().get(8));
        assertEquals(this.livro6,Adm.livrosMaisPopulares().get(9));
    }

    @Test
    void leitoresBloqueados(){
        assertTrue(Adm.leitoresBloqueados().isEmpty());

        this.leitor1.setBloqueado(true);
        this.leitor2.setBloqueado(true);
        this.leitor3.setBloqueado(true);
        this.leitor4.setBloqueado(true);
        DAO.getLeitor().atualizar(this.leitor1);
        DAO.getLeitor().atualizar(this.leitor2);
        DAO.getLeitor().atualizar(this.leitor3);
        DAO.getLeitor().atualizar(this.leitor4);

        assertEquals(4,Adm.leitoresBloqueados().size());
    }
}