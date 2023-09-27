package dao.prazo;

import model.Prazos;
import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;

public class PrazosImplArquivo implements PrazosDAO{

    private String nomeArquivo;
    private String nomePasta;
    private List<Prazos> listaPrazos;

    public PrazosImplArquivo() {
        this.nomeArquivo = "prazos.dat";
        this.nomePasta = "Prazos";

        this.listaPrazos = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);
    }

    @Override
    public Prazos criar(Prazos obj) {
        this.listaPrazos.add(obj);
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
        return obj;
    }

    @Override
    public void remover(int id) {
        List<Prazos> prazosARemover = new ArrayList<>();

        for(Prazos prazos : this.listaPrazos){
            if (prazos.getLeitor() == id)
                prazosARemover.add(prazos);
        }

        this.listaPrazos.removeAll(prazosARemover);
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
    }

    @Override
    public void removerTodos() {
        this.listaPrazos.clear();
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
    }

    @Override
    public Prazos encontrarPorId(int id) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLeitor() == id){
                return prazo;
            }
        }

        return null;
    }

    @Override
    public List<Prazos> encontrarTodos() {
        return this.listaPrazos;
    }

    @Override
    public Prazos atualizar(Prazos obj) {
        return null;
    }

    @Override
    public Prazos encontrarPrazoDeUmLivro(double isbn) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro() == isbn){
                return prazo;
            }
        }

        return null;
    }

    @Override
    public void removerPrazoDeUmLivro(double isbn) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro() == isbn){
                this.listaPrazos.remove(prazo);
                ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
                return;
            }
        }
    }

    @Override
    public List<Prazos> prazosDeUmLeitor(int id) {
        List<Prazos> listaPrazosLeitor = new ArrayList<Prazos>();

        for(Prazos prazos : this.listaPrazos){
            if(prazos.getLeitor() == id)
                listaPrazosLeitor.add(prazos);
        }

        return listaPrazosLeitor;
    }

    @Override
    public void atualizarPrazos(List<Prazos> removePrazos, List<Prazos> adinionaPrazos) {
        this.listaPrazos.removeAll(removePrazos);
        this.listaPrazos.addAll(adinionaPrazos);
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
    }
}
