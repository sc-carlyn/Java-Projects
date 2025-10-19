import java.util.Scanner;

public class GeriSayim {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Kaç saniyeden geriye sayılsın? ");
        int saniye = scanner.nextInt();

        for (int i = saniye; i >= 0; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000); // 1 saniye bekle
        }

        System.out.println("⏰ Süre doldu!");
        scanner.close();
    }
}
