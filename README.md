# 💻 Computer Rental Management System (Warnet)

Sistem manajemen rental komputer (warnet) berbasis Java dengan antarmuka GUI menggunakan Swing. Aplikasi ini dirancang untuk mengelola operasional warnet termasuk monitoring komputer, pengelolaan pelanggan, transaksi, dan pelaporan.

## 📋 Deskripsi

Sistem ini merupakan aplikasi desktop yang memungkinkan operator warnet untuk:
- Login dan autentikasi operator
- Monitoring status komputer secara real-time
- Mengelola data pelanggan, operator, dan komputer
- Memulai dan mengakhiri sesi penggunaan komputer
- Menghitung biaya otomatis berdasarkan durasi penggunaan
- Menghasilkan laporan pendapatan

## ✨ Fitur Utama

### 🔐 Autentikasi
- Login operator dengan username dan password
- Sistem keamanan untuk akses ke panel operator

### 🖥️ Manajemen Komputer
- Monitoring status komputer (KOSONG, AKTIF, MAINTENANCE)
- Menampilkan informasi pengguna aktif
- Tracking durasi penggunaan per komputer

### 👥 Manajemen Pelanggan
- Registrasi pelanggan baru
- Sistem member dan non-member
- Penyimpanan informasi kontak pelanggan

### 💳 Sistem Transaksi
- Pencatatan waktu mulai dan selesai penggunaan
- Perhitungan biaya otomatis berdasarkan durasi
- Cetak struk transaksi

### 📊 Pelaporan
- Laporan pendapatan periodik
- Ringkasan transaksi
- Total pendapatan


## 🏗️ Struktur Class

### 1. **Class Komputer**
Mengelola informasi dan status komputer. 

**Atribut:**
- `idKomputer` :  String
- `nomorKomputer` : int
- `status` : String (KOSONG, AKTIF, MAINTENANCE)
- `durasiPenggunaan` : int (dalam menit)
- `pelangganAktif` : Pelanggan

**Method:**
- `aktifkan(Pelanggan pelanggan)` - Mengaktifkan komputer untuk pelanggan
- `matikan()` - Menonaktifkan komputer dan menghentikan sesi
- `setStatus(String status)` - Mengubah status komputer
- `getStatus()` - Mendapatkan status komputer saat ini
- `hitungDurasi(LocalDateTime mulai, LocalDateTime selesai)` - Menghitung durasi penggunaan

### 2. **Class Pelanggan**
Mengelola data pelanggan warnet.

**Atribut:**
- `idPelanggan` : String
- `nama` : String
- `tipeAkun` : String (MEMBER, NON_MEMBER)
- `noTelepon` : String

**Method:**
- `login()` - Melakukan login pelanggan
- `logout()` - Melakukan logout pelanggan
- `tampilInfo()` - Menampilkan informasi pelanggan

### 3. **Class Operator**
Mengelola akun dan aktivitas operator warnet.

**Atribut:**
- `idOperator` : String
- `nama` : String
- `username` : String
- `password` : String

**Method:**
- `login(String username, String password)` - Validasi login operator
- `monitorKomputer(List<Komputer> komputerList)` - Monitoring semua komputer
- `prosesTransaksi(Pelanggan p, Komputer k)` - Memproses transaksi baru

### 4. **Class Transaksi**
Mencatat dan menghitung transaksi rental komputer.

**Atribut:**
- `idTransaksi` : String
- `pelanggan` : Pelanggan
- `komputer` : Komputer
- `waktuMulai` : LocalDateTime
- `waktuSelesai` : LocalDateTime
- `totalDurasi` : int
- `tarifPerJam` : double
- `totalBayar` : double

**Method:**
- `hitungDurasi()` - Menghitung durasi penggunaan dalam menit
- `hitungTotalBayar()` - Menghitung total biaya berdasarkan durasi
- `cetakStruk()` - Mencetak struk pembayaran

### 5. **Class Laporan**
Menghasilkan laporan pendapatan dan statistik.

**Atribut:**
- `idLaporan` : String
- `periodeAwal` : LocalDate
- `periodeAkhir` : LocalDate
- `daftarTransaksi` : List<Transaksi>
- `totalPendapatan` : double

**Method:**
- `generateLaporan()` - Membuat laporan periode tertentu
- `tampilkanRingkasan()` - Menampilkan ringkasan laporan
- `cetakLaporan()` - Mencetak laporan lengkap

### 6. **Class Main**
Class utama untuk menjalankan aplikasi. 

**Method:**
- `main(String[] args)` - Entry point aplikasi
- `menuUtama()` - Menampilkan menu utama
- `prosesLoginOperator()` - Memproses login operator
- `mulaiSesi()` - Memulai sesi penggunaan komputer
- `akhiriSesi()` - Mengakhiri sesi dan menghitung biaya

### 7. **Class GUI**
Antarmuka grafis pengguna menggunakan Java Swing.

**Fitur GUI:**
- Panel login operator


## 🔄 Alur Kerja Sistem

```
Login Operator → Menu Utama → Pilih Operasi
                              ├─ Monitor Komputer
                              ├─ Tambah Data
                              ├─ Mulai Sesi → Pilih Pelanggan & Komputer
                              ├─ Akhiri Sesi → Hitung Biaya → Cetak Struk
                              ├─ Cetak Laporan
                              └─ Logout
```
## 👨‍💻 Author

**bagaspng**
- GitHub: [@bagaspng](https://github.com/bagaspng)
---

⭐ **Jangan lupa berikan star jika proyek ini bermanfaat!** ⭐


