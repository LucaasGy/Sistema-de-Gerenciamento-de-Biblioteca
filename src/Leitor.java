import java.time.LocalDate;
import java.util.List;

public class Leitor extends Usuario{
    private String endereco;
    private String telefone;
    private LocalDate dataMulta;
    private int limiteRenova;
    private boolean bloqueado;

    public Leitor(String nome, String endereco, String telefone, String senha) {
        super(nome,senha,TipoUsuario.Leitor);

        this.endereco = endereco.toLowerCase();
        this.telefone = telefone.toLowerCase();

        this.bloqueado = false;
        this.dataMulta = null;

        this.limiteRenova=0;
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

    public void renovarEmprestimo(){
        Emprestimo novo = DAO.getEmprestimo().encontrarPorId(this.getID());

        DAO.getEmprestimo().remover(novo.getLeitor().getID());

        novo.setdataPegou(LocalDate.now());
        novo.setdataPrevista(novo.getdataPegou().plusDays(7));

        DAO.getEmprestimo().criar(novo);

        this.limiteRenova++;
    }

    public void reservarLivro(double isbn){
        Reserva reserva = new Reserva(DAO.getLivro().encontrarPorISBN(isbn) , DAO.getLeitor().encontrarPorId(this.getID()));

        DAO.getReserva().criar(reserva);
    }
}
