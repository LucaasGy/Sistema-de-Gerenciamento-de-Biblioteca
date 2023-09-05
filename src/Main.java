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

        for(Integer ll : sla){
            if(ll==2)
                sla.remove(ll);
        }

        for(Integer kk : sla){
            System.out.println(kk);
        }


    }
}