package testes.dao;

import dao.DAO;
import model.Leitor;
import model.Livro;
import model.Prazos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrazosImplTest {

    private Prazos prazo1, prazo2, prazo3, prazo4, prazo5, prazo6, prazo7;

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

        prazo1 = DAO.getPrazos().criar(new Prazos(1000,10.00000));
        prazo2 = DAO.getPrazos().criar(new Prazos(1100,11.00000));
        prazo3 = DAO.getPrazos().criar(new Prazos(1200,12.00000));
        prazo4 = DAO.getPrazos().criar(new Prazos(1300,13.00000));
        prazo5 = DAO.getPrazos().criar(new Prazos(1400,14.00000));
        prazo6 = DAO.getPrazos().criar(new Prazos(1500,15.00000));
        prazo7 = DAO.getPrazos().criar(new Prazos(1600,16.00000));
    }

    @AfterEach
    void tearDown() {
        DAO.getPrazos().removerTodos();
    }

    @Test
    void criar() {
        Livro livroEsperado = new Livro("Harry Potter","Leonardo","Seu desespero",2003,"Fantasia");
        livroEsperado.setISBN(13.00000);

        Leitor leitorEsperado = new Leitor("Joaquim","Rua banana","01010101","sapato");
        leitorEsperado.setID(1300);

        Prazos prazoEsperado = new Prazos(1300,13.00000);

        Prazos atual =  DAO.getPrazos().criar(prazoEsperado);

        assertEquals(prazoEsperado, atual);
    }

    @Test
    void removerTodos() {
        DAO.getPrazos().removerTodos();

        assertTrue(DAO.getPrazos().encontrarTodos().isEmpty());
    }

    @Test
    void encontrarTodos() {
        assertEquals(7, DAO.getPrazos().encontrarTodos().size());
    }

    @Test
    void encontrarPrazoDeUmLivro() {
        Prazos esperado = this.prazo1;
        Prazos atual = DAO.getPrazos().encontrarPrazoDeUmLivro(10.00000);

        assertEquals(esperado,atual);
    }

    @Test
    void prazosDeUmLeitor() {
        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        leitor1.setID(1000);

        Livro livro8 = new Livro("Os aflitos","Donald","Seu medo",1982,"Terror");
        Livro livro9 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");

        livro8.setISBN(17.00000);
        livro9.setISBN(18.00000);

        DAO.getPrazos().criar(new Prazos(1000,17.00000));
        DAO.getPrazos().criar(new Prazos(1000,18.00000));

        assertEquals(3,DAO.getPrazos().prazosDeUmLeitor(1000).size());
    }

    @Test
    void encontrarPorId() {
        Prazos esperado = this.prazo4;
        Prazos atual = DAO.getPrazos().encontrarPorId(1300);

        assertEquals(esperado,atual);
    }

    @Test
    void removerPrazoDeUmLivro() {
        DAO.getPrazos().removerPrazoDeUmLivro(12.000000);
        DAO.getPrazos().removerPrazoDeUmLivro(10.000000);

        Prazos esperado = DAO.getPrazos().encontrarPrazoDeUmLivro(12.00000);
        Prazos esperado2 = DAO.getPrazos().encontrarPrazoDeUmLivro(10.00000);

        assertNull(esperado);
        assertNull(esperado2);

        assertEquals(5,DAO.getPrazos().encontrarTodos().size());
    }

    @Test
    void remover() {
        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        leitor1.setID(1000);

        Livro livro8 = new Livro("Os aflitos","Donald","Seu medo",1982,"Terror");
        Livro livro9 = new Livro("A origem","Ana","Seu medo",1994,"Suspense");

        livro8.setISBN(17.00000);
        livro9.setISBN(18.00000);

        DAO.getPrazos().criar(new Prazos(1000,17.00000));
        DAO.getPrazos().criar(new Prazos(1000,18.00000));

        assertEquals(9, DAO.getPrazos().encontrarTodos().size());

        DAO.getPrazos().remover(1000);

        assertEquals(6, DAO.getPrazos().encontrarTodos().size());
    }

    @Test
    void atualizarPrazos() {
        Livro livro1 = new Livro("As cronicas","Jorge","Sua paz",2000,"Fantasia");
        Livro livro2 = new Livro("As aventuras","Leall","Sua guerra",2010,"Aventura");
        livro1.setISBN(10.00000);
        livro2.setISBN(11.00000);

        Leitor leitor1 = new Leitor("Lucas", "Rua Alameda", "75757575", "batata");
        Leitor leitor2 = new Leitor("Ana", "Avenida Central", "12345678", "senha123");
        leitor1.setID(1000);
        leitor2.setID(1100);

        Prazos novoPrazo1 = new Prazos(1000, 11.00000);
        Prazos novoPrazo2 = new Prazos(1100, 10.00000);

        List<Prazos> novosPrazos = new ArrayList<Prazos>();
        novosPrazos.add(novoPrazo1);
        novosPrazos.add(novoPrazo2);

        List<Prazos> naoRemovePrazos = new ArrayList<>();

        DAO.getPrazos().atualizarPrazos(naoRemovePrazos,novosPrazos);

        assertEquals(9, DAO.getPrazos().encontrarTodos().size());
    }
}