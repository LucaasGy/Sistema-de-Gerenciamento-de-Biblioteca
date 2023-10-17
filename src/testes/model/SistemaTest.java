package testes.model;

import dao.DAO;

import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroNaoPossuiEmprestimo;
import erros.livro.LivroReservado;
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
        DAO.getLeitor().alteraParaPastaTeste();
        DAO.getAdm().alteraParaPastaTeste();
        DAO.getBibliotecario().alteraParaPastaTeste();
        DAO.getReserva().alteraParaPastaTeste();
        DAO.getLivro().alteraParaPastaTeste();
        DAO.getPrazos().alteraParaPastaTeste();
        DAO.getEmprestimo().alteraParaPastaTeste();

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

        reserva1 = DAO.getReserva().criar(new Reserva(livro1.getISBN(),leitor1.getID()));
        reserva5 = DAO.getReserva().criar(new Reserva(livro1.getISBN(),leitor3.getID()));
        reserva2 = DAO.getReserva().criar(new Reserva(livro2.getISBN(),leitor1.getID()));
        reserva4 = DAO.getReserva().criar(new Reserva(livro2.getISBN(),leitor2.getID()));
        reserva3 = DAO.getReserva().criar(new Reserva(livro4.getISBN(),leitor2.getID()));

        prazo1 = DAO.getPrazos().criar(new Prazos(leitor1.getID(), livro1.getISBN()));
        prazo2 = DAO.getPrazos().criar(new Prazos(leitor1.getID(), livro2.getISBN()));
        prazo3 = DAO.getPrazos().criar(new Prazos(leitor2.getID(), livro4.getISBN()));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitor().removerTodos();
        DAO.getAdm().removerTodos();
        DAO.getBibliotecario().removerTodos();
        DAO.getReserva().removerTodos();
        DAO.getLivro().removerTodos();
        DAO.getPrazos().removerTodos();

        DAO.getLeitor().alteraParaPastaPrincipal();
        DAO.getAdm().alteraParaPastaPrincipal();
        DAO.getBibliotecario().alteraParaPastaPrincipal();
        DAO.getReserva().alteraParaPastaPrincipal();
        DAO.getLivro().alteraParaPastaPrincipal();
        DAO.getPrazos().alteraParaPastaPrincipal();
        DAO.getEmprestimo().alteraParaPastaPrincipal();
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
        Emprestimo emp1 = new Emprestimo(this.livro1.getISBN(),this.leitor1.getID());
        Emprestimo emp2 = new Emprestimo(this.livro2.getISBN(),this.leitor2.getID());
        Emprestimo emp3 = new Emprestimo(this.livro3.getISBN(),this.leitor3.getID());
        emp1.setdataPrevista(LocalDate.now());
        emp2.setdataPrevista(LocalDate.now().minusDays(2));
        emp3.setdataPrevista(LocalDate.now().minusDays(3));
        DAO.getEmprestimo().criar(emp1);
        DAO.getEmprestimo().criar(emp2);
        DAO.getEmprestimo().criar(emp3);

        //nenhum leitor está multado (leitor 2 e 3 possuem empréstimo em atraso)
        assertNull(this.leitor1.getDataMulta());
        assertNull(this.leitor2.getDataMulta());
        assertNull(this.leitor3.getDataMulta());

        //checando quem possui empréstimo em atraso
        Sistema.aplicarMulta(emp1);
        Sistema.aplicarMulta(emp2);
        Sistema.aplicarMulta(emp3);

        //leitor1 continua sem multa, leitor 2 e 3 possuem multa equivalente ao dobro de dias de atraso
        assertNull(this.leitor1.getDataMulta());
        assertEquals(this.leitor2.getDataMulta(), LocalDate.now().plusDays(4));
        assertEquals(this.leitor3.getDataMulta(), LocalDate.now().plusDays(6));
        DAO.getEmprestimo().removerTodos();
    }

    @Test
    void verificarMultasLeitores() {
        //somente leitor 1 não possui multa vencida
        this.leitor1.setDataMulta(LocalDate.now().plusDays(1));
        this.leitor2.setDataMulta(LocalDate.now().minusDays(2));
        this.leitor3.setDataMulta(LocalDate.now().minusDays(3));

        //ps: trocar a data por um dia posterior à data da realização do teste
        assertEquals(this.leitor1.getDataMulta(), LocalDate.of(2023, 9, 30));
        //ps: trocar a data por dois/tres dias anterior à data da realização do teste
        assertEquals(this.leitor2.getDataMulta(), LocalDate.of(2023, 9, 27));
        assertEquals(this.leitor3.getDataMulta(), LocalDate.of(2023, 9, 26));

        //checa quais leitores possuem multas ativas
        Sistema.verificarMultasLeitores();

        //leitor 2 e 3 tem suas multas retiradas
        assertNotNull(this.leitor1.getDataMulta());
        assertNull(this.leitor2.getDataMulta());
        assertNull(this.leitor3.getDataMulta());
    }

    @Test
    void verificarPrazosEReservas() {
        assertEquals(3, DAO.getPrazos().encontrarTodos().size());
        assertEquals(5, DAO.getReserva().encontrarTodos().size());

        //todos os prazos vencidos, porém livro 1 e 2 possuem leitores na fila de reserva
        this.prazo1.setDataLimite(LocalDate.now().minusDays(1));
        this.prazo2.setDataLimite(LocalDate.now().minusDays(1));
        this.prazo3.setDataLimite(LocalDate.now().minusDays(1));

        //checa prazos vencidos
        Sistema.verificarPrazosEReservas();

        //todos os prazos são removidos, porém como o livro 1 e 2 possuiam leitores na fila de reserva,
        //são criados novos prazos para eles
        assertEquals(2, DAO.getPrazos().encontrarTodos().size());
        //reservas com prazos ativos em atraso, deletadas
        assertEquals(2, DAO.getReserva().encontrarTodos().size());
    }

    @Test
    void adicionarPrazoParaTop2reserva() {
        //top2 da fila de reserva do livro 1 não possui prazo ativo
        assertNull(DAO.getPrazos().encontrarPorId(this.leitor3.getID()));
        //leitor 1 possui 2 prazos para livros diferentes, ambos com atraso
        assertEquals(2, DAO.getPrazos().prazosDeUmLeitor(this.leitor1.getID()).size());

        //checa prazos ativos em atraso do leitor 2 e transfere para o top2 da fila de reserva de um livro,
        //caso tenha um top2
        Sistema.adicionarPrazoParaTop2reserva(this.leitor1.getID());

        //leitor 2 não possui mais prazos ativos
        assertTrue(DAO.getPrazos().prazosDeUmLeitor(this.leitor1.getID()).isEmpty());
        //leitor 3, antigo top2 da fila de reserva do livro 1, agora tem um prazo de 2 dias para realizar
        //o empréstimo do livro 1
        assertEquals(LocalDate.now().plusDays(2),DAO.getPrazos().encontrarPorId(this.leitor3.getID()).getDataLimite());
    }

    @Test
    void pesquisarLivroPorTitulo() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()-> Sistema.pesquisarLivroPorTitulo("naoexiste"));
        assertEquals(1, Sistema.pesquisarLivroPorTitulo("livro1").size());
    }

    @Test
    void pesquisarLivroPorAutor() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()-> Sistema.pesquisarLivroPorAutor("naoexiste"));
        assertEquals(1, Sistema.pesquisarLivroPorAutor("autor3").size());
    }

    @Test
    void pesquisarLivroPorCategoria() throws ObjetoInvalido {
        assertThrows(ObjetoInvalido.class, ()-> Sistema.pesquisarLivroPorCategoria("naoexiste"));
        assertEquals(1, Sistema.pesquisarLivroPorCategoria("categoria5").size());
    }

    @Test
    void pesquisarLivroPorISBN() throws ObjetoInvalido {
        double isbn5 = DAO.getLivro().encontrarTodos().get(4).getISBN();
        Livro esperado = this.livro5;

        assertThrows(ObjetoInvalido.class, ()-> Sistema.pesquisarLivroPorISBN(99999999));
        assertEquals(esperado, Sistema.pesquisarLivroPorISBN(isbn5));
    }

    /*@Test
    void fazerEmprestimo() throws ObjetoInvalido, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimo, LivroReservado, LivroEmprestado {
        DAO.getLivro().removerTodos();
        DAO.getPrazos().removerTodos();
        DAO.getReserva().removerTodos();
        DAO.getLeitor().removerTodos();

        //não existe leitor
        assertThrows(ObjetoInvalido.class, ()-> Sistema.fazerEmprestimo(33,33));

        Leitor leitor1 = new Leitor("LEITOR1", "RUA ALAMEDA","75999128840","SENHA1");
        DAO.getLeitor().criar(leitor1);

        Livro livro1 = new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1");
        DAO.getLivro().criar(livro1);
        DAO.getLivro().encontrarTodos().get(0).setISBN(10);

        //leitor ta bloqueado
        leitor1.setBloqueado(true);
        DAO.getLeitor().atualizar(leitor1);
        assertThrows(LeitorBloqueado.class, ()-> Sistema.fazerEmprestimo(1003,10));
        leitor1.setBloqueado(false);
        DAO.getLeitor().atualizar(leitor1);

        //leitor ta multado
        leitor1.setDataMulta(LocalDate.now());
        DAO.getLeitor().atualizar(leitor1);
        assertThrows(LeitorMultado.class, ()-> Sistema.fazerEmprestimo(1003,10));
        leitor1.setDataMulta(null);
        DAO.getLeitor().atualizar(leitor1);

        //leitor ja possui um emprestimo ativo
        Livro livro2 = new Livro("LIVRO2","AUTOR2","EDITORA2",2000,"CATEGORIA2");
        Livro att = DAO.getLivro().criar(livro2);
        Emprestimo emp2 = new Emprestimo(att.getISBN(),leitor1.getID());
        DAO.getEmprestimo().criar(emp2);
        assertThrows(LeitorTemEmprestimo.class, ()-> Sistema.fazerEmprestimo(1003,10));
        DAO.getEmprestimo().removerTodos();

        //livro possui uma reserva ativa e o top 1 da reserva não é o leitor que tá tentando realizar o emprestimo
        Reserva reserva1 = new Reserva(livro1.getISBN(),leitor2.getID());
        DAO.getReserva().criar(reserva1);
        assertThrows(LivroReservado.class, ()-> Sistema.fazerEmprestimo(1003,10));
        DAO.getReserva().removerTodos();

        ////livro possui uma reserva ativa e o top 1 da reserva é o leitor que tá tentando realizar o emprestimo
        Reserva reserva2 = new Reserva(livro1.getISBN(),leitor1.getID());
        DAO.getReserva().criar(reserva2);
        Prazos prazo1 = new Prazos(leitor1.getID(),livro1.getISBN());
        DAO.getPrazos().criar(prazo1);
        assertEquals(1, DAO.getReserva().encontrarTodos().size());
        assertEquals(1, DAO.getPrazos().encontrarTodos().size());

        //tudo correto, leitor possui reserva ativa do livro que ta tentando fazer o empréstimo
        Sistema.fazerEmprestimo(1003,10);

        assertTrue(DAO.getReserva().encontrarTodos().isEmpty());
        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());

        assertEquals(leitor1, DAO.getLeitor().encontrarPorId(DAO.getEmprestimo().encontrarTodos().get(0).getLeitor()));
    }*/

    @Test
    void devolverLivro() throws LivroNaoPossuiEmprestimo {
        DAO.getLivro().removerTodos();
        DAO.getPrazos().removerTodos();
        DAO.getReserva().removerTodos();

        Livro livro1 = new Livro("LIVRO1","AUTOR1","EDITORA1",2000,"CATEGORIA1");
        DAO.getLivro().criar(livro1);

        //leitor nao possui livro a devolver
        assertThrows(LivroNaoPossuiEmprestimo.class, ()-> Sistema.devolverLivro(livro1.getISBN()));

        //leitor atrasou a devolução do livro
        Leitor leitor1 = new Leitor("LEITOR1", "RUA ALAMEDA","75999128840","SENHA1");
        DAO.getLeitor().criar(leitor1);
        Emprestimo emp1 = new Emprestimo(livro1.getISBN(),leitor1.getID());
        DAO.getEmprestimo().criar(emp1);
        emp1.setdataPrevista(LocalDate.now().minusDays(1));

        //livro devolvido tem reserva
        Leitor leitor2 = new Leitor("LEITOR2", "RUA ALAMEDA2","74999128840","SENHA2");
        Reserva reserva1 = new Reserva(livro1.getISBN(),leitor2.getID());
        DAO.getReserva().criar(reserva1);

        assertNull(leitor1.getDataMulta());
        assertFalse(DAO.getLivro().encontrarTodos().get(0).getDisponivel());
        assertEquals(1,DAO.getLivro().encontrarTodos().get(0).getQtdEmprestimo());
        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());
        assertEquals(2, DAO.getEmprestimo().encontrarTodos().size());

        //tudo correto, leitor atrasou a devolução do livro
        Sistema.devolverLivro(livro1.getISBN());

        assertEquals(LocalDate.now().plusDays(2),leitor1.getDataMulta());
        assertTrue(DAO.getLivro().encontrarTodos().get(0).getDisponivel());
        assertEquals(0,DAO.getLeitor().encontrarTodos().get(0).getLimiteRenova());
        assertEquals(1,DAO.getPrazos().encontrarTodos().size());
        assertTrue(DAO.getEmprestimo().encontrarTodosAtuais().isEmpty());
        assertEquals(2, DAO.getEmprestimo().encontrarTodos().size());
    }
}