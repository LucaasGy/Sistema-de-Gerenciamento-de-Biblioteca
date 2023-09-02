package dao.adm;

import model.Adm;

import java.util.ArrayList;
import java.util.List;

public class AdmImpl implements AdmDAO {
    private List<Adm> listaAdm;
    private int nextID;

    public AdmImpl() {
        this.listaAdm = new ArrayList<Adm>();
        this.nextID = 1001;
    }

    @Override
    public Adm criar(Adm obj) {
        obj.setID(this.nextID);
        this.nextID += 10;
        this.listaAdm.add(obj);

        return obj;
    }

    @Override
    public Adm encontrarPorId(int id) {
        for(Adm adm : this.listaAdm){
            if(adm.getID() == id)
                return adm;
        }

        return null;
    }

    @Override
    public List<Adm> encontrarPorNome(String nome) {
        List<Adm> listaNome = new ArrayList<Adm>();

        for(Adm adm : this.listaAdm){
            if(adm.getNome().equals(nome))
                listaNome.add(adm);
        }

        return listaNome;
    }

    @Override
    public void remover(int id) {
        for(Adm adm : this.listaAdm){
            if(adm.getID()==id) {
                this.listaAdm.remove(adm);
                return;
            }
        }
    }

    @Override
    public List<Adm> encontrarTodos() {

        return this.listaAdm;
    }

    @Override
    public void removerTodos() {
        this.listaAdm.clear();
        this.nextID = 1001;
    }
}
