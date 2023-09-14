import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MusteriKayitFormu extends JFrame {
    private JTextField musteriIDField;
    private JTextField adField;
    private JTextField soyadField;
    private DefaultTableModel tableModel;
    private MusteriService musteriService;

    public MusteriKayitFormu() {
        super("Müşteri Kayıt Formu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        musteriService = new MusteriService();

        String[] columnNames = {"Müşteri ID", "Müşteri Adı", "Müşteri Soyadı"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        JLabel lblMusteriID = new JLabel("Müşteri ID:");
        musteriIDField = new JTextField();
        JLabel lblAd = new JLabel("Müşteri Adı:");
        adField = new JTextField();
        JLabel lblSoyad = new JLabel("Müşteri Soyadı:");
        soyadField = new JTextField();
        JButton ekleButton = new JButton("Müşteri Ekle");

        ekleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String musteriID = musteriIDField.getText();
                String ad = adField.getText();
                String soyad = soyadField.getText();

                if (!musteriID.isEmpty() && !ad.isEmpty() && !soyad.isEmpty()) {
                    Musteri musteri = new Musteri(musteriID, ad, soyad);
                    musteriService.musteriEkle(musteri);
                    tabloyuGuncelle();
                } else {
                    JOptionPane.showMessageDialog(MusteriKayitFormu.this, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        inputPanel.add(lblMusteriID);
        inputPanel.add(musteriIDField);
        inputPanel.add(lblAd);
        inputPanel.add(adField);
        inputPanel.add(lblSoyad);
        inputPanel.add(soyadField);
        inputPanel.add(ekleButton);

        setVisible(true);
    }

    private void tabloyuGuncelle() {
        List<Musteri> musteriler = musteriService.getMusteriler();
        tableModel.getDataVector().removeAllElements();

        for (Musteri musteri : musteriler) {
            Object[] row = {musteri.getMusteriID(), musteri.getAd(), musteri.getSoyad()};
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusteriKayitFormu();
            }
        });
    }
}

class Musteri {
    private String musteriID;
    private String ad;
    private String soyad;

    public Musteri(String musteriID, String ad, String soyad) {
        this.musteriID = musteriID;
        this.ad = ad;
        this.soyad = soyad;
    }

    public String getMusteriID() {
        return musteriID;
    }

    public void setMusteriID(String musteriID) {
        this.musteriID = musteriID;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
}

class MusteriService {
    private List<Musteri> musteriler;

    public MusteriService() {
        musteriler = new ArrayList<>();
    }

    public void musteriEkle(Musteri musteri) {
        musteriler.add(musteri);
    }

    public List<Musteri> getMusteriler() {
        return musteriler;
    }
}
