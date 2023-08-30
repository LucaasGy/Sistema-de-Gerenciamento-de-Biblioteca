import java.util.ArrayList;
import java.util.List;

public class EmprestimoImpl implements EmprestimoDAO{
    private List<Emprestimo> listaEmprestimoTotal;
    private List<Emprestimo> listaEmprestimoAtual;

    public EmprestimoImpl() {
        this.listaEmprestimoTotal = new ArrayList<Emprestimo>();
        this.listaEmprestimoAtual = new ArrayList<Emprestimo>();
    }

    @Override
    public Emprestimo criar(Emprestimo obj) {
        obj.getLivro().setDisponivel(false);
        obj.getLivro().setQtdEmprestimo(obj.getLivro().getQtdEmprestimo()+1);

        this.listaEmprestimoTotal.add(obj);
        this.listaEmprestimoAtual.add(obj);

        return obj;
    }

    @Override
    public void remover(int id) {
        for(Emprestimo emp : this.listaEmprestimoAtual){
            if(emp.getLeitor().getID() == id){
                this.listaEmprestimoAtual.remove(emp);
                return;
            }
        }
    }

    @Override
    public Emprestimo encontrarPorId(int id) {
        for(Emprestimo emp : this.listaEmprestimoAtual){
            if(emp.getLeitor().getID()==id)
                return emp;
        }

        return null;
    }

    @Override //todos os emprestimos ja registrados
    public List<Emprestimo> encontrarTodos() {
        return this.listaEmprestimoTotal;
    }

    @Override //todos os emprestimos atuais
    public List<Emprestimo> encontrarTodosAtuais() {
        return this.listaEmprestimoAtual;
    }

    @Override
    public List<Emprestimo> encontrarHistoricoDeUmLeitor(int id) {
        List<Emprestimo> historicoDoID = new ArrayList<Emprestimo>();

        for(Emprestimo emp : this.listaEmprestimoTotal){
            if(emp.getLeitor().getID()==id)
                historicoDoID.add(emp);
        }

        return historicoDoID;
    }

    @Override
    public void removerTodos(){
        for(Emprestimo emp : this.listaEmprestimoTotal){
            emp.getLivro().setQtdEmprestimo(0);
        }
        this.listaEmprestimoAtual.clear();
        this.listaEmprestimoTotal.clear();
    }
}
