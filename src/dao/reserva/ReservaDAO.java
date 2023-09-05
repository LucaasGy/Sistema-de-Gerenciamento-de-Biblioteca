package dao.reserva;

import dao.CRUD;
import model.Reserva;

import java.util.List;

public interface ReservaDAO extends CRUD<Reserva> {
    public List<Reserva> encontrarLeitorReservouLivro(double isbn);
    public List<Reserva> encontrarLivroReservadoPorLeitor(int id);
    public void removerReservasDeUmLeitor(int id);
    public void removerReservasDeUmLivro(double isbn);
    public void removerUmaReserva(int id, double isbn);
    public boolean leitorTemReserva(int id);
    public boolean livroTemReserva(double isbn);
    public boolean leitorJaReservouEsseLivro(int id, double isbn);
    public Reserva top1Reserva(double isbn);
    public void removeTop1(double isbn);
}
