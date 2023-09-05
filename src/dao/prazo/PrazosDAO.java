package dao.prazo;

import dao.CRUD;
import model.Prazos;

import java.util.List;

public interface PrazosDAO extends CRUD<Prazos> {
    public List<Prazos> encontrarPrazosDeUmLeitor(int id);
    public Prazos encontrarPrazoDeUmLivro(double isbn);
    public Prazos encontrarUmPrazo(int id, double isbn);
    public void removerPrazosDeUmLeitor(int id);
    public void removerPrazosDeUmLivro(double isbn);
    public void removerUmPrazo(int id, double isbn);
    public boolean leitorTemPrazos(int id);
    public boolean livroTemPrazos(double isbn);
}
