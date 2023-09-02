package dao.prazo;

import model.Prazos;

import java.util.ArrayList;
import java.util.List;

public class PrazosImpl implements PrazosDAO {

    private List<Prazos> listaPrazos;

    public PrazosImpl() {
        this.listaPrazos = new ArrayList<Prazos>();
    }

    @Override
    public Prazos criar(Prazos obj) {
        this.listaPrazos.add(obj);
        return obj;
    }

    @Override
    public void removerUmPrazo(int id, double isbn){
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLeitor().getID()==id && prazo.getLivro().getISBN()==isbn) {
                this.listaPrazos.remove(prazo);
                return;
            }
        }
    }

    @Override
    public void removerTodos() {
        this.listaPrazos.clear();
    }

    @Override
    public List<Prazos> encontrarTodos() {
       return this.listaPrazos;
    }

    @Override
    public List<Prazos> encontrarPrazosDeUmLeitor(int id) {
        List<Prazos> prazosLeitor = new ArrayList<Prazos>();
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLeitor().getID()==id){
                prazosLeitor.add(prazo);
            }
        }

        return prazosLeitor;
    }

    @Override
    public List<Prazos> encontrarPrazosDeUmLivro(double isbn) {
        List<Prazos> prazosLivro = new ArrayList<Prazos>();
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro().getISBN()==isbn){
                prazosLivro.add(prazo);
            }
        }

        return prazosLivro;
    }

    @Override
    public Prazos encontrarUmPrazo(int id, double isbn){
        for(Prazos prazos : this.listaPrazos){
            if(prazos.getLeitor().getID()==id && prazos.getLivro().getISBN()==isbn)
                return prazos;
        }

        return null;
    }

    @Override
    public void removerPrazosDeUmLeitor(int id) {
        for(int i = 0; i < this.listaPrazos.size(); i++){
            if (this.listaPrazos.get(i).getLeitor().getID()==id)
                this.listaPrazos.remove(i);
        }
    }

    @Override
    public void removerPrazosDeUmLivro(double isbn) {
        for(int i = 0; i < this.listaPrazos.size(); i++){
            if (this.listaPrazos.get(i).getLivro().getISBN()==isbn)
                this.listaPrazos.remove(i);
        }
    }

    @Override
    public boolean leitorTemPrazos(int id) {
        for(Prazos prazos : this.listaPrazos){
            if(prazos.getLeitor().getID()==id)
                return true;
        }

        return false;
    }

    @Override
    public boolean livroTemPrazos(double isbn) {
        for(Prazos prazos : this.listaPrazos){
            if(prazos.getLivro().getISBN()==isbn)
                return true;
        }

        return false;
    }

    @Override
    public void remover(int id) {
    }

    @Override
    public Prazos encontrarPorId(int id) {
        return null;
    }
}
