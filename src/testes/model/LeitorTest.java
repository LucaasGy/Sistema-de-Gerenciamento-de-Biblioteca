package testes.model;

import dao.DAO;

import erros.leitor.*;
import erros.livro.LivroLimiteDeReservas;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroNaoPossuiEmprestimoNemReserva;
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
        DAO.getLeitor().alteraParaPastaTeste();
        DAO.getLivro().alteraParaPastaTeste();
        DAO.getPrazos().alteraParaPastaTeste();
        DAO.getReserva().alteraParaPastaTeste();
        DAO.getEmprestimo().alteraParaPastaTeste();

        lucas = DAO.getLeitor().criar(new Leitor("lucas","Rua Coronel","75999128840","feijao"));
        guerrasmedias = DAO.getLivro().criar(new Livro("Guerras medias", "Joao Pedro","Sua paz",2000,"Açao"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitor().removerTodos();
        DAO.getLivro().removerTodos();
        DAO.getPrazos().removerTodos();
        DAO.getReserva().removerTodos();

        DAO.getLeitor().alteraParaPastaPrincipal();
        DAO.getLivro().alteraParaPastaPrincipal();
        DAO.getPrazos().alteraParaPastaPrincipal();
        DAO.getReserva().alteraParaPastaPrincipal();
        DAO.getEmprestimo().alteraParaPastaPrincipal();
    }

    @Test
    void renovarEmprestimo() throws LeitorNaoPossuiEmprestimo, LeitorBloqueado,LeitorLimiteDeRenovacao,LivroReservado,LeitorTemEmprestimoEmAtraso{
        DAO.getEmprestimo().alteraParaPastaTeste();

        //leitor bloqueado
        this.lucas.setBloqueado(true);
        assertThrows(LeitorBloqueado.class, ()-> this.lucas.renovarEmprestimo());
        this.lucas.setBloqueado(false);

        //não tem empréstimo ativo a ser renovado em nome do leitor
        assertThrows(LeitorNaoPossuiEmprestimo.class, ()-> this.lucas.renovarEmprestimo());

        //leitor tem empréstimo ativo mas está atrasado
        DAO.getEmprestimo().criar(new Emprestimo(this.guerrasmedias.getISBN(),this.lucas.getID()));
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().minusDays(9));
        assertThrows(LeitorTemEmprestimoEmAtraso.class, ()-> this.lucas.renovarEmprestimo());
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().plusDays(9));

        //livro possui reserva ativa
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),this.lucas.getID()));
        assertThrows(LivroReservado.class, ()-> this.lucas.renovarEmprestimo());
        DAO.getReserva().removerTodos();

        //leitor atingiu limite de renovação
        this.lucas.setLimiteRenova(1);
        assertThrows(LeitorLimiteDeRenovacao.class, ()-> this.lucas.renovarEmprestimo());
        this.lucas.setLimiteRenova(0);

        //data que pegou e data prevista do empréstimo alterada para provar renovação das mesmas
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPegou(LocalDate.now().minusDays(5));
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().plusDays(2));

        //ps: alterar a data para 5 dias antes da data atual do teste
        assertEquals(LocalDate.of(2023,9,24), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPegou());
        //ps: alterar a data para 2 dias depois da data atual do teste
        assertEquals(LocalDate.of(2023,10,1), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPrevista());

        //tudo correto
        this.lucas.renovarEmprestimo();

        assertEquals(1, this.lucas.getLimiteRenova());
        assertNotNull(DAO.getEmprestimo().encontrarPorId(this.lucas.getID()));
        assertEquals(LocalDate.now(), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPegou());
        assertEquals(LocalDate.now().plusDays(7), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPrevista());
        DAO.getEmprestimo().removerTodos();
    }

    @Test
    void reservarLivro() throws ObjetoInvalido, LeitorReservarLivroEmMaos, LivroLimiteDeReservas, LeitorLimiteDeReservas, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimoEmAtraso, LeitorPossuiReservaDesseLivro, LivroNaoDisponivel, LivroNaoPossuiEmprestimoNemReserva {
        //leitor bloqueado
        this.lucas.setBloqueado(true);
        assertThrows(LeitorBloqueado.class, ()-> this.lucas.reservarLivro(333));
        this.lucas.setBloqueado(false);

        //leitor multado
        this.lucas.setDataMulta(LocalDate.now());
        assertThrows(LeitorMultado.class, ()-> this.lucas.reservarLivro(333));
        this.lucas.setDataMulta(null);

        //livro não existe
        assertThrows(ObjetoInvalido.class, ()-> this.lucas.reservarLivro(333));

        //leitor possui um empréstimo ativo em atraso
        Livro livro2 = DAO.getLivro().criar(new Livro("LIVRO2","AUTOR2","EDITORA2",2000,"CATEGORIA2"));
        livro2.setISBN(20);
        Emprestimo emp1 = new Emprestimo(DAO.getLivro().atualizar(livro2).getISBN(),this.lucas.getID());
        DAO.getEmprestimo().criar(emp1);
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().minusDays(1));
        assertThrows(LeitorTemEmprestimoEmAtraso.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getEmprestimo().removerTodos();

        //livro nao ta disponivel (adm alterou disponibilidade)
        this.guerrasmedias.setDisponivel(false);
        assertThrows(LivroNaoDisponivel.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        this.guerrasmedias.setDisponivel(true);

        //leitor tentando reservar livro que ele ta em mãos
        Emprestimo emp2 = new Emprestimo(this.guerrasmedias.getISBN(),this.lucas.getID());
        DAO.getEmprestimo().criar(emp2);
        assertThrows(LeitorReservarLivroEmMaos.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getEmprestimo().removerTodos();

        //leitor tentando reservar livro que ele já possui reserva ativa
        Reserva reserva1 = new Reserva(this.guerrasmedias.getISBN(),this.lucas.getID());
        DAO.getReserva().criar(reserva1);
        assertThrows(LeitorPossuiReservaDesseLivro.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getReserva().removerTodos();

        //livro atingiu limites de reservas
        Leitor leitor2 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        Leitor leitor3 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        Leitor leitor4 = new Leitor("Pedro", "Rua das Flores", "98765432", "abcd1234");
        Leitor leitor5 = new Leitor("Mariana", "Avenida Principal", "55555555", "senhamariana");
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),leitor2.getID()));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),leitor3.getID()));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),leitor4.getID()));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),leitor5.getID()));
        assertThrows(LivroLimiteDeReservas.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getReserva().removerTodos();

        //leitor atingiu limites de reservas
        Livro livro3 = new Livro("As cronicas","Jorge","Sua paz",2000,"Fantasia");
        Livro livro4 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        Livro livro5 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");
        DAO.getReserva().criar(new Reserva(livro3.getISBN(),this.lucas.getID()));
        DAO.getReserva().criar(new Reserva(livro4.getISBN(),this.lucas.getID()));
        DAO.getReserva().criar(new Reserva(livro5.getISBN(),this.lucas.getID()));
        assertThrows(LeitorLimiteDeReservas.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getReserva().removerTodos();

        //livro está disponivel e sem reservas ativas (leitor deve fazer empréstimo não reserva)
        assertThrows(LivroNaoPossuiEmprestimoNemReserva.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));

        //livro possui uma reserva ativa, tudo correto
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),leitor2.getID()));
        this.lucas.reservarLivro(this.guerrasmedias.getISBN());

        //nova reserva criada
        assertEquals(2,DAO.getReserva().encontrarTodos().size());
        assertEquals(this.lucas,DAO.getLeitor().encontrarPorId(DAO.getReserva().encontrarReservasLeitor(this.lucas.getID()).get(0).getLeitor()));
        DAO.getEmprestimo().removerTodos();
    }

    @Test
    void retirarReserva() throws ObjetoInvalido, LeitorNaoPossuiReservaDesseLivro {
        //livro nao existe
        assertThrows(ObjetoInvalido.class, ()-> this.lucas.retirarReserva(889));

        //leitor nao reservou o livro
        assertThrows(LeitorNaoPossuiReservaDesseLivro.class, ()-> this.lucas.retirarReserva(this.guerrasmedias.getISBN()));

        //reserva criada do leitor com o livro e ele é o top 1 da fila de reservas, logo possui prazo ativo
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),this.lucas.getID()));
        DAO.getPrazos().criar(new Prazos(this.lucas.getID(),this.guerrasmedias.getISBN()));
        Leitor novo = DAO.getLeitor().criar(new Leitor("novo","33","333","ss"));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias.getISBN(),novo.getID()));

        //top 2 da fila de reserva não possui prazo e top1 da fila ( vai retirar a reserva ) possui prazo
        assertTrue(DAO.getPrazos().prazosDeUmLeitor(novo.getID()).isEmpty());
        assertEquals(this.lucas, DAO.getLeitor().encontrarPorId(DAO.getPrazos().encontrarPrazoDeUmLivro(this.guerrasmedias.getISBN()).getLeitor()));

        //tudo correto
        this.lucas.retirarReserva(this.guerrasmedias.getISBN());

        //antigo top 1 da fila de reserva não possui mais prazo ativo e antigo top 2 agora possui um prazo ativo
        assertTrue(DAO.getPrazos().prazosDeUmLeitor(this.lucas.getID()).isEmpty());
        assertEquals(novo, DAO.getLeitor().encontrarPorId(DAO.getPrazos().encontrarPrazoDeUmLivro(this.guerrasmedias.getISBN()).getLeitor()));
    }
}