package pekan5;
import java.util.*;

public class Rekening {
    private String nomorRekening;
    private String namaPemilik;
    private String pin;

    protected double saldo;
    protected ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;

        if (pinAwal.length() == 6) {
            this.pin = pinAwal;
        } else {
            System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
            this.pin = "123456";
        }

        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat");
    }

    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }

    public boolean otentikasi(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            String idTrx = "TRX-S-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
        }
    }

    // Mengubah void menjadi boolean
    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
            return false;
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
            return false;
        } else {
            saldo -= nominal;

            String idTrx = "TRX-T-" + System.currentTimeMillis();
            riwayatTransaksi.add(new Transaksi(idTrx, "Debit", nominal));

            System.out.println("Tarik tunai Rp" + nominal + " berhasil. Sisa saldo: Rp" + saldo);
            return true; // Transaksi sukses
        }
    }

    // Parameter polimorfik: tujuan bisa bertipe Tabungan atau Giro
    public void transfer(Rekening tujuan, double nominal, String pinInput) {
        if (!otentikasi(pinInput)) {
            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
            return;
        }
        if (tujuan == this) {
            System.out.println("Transfer Gagal: Rekening tujuan tidak boleh sama dengan rekening asal.");
            return;
        }
        if (this.tarikTunai(nominal)) { // overriding: kalau this = Giro, aturan overdraft dipakai
            tujuan.setorTunai(nominal);
            System.out.println("Transfer Rp" + nominal + " ke " + tujuan.getNamaPemilik() + " berhasil.");
        } else {
            System.out.println("Transfer Gagal: penarikan dari rekening asal tidak berhasil.");
        }
    }

    public void cetakMutasi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");
        } else {
            for (Transaksi t : riwayatTransaksi) {
                t.cetakDetail();
            }
        }
    }

    public boolean cocokDenganNomor(String nomor) {
        return this.nomorRekening.equals(nomor);
    }

    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("----------------------");
    }
}