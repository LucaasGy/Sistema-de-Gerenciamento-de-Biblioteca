package dao.livro;

import model.Livro;
import utils.ArmazenamentoArquivo;

import java.util.List;
import java.util.Random;

public class LivroImplArquivo implements LivroDAO{

    private String nomeArquivoLivro;
    private String nomeArquivoISBN;
    private String nomePasta;
    private List<Livro> listaLivro;
    private List<Double> isbnCadastrado;
    private double isbn;

    public LivroImplArquivo() {
        this.nomeArquivoLivro = "livro.dat";
        this.nomeArquivoISBN = "ISBN.dat";
        this.nomePasta = "Livro";

        this.listaLivro = ArmazenamentoArquivo.resgatar(this.nomeArquivoLivro,this.nomePasta);
        this.isbnCadastrado = ArmazenamentoArquivo.resgatar(this.nomeArquivoISBN,this.nomePasta);

        this.isbn = isbnAleatorio();
    }

    /**
     * Método que gera números aleatórios no intervalo de 10 a 99, com 5 casas decimais
     * para serem usados como ISBN de livros.
     * Enquanto sortear um número já sorteado anteriomente, é sorteado um novo.
     *
     * @return retorna número aleatório único
     */

    public Double isbnAleatorio(){
        Random r = new Random();

        double chute;

        do{
            chute = 10.0 + r.nextDouble() * 90;
            chute = Math.round(chute * 100000.0) / 100000.0;
        } while (this.isbnCadastrado.contains(chute));

        this.isbnCadastrado.add(chute);
        ArmazenamentoArquivo.resgatar(this.nomeArquivoISBN,this.nomePasta);

        return chute;
    }

    @Override
    public Livro criar(Livro obj) {
        obj.setISBN(this.isbn);
        this.isbn = isbnAleatorio();
        this.listaLivro.add(obj);
        ArmazenamentoArquivo.guardar(this.listaLivro,this.nomeArquivoLivro,this.nomePasta);

        return obj;
    }

    @Override
    public void removerPorISBN(double isbn) {
        for(Livro livro : this.listaLivro) {
            if (livro.getISBN() == isbn) {
                this.listaLivro.remove(livro);
                this.isbnCadastrado.remove(isbn);
                ArmazenamentoArquivo.guardar(this.listaLivro,this.nomeArquivoLivro,this.nomePasta);
                ArmazenamentoArquivo.guardar(this.checarListaISBN(),this.nomeArquivoISBN,this.nomePasta);
                return;
            }
        }
    }

    @Override
    public void removerTodos() {

    }

    @Override
    public Livro encontrarPorId(int id) {
        return null;
    }

    @Override
    public List<Livro> encontrarTodos() {
        return null;
    }

    @Override
    public Livro atualizar(Livro obj) {
        return null;
    }

    @Override
    public List<Livro> encontrarPorTitulo(String titulo) {
        return null;
    }

    @Override
    public List<Livro> encontrarPorAutor(String autor) {
        return null;
    }

    @Override
    public List<Livro> encontrarPorCategoria(String categoria) {
        return null;
    }

    @Override
    public Livro encontrarPorISBN(double isbn) {
        return null;
    }

    @Override
    public List<Double> checarListaISBN() {
        return null;
    }

    @Override
    public void remover(int id) {
    }
}
