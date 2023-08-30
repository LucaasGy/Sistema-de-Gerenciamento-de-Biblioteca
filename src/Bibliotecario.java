import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Bibliotecario extends Usuario{

    public Bibliotecario(String nome, String senha){
        super(nome, senha,TipoUsuario.Bibliotecario);
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

    public void registrarLivro(String titulo, String autor, String editora, int ano, String categoria){
        Livro opera = new Livro(titulo, autor, editora, ano, categoria);

        DAO.getLivro().criar(opera);
    }

    public void fazerEmprestimo(int id, double isbn){
        Emprestimo novo = new Emprestimo(DAO.getLivro().encontrarPorISBN(isbn),DAO.getLeitor().encontrarPorId(id));

        DAO.getEmprestimo().criar(novo);
    }

    public void devolverLivro(int id){
        Emprestimo opera = DAO.getEmprestimo().encontrarPorId(id);

        LocalDate devolve = LocalDate.now();

        long dias = ChronoUnit.DAYS.between(opera.getdataPrevista(),devolve);

        if(dias>0) {

            opera.getLeitor().setDataMulta(devolve.plusDays(dias*2));

            if(DAO.getReserva().leitorTemReserva(id))
                DAO.getReserva().remover(id);

            if(DAO.getPrazos().leitorTemPrazos(id))
                DAO.getPrazos().removerPrazosDeUmLeitor(id);
        }

        //caso o livro tenha reserva, ao ser devolvido, eu pego o top 1 da fila e crio um prazo para ele
        //fazer o emprestimo do livro e adiciono na lista de prazos cotendo de quem é o prazo, o livro do prazo
        //e a data limite que ele tem pra fazer o emprestimo
        //Ao tentar fazer o emprestimo, verifico se ta dentro do prazo
        //Se tiver, faco o emprestimo, removo o prazo e removo a reserva dele
        //Se nao tiver, nao faco o emprestimo, removo o prazo e removo a reserva dele
        if(DAO.getReserva().livroTemReserva(opera.getLivro().getISBN())){
            Reserva opera2 = DAO.getReserva().top1Reserva(opera.getLivro().getISBN());
            Prazos novo = new Prazos(opera2.getLeitor(),opera2.getLivro(),devolve.plusDays(2));

            DAO.getPrazos().criar(novo);
        }

        opera.getLivro().setDisponivel(true);
        opera.getLeitor().setLimiteRenova(0);

        DAO.getEmprestimo().remover(id);
    }
}
