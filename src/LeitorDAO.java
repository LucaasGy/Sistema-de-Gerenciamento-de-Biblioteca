import java.util.List;

public interface LeitorDAO extends CRUD<Leitor>{
    public List<Leitor> encontrarPorNome(String nome);
    public List<Leitor> encontrarPorTelefone(String telefone);
}
