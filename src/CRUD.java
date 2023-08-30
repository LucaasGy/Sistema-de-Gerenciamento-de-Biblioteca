import java.util.List;

public interface CRUD<T> {
    T criar(T obj);
    void remover(int id);
    void removerTodos();
    T encontrarPorId(int id);
    List<T> encontrarTodos();
}
