import java.util.ArrayList;
import java.util.List;

public class ReservaImpl implements ReservaDAO{

    private List<Reserva> listaReserva;

    public ReservaImpl() {
        this.listaReserva = new ArrayList<Reserva>();
    }

    @Override
    public Reserva criar(Reserva obj) {
        this.listaReserva.add(obj);

        return obj;
    }

    //remove todas as reservas de um leitor
    @Override
    public void remover(int id) {
        for(int i = 0; i < this.listaReserva.size(); i++){
            if (this.listaReserva.get(i).getLeitor().getID()==id)
                this.listaReserva.remove(i);
        }
    }

    //remove todas as reservas de um livro
    @Override
    public void removerReservasDeUmLivro(double isbn) {
        for(int i = 0; i < this.listaReserva.size(); i++){
            if (this.listaReserva.get(i).getLivro().getISBN()==isbn)
                this.listaReserva.remove(i);
        }
    }

    @Override
    public void removerTodos() {
        this.listaReserva.clear();
    }

    @Override
    public List<Reserva> encontrarTodos() {
        return this.listaReserva;
    }

    @Override
    public List<Reserva> encontrarLeitorReservouLivro(double isbn) {
        List<Reserva> listaReservaLeitor = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                listaReservaLeitor.add(reserva);
        }

        return listaReservaLeitor;
    }

    @Override
    public List<Reserva> encontrarLivroReservadoPorLeitor(int id) {
        List<Reserva> listaReservaLivro = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id)
                listaReservaLivro.add(reserva);
        }

        return listaReservaLivro;
    }

    @Override
    public boolean leitorTemReserva(int id){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id)
                return true;
        }

        return false;
    }

    @Override
    public boolean livroTemReserva(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                return true;
        }

        return false;
    }

    //retorno a primeira reserva de um livro para comparar de Id dessa reserva é o mesmo id de quem ta querendo fazer
    //o emprestimo de um livro reservado
    @Override
    public Reserva top1Reserva(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                return reserva;
        }

        return null;
    }

    //removo a primeira reserva de um livro ( top1 )
    @Override
    public void removeTop1(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn){
                this.listaReserva.remove(reserva);
                return;
            }
        }
    }

    @Override
    public Reserva encontrarPorId(int id) {
        return null;
    }
}
