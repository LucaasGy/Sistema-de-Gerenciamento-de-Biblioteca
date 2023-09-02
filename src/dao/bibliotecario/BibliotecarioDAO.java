package dao.bibliotecario;

import dao.CRUD;
import model.Bibliotecario;

import java.util.List;

public interface BibliotecarioDAO extends CRUD<Bibliotecario> {
    public List<Bibliotecario> encontrarPorNome(String nome);
}
