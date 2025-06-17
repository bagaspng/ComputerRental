import java.time.LocalDateTime;
public class Transaksi {
    private int idTransaksi;
    private Pelanggan pelanggan;
    private Komputer komputer;
    private LocalDateTime waktuMulai;
    private LocalDateTime waktuSelesai;
    private int totalDurasi;
    private final double tarifPerJam = 5000;
    private double totalBayar;

    public Transaksi(int id, Pelanggan p, Komputer k, LocalDateTime mulai) {
        this.idTransaksi = id;
        this.pelanggan = p;
        this.komputer = k;
        this.waktuMulai = mulai;
    }

    public int hitungDurasi() {
        this.waktuSelesai = LocalDateTime.now();
        this.totalDurasi = komputer.hitungDurasi(waktuMulai, waktuSelesai);
        return totalDurasi;
    }

    public double hitungTotalBayar() {
        double jam = totalDurasi / 60.0;
        this.totalBayar = tarifPerJam * jam;
        return totalBayar;
    }

    public void cetakStruk() {
        System.out.println("Transaksi ID: " + idTransaksi);
        System.out.println("Pelanggan: " + pelanggan.getNama());
        System.out.println("Durasi: " + totalDurasi + " menit");
        System.out.println("Total Bayar: Rp" + totalBayar);
    }
    
    @Override
    public String toString() {
    return "ID: " + idTransaksi +
           ", Pelanggan: " + pelanggan.getNama() +
           ", Komputer: " + komputer.getNomorKomputer() +
           ", Mulai: " + waktuMulai +
           ", Selesai: " + (waktuSelesai != null ? waktuSelesai : "Belum selesai") +
           ", Total: Rp" + totalBayar;
    }

    // Getters and Setters
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public void setPelanggan(Pelanggan pelanggan) { this.pelanggan = pelanggan; }
    public Komputer getKomputer() { return komputer; }
    public void setKomputer(Komputer komputer) { this.komputer = komputer; }
    public LocalDateTime getWaktuMulai() { return waktuMulai; }
    public void setWaktuMulai(LocalDateTime waktuMulai) { this.waktuMulai = waktuMulai; }
    public LocalDateTime getWaktuSelesai() { return waktuSelesai; }
    public void setWaktuSelesai(LocalDateTime waktuSelesai) { this.waktuSelesai = waktuSelesai; }
    public int getTotalDurasi() { return totalDurasi; }
    public void setTotalDurasi(int totalDurasi) { this.totalDurasi = totalDurasi; }
    public double getTarifPerJam() { return tarifPerJam; }
    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }
}