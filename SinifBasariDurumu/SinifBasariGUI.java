import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SinifBasariGUI extends JFrame {
    private JTextField ogrenciSayisiField;
    private JButton notlariGirButton;
    private JTextArea sonucAlani;

    public SinifBasariGUI() {
        setTitle("Sınıf Başarı Durumu");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Öğrenci sayısını girin:");
        ogrenciSayisiField = new JTextField(10);
        notlariGirButton = new JButton("Notları Gir");
        sonucAlani = new JTextArea(10, 30);
        sonucAlani.setEditable(false);
        JScrollPane scroll = new JScrollPane(sonucAlani);

        add(label);
        add(ogrenciSayisiField);
        add(notlariGirButton);
        add(scroll);

        notlariGirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notlariAlVeHesapla();
            }
        });
    }

    private void notlariAlVeHesapla() {
        try {
            int ogrenciSayisi = Integer.parseInt(ogrenciSayisiField.getText());
            int[] notlar = new int[ogrenciSayisi];

            // Notları diyalog kutularından al
            for (int i = 0; i < ogrenciSayisi; i++) {
                String input = JOptionPane.showInputDialog(
                        SinifBasariGUI.this,
                        (i + 1) + ". öğrencinin notunu giriniz:",
                        "Not Girişi",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (input == null) return; // İptal edilirse çık
                notlar[i] = Integer.parseInt(input);
            }

            // Ortalama hesapla
            int toplam = 0;
            for (int not : notlar) {
                toplam += not;
            }
            double ortalama = (double) toplam / ogrenciSayisi;

            // En yüksek ve en düşük notu bul
            int max = notlar[0];
            int min = notlar[0];
            for (int i = 1; i < notlar.length; i++) {
                if (notlar[i] > max) max = notlar[i];
                if (notlar[i] < min) min = notlar[i];
            }

            // Durumu if-else ile belirle
            String durum;
            if (ortalama >= 85) {
                durum = "🏆 Mükemmel! Sınıf çok başarılı!";
            } else if (ortalama >= 70) {
                durum = "👍 İyi! Daha da yükselebilir.";
            } else if (ortalama >= 50) {
                durum = "⚠️ Orta! Daha çok çalışmak gerek.";
            } else {
                durum = "❌ Başarısız! Acil destek gerekli.";
            }

            // Sonucu ekrana yazdır
            sonucAlani.setText("Sınıf Ortalaması: " + ortalama + "\n");
            sonucAlani.append("En yüksek not: " + max + "\n");
            sonucAlani.append("En düşük not: " + min + "\n");
            sonucAlani.append("Durum: " + durum);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SinifBasariGUI().setVisible(true);
        });
    }
}