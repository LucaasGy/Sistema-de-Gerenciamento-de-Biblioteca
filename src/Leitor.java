import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Leitor extends Usuario{
    private String endereco;
    private String telefone;
    private LocalDate dataMulta;
    //como um usuario pode reservar ate 3 livros, quando o livro reservado for devolvido e o leitor ser o top 1 da fila
    //cria-se um prazo para o livro em questao, como sao no max 3 livros, preciso ter 3 prazos na listaque conteria,
    // o livro e a data limite para o leitor fazer o emprestimo dele.
    //caso ao tentar fazer o emprestimo de um dos 3 livros o prazo tenha sido estrapolado, eu removo esse prazo da
    //lista e removo o usuario da fila de reserva do livro em questao, nao interferindo na reserva e prazo dos outros 2
    //livros restantes.
    private List<Prazos> prazos;
    private int limiteRenova;
    private boolean bloqueado;

    public Leitor(String nome, String endereco, String telefone, String senha) {
        super(nome,senha,TipoUsuario.Leitor);

        this.endereco = endereco.toLowerCase();
        this.telefone = telefone.toLowerCase();

        this.bloqueado = false;
        this.dataMulta = null;

        this.prazos = new ArrayList<Prazos>();

        this.limiteRenova=0;
    }

    public List<Prazos> getPrazos() {
        return prazos;
    }

    public void addPrazos(Prazos prazos) {
        this.prazos.add(prazos);
    }

    public void removPrazos(Prazos prazos){
        this.prazos.remove(prazos);
    }

    public LocalDate getDataMulta() {
        return dataMulta;
    }

    public void setDataMulta(LocalDate dataMulta) {
        this.dataMulta = dataMulta;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getLimiteRenova() {
        return limiteRenova;
    }

    public void setLimiteRenova(int limiteRenova) {
        this.limiteRenova = limiteRenova;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public void renovarEmprestimo(){
        EmprestimoImpl opera = new EmprestimoImpl();
        Emprestimo novo = opera.encontrarPorId(this.getID());

        opera.remover(novo.getLeitor().getID());

        novo.setdataPegou(LocalDate.now());
        novo.setdataPrevista(novo.getdataPegou().plusDays(7));

        opera.criar(novo);

        this.limiteRenova++;
    }

    public void reservarLivro(double isbn){
        ReservaImpl opera = new ReservaImpl();
        LeitorImpl opera2 = new LeitorImpl();
        LivroImpl opera3 = new LivroImpl();

        Reserva reserva = new Reserva(opera3.encontrarPorISBN(isbn) , opera2.encontrarPorId(this.getID()));

        opera.criar(reserva);
    }
}
