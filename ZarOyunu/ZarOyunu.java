import java.util.Random;
import java.util.Scanner;

public class ZarOyunu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("🎲 Zar Oyunu Başlıyor!");
        System.out.print("Zar atmak için ENTER'a bas...");
        scanner.nextLine();

        int oyuncuZar = random.nextInt(6) + 1;
        int pcZar = random.nextInt(6) + 1;

        System.out.println("Senin zarın: " + oyuncuZar);
        System.out.println("Bilgisayarın zarı: " + pcZar);

        if (oyuncuZar > pcZar) {
            System.out.println("🏆 Tebrikler! Sen kazandın!");
        } else if (oyuncuZar < pcZar) {
            System.out.println("🤖 Bilgisayar kazandı!");
        } else {
            System.out.println("🤝 Berabere!");
        }

    
        scanner.close();
    }
}
