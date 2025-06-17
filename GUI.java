import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private JPanel panelLogin, panelMenu;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextArea textAreaOutput;
    private JTable tableKomputer;
    private Operator currentOperator;

    public GUI() {
        setTitle("Sistem Warnet - Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initLoginPanel();
        setVisible(true);
    }

    private void initLoginPanel() {
        panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        panelLogin.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Login Operator");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(15);
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15);

        JPanel panelFields = new JPanel();
        panelFields.setLayout(new GridLayout(2, 2, 10, 10));
        panelFields.setOpaque(false);
        panelFields.add(lblUsername);
        panelFields.add(txtUsername);
        panelFields.add(lblPassword);
        panelFields.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        JButton btnExit = new JButton("Keluar");
        btnLogin.setPreferredSize(new Dimension(100, 36));
        btnExit.setPreferredSize(new Dimension(100, 36));

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelButtons.setOpaque(false);
        panelButtons.add(btnLogin);
        panelButtons.add(btnExit);

        panelLogin.add(lblTitle);
        panelLogin.add(Box.createVerticalStrut(25));
        panelLogin.add(panelFields);
        panelLogin.add(Box.createVerticalStrut(15));
        panelLogin.add(panelButtons);

        setContentPane(panelLogin);

        btnLogin.addActionListener(e -> prosesLogin());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void prosesLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        for (Operator o : Main.getOperatorList()) {
            if (o.login(username, password)) {
                currentOperator = o;
                JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + o.getNama());
                showMenuPanel();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Login gagal. Username atau password salah.");
    }

    private void showMenuPanel() {
        getContentPane().removeAll();
        setTitle("Sistem Warnet - Menu Operator");

        panelMenu = new JPanel(new BorderLayout(10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        panelMenu.setBackground(Color.WHITE);

        JLabel lblMenu = new JLabel("Menu Operator");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMenu.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelButtons = new JPanel(new GridBagLayout());
        panelButtons.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        String[] btnNames = {
            "Monitor Komputer", "Tambah Data", "Mulai Sesi",
            "Akhiri sesi", "Cetak Laporan", "Logout"
        };
        JButton[] buttons = new JButton[btnNames.length];
        for (int i = 0; i < btnNames.length; i++) {
            buttons[i] = new JButton(btnNames[i]);
            buttons[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
            buttons[i].setPreferredSize(new Dimension(180, 38));
            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            panelButtons.add(buttons[i], gbc);
        }

        // Assign actions
        buttons[0].addActionListener(e -> tampilkanDataKomputer());
        buttons[1].addActionListener(e -> tambahData());
        buttons[2].addActionListener(e -> mulaiSesi());
        buttons[3].addActionListener(e -> akhiriSesi());
        buttons[4].addActionListener(e -> cetakLaporan());
        buttons[5].addActionListener(e -> logout());

        panelMenu.add(lblMenu, BorderLayout.NORTH);
        panelMenu.add(panelButtons, BorderLayout.CENTER);

        setContentPane(panelMenu);
        revalidate();
        repaint();
    }

    private void mulaiSesi() {
        List<Komputer> komputerList = Main.getKomputerList();
        List<Pelanggan> pelangganList = Main.getPelangganList();

        if (komputerList.isEmpty() || pelangganList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data komputer atau pelanggan kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] pelangganOptions = pelangganList.stream().map(Pelanggan::getNama).toArray(String[]::new);
        String namaPelanggan = (String) JOptionPane.showInputDialog(this, "Pilih Pelanggan:", "Mulai Sesi", JOptionPane.QUESTION_MESSAGE, null, pelangganOptions, pelangganOptions[0]);

        Pelanggan selectedPelanggan = pelangganList.stream().filter(p -> p.getNama().equals(namaPelanggan)).findFirst().orElse(null);
        String nomorKomputer = JOptionPane.showInputDialog(this, "Masukkan Nomor Komputer:");

        for (Komputer k : komputerList) {
            if (String.valueOf(k.getNomorKomputer()).equals(nomorKomputer) && k.getStatus().equals("KOSONG")) {
                Transaksi t = currentOperator.prosesTransaksi(selectedPelanggan, k);
                Main.getTransaksiList().add(t);
                JOptionPane.showMessageDialog(this, "Sesi dimulai untuk Komputer " + nomorKomputer);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Komputer tidak tersedia atau nomor salah.");
    }

    private void akhiriSesi() {
        List<Transaksi> transaksiList = Main.getTransaksiList();

        if (transaksiList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada transaksi.");
            return;
        }

        String[] transaksiOptions = transaksiList.stream().map(t -> "ID " + t.getIdTransaksi() + " - " + t.getPelanggan().getNama()).toArray(String[]::new);
        String pilihan = (String) JOptionPane.showInputDialog(this, "Pilih Transaksi:", "Akhiri Sesi", JOptionPane.QUESTION_MESSAGE, null, transaksiOptions, transaksiOptions[0]);

        Transaksi t = transaksiList.stream().filter(tx -> pilihan.contains(String.valueOf(tx.getIdTransaksi()))).findFirst().orElse(null);

        if (t != null) {
            int durasi = t.hitungDurasi();
            double bayar = t.hitungTotalBayar();
            t.getKomputer().matikan();
            JOptionPane.showMessageDialog(this, "Durasi: " + durasi + " menit\nTotal Bayar: Rp" + bayar);
        }
    }

    private void tambahData() {
        String[] opsi = {"Tambah Operator", "Tambah Komputer", "Tambah Pelanggan"};
        String pilihan = (String) JOptionPane.showInputDialog(this, "Pilih Data yang Ingin Ditambah:", "Tambah Data", JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[0]);

        if (pilihan == null) return;

        switch (pilihan) {
            case "Tambah Operator":
                try {
                    String namaOp = JOptionPane.showInputDialog(this, "Nama:");
                    if (namaOp == null || namaOp.trim().isEmpty()) throw new Exception("Nama tidak boleh kosong.");
                    String userOp = JOptionPane.showInputDialog(this, "Username:");
                    if (userOp == null || userOp.trim().isEmpty()) throw new Exception("Username tidak boleh kosong.");
                    String passOp = JOptionPane.showInputDialog(this, "Password:");
                    if (passOp == null || passOp.trim().isEmpty()) throw new Exception("Password tidak boleh kosong.");
                    Main.getOperatorList().add(new Operator(Main.generateId(), namaOp, userOp, passOp));
                    JOptionPane.showMessageDialog(this, "Operator berhasil ditambahkan.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menambah operator: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Tambah Komputer":
                try {
                    String nomorStr = JOptionPane.showInputDialog(this, "Nomor Komputer:");
                    if (nomorStr == null || nomorStr.trim().isEmpty()) throw new Exception("Nomor komputer tidak boleh kosong.");
                    int nomor;
                    try {
                        nomor = Integer.parseInt(nomorStr.trim());
                    } catch (NumberFormatException e) {
                        throw new Exception("Nomor komputer harus berupa angka!");
                    }
                    // Cek duplikasi nomor komputer
                    for (Komputer k : Main.getKomputerList()) {
                        if (k.getNomorKomputer() == nomor) {
                            throw new Exception("Nomor komputer sudah terdaftar!");
                        }
                    }
                    Main.getKomputerList().add(new Komputer(Main.getKomputerList().size() + 1, nomor));
                    JOptionPane.showMessageDialog(this, "Komputer berhasil ditambahkan dengan Nomor: " + nomor);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menambah komputer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Tambah Pelanggan":
                try {
                    String namaP = JOptionPane.showInputDialog(this, "Nama Pelanggan:");
                    if (namaP == null || namaP.trim().isEmpty()) throw new Exception("Nama pelanggan tidak boleh kosong.");
                    String telp = JOptionPane.showInputDialog(this, "No Telepon:");
                    if (telp == null || telp.trim().isEmpty()) throw new Exception("No telepon tidak boleh kosong.");
                    Main.getPelangganList().add(new Pelanggan(Main.generateId(), namaP, telp));
                    JOptionPane.showMessageDialog(this, "Pelanggan berhasil ditambahkan.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menambah pelanggan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }

    private void tampilkanDataKomputer() {
        List<Komputer> list = Main.getKomputerList();

        String[] columnNames = {"ID", "Nomor Komputer", "Status", "Nama Pengguna", "No HP"};
        String[][] data = new String[list.size()][5];

        for (int i = 0; i < list.size(); i++) {
            Komputer k = list.get(i);
            data[i][0] = String.valueOf(k.getIdKomputer());
            data[i][1] = String.valueOf(k.getNomorKomputer());
            data[i][2] = k.getStatus();
            data[i][3] = k.getPengguna() != null ? k.getPengguna().getNama() : "-";
            data[i][4] = k.getPengguna() != null ? k.getPengguna().getNoTelepon() : "-";
        }

        tableKomputer = new JTable(new DefaultTableModel(data, columnNames));
        JOptionPane.showMessageDialog(this, new JScrollPane(tableKomputer), "Data Komputer", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cetakLaporan() {
        Laporan laporan = new Laporan();
        laporan.setIdLaporan("LAP" + System.currentTimeMillis());
        laporan.setPeriodeAwal(java.time.LocalDate.now().minusDays(7));
        laporan.setPeriodeAkhir(java.time.LocalDate.now());
        laporan.setDaftarTransaksi(Main.getTransaksiList());
        laporan.generateLaporan();

        // Akumulasi total semua transaksi
        double totalSemua = 0;
        for (Transaksi t : Main.getTransaksiList()) {
            totalSemua += t.hitungTotalBayar();
        }

        String isiLaporan = laporan.getIsiLaporan() + "\n\nTotal Pendapatan: Rp" + totalSemua;

        JTextArea laporanArea = new JTextArea(isiLaporan);
        laporanArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(laporanArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Laporan Mingguan", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        currentOperator = null;
        getContentPane().removeAll();
        initLoginPanel();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        Main.addAll(new Operator(1, "Admin", "admin", "admin123"));
        SwingUtilities.invokeLater(GUI::new);
    }
}