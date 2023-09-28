package testes.dao.reserva;

import dao.DAO;
import model.Leitor;
import model.Livro;
import model.Reserva;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ArmazenamentoArquivo;

import static org.junit.jupiter.api.Assertions.*;

class ReservaImplArquivoTest {

    private Reserva reserva1, reserva2, reserva3, reserva4, reserva5, reserva6, reserva7;

    @BeforeEach
    void setUp() {
        Livro livro1 = new Livro("As cronicas","Jorge","Sua paz",2000,"Fantasia");
        Livro livro2 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        Livro livro3 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");
        Livro livro4 = new Livro("Os jogos","Ronaldo","Sua alegria",2020,"Comedia");
        Livro livro5 = new Livro("O grito","Percy","Seu medo",1991,"Horror");
        Livro livro6 = new Livro("Os aflitos","Donald","Seu medo",1982,"Terror");
        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro1.setISBN(10.00000);
        livro2.setISBN(11.00000);
        livro3.setISBN(12.00000);
        livro4.setISBN(13.00000);
        livro5.setISBN(14.00000);
        livro6.setISBN(15.00000);
        livro7.setISBN(16.00000);

        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        Leitor leitor2 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        Leitor leitor3 = new Leitor("Pedro", "Rua das Flores", "98765432", "abcd1234");
        Leitor leitor4 = new Leitor("Mariana", "Avenida Principal", "55555555", "senhamariana");
        Leitor leitor5 = new Leitor("Carlos", "Rua dos Passarinhos", "11111111", "senha12345");
        Leitor leitor6 = new Leitor("Sofia", "Avenida das Árvores", "99999999", "sofiasenha");
        Leitor leitor7 = new Leitor("Valentina", "Avenida Principal", "99999999", "senha8372");
        leitor1.setID(1000);
        leitor2.setID(1100);
        leitor3.setID(1200);
        leitor4.setID(1300);
        leitor5.setID(1400);
        leitor6.setID(1500);
        leitor7.setID(1600);

        reserva1 = DAO.getReserva().criar(new Reserva(10.00000,1000));
        reserva2 = DAO.getReserva().criar(new Reserva(11.00000,1100));
        reserva3 = DAO.getReserva().criar(new Reserva(12.00000,1200));
        reserva4 = DAO.getReserva().criar(new Reserva(13.00000,1300));
        reserva5 = DAO.getReserva().criar(new Reserva(14.00000,1400));
        reserva6 = DAO.getReserva().criar(new Reserva(15.00000,1500));
        reserva7 = DAO.getReserva().criar(new Reserva(16.00000,1600));
    }

    @AfterEach
    void tearDown() {
        DAO.getReserva().removerTodos();
    }

    @Test
    void criar() {
        Livro livroEsperado = new Livro("Harry Potter","Leonardo","Seu desespero",2003,"Fantasia");
        livroEsperado.setISBN(13.00000);

        Leitor leitorEsperado = new Leitor("Joaquim","Rua banana","01010101","sapato");
        leitorEsperado.setID(1300);

        Reserva reservaEsperada = new Reserva(13.00000,1300);

        Reserva atual =  DAO.getReserva().criar(reservaEsperada);

        assertEquals(reservaEsperada, atual);
    }

    @Test
    void remover() {
        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        leitor1.setID(1000);

        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro7.setISBN(16.00000);

        Livro livro3 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");
        livro3.setISBN(12.00000);

        DAO.getReserva().criar(new Reserva(16.00000,1000));
        DAO.getReserva().criar(new Reserva(12.00000,1000));

        assertEquals(3, DAO.getReserva().encontrarReservasLeitor(1000).size());

        DAO.getReserva().remover(1000);

        assertEquals(6, DAO.getReserva().encontrarTodos().size());
        assertEquals(6, ArmazenamentoArquivo.resgatar("reserva.dat","Reserva").size());
    }

    @Test
    void removerTodos() {
        DAO.getReserva().removerTodos();

        assertTrue(DAO.getReserva().encontrarTodos().isEmpty());
        assertTrue(ArmazenamentoArquivo.resgatar("reserva.dat","Reserva").isEmpty());
    }

    @Test
    void encontrarTodos() {
        assertEquals(7, DAO.getReserva().encontrarTodos().size());
        assertEquals(7, ArmazenamentoArquivo.resgatar("reserva.dat","Reserva").size());
    }

