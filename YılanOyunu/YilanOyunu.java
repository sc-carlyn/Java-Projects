import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class YilanOyunu extends JPanel implements ActionListener, KeyListener {

    private int genislik = 600;
    private int yukseklik = 600;
    private int birim = 25;
    private int oyunBirimSayisi = (genislik * yukseklik) / (birim * birim);
    private int[] yilanX = new int[oyunBirimSayisi];
    private int[] yilanY = new int[oyunBirimSayisi];
    private int yilanUzunlugu = 6;

    private int yemX, yemY;
    private int powerX = -100, powerY = -100;
    private boolean powerAktif = false;
    private char yon = 'R';
    private boolean calisiyor = false;
    private Timer zamanlayici;
    private int hiz;
    private Random random;

    private int[][] duvarlar = {
            {200, 200}, {225, 200}, {250, 200}, {275, 200}, {300, 200}, {325, 200}, {350, 200}, {375, 200},
            {200, 400}, {225, 400}, {250, 400}, {275, 400}, {300, 400}, {325, 400}, {350, 400}, {375, 400},
    };

    // Menü ve animasyon
    private boolean menu = true;
    private String zorlukSecildi = "";
    private int animasyonFrame = 0;

    public YilanOyunuPro_Sessiz() {
        random = new Random();
        this.setPreferredSize(new Dimension(genislik, yukseklik));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        Timer anim = new Timer(50, e -> {
            if (menu) {
                animasyonFrame++;
                repaint();
            }
        });
        anim.start();
    }

    public void baslatOyun() {
        yeniYem();
        calisiyor = true;
        hiz = switch (zorlukSecildi) {
            case "Kolay" -> 150;
            case "Orta" -> 100;
            default -> 70;
        };
        zamanlayici = new Timer(hiz, this);
        zamanlayici.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (menu) cizMenu(g);
        else if (calisiyor) cizim(g);
        else oyunBitti(g);
    }

    private void cizMenu(Graphics g) {
        g.setColor(Color.cyan);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrik = getFontMetrics(g.getFont());
        g.drawString("🐍 Yilan Oyunu", (genislik - metrik.stringWidth("Yilan Oyunu")) / 2, 100 + 10 * (animasyonFrame % 5));

        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        String[] secenekler = {"1-Kolay", "2-Orta", "3-Zor"};
        for (int i = 0; i < secenekler.length; i++) {
            g.drawString(secenekler[i], genislik / 2 - 60, 250 + i * 50);
        }

        g.setFont(new Font("Ink Free", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Secmek icin 1-3 tuslarini kullanin, ESC cikis", 120, 550);
    }

    private void cizim(Graphics g) {
        // Yem
        g.setColor(Color.red);
        g.fillOval(yemX, yemY, birim, birim);

        // Power-up
        if (powerAktif) {
            g.setColor(Color.yellow);
            g.fillOval(powerX, powerY, birim, birim);
        }

        // Duvarlar
        g.setColor(Color.gray);
        for (int[] duvar : duvarlar) g.fillRect(duvar[0], duvar[1], birim, birim);

        // Yılan
        for (int i = 0; i < yilanUzunlugu; i++) {
            g.setColor(i == 0 ? Color.green : new Color(45, 180, 0));
            g.fillRect(yilanX[i], yilanY[i], birim, birim);
        }

        // Skor
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrik = getFontMetrics(g.getFont());
        g.drawString("Skor: " + (yilanUzunlugu - 6), (genislik - metrik.stringWidth("Skor: " + (yilanUzunlugu - 6))) / 2, g.getFont().getSize());
    }

    private void yeniYem() {
        yemX = random.nextInt(genislik / birim) * birim;
        yemY = random.nextInt(yukseklik / birim) * birim;
    }

    private void hareket() {
        for (int i = yilanUzunlugu; i > 0; i--) {
            yilanX[i] = yilanX[i - 1];
            yilanY[i] = yilanY[i - 1];
        }
        switch (yon) {
            case 'U' -> yilanY[0] -= birim;
            case 'D' -> yilanY[0] += birim;
            case 'L' -> yilanX[0] -= birim;
            case 'R' -> yilanX[0] += birim;
        }
    }

    private void yemKontrol() {
        if (yilanX[0] == yemX && yilanY[0] == yemY) {
            yilanUzunlugu++;
            yeniYem();

            if (hiz > 50) {
                hiz -= 5;
                zamanlayici.setDelay(hiz);
            }

            if (!powerAktif && random.nextInt(5) == 0) {
                powerX = random.nextInt(genislik / birim) * birim;
                powerY = random.nextInt(yukseklik / birim) * birim;
                powerAktif = true;
            }
        }

        if (powerAktif && yilanX[0] == powerX && yilanY[0] == powerY) {
            yilanUzunlugu += 3;
            powerAktif = false;
            powerX = -100;
            powerY = -100;
            hiz += 10;
            zamanlayici.setDelay(hiz);
        }
    }

    private void carpmaKontrol() {
        for (int i = yilanUzunlugu; i > 0; i--) {
            if (yilanX[0] == yilanX[i] && yilanY[0] == yilanY[i]) calisiyor = false;
        }
        for (int[] duvar : duvarlar) {
            if (yilanX[0] == duvar[0] && yilanY[0] == duvar[1]) calisiyor = false;
        }
        if (yilanX[0] < 0 || yilanX[0] >= genislik || yilanY[0] < 0 || yilanY[0] >= yukseklik) calisiyor = false;

        if (!calisiyor) zamanlayici.stop();
    }

    private void oyunBitti(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrik1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (genislik - metrik1.stringWidth("GAME OVER")) / 2, yukseklik / 2);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrik2 = getFontMetrics(g.getFont());
        g.drawString("Skor: " + (yilanUzunlugu - 6), (genislik - metrik2.stringWidth("Skor: " + (yilanUzunlugu - 6))) / 2, yukseklik / 2 + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (calisiyor) {
            hareket();
            yemKontrol();
            carpmaKontrol();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (menu) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1 -> {
                    zorlukSecildi = "Kolay";
                    menu = false;
                    baslatOyun();
                }
                case KeyEvent.VK_2 -> {
                    zorlukSecildi = "Orta";
                    menu = false;
                    baslatOyun();
                }
                case KeyEvent.VK_3 -> {
                    zorlukSecildi = "Zor";
                    menu = false;
                    baslatOyun();
                }
                case KeyEvent.VK_ESCAPE -> System.exit(0);
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (yon != 'R') yon = 'L';
                }
                case KeyEvent.VK_RIGHT -> {
                    if (yon != 'L') yon = 'R';
                }
                case KeyEvent.VK_UP -> {
                    if (yon != 'D') yon = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if (yon != 'U') yon = 'D';
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        YilanOyunuPro_Sessiz oyun = new YilanOyunuPro_Sessiz();
        frame.add(oyun);
        frame.setTitle("🐍 Yilan Oyunu - Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
