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
        LivroImpl opera2 = new LivroImpl();

        opera2.criar(opera);
    }

    public void removerUmLivro(double isbn){
        LivroImpl opera = new LivroImpl();
        ReservaImpl opera2 = new ReservaImpl();
        PrazosImpl opera3 = new PrazosImpl();

        if(opera2.livroTemReserva(isbn))
            opera2.removerReservasDeUmLivro(isbn);

        if(opera3.livroTemPrazos(isbn))
            opera3.removerPrazosDeUmLivro(isbn);

        opera.removerPorISBN(isbn);

    }

    public Livro pesquisarLivroPorISBN(double isbn){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorISBN(isbn);
    }

    public List<Livro> pesquisarLivroPorTitulo(String titulo){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorTitulo(titulo);
    }

    public List<Livro> pesquisarLivroPorAutor(String autor){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorAutor(autor);
    }

    public List<Livro> pesquisarLivroPorCategoria(String categoria){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorCategoria(categoria);
    }

    public void criarLeitor(String nome, String endereco, String telefone, String senha){
        Leitor novo = new Leitor(nome, endereco, telefone, senha);
        LeitorImpl opera = new LeitorImpl();

        opera.criar(novo);
    }

    public void removerLeitor(int id){
        LeitorImpl opera = new LeitorImpl();
        ReservaImpl opera2 = new ReservaImpl();
        PrazosImpl opera3 = new PrazosImpl();

        if(opera2.leitorTemReserva(id))
            opera2.removerReservasDeUmLeitor(id);

        if(opera3.leitorTemPrazos(id))
            opera3.removerPrazosDeUmLeitor(id);

        opera.remover(id);
    }

    public void atualizarSenhaLeitor(String senha, int id){
        LeitorImpl opera = new LeitorImpl();
        Leitor leitor = opera.encontrarPorId(id);

        leitor.setSenha(senha);
    }

    public void atualizarEnderecoLeitor(String endereco, int id){
        LeitorImpl opera = new LeitorImpl();
        Leitor leitor = opera.encontrarPorId(id);

        leitor.setEndereco(endereco);
    }

    public void atualizarTelefoneLeitor(String telefone, int id){
        LeitorImpl opera = new LeitorImpl();
        Leitor leitor = opera.encontrarPorId(id);

        leitor.setTelefone(telefone);
    }

    public void criarBibliotecario(String nome, String senha){
        Bibliotecario novo = new Bibliotecario(nome, senha);
        BibliotecarioImpl opera = new BibliotecarioImpl();

        opera.criar(novo);
    }

    public void removerBibliotecario(int id){
        BibliotecarioImpl opera = new BibliotecarioImpl();

        opera.remover(id);
    }

    public void atualizarSenhaBibliotecario(String senha, int id){
        BibliotecarioImpl opera = new BibliotecarioImpl();
        Bibliotecario bibliotecario = opera.encontrarPorId(id);

        bibliotecario.setSenha(senha);
    }

    public void criarAdm(String nome, String senha){
        Adm novo = new Adm(nome, senha);
        AdmImpl opera = new AdmImpl();

        opera.criar(novo);
    }

    public void removerAdm(int id){
        AdmImpl opera = new AdmImpl();

        opera.remover(id);
    }

    public void atualizarSenhaAdm(String senha, int id){
        AdmImpl opera = new AdmImpl();
        Adm adm = opera.encontrarPorId(id);

        adm.setSenha(senha);
    }

    public void bloquearLeitor(int id){
        LeitorImpl opera = new LeitorImpl();
        ReservaImpl opera2 = new ReservaImpl();
        PrazosImpl opera3 = new PrazosImpl();
        Leitor bloquear = opera.encontrarPorId(id);

        if(opera2.leitorTemReserva(id))
            opera2.removerReservasDeUmLeitor(id);

        if(opera3.leitorTemPrazos(id))
            opera3.removerPrazosDeUmLeitor(id);

        bloquear.setBloqueado(true);
    }

    public void desbloquearLeitor(int id){
        LeitorImpl opera = new LeitorImpl();
        Leitor bloquear = opera.encontrarPorId(id);

        bloquear.setBloqueado(false);
    }

    public void tirarMulta(int id){
        LeitorImpl opera = new LeitorImpl();
        Leitor tira = opera.encontrarPorId(id);

        tira.setDataMulta(null);
    }

    public void atualizarTituloLivro(String titulo, double isbn){
        LivroImpl opera = new LivroImpl();
        Livro opera2 = opera.encontrarPorISBN(isbn);

        opera2.setTitulo(titulo);
    }

    public void atualizarAutorLivro(String autor, double isbn){
        LivroImpl opera = new LivroImpl();
        Livro opera2 = opera.encontrarPorISBN(isbn);

        opera2.setAutor(autor);
    }

    public void atualizarEditoraLivro(String editora, double isbn){
        LivroImpl opera = new LivroImpl();
        Livro opera2 = opera.encontrarPorISBN(isbn);

        opera2.setEditora(editora);
    }

    public void atualizarAnoLivro(int ano, double isbn){
        LivroImpl opera = new LivroImpl();
        Livro opera2 = opera.encontrarPorISBN(isbn);

        opera2.setAno(ano);
    }

    public void atualizarCategoriaLivro(String categoria, double isbn){
        LivroImpl opera = new LivroImpl();
        Livro opera2 = opera.encontrarPorISBN(isbn);

        opera2.setCategoria(categoria);
    }

    public void atualizarDisponibilidadeLivro(double isbn, boolean FouT){
        LivroImpl opera = new LivroImpl();
        ReservaImpl opera2 = new ReservaImpl();
        PrazosImpl opera3 = new PrazosImpl();

        Livro novo = opera.encontrarPorISBN(isbn);

        if(opera2.livroTemReserva(isbn))
            opera2.removerReservasDeUmLivro(isbn);

        if(opera3.livroTemPrazos(isbn))
            opera3.removerPrazosDeUmLivro(isbn);

        novo.setDisponivel(FouT);
    }

    public List<Livro> estoque(){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarTodos();
    }

    public List<Livro> livrosEmprestados(){
        EmprestimoImpl opera = new EmprestimoImpl();
        List<Livro> livros = new ArrayList<Livro>();

        for(Emprestimo emp : opera.encontrarTodosAtuais()){
            livros.add(emp.getLivro());
        }

        return livros;
    }

    public List<Livro> livrosAtrasados(){
        EmprestimoImpl opera = new EmprestimoImpl();
        List<Livro> livrosAtrasados = new ArrayList<Livro>();

        LocalDate agr = LocalDate.now();

        for(Emprestimo emp : opera.encontrarTodosAtuais()){
            if(emp.getdataPrevista().isBefore(agr)){
                livrosAtrasados.add(emp.getLivro());
            }
        }

        return livrosAtrasados;
    }

    public List<Livro> livrosReservados(){
        ReservaImpl opera = new ReservaImpl();
        List<Livro> livros = new ArrayList<Livro>();

        for(Reserva res : opera.encontrarTodos()){
            livros.add(res.getLivro());
        }

        return livros;
    }

    public List<Emprestimo> historicoEmprestimoDeUmLeitor(int id){
        EmprestimoImpl opera = new EmprestimoImpl();

        return opera.encontrarHistoricoDeUmLeitor(id);
    }

    public List<Livro> livrosMaisPopulares(){
        LivroImpl opera = new LivroImpl();
        List<Livro> livrosPopulares = opera.encontrarTodos();

        Comparator<Livro> comparador = Comparator.comparingInt(Livro::getQtdEmprestimo);
        //Collections.sort(copia, comparador);
        livrosPopulares.sort(comparador);

        return livrosPopulares;
    }
}