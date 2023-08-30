import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LivroImpl implements LivroDAO{

    private List<Livro> listaLivro;
    private static List<Double> isbnCadastrado;
    private double isbn;

    public LivroImpl() {
        this.listaLivro = new ArrayList<Livro>();
        this.isbn = isbnAleatorio();
    }

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

    @Override
    public Livro criar(Livro obj) {
        obj.setISBN(this.isbn);
        this.listaLivro.add(obj);

        return obj;
    }

    @Override
    public void removerPorISBN(double isbn){
        for(Livro livro : this.listaLivro) {
            if (livro.getISBN() == isbn) {
                this.listaLivro.remove(livro);
                return;
            }
        }
    }

    @Override
    public List<Livro> encontrarTodos() {
        return this.listaLivro;
    }

    @Override
    public void removerTodos() {
        this.listaLivro.clear();
        isbnCadastrado.clear();
    }

    @Override
    public List<Livro> encontrarPorTitulo(String titulo) {
        List<Livro> listaTitulo = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getTitulo().equals(titulo))
                listaTitulo.add(livro);
        }

        return listaTitulo;
    }

    @Override
    public List<Livro> encontrarPorAutor(String autor) {
        List<Livro> listaAutor = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getAutor().equals(autor))
                listaAutor.add(livro);
        }

        return listaAutor;
    }

    @Override
    public List<Livro> encontrarPorCategoria(String categoria) {
        List<Livro> listaCategoria = new ArrayList<Livro>();

        for(Livro livro : this.listaLivro){
            if(livro.getCategoria().equals(categoria))
                listaCategoria.add(livro);
        }

        return listaCategoria;
    }

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
