package pekan4;

public class RekeningGiro extends Rekening {
	private double batasOverdraft;
	
	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft)
	{		// Memanggil inisialisasi dasar dari SuperClass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	// Getter khusu Giro
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	// (Catatan: Penarikan hingga limit Overdraft akan kita selesaikan di Modul 5)
}
