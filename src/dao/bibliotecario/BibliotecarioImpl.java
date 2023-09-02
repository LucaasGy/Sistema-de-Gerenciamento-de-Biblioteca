package dao.bibliotecario;

import model.Bibliotecario;

import java.util.ArrayList;
import java.util.List;

public class BibliotecarioImpl implements BibliotecarioDAO {
    private List<Bibliotecario> listaBibliotecario;
    private int nextID;

    public BibliotecarioImpl() {
        this.listaBibliotecario = new ArrayList<Bibliotecario>();
        this.nextID = 1002;
    }

    @Override
    public Bibliotecario criar(Bibliotecario obj) {
        obj.setID(this.nextID);
        this.nextID += 10;
        this.listaBibliotecario.add(obj);

        return obj;
    }

    @Override
    public Bibliotecario encontrarPorId(int id) {
        for(Bibliotecario biblio : this.listaBibliotecario){
            if(biblio.getID() == id)
                return biblio;
        }

        return null;
    }

    @Override
    public List<Bibliotecario> encontrarPorNome(String nome) {
        List<Bibliotecario> listaNome = new ArrayList<Bibliotecario>();

        for(Bibliotecario biblio : this.listaBibliotecario){
            if(biblio.getNome().equals(nome))
                listaNome.add(biblio);
        }

        return listaNome;
    }

    @Override
    public void remover(int id) {
        for(Bibliotecario biblio : this.listaBibliotecario){
            if(biblio.getID()==id) {
                this.listaBibliotecario.remove(biblio);
                return;
            }
        }
    }

    @Override
    public List<Bibliotecario> encontrarTodos() {
        return this.listaBibliotecario;
    }

    @Override
    public void removerTodos() {
        this.listaBibliotecario.clear();
        this.nextID = 1002;
    }
}
