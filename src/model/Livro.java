package model;

/**
 * Classe Livro que representa um Livro do sistema.
 *
 * É definida por informações básica como titulo, autor, editora,
 * identificação única ( ISBN ), ano de publicação e a categoria a qual ele pertence.
 * Possui também um atributo para verificar a disponibilidade do livro.
 * Ademais possui um atributo para a cada vez que o livro for emprestado, somar 1,
 * informação útil para verificar livros mais populares.
 *
 * @author Lucas Gabriel.
 */

public class Livro {
    private String titulo;
    private String autor;
    private String editora;
    private double ISBN;
    private int ano;
    private String categoria;
    private boolean disponivel;
    private int qtdEmprestimo;

    /**
     * Construtor de um Livro do sistema.
     *
     * Recebe como parâmetro as informações básicas para inseri-las diretamente. Os dados do tipo "String" são
     * armazenadas nos atributos da classe com letras minúsculas, para melhor controle das informações
     * no sistema.
     * O atributo disponível é inicializado como "true", indicando disponibilidade para empréstimo.
     * O atributo qtdEmprestimo é inicializado com 0, indicando que nunca houve empréstimo.
     *
     * @param titulo titulo do livro
     * @param autor autor do livro
     * @param editora editora do livro
     * @param ano ano de publicação do livro
     * @param categoria categoria do livro
     */

    public Livro(String titulo, String autor, String editora, int ano, String categoria) {
        this.titulo = titulo.toLowerCase();
        this.autor = autor.toLowerCase();
        this.editora = editora.toLowerCase();
        this.ano = ano;
        this.categoria = categoria.toLowerCase();

        this.disponivel = true;
        this.qtdEmprestimo = 0;
    }

    public int getQtdEmprestimo() {
        return qtdEmprestimo;
    }

    public void setQtdEmprestimo(int qtdEmprestimo) {
        this.qtdEmprestimo = qtdEmprestimo;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public double getISBN() {
        return ISBN;
    }

    public void setISBN(double ISBN) {
        this.ISBN = ISBN;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
