package dao.emprestimo;

import dao.CRUD;
import model.Emprestimo;

import java.util.List;

public interface EmprestimoDAO extends CRUD<Emprestimo> {
    public List<Emprestimo> encontrarTodosAtuais();
    public List<Emprestimo> encontrarHistoricoDeUmLeitor(int id);
    public Emprestimo encontrarPorISBN(double isbn);
    public boolean livroTemEmprestimo(double isbn);
}