    @Test
    void encontrarReservasLivro() {
        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro7.setISBN(16.00000);

        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        Leitor leitor2 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        leitor1.setID(1000);
        leitor2.setID(1100);

        assertEquals(1, DAO.getReserva().encontrarReservasLivro(16.00000).size());

        DAO.getReserva().criar(new Reserva(16.00000,1100));
        DAO.getReserva().criar(new Reserva(16.00000,1000));

        assertEquals(3, DAO.getReserva().encontrarReservasLivro(16.00000).size());
    }

    @Test
    void encontrarReservasLeitor() {
        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        leitor1.setID(1000);

        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro7.setISBN(16.00000);

        Livro livro3 = new Livro("As peripecias","Cristiano","Sua alegria",2020,"Suspense");
        livro3.setISBN(12.00000);

        assertEquals(1, DAO.getReserva().encontrarReservasLeitor(1000).size());

        DAO.getReserva().criar(new Reserva(16.00000,1000));
        DAO.getReserva().criar(new Reserva(12.00000,1000));

        assertEquals(3, DAO.getReserva().encontrarReservasLeitor(1000).size());
    }

    @Test
    void removerReservasDeUmLivro() {
        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro7.setISBN(16.00000);

        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        leitor1.setID(1000);

        Leitor leitor2 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        leitor2.setID(1100);

        DAO.getReserva().criar(new Reserva(16.00000,1000));
        DAO.getReserva().criar(new Reserva(16.00000,1100));

        assertEquals(3, DAO.getReserva().encontrarReservasLivro(16.00000).size());

        DAO.getReserva().removerReservasDeUmLivro(16.00000);

        assertEquals(6, DAO.getReserva().encontrarTodos().size());
        assertEquals(6, ArmazenamentoArquivo.resgatar("reserva.dat","Reserva").size());
    }

    @Test
    void removerUmaReserva() {
        DAO.getReserva().removerUmaReserva(1000,10.00000);
        DAO.getReserva().removerUmaReserva(1100,11.00000);

        Reserva procurarReservaLeitor1000 = DAO.getReserva().encontrarPorId(1000);
        Reserva procurarReservaLeitor1100 = DAO.getReserva().encontrarPorId(1100);

        assertNull(procurarReservaLeitor1000);
        assertNull(procurarReservaLeitor1100);

        assertEquals(5, DAO.getReserva().encontrarTodos().size());
        assertEquals(5, ArmazenamentoArquivo.resgatar("reserva.dat","Reserva").size());
    }

    @Test
    void removeTop1() {
        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro7.setISBN(16.00000);

        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        Leitor leitor2 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        leitor1.setID(1000);
        leitor2.setID(1100);

        Reserva novaReservaDoLivro1600000 = new Reserva(16.00000,1100);

        DAO.getReserva().criar(novaReservaDoLivro1600000);
        DAO.getReserva().criar(new Reserva(16.00000,1000));

        Reserva procurarReservaASerRemovida = DAO.getReserva().top1Reserva(16.00000);

        assertEquals(this.reserva7, procurarReservaASerRemovida);

        DAO.getReserva().removeTop1(16.00000);

        Reserva procurarNovoTOP1 = DAO.getReserva().top1Reserva(16.00000);

        assertEquals(novaReservaDoLivro1600000, procurarNovoTOP1);
    }

    @Test
    void livroTemReserva() {
        assertTrue(DAO.getReserva().livroTemReserva(14.00000));
        assertFalse(DAO.getReserva().livroTemReserva(20.00000));
        assertTrue(DAO.getReserva().livroTemReserva(15.00000));
    }

    @Test
    void leitorJaReservouEsseLivro() {
        assertTrue(DAO.getReserva().leitorJaReservouEsseLivro(1000,10.00000));
        assertFalse(DAO.getReserva().leitorJaReservouEsseLivro(1000,12.00000));
        assertTrue(DAO.getReserva().leitorJaReservouEsseLivro(1200,12.00000));
    }

    @Test
    void top1Reserva() {
        DAO.getReserva().removerTodos();

        Livro livro7 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");
        livro7.setISBN(16.00000);

        Livro livro2 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        livro2.setISBN(11.00000);

        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        leitor1.setID(1000);

        Reserva reserva1 = new Reserva(16.00000,1000);
        Reserva reserva2 = new Reserva(11.00000,1000);
        Reserva reserva3 = new Reserva(16.00000,1000);

        DAO.getReserva().criar(reserva1);
        DAO.getReserva().criar(reserva2);
        DAO.getReserva().criar(reserva3);

        assertEquals(reserva1, DAO.getReserva().top1Reserva(16.00000));
    }
}