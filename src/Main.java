import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main{

    public static void main(String[] args) {

        List<Integer> sla = new ArrayList<>();
        sla.add(2);
        sla.add(1);
        sla.add(5);
        sla.add(2);
        sla.add(18);
        sla.add(2);

        for(Integer kk : sla){
            System.out.println(kk);
        }

        for(int i=0; i<sla.size(); i++){
            if(sla.get(i)==2) {
                sla.add(6);
                sla.remove(sla.get(i));
            }
        }

        System.out.println("-------------------------------");

        for(Integer kk : sla){
            System.out.println(kk);
        }


    }
}