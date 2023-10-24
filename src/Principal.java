import br.com.reclamabus.modelos.*;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PublicoGeral pg = new PublicoGeral();
        pg.setNome(System.out.println(sc.nextLine));

        pg.exibeConta();
    }



}
