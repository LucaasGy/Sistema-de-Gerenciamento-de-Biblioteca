package model;

public class Livro {
    private String titulo;
    private String autor;
    private String editora;
    private double ISBN;
    private int ano;
    private String categoria;
    private boolean disponivel;

    //medir popularidade ( quantas vezes tal dao.livro ja foi emprestado )
    private int qtdEmprestimo;

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
