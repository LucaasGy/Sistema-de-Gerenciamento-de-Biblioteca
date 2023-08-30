import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Bibliotecario extends Usuario{

    public Bibliotecario(String nome, String senha){
        super(nome, senha,TipoUsuario.Bibliotecario);
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

    public void registrarLivro(String titulo, String autor, String editora, int ano, String categoria){
        Livro opera = new Livro(titulo, autor, editora, ano, categoria);
        LivroImpl opera2 = new LivroImpl();

        opera2.criar(opera);
    }

    public void fazerEmprestimo(int id, double isbn){
        LivroImpl opera = new LivroImpl();
        LeitorImpl opera2 = new LeitorImpl();
        EmprestimoImpl opera3 = new EmprestimoImpl();

        Emprestimo novo = new Emprestimo(opera.encontrarPorISBN(isbn),opera2.encontrarPorId(id));

        opera3.criar(novo);
    }

    public void devolverLivro(int id){
        EmprestimoImpl opera = new EmprestimoImpl();
        ReservaImpl opera2 = new ReservaImpl();
        Emprestimo opera3 = opera.encontrarPorId(id);
        PrazosImpl opera4 = new PrazosImpl();

        LocalDate devolve = LocalDate.now();

        long dias = ChronoUnit.DAYS.between(opera3.getdataPrevista(),devolve);

        if(dias>0) {

            opera3.getLeitor().setDataMulta(devolve.plusDays(dias*2));

            if(opera2.leitorTemReserva(id))
                opera2.remover(id);

            if(opera4.leitorTemPrazos(id))
                opera4.removerPrazosDeUmLeitor(id);
        }

        //caso o livro tenha reserva, ao ser devolvido, eu pego o top 1 da fila e crio um prazo para ele
        //fazer o emprestimo do livro e adiciono na lista de prazos cotendo de quem é o prazo, o livro do prazo
        //e a data limite que ele tem pra fazer o emprestimo
        //Ao tentar fazer o emprestimo, verifico se ta dentro do prazo
        //Se tiver, faco o emprestimo, removo o prazo e removo a reserva dele
        //Se nao tiver, nao faco o emprestimo, removo o prazo e removo a reserva dele
        if(opera2.livroTemReserva(opera3.getLivro().getISBN())){
            Reserva opera5 = opera2.top1Reserva(opera3.getLivro().getISBN());
            Prazos novo = new Prazos(opera5.getLeitor(),opera5.getLivro(),devolve.plusDays(2));

            opera4.criar(novo);
        }

        opera3.getLivro().setDisponivel(true);
        opera3.getLeitor().setLimiteRenova(0);

        opera.remover(id);
    }
}
