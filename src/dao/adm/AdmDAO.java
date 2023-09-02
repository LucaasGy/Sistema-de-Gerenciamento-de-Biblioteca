package dao.adm;

import dao.CRUD;
import model.Adm;

import java.util.List;
public interface AdmDAO extends CRUD<Adm> {
    public List<Adm> encontrarPorNome(String nome);
}
