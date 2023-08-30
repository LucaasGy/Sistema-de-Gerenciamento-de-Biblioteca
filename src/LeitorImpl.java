import java.util.ArrayList;
import java.util.List;
public class LeitorImpl implements LeitorDAO{
    private List<Leitor> listaLeitor;
    private int nextID;

    public LeitorImpl() {
        this.listaLeitor = new ArrayList<Leitor>();
        this.nextID = 1003;
    }

    @Override
    public Leitor criar(Leitor obj) {
        obj.setID(this.nextID);
        this.nextID+=10;
        this.listaLeitor.add(obj);

        return obj;
    }

    @Override
    public List<Leitor> encontrarPorNome(String nome) {
        List<Leitor> listaNome = new ArrayList<Leitor>();

        for(Leitor leitor : this.listaLeitor){
            if(leitor.getNome().equals(nome))
                listaNome.add(leitor);
        }

        return listaNome;
    }

    @Override
    public List<Leitor> encontrarPorTelefone(String telefone) {
        List<Leitor> listaTelefone = new ArrayList<Leitor>();

        for(Leitor leitor : this.listaLeitor){
            if(leitor.getTelefone().equals(telefone))
                listaTelefone.add(leitor);
        }

        return listaTelefone;
    }

    @Override
    public Leitor encontrarPorId(int id) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID() == id)
                return leitor;
        }

        return null;
    }

    @Override
    public void remover(int id) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID()==id){
                this.listaLeitor.remove(leitor);
                return;
            }
        }
    }

    @Override
    public List<Leitor> encontrarTodos() {
        return this.listaLeitor;
    }

    @Override
    public void removerTodos() {
        this.listaLeitor.clear();
        this.nextID=1003;
    }
}
