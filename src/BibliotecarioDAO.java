import java.util.List;

public interface BibliotecarioDAO extends CRUD<Bibliotecario>{
    public List<Bibliotecario> encontrarPorNome(String nome);
}
