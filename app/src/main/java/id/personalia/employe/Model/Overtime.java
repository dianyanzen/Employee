package id.personalia.employe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Overtime {
    // Variable
    @SerializedName("OTTANGGAL")
    @Expose
    private String OTTANGGAL;

    @SerializedName("OTDIAJUKAN")
    @Expose
    private String OTDIAJUKAN;

    @SerializedName("OTKETERANGAN")
    @Expose
    private String OTKETERANGAN;

    @SerializedName("OTJAMLEMBUR")
    @Expose
    private String OTJAMLEMBUR;

    @SerializedName("OTKALKULASI")
    @Expose
    private String OTKALKULASI;

    @SerializedName("OTJUMLAH")
    @Expose
    private String OTJUMLAH;

    @SerializedName("OTSTATUS")
    @Expose
    private String OTSTATUS;

    // Function
    public String getOTTANGGAL() {
        return OTTANGGAL;
    }

    public void setOTTANGGAL(String OTTANGGAL) {
        this.OTTANGGAL = OTTANGGAL;
    }

    // Function
    public String getOTDIAJUKAN() {
        return OTDIAJUKAN;
    }

    public void setOTDIAJUKAN(String OTDIAJUKAN) {
        this.OTDIAJUKAN = OTDIAJUKAN;
    }

    public String getOTKETERANGAN() {
        return OTKETERANGAN;
    }

    public void setOTKETERANGAN(String OTKETERANGAN) {
        this.OTKETERANGAN = OTKETERANGAN;
    }

    public String getOTJAMLEMBUR() {
        return OTJAMLEMBUR;
    }

    public void setOTJAMLEMBUR(String OTJAMLEMBUR) {
        this.OTJAMLEMBUR = OTJAMLEMBUR;
    }

    public String getOTKALKULASI() {
        return OTKALKULASI;
    }

    public void setOTKALKULASI(String OTKALKULASI) {
        this.OTKALKULASI = OTKALKULASI;
    }

    public String getOTJUMLAH() {
        return OTJUMLAH;
    }

    public void setOTJUMLAH(String OTJUMLAH) {
        this.OTJUMLAH = OTJUMLAH;
    }

    public String getOTSTATUS() {
        return OTSTATUS;
    }

    public void setOTSTATUS(String OTSTATUS) {
        this.OTSTATUS = OTSTATUS;
    }

}