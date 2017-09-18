package id.personalia.employe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Travel {
    @SerializedName("TANGGAL")
    @Expose
    private String TANGGAL;

    @SerializedName("TUJUAN")
    @Expose
    private String TUJUAN;

    @SerializedName("PROYEK")
    @Expose
    private String PROYEK;

    @SerializedName("STATUS")
    @Expose
    private int STATUS;

    public String getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public String getTUJUAN() {
        return TUJUAN;
    }

    public void setTUJUAN(String TUJUAN) {
        this.TUJUAN = TUJUAN;
    }

    public String getPROYEK() {
        return PROYEK;
    }

    public void setPROYEK(String PROYEK) {
        this.PROYEK = PROYEK;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }
}
