package id.personalia.employe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Attendance {
    // Variable
    @SerializedName("ATTLAMA")
    @Expose
    private String ATTLAMA;

    @SerializedName("ATTTANGGAL")
    @Expose
    private String ATTTANGGAL;

    @SerializedName("ATTDESCRIP")
    @Expose
    private String ATTDESCRIP;

    @SerializedName("ATTDIAJUKAN")
    @Expose
    private String ATTDIAJUKAN;

    @SerializedName("ATTSTATUS")
    @Expose
    private String ATTSTATUS;


    // Function
    public String getATTTANGGAL() {
        return ATTTANGGAL;
    }

    public void setATTTANGGAL(String ATTTANGGAL) {
        this.ATTTANGGAL = ATTTANGGAL;
    }

    public String getATTLAMA() {
        return ATTLAMA;
    }

    public void setATTLAMA(String ATTLAMA) {
        this.ATTLAMA = ATTLAMA;
    }

    public String getATTDESCRIP() {
        return ATTDESCRIP;
    }

    public void setATTDESCRIP(String ATTDESCRIP) {
        this.ATTDESCRIP = ATTDESCRIP;
    }

    public String getATTDIAJUKAN() {
        return ATTDIAJUKAN;
    }

    public void setATTDIAJUKAN(String ATTDIAJUKAN) {
        this.ATTDIAJUKAN = ATTDIAJUKAN;
    }

    public String getATTSTATUS() {
        return ATTSTATUS;
    }

    public void setATTSTATUS(String ATTSTATUS) { this.ATTSTATUS = ATTSTATUS; }

}
