import java.util.List;

public interface EmprestimoDAO extends CRUD<Emprestimo>{
    public List<Emprestimo> encontrarTodosAtuais();
    public List<Emprestimo> encontrarHistoricoDeUmLeitor(int id);
}
