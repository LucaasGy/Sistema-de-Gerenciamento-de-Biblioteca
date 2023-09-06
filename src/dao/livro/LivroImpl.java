package dao.livro;

import model.Livro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * É responsável por armazenar todos os livros do sistema, e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface LivroDAO.
 * Contém uma lista estática para armazenar ISBN já gerados.
 *
 * @author Lucas Gabriel.
 */

public class LivroImpl implements LivroDAO {

    private List<Livro> listaLivro;
    private static List<Double> isbnCadastrado;
    private double isbn;

    /**
     * Construtor que inicializa a lista de armazenamento de livros e o número do ISBN. O ISBN do
     * livro é gerado por um número aleatório no intervalo de 10 a 99, seguido por 5 casas decimais.
     * A cada sorteio, é verificado se o número já foi sorteado, caso não tenha sido sorteado, o número
     * é guardado em uma lista estática da classe, impedindo assim ISBN iguais.
     */

    public LivroImpl() {
        this.listaLivro = new ArrayList<Livro>();
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
        } while (isbnCadastrado.contains(chute));

        isbnCadastrado.add(chute);

        return chute;
    }

    /**
     * Método para adicionar um Livro na lista de armazenamento. O ISBN é inserido nos dados do livro
     * antes de adicioná-lo na lista.
     *
     * @param obj Livro que deve ser armazenado
     * @return retorna objeto livro criado
     */

    @Override
    public Livro criar(Livro obj) {
        obj.setISBN(this.isbn);
        this.listaLivro.add(obj);

        return obj;
    }

    /**
     * Método para remover determinado livro da estrutura de armazenamento.
     *
     * @param isbn o ISBN sobre o qual o livro deve ser encontrado e removido
     */

    @Override
    public void removerPorISBN(double isbn){
        for(Livro livro : this.listaLivro) {
            if (livro.getISBN() == isbn) {
                this.listaLivro.remove(livro);
                return;
            }
        }
    }

    /**
     * Deleta todos os objetos do tipo Livro do banco de dados.
     * A lista de ISBN sorteados é limpa, apagando todos números sorteados.
     */

    @Override
    public void removerTodos() {
        this.listaLivro.clear();
        isbnCadastrado.clear();
    }

    /**
     * Método de retorno de todos os objetos do tipo Livro do banco de dados.
     *
     * @return retorna todos os objetos do tipo livro
     */

    @Override
    public List<Livro> encontrarTodos() {
        return this.listaLivro;
    }

    /**
     * Método que encontra objetos Livro por meio do titulo.
     * Objetos Livro que possuam o titulo informado, são adicionados numa
     * lista e retornados.
     *
     * @param titulo o titulo sobre os quais os livros devem ser encontrados
     * @return retorna lista de livros encontrados
     */

    @Override
    public List<Livro> encontrarPorTitulo(String titulo) {
        List<Livro> listaTitulo = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getTitulo().equals(titulo))
                listaTitulo.add(livro);
        }

        return listaTitulo;
    }

    /**
     * Método que encontra objetos Livro por meio da categoria.
     * Objetos Livro que possuam o autor informado, são adicionados numa
     *
     *
     * @param autor o autor sobre os quais os livros devem ser encontrados
     * @return retorna lista de livros encontrados
     */

    @Override
    public List<Livro> encontrarPorAutor(String autor) {
        List<Livro> listaAutor = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getAutor().equals(autor))
                listaAutor.add(livro);
        }

        return listaAutor;
    }

    /**
     * Método que encontra objetos Livro por meio da categoria.
     * Objetos Livro que possuam a categoria informada, são adicionados numa
     * lista e retornados.
     *
     * @param categoria a categoria sobre os quais os livros devem ser encontrados
     * @return retorna objeto livro encontrado
     */

    @Override
    public List<Livro> encontrarPorCategoria(String categoria) {
        List<Livro> listaCategoria = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getCategoria().equals(categoria))
                listaCategoria.add(livro);
        }

        return listaCategoria;
    }

    /**
     * Método que encontra objeto Livro por meio do ISBN.
     *
     * @param isbn o ISBN sobre o qual o livro deve ser encontrado
     * @return retorna objeto livro encontrado
     */

    @Override
    public Livro encontrarPorISBN(double isbn) {
        for(Livro livro : this.listaLivro){
            if(livro.getISBN() == isbn)
                return livro;
        }

        return null;
    }

    @Override
    public void remover(int id) {
    }

    @Override
    public Livro encontrarPorId(int id) {
        return null;
    }
}
