import java.util.Random;

public class Main{

    public static void main(String[] args) {

        Random r = new Random();

        double ra = 10.0 + r.nextDouble()*90;
        ra = Math.round(ra*100000.0)/100000.0;
        System.out.println(ra);
    }
}