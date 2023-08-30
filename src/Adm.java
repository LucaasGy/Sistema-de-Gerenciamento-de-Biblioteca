import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Adm extends Usuario {

    public Adm(String nome, String senha){
        super(nome, senha,TipoUsuario.ADM);
    }

    public void registrarLivro(String titulo, String autor, String editora, int ano, String categoria){
        Livro opera = new Livro(titulo, autor, editora, ano, categoria);

        DAO.getLivro().criar(opera);
    }

    public void removerUmLivro(double isbn){
        if(DAO.getReserva().livroTemReserva(isbn))
            DAO.getReserva().removerReservasDeUmLivro(isbn);

        if(DAO.getPrazos().livroTemPrazos(isbn))
            DAO.getPrazos().removerPrazosDeUmLivro(isbn);

        DAO.getLivro().removerPorISBN(isbn);
    }

    public Livro pesquisarLivroPorISBN(double isbn){
        return DAO.getLivro().encontrarPorISBN(isbn);
    }

    public List<Livro> pesquisarLivroPorTitulo(String titulo){
        return DAO.getLivro().encontrarPorTitulo(titulo);
    }

    public List<Livro> pesquisarLivroPorAutor(String autor){
        return DAO.getLivro().encontrarPorAutor(autor);
    }

    public List<Livro> pesquisarLivroPorCategoria(String categoria){
        return DAO.getLivro().encontrarPorCategoria(categoria);
    }

    public void criarLeitor(String nome, String endereco, String telefone, String senha){
        Leitor novo = new Leitor(nome, endereco, telefone, senha);

        DAO.getLeitor().criar(novo);
    }

    public void removerLeitor(int id){
        if(DAO.getReserva().leitorTemReserva(id))
            DAO.getReserva().removerReservasDeUmLeitor(id);

        if(DAO.getPrazos().leitorTemPrazos(id))
            DAO.getPrazos().removerPrazosDeUmLeitor(id);

        DAO.getLeitor().remover(id);
    }

    public void atualizarSenhaLeitor(String senha, int id){
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        leitor.setSenha(senha);
    }

    public void atualizarEnderecoLeitor(String endereco, int id){
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        leitor.setEndereco(endereco);
    }

    public void atualizarTelefoneLeitor(String telefone, int id){
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        leitor.setTelefone(telefone);
    }

    public void criarBibliotecario(String nome, String senha){
        Bibliotecario novo = new Bibliotecario(nome, senha);

        DAO.getBibliotecario().criar(novo);
    }

    public void removerBibliotecario(int id){
        DAO.getBibliotecario().remover(id);
    }

    public void atualizarSenhaBibliotecario(String senha, int id){
        Bibliotecario bibliotecario = DAO.getBibliotecario().encontrarPorId(id);

        bibliotecario.setSenha(senha);
    }

    public void criarAdm(String nome, String senha){
        Adm novo = new Adm(nome, senha);

        DAO.getAdm().criar(novo);
    }

    public void removerAdm(int id){
        DAO.getAdm().remover(id);
    }

    public void atualizarSenhaAdm(String senha, int id){
        Adm adm = DAO.getAdm().encontrarPorId(id);

        adm.setSenha(senha);
    }

    public void bloquearLeitor(int id){
        Leitor bloquear = DAO.getLeitor().encontrarPorId(id);

        if(DAO.getReserva().leitorTemReserva(id))
            DAO.getReserva().removerReservasDeUmLeitor(id);

        if(DAO.getPrazos().leitorTemPrazos(id))
            DAO.getPrazos().removerPrazosDeUmLeitor(id);

        bloquear.setBloqueado(true);
    }

    public void desbloquearLeitor(int id){
        Leitor bloquear = DAO.getLeitor().encontrarPorId(id);

        bloquear.setBloqueado(false);
    }

    public void tirarMulta(int id){
        Leitor tira = DAO.getLeitor().encontrarPorId(id);

        tira.setDataMulta(null);
    }

    public void atualizarTituloLivro(String titulo, double isbn){
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        opera2.setTitulo(titulo);
    }

    public void atualizarAutorLivro(String autor, double isbn){
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        opera2.setAutor(autor);
    }

    public void atualizarEditoraLivro(String editora, double isbn){
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        opera2.setEditora(editora);
    }

    public void atualizarAnoLivro(int ano, double isbn){
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        opera2.setAno(ano);
    }

    public void atualizarCategoriaLivro(String categoria, double isbn){
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        opera2.setCategoria(categoria);
    }

    public void atualizarDisponibilidadeLivro(double isbn, boolean FouT){
        Livro novo = DAO.getLivro().encontrarPorISBN(isbn);

        if(DAO.getReserva().livroTemReserva(isbn))
            DAO.getReserva().removerReservasDeUmLivro(isbn);

        if(DAO.getPrazos().livroTemPrazos(isbn))
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

    public List<Emprestimo> historicoEmprestimoDeUmLeitor(int id){
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