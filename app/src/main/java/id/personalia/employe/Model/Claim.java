package id.personalia.employe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Claim {
    // Variable
    @SerializedName("CLNMPENGAJU")
    @Expose
    private String CLNMPENGAJU;

    @SerializedName("CLTTANGGAL")
    @Expose
    private String CLTTANGGAL;

    @SerializedName("CLJUMLAH")
    @Expose
    private String CLJUMLAH;

    @SerializedName("CLDESKRIKSI")
    @Expose
    private String CLDESKRIKSI;

    @SerializedName("CLSTATUS")
    @Expose
    private String CLSTATUS;

    // Function
    public String getCLNMPENGAJU() {
        return CLNMPENGAJU;
    }

    public void setCLNMPENGAJU(String CLNMPENGAJU) {
        this.CLNMPENGAJU = CLNMPENGAJU;
    }

    // Function
    public String getCLTTANGGAL() {
        return CLTTANGGAL;
    }

    public void setCLTTANGGAL(String CLTTANGGAL) {
        this.CLTTANGGAL = CLTTANGGAL;
    }

    public String getCLJUMLAH() {
        return CLJUMLAH;
    }

    public void setCLJUMLAH(String CLJUMLAH) {
        this.CLJUMLAH = CLJUMLAH;
    }

    public String getCLDESKRIKSI() {
        return CLDESKRIKSI;
    }

    public void setCLDESKRIKSI(String CLDESKRIKSI) {
        this.CLDESKRIKSI = CLDESKRIKSI;
    }

    public String getCLSTATUS() {
        return CLSTATUS;
    }

    public void setCLSTATUS(String CLSTATUS) {
        this.CLSTATUS = CLSTATUS;
    }

}
