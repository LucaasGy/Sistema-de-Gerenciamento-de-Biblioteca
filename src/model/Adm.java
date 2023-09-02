package model;

import dao.DAO;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.objetos.ObjetoInvalido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Adm extends Usuario {

    public Adm(String nome, String senha){
        super(nome, senha, TipoUsuario.ADM);
    }

    public void registrarLivro(Livro livro){
        DAO.getLivro().criar(livro);
    }

    public void removerUmLivro(double isbn) throws ObjetoInvalido, LivroEmprestado {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(DAO.getEmprestimo().livroTemEmprestimo(isbn))
            throw new LivroEmprestado();

        DAO.getReserva().removerReservasDeUmLivro(isbn);
        DAO.getPrazos().removerPrazosDeUmLivro(isbn);

        DAO.getLivro().removerPorISBN(isbn);
    }

    public void criarLeitor(Leitor leitor){

        DAO.getLeitor().criar(leitor);
    }

    public void removerLeitor(int id) throws ObjetoInvalido, LeitorTemEmprestimo {
        if(DAO.getLeitor().encontrarPorId(id)==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        if(DAO.getEmprestimo().encontrarPorId(id)!=null)
            throw new LeitorTemEmprestimo();

        DAO.getReserva().removerReservasDeUmLeitor(id);
        DAO.getPrazos().removerPrazosDeUmLeitor(id);

        DAO.getLeitor().remover(id);
    }

    public void atualizarSenhaLeitor(String senha, int id) throws ObjetoInvalido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        leitor.setSenha(senha);
    }

    public void atualizarEnderecoLeitor(String endereco, int id) throws ObjetoInvalido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        leitor.setEndereco(endereco);
    }

    public void atualizarTelefoneLeitor(String telefone, int id) throws ObjetoInvalido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        leitor.setTelefone(telefone);
    }

    public void criarBibliotecario(Bibliotecario bibliotecario){
        DAO.getBibliotecario().criar(bibliotecario);
    }

    public void removerBibliotecario(int id) throws ObjetoInvalido {
        if(DAO.getBibliotecario().encontrarPorId(id)==null)
            throw new ObjetoInvalido("BIBLIOTECARIO NAO ENCONTRADO");

        DAO.getBibliotecario().remover(id);
    }

    public void atualizarSenhaBibliotecario(String senha, int id) throws ObjetoInvalido {
        Bibliotecario bibliotecario = DAO.getBibliotecario().encontrarPorId(id);

        if(bibliotecario==null)
            throw new ObjetoInvalido("BIBLIOTECARIO NAO ENCONTRADO");

        bibliotecario.setSenha(senha);
    }

    public void criarAdm(Adm adm){
        DAO.getAdm().criar(adm);
    }

    public void removerAdm(int id) throws ObjetoInvalido {
        if(DAO.getAdm().encontrarPorId(id)==null)
            throw new ObjetoInvalido("ADMINISTRADOR NAO ENCONTRADO");

        DAO.getAdm().remover(id);
    }

    public void atualizarSenhaAdm(String senha, int id) throws ObjetoInvalido {
        Adm adm = DAO.getAdm().encontrarPorId(id);

        if(adm==null)
            throw new ObjetoInvalido("ADMINISTRADOR NAO ENCONTRADO");

        adm.setSenha(senha);
    }

    public void bloquearLeitor(int id) throws ObjetoInvalido {
        Leitor bloquear = DAO.getLeitor().encontrarPorId(id);

        if(bloquear==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        DAO.getReserva().removerReservasDeUmLeitor(id);
        DAO.getPrazos().removerPrazosDeUmLeitor(id);

        bloquear.setBloqueado(true);
    }

    public void desbloquearLeitor(int id) throws ObjetoInvalido {
        Leitor bloquear = DAO.getLeitor().encontrarPorId(id);

        if(bloquear==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        bloquear.setBloqueado(false);
    }

    public void tirarMulta(int id) throws ObjetoInvalido {
        Leitor tira = DAO.getLeitor().encontrarPorId(id);

        if(tira==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        tira.setDataMulta(null);
    }

    public void atualizarTituloLivro(String titulo, double isbn) throws ObjetoInvalido {
        Livro opera = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera.setTitulo(titulo);
    }

    public void atualizarAutorLivro(String autor, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setAutor(autor);
    }

    public void atualizarEditoraLivro(String editora, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setEditora(editora);
    }

    public void atualizarAnoLivro(int ano, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setAno(ano);
    }

    public void atualizarCategoriaLivro(String categoria, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setCategoria(categoria);
    }

    public void atualizarDisponibilidadeLivro(double isbn, boolean FouT) throws ObjetoInvalido, LivroEmprestado {
        Livro novo = DAO.getLivro().encontrarPorISBN(isbn);

        if(novo==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(DAO.getEmprestimo().livroTemEmprestimo(isbn))
            throw new LivroEmprestado();

        DAO.getReserva().removerReservasDeUmLivro(isbn);
        DAO.getPrazos().removerPrazosDeUmLivro(isbn);

        novo.setDisponivel(FouT);
    }

    public List<Livro> estoque(){
        return DAO.getLivro().encontrarTodos();
    }

    public List<Livro> livrosEmprestados(){
        List<Livro> livros = new ArrayList<Livro>();

        for(Emprestimo emp : DAO.getEmprestimo().encontrarTodosAtuais()){
            livros.add(emp.getLivro());
        }

        return livros;
    }

    public List<Livro> livrosAtrasados(){
        List<Livro> livrosAtrasados = new ArrayList<Livro>();

        LocalDate agr = LocalDate.now();

        for(Emprestimo emp : DAO.getEmprestimo().encontrarTodosAtuais()){
            if(emp.getdataPrevista().isBefore(agr)){
                livrosAtrasados.add(emp.getLivro());
            }
        }

        return livrosAtrasados;
    }

    public List<Livro> livrosReservados(){
        List<Livro> livros = new ArrayList<Livro>();

        for(Reserva res : DAO.getReserva().encontrarTodos()){
            livros.add(res.getLivro());
        }

        return livros;
    }

    public List<Emprestimo> historicoEmprestimoDeUmLeitor(int id) throws ObjetoInvalido {
        if(DAO.getLeitor().encontrarPorId(id)==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        return DAO.getEmprestimo().encontrarHistoricoDeUmLeitor(id);
    }

    public List<Livro> livrosMaisPopulares(){
        List<Livro> livrosPopulares = DAO.getLivro().encontrarTodos();

        Comparator<Livro> comparador = Comparator.comparingInt(Livro::getQtdEmprestimo);
        //Collections.sort(copia, comparador);
        livrosPopulares.sort(comparador);

        return livrosPopulares;
    }
}