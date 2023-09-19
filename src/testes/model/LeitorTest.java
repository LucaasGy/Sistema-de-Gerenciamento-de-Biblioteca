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
        //leitor bloqueado
        this.lucas.setBloqueado(true);
        assertThrows(LeitorBloqueado.class, ()-> this.lucas.renovarEmprestimo());
        this.lucas.setBloqueado(false);

        //não tem empréstimo ativo a ser renovado em nome do leitor
        assertThrows(LeitorNaoPossuiEmprestimo.class, ()-> this.lucas.renovarEmprestimo());

        //leitor tem empréstimo ativo mas está atrasado
        DAO.getEmprestimo().criar(new Emprestimo(this.guerrasmedias,this.lucas));
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().minusDays(9));
        assertThrows(LeitorTemEmprestimoEmAtraso.class, ()-> this.lucas.renovarEmprestimo());
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().plusDays(9));

        //livro possui reserva ativa
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,this.lucas));
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
        assertEquals(LocalDate.of(2023,9,13), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPegou());
        //ps: alterar a data para 2 dias depois da data atual do teste
        assertEquals(LocalDate.of(2023,9,20), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPrevista());

        //tudo correto
        this.lucas.renovarEmprestimo();

        assertEquals(1, this.lucas.getLimiteRenova());
        assertNotNull(DAO.getEmprestimo().encontrarPorId(this.lucas.getID()));
        assertEquals(LocalDate.now(), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPegou());
        assertEquals(LocalDate.now().plusDays(7), DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).getdataPrevista());
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
        Livro livro2 = new Livro("LIVRO2","AUTOR2","EDITORA2",2000,"CATEGORIA2");
        DAO.getLivro().criar(livro2);
        DAO.getLivro().encontrarTodos().get(1).setISBN(20);
        Emprestimo emp1 = new Emprestimo(livro2,this.lucas);
        DAO.getEmprestimo().criar(emp1);
        DAO.getEmprestimo().encontrarPorId(this.lucas.getID()).setdataPrevista(LocalDate.now().minusDays(1));
        assertThrows(LeitorTemEmprestimoEmAtraso.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getEmprestimo().removerTodos();

        //livro nao ta disponivel (adm alterou disponibilidade)
        this.guerrasmedias.setDisponivel(false);
        assertThrows(LivroNaoDisponivel.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        this.guerrasmedias.setDisponivel(true);

        //leitor tentando reservar livro que ele ta em mãos
        Emprestimo emp2 = new Emprestimo(this.guerrasmedias,this.lucas);
        DAO.getEmprestimo().criar(emp2);
        assertThrows(LeitorReservarLivroEmMaos.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getEmprestimo().removerTodos();

        //leitor tentando reservar livro que ele já possui reserva ativa
        Reserva reserva1 = new Reserva(this.guerrasmedias,this.lucas);
        DAO.getReserva().criar(reserva1);
        assertThrows(LeitorPossuiReservaDesseLivro.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getReserva().removerTodos();

        //livro atingiu limites de reservas
        Leitor leitor2 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        Leitor leitor3 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        Leitor leitor4 = new Leitor("Pedro", "Rua das Flores", "98765432", "abcd1234");
        Leitor leitor5 = new Leitor("Mariana", "Avenida Principal", "55555555", "senhamariana");
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,leitor2));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,leitor3));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,leitor4));
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,leitor5));
        assertThrows(LivroLimiteDeReservas.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getReserva().removerTodos();

        //leitor atingiu limites de reservas
        Livro livro3 = new Livro("As cronicas","Jorge","Sua paz",2000,"Fantasia");
        Livro livro4 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        Livro livro5 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");
        DAO.getReserva().criar(new Reserva(livro3,this.lucas));
        DAO.getReserva().criar(new Reserva(livro4,this.lucas));
        DAO.getReserva().criar(new Reserva(livro5,this.lucas));
        assertThrows(LeitorLimiteDeReservas.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));
        DAO.getReserva().removerTodos();

        //livro está disponivel e sem reservas ativas (leitor deve fazer empréstimo não reserva)
        assertThrows(LivroNaoPossuiEmprestimoNemReserva.class, ()-> this.lucas.reservarLivro(this.guerrasmedias.getISBN()));

        //livro possui uma reserva ativa, tudo correto
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,leitor2));
        this.lucas.reservarLivro(this.guerrasmedias.getISBN());

        //nova reserva criada
        assertEquals(2,DAO.getReserva().encontrarTodos().size());
        assertEquals(this.lucas,DAO.getReserva().encontrarReservasLeitor(this.lucas.getID()).get(0).getLeitor());
    }

    @Test
    void retirarReserva() throws ObjetoInvalido, LeitorNaoPossuiReservaDesseLivro {
        //livro nao existe
        assertThrows(ObjetoInvalido.class, ()-> this.lucas.retirarReserva(889));

        //leitor nao reservou o livro
        assertThrows(LeitorNaoPossuiReservaDesseLivro.class, ()-> this.lucas.retirarReserva(this.guerrasmedias.getISBN()));

        //reserva criada do leitor com o livro e ele é o top 1 da fila de reservas, logo possui prazo ativo
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,this.lucas));
        DAO.getPrazos().criar(new Prazos(this.lucas,this.guerrasmedias));
        Leitor novo = new Leitor("novo","33","333","ss");
        DAO.getReserva().criar(new Reserva(this.guerrasmedias,novo));

        //top 2 da fila de reserva não possui prazo e top1 da fila ( vai retirar a reserva ) possui prazo
        assertEquals(0,DAO.getPrazos().prazosDeUmLeitor(novo.getID()).size());
        assertEquals(this.lucas, DAO.getPrazos().encontrarPrazoDeUmLivro(this.guerrasmedias.getISBN()).getLeitor());

        //tudo correto
        this.lucas.retirarReserva(this.guerrasmedias.getISBN());

        //antigo top 1 da fila de reserva não possui mais prazo ativo e antigo top 2 agora possui um prazo ativo
        assertEquals(0,DAO.getPrazos().prazosDeUmLeitor(this.lucas.getID()).size());
        assertEquals(novo, DAO.getPrazos().encontrarPrazoDeUmLivro(this.guerrasmedias.getISBN()).getLeitor());
    }
}