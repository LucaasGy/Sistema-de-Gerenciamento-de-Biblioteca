package dao.livro;

import model.Livro;
import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * É responsável por armazenar todos os livros do sistema em um arquivo binário e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface LivroDAO.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LivroImplArquivo implements LivroDAO{

    private String nomeArquivoLivro;
    private String nomeArquivoISBN;
    private String nomePasta;
    private List<Livro> listaLivro;
    private List<Double> isbnCadastrado;
    private double isbn;

    /**
     * Construtor que atribui os nomes dos arquivos a serem resgatados e resgata a lista de armazenamento
     * de livros e ISBN sorteados, armazenados em arquivo binário.
     * O ISBN do livro recebe um número aleatório sorteado no intervalo de 10 a 99, seguido por 5 casas decimais.
     * A cada sorteio, é verificado se o número já foi sorteado, caso não tenha sido sorteado, o número
     * é guardado na lista de isbnCadastrado e em arquivo, impedindo assim ISBN iguais.
     * O primeiro livro cadastrado recebe ISBN inicial 10.00000
     */

    public LivroImplArquivo() {
        this.nomeArquivoLivro = "livro.dat";
        this.nomeArquivoISBN = "ISBN.dat";
        this.nomePasta = "Livro";

        this.listaLivro = ArmazenamentoArquivo.resgatar(this.nomeArquivoLivro,this.nomePasta);
        this.isbnCadastrado = ArmazenamentoArquivo.resgatar(this.nomeArquivoISBN,this.nomePasta);

        if(this.isbnCadastrado.isEmpty())
            this.isbn = 10.00000;
        else
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
        } while (this.isbnCadastrado.contains(chute) || chute==10.00000);

        this.isbnCadastrado.add(chute);
        ArmazenamentoArquivo.guardar(this.isbnCadastrado,this.nomeArquivoISBN,this.nomePasta);

        return chute;
    }

    /**
     * Método para adicionar um Livro na lista de armazenamento e guardar no arquivo.
     * O ISBN é inserido nos dados do livro antes de adicioná-lo na lista e guardar no arquivo.
     *
     * @param obj livro que deve ser armazenado
     * @return retorna objeto livro criado
     */

    @Override
    public Livro criar(Livro obj) {
        obj.setISBN(this.isbn);
        this.isbn = isbnAleatorio();
        this.listaLivro.add(obj);
        ArmazenamentoArquivo.guardar(this.listaLivro,this.nomeArquivoLivro,this.nomePasta);

        return obj;
    }

    /**
     * Método para remover determinado Livro da estrutura de armazenamento (lista e arquivo).
     *
     * @param isbn o ISBN sobre o qual o livro deve ser encontrado e removido
     */

    @Override
    public void removerPorISBN(double isbn) {
        for(Livro livro : this.listaLivro) {
            if (livro.getISBN() == isbn) {
                this.listaLivro.remove(livro);
                this.isbnCadastrado.remove(isbn);
                ArmazenamentoArquivo.guardar(this.listaLivro,this.nomeArquivoLivro,this.nomePasta);
                ArmazenamentoArquivo.guardar(this.isbnCadastrado,this.nomeArquivoISBN,this.nomePasta);
                return;
            }
        }
    }

    /**
     * Deleta todos os objetos do tipo Livro do banco de dados.
     *
     * A lista de ISBN sorteados é limpa, apagando todos números sorteados.
     * ISBN inicial é resetado para 10.00000
     */

    @Override
    public void removerTodos() {
        this.listaLivro.clear();
        this.isbnCadastrado.clear();
        this.isbn = 10.00000;
        ArmazenamentoArquivo.guardar(this.listaLivro,this.nomeArquivoLivro,this.nomePasta);
        ArmazenamentoArquivo.guardar(this.isbnCadastrado,this.nomeArquivoISBN,this.nomePasta);
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
     * Método que atualiza um objeto do tipo Livro e guarda a atualização no arquivo.
     *
     * @param obj livro a ser atualizado
     * @return retorna livro que foi atualizado
     */


    @Override
    public Livro atualizar(Livro obj) {
        for(Livro livro : this.listaLivro){
            if(livro.getISBN()==obj.getISBN()){
                this.listaLivro.set(listaLivro.indexOf(livro), obj);
                ArmazenamentoArquivo.guardar(this.listaLivro,this.nomeArquivoLivro,this.nomePasta);
                return obj;
            }
        }

        return null;
    }

    /**
     * Método que encontra objetos Livro por meio do titulo.
     *
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
            if(livro.getTitulo().equals(titulo.toLowerCase()))
                listaTitulo.add(livro);
        }

        return listaTitulo;
    }

    /**
     * Método que encontra objetos Livro por meio da categoria.
     *
     * Objetos Livro que possuam o autor informado, são adicionados numa
     * lista e retornados.
     *
     * @param autor o autor sobre os quais os livros devem ser encontrados
     * @return retorna lista de livros encontrados
     */

    @Override
    public List<Livro> encontrarPorAutor(String autor) {
        List<Livro> listaAutor = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getAutor().equals(autor.toLowerCase()))
                listaAutor.add(livro);
        }

        return listaAutor;
    }

    /**
     * Método que encontra objetos Livro por meio da categoria.
     *
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
            if(livro.getCategoria().equals(categoria.toLowerCase()))
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

    /**
     * Método de retorno de todos os ISBN de livros sorteados do banco de dados.
     *
     * @return retorna todos os ISBN de livros sorteados
     */

    @Override
    public List<Double> checarListaISBN() {
        return this.isbnCadastrado;
    }

    /**
     * Método que altera o caminho do arquivo Livro para realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaTeste() {
        this.nomePasta = "Livro Teste";
        this.nomeArquivoLivro = "livroTeste.dat";
        this.nomeArquivoISBN = "ISBNTeste.dat";
        this.listaLivro = ArmazenamentoArquivo.resgatar(this.nomeArquivoLivro,this.nomePasta);
        this.isbnCadastrado = ArmazenamentoArquivo.resgatar(this.nomeArquivoISBN,this.nomePasta);
        if(this.isbnCadastrado.isEmpty())
            this.isbn = 10.00000;
        else
            this.isbn = isbnAleatorio();
    }

    /**
     * Método que retorna o caminho do arquivo Livro após realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaPrincipal() {
        this.nomePasta = "Livro";
        this.nomeArquivoLivro = "livro.dat";
        this.nomeArquivoISBN = "ISBN.dat";
        this.listaLivro = ArmazenamentoArquivo.resgatar(this.nomeArquivoLivro,this.nomePasta);
        this.isbnCadastrado = ArmazenamentoArquivo.resgatar(this.nomeArquivoISBN,this.nomePasta);
        if(this.isbnCadastrado.isEmpty())
            this.isbn = 10.00000;
        else
            this.isbn = isbnAleatorio();
    }

    @Override
    public void remover(int id) {
    }

    @Override
    public Livro encontrarPorId(int id) {
        return null;
    }
}
