import model.Adm;
import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args) {

        Adm adm = new Adm("adm1","senha1");
        Adm adm2 = new Adm("adm2","senha2");
        Adm adm3= new Adm("adm3","senha3");
        Adm adm4= new Adm("adm4","senha4");
        Adm adm5 = new Adm("adm5","senha5");

        List<Adm> lista = new ArrayList<Adm>();

        lista.add(adm);
        lista.add(adm2);
        lista.add(adm3);
        lista.add(adm4);
        lista.add(adm5);

        ArmazenamentoArquivo.guardar(lista,"adms.dat","Adm");

        List<Adm> resgata = new ArrayList<>(ArmazenamentoArquivo.resgatar("adms.dat","Adm"));

        for(Adm adms : resgata){
            System.out.println(adms.getNome());
        }
    }
}