package testes.model;

import dao.DAO;

import erros.leitor.*;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;
import model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LeitorTest {

    private Leitor lucas;
    private Livro guerrasmedias;

    @BeforeEach
    void setUp() {
        lucas = DAO.getLeitor().criar(new Leitor("lucas","Rua Coronel","75999128840","feijao"));
        guerrasmedias = DAO.getLivro().criar(new Livro("Guerras medias", "Joao Pedro","Sua paz",2000,"Açao"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitor().removerTodos();
        DAO.getLivro().removerTodos();
        DAO.getEmprestimo().removerTodos();
        DAO.getPrazos().removerTodos();
        DAO.getReserva().removerTodos();
    }

    @Test
    void renovarEmprestimo() throws LeitorNaoPossuiEmprestimo, LeitorBloqueado,LeitorLimiteDeRenovacao,LivroReservado,LeitorTemEmprestimoEmAtraso{
        assertThrows(LeitorNaoPossuiEmprestimo.class, ()-> this.lucas.renovarEmprestimo());

        DAO.getEmprestimo().criar(new Emprestimo(this.guerrasmedias,this.lucas));

        this.lucas.setBloqueado(true);
        assertThrows(LeitorBloqueado.class, ()-> this.lucas.renovarEmprestimo());
        this.lucas.setBloqueado(false);

        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().minusDays(9));
        assertThrows(LeitorTemEmprestimoEmAtraso.class, ()-> this.lucas.renovarEmprestimo());
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().plusDays(9));

        DAO.getReserva().criar(new Reserva(this.guerrasmedias,this.lucas));
        assertThrows(LivroReservado.class, ()-> this.lucas.renovarEmprestimo());
        DAO.getReserva().removerTodos();

        this.lucas.setLimiteRenova(1);
        assertThrows(LeitorLimiteDeRenovacao.class, ()-> this.lucas.renovarEmprestimo());
        this.lucas.setLimiteRenova(0);

        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPegou(LocalDate.now().minusDays(5));
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().plusDays(2));

        //ps: alterar a data para 5 dias antes da data atual do teste
        assertEquals(LocalDate.of(2023,9,13), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPegou());
        //ps: alterar a data para 2 dias depois da data atual do teste
        assertEquals(LocalDate.of(2023,9,20), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPrevista());

        this.lucas.renovarEmprestimo();

        assertEquals(1, this.lucas.getLimiteRenova());
        assertNotNull(DAO.getEmprestimo().encontrarPorId(this.lucas.getID()));
        assertEquals(LocalDate.now(), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPegou());
        assertEquals(LocalDate.now().plusDays(7), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPrevista());
    }

    @Test
    void reservarLivro() {

    }

    @Test
    void retirarReserva() throws ObjetoInvalido, LeitorNaoPossuiReservaDesseLivro {
        assertThrows(ObjetoInvalido.class, ()-> this.lucas.retirarReserva(889));

        assertThrows(LeitorNaoPossuiReservaDesseLivro.class, ()-> this.lucas.retirarReserva(this.guerrasmedias.getISBN()));

        DAO.getReserva().criar(new Reserva(this.guerrasmedias,this.lucas));

        Leitor novo = new Leitor("novo","33","333","ss");
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,novo));
        DAO.getPrazos().criar(new Prazos(this.lucas,this.guerrasmedias));

        assertEquals(0,DAO.getPrazos().prazosDeUmLeitor(novo.getID()).size());
        assertEquals(this.lucas, DAO.getPrazos().encontrarPrazoDeUmLivro(this.guerrasmedias.getISBN()).getLeitor());

        this.lucas.retirarReserva(this.guerrasmedias.getISBN());

        assertEquals(0,DAO.getPrazos().prazosDeUmLeitor(this.lucas.getID()).size());
        assertEquals(novo, DAO.getPrazos().encontrarPrazoDeUmLivro(this.guerrasmedias.getISBN()).getLeitor());
    }
}