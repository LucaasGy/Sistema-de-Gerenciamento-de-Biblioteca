package dao.reserva;

import model.Reserva;

import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar em um arquivo binário, todas as reservas de livros do sistema
 * e estruturar os métodos necessários para inserir, consultar, alterar ou remover.
 * Implementa a interface PrazosDAO.
 *
 * @author Lucas Gabriel.
 */

public class ReservaImplArquivo implements ReservaDAO{

    private String nomeArquivo;
    private String nomePasta;
    private List<Reserva> listaReserva;

    /**
     * Construtor que atribui o nome do arquivo a ser resgatado e resgata a lista de armazenamento de reservas
     * armazenadas em um arquivo binário.
     */

    public ReservaImplArquivo() {
        this.nomeArquivo = "reserva.dat";
        this.nomePasta = "Reserva";

        this.listaReserva = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método para adicionar um objeto do tipo Reserva na lista de armazenamento e guardar no arquivo.
     *
     * @param obj reserva que deve ser armazenada
     * @return retorna objeto reserva criado
     */

    @Override
    public Reserva criar(Reserva obj) {
        this.listaReserva.add(obj);
        ArmazenamentoArquivo.guardar(this.listaReserva,this.nomeArquivo,this.nomePasta);

        return obj;
    }

    /**
     * Método que remove todas as reservas ativas de um leitor da estrutura de armazenamento (lista e arquivo).
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     *
     * @param id identificação do leitor
     */

    @Override
    public void remover(int id) {
        List<Reserva> reservasARemover = new ArrayList<Reserva>();

        for (Reserva reserva : this.listaReserva) {
            if (reserva.getLeitor() == id)
                reservasARemover.add(reserva);
        }

        this.listaReserva.removeAll(reservasARemover);
        ArmazenamentoArquivo.guardar(this.listaReserva,this.nomeArquivo,this.nomePasta);
    }

    /**
     * Deleta todos os objetos do tipo Reserva do banco de dados.
     */

    @Override
    public void removerTodos() {
        this.listaReserva.clear();
        ArmazenamentoArquivo.guardar(this.listaReserva,this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método de retorno da Reserva através da busca por ID.
     *
     * @param id identificação do leitor que fez a reserva a ser encontrada
     * @return retorna objeto reserva encontrado
     */

    @Override
    public Reserva encontrarPorId(int id) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor() == id){
                return reserva;
            }
        }

        return null;
    }

    /**
     * Método que encontra todas as reservas ativas.
     *
     * @return retorna lista de reservas ativas encontradas
     */

    @Override
    public List<Reserva> encontrarTodos() {
        return this.listaReserva;
    }

    /**
     * Método que encontra todas as reserva ativas de um livro.
     *
     * Objetos Reserva que possuam o ISBN informado, são adicionados numa
     * lista e retornados.
     *
     * @param isbn isbn do livro reservado
     * @return retorna lista de reservas ativas de um livro
     */

    @Override
    public List<Reserva> encontrarReservasLivro(double isbn) {
        List<Reserva> listaReservaLeitor = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro() == isbn)
                listaReservaLeitor.add(reserva);
        }

        return listaReservaLeitor;
    }

    /**
     * Método que encontra todas as reserva ativas de um leitor.
     *
     * Objetos Reserva que possuam o ID informado, são adicionados numa
     * lista e retornados.
     *
     * @param id identificação do leitor
     * @return retorna lista de reservas ativas de um leitor
     */

    @Override
    public List<Reserva> encontrarReservasLeitor(int id) {
        List<Reserva> listaReservaLivro = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor() == id)
                listaReservaLivro.add(reserva);
        }

        return listaReservaLivro;
    }

    /**
     * Método que remove todas as reservas ativas de um livro da estrutura de armazenamento (lista e arquivo).
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     *
     * @param isbn isbn do livro
     */

    @Override
    public void removerReservasDeUmLivro(double isbn) {
        List<Reserva> reservasARemover = new ArrayList<Reserva>();

        for (Reserva reserva : this.listaReserva) {
            if (reserva.getLivro() == isbn)
                reservasARemover.add(reserva);
        }

        this.listaReserva.removeAll(reservasARemover);
        ArmazenamentoArquivo.guardar(this.listaReserva,this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método que remove uma determinada reserva ativa da estrutura de armazenamento (lista e arquivo).
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     */

    @Override
    public void removerUmaReserva(int id, double isbn) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor() == id && reserva.getLivro() == isbn) {
                this.listaReserva.remove(reserva);
                ArmazenamentoArquivo.guardar(this.listaReserva,this.nomeArquivo,this.nomePasta);
                return;
            }
        }
    }

    /**
     * Método que remove a primeira reserva de um livro da estrutura de armazenamento (lista e arquivo).
     *
     * @param isbn isbn do livro
     */

    @Override
    public void removeTop1(double isbn) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro() == isbn){
                this.listaReserva.remove(reserva);
                ArmazenamentoArquivo.guardar(this.listaReserva,this.nomeArquivo,this.nomePasta);
                return;
            }
        }
    }

    /**
     * Método que verifica se determinado livro possui alguma reserva ativa.
     *
     * @param isbn isbn do livro
     * @return retorna true caso encontre e false caso não encontre
     */

    @Override
    public boolean livroTemReserva(double isbn) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro() == isbn)
                return true;
        }

        return false;
    }

    /**
     * Método que verifica se já possui uma reserva ativa de um livro por um leitor.
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     * @return retorna true caso encontre e false caso não encontre
     */

    @Override
    public boolean leitorJaReservouEsseLivro(int id, double isbn) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor() == id && reserva.getLivro() == isbn)
                return true;
        }

        return false;
    }

    /**
     * Método que encontra a primeira reserva de um livro.
     *
     * @param isbn isbn do livro
     * @return retorna a primeira reserva encontrada de um livro
     */

    @Override
    public Reserva top1Reserva(double isbn) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro() == isbn)
                return reserva;
        }

        return null;
    }

    @Override
    public Reserva atualizar(Reserva obj) {
        return null;
    }

    /**
     * Método que altera o caminho do arquivo Reserva para realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaTeste() {
        this.nomePasta = "Reserva Teste";
        this.nomeArquivo = "reservaTeste.dat";
    }

    /**
     * Método que retorna o caminho do arquivo Reserva após realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaPrincipal() {
        this.nomePasta = "Reserva";
        this.nomeArquivo = "reserva.dat";
    }
}
