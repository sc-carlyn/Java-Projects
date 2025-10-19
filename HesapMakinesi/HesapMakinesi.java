import java.util.Scanner;

public class HesapMakinesi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Birinci sayıyı gir: ");
        double sayi1 = scanner.nextDouble();

        System.out.print("İkinci sayıyı gir: ");
        double sayi2 = scanner.nextDouble();

        System.out.print("İşlem seç (+ - * /): ");
        char islem = scanner.next().charAt(0);

        double sonuc = 0;

        if (islem == '+') {
            sonuc = sayi1 + sayi2;
        } else if (islem == '-') {
            sonuc = sayi1 - sayi2;
        } else if (islem == '*') {
            sonuc = sayi1 * sayi2;
        } else if (islem == '/') {
            if (sayi2 != 0) {
                sonuc = sayi1 / sayi2;
            } else {
                System.out.println("0'a bölme hatası!");
                scanner.close();
                return;
            }
        } else {
            System.out.println("Geçersiz işlem!");
            scanner.close();
            return;
        }

        System.out.println("Sonuç: " + sonuc);
        scanner.close();
    }
}
