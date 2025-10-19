import java.util.Scanner;

public class SinifBasariDurumu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Sınıftaki öğrenci sayısını giriniz: ");
        int ogrenciSayisi = scanner.nextInt();

        int[] notlar = new int[ogrenciSayisi];

        for (int i = 0; i < ogrenciSayisi; i++) {
            System.out.print((i + 1) + ". öğrencinin notunu giriniz: ");
            notlar[i] = scanner.nextInt();
        }

        int toplam = 0;
        for (int not : notlar) {
            toplam += not;
        }
        double ortalama = (double) toplam / ogrenciSayisi;

        System.out.println("\n--- SINIF SONUÇLARI ---");
        System.out.println("Sınıf Ortalaması: " + ortalama);

        if (ortalama >= 85) {
            System.out.println("Durum: 🏆 Mükemmel! Sınıf çok başarılı!");
        } else if (ortalama >= 70) {
            System.out.println("Durum: 👍 İyi! Daha da yükselebilir.");
        } else if (ortalama >= 50) {
            System.out.println("Durum: ⚠️ Orta! Daha çok çalışmak gerek.");
        } else {
            System.out.println("Durum: ❌ Başarısız! Acil destek gerekli.");
        }

        int max = notlar[0];
        int min = notlar[0];
        for (int i = 1; i < notlar.length; i++) {
            if (notlar[i] > max) max = notlar[i];
            if (notlar[i] < min) min = notlar[i];
        }

        System.out.println("En yüksek not: " + max);
        System.out.println("En düşük not: " + min);

        scanner.close();
    }
}