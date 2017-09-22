package id.personalia.employe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Report {
    // Variable
    @SerializedName("RPNAMA")
    @Expose
    private String RPNAMA;

    @SerializedName("RPTANGGAL")
    @Expose
    private String RPTANGGAL;

    @SerializedName("RPJABATAN")
    @Expose
    private String RPJABATAN;

    @SerializedName("RPCL_IN")
    @Expose
    private String RPCL_IN;

    @SerializedName("RPCL_OUT")
    @Expose
    private String RPCL_OUT;

    @SerializedName("RPWORKHOUR")
    @Expose
    private String RPWORKHOUR;

    @SerializedName("RPSTATUS")
    @Expose
    private String RPSTATUS;

    @SerializedName("RPNOREG")
    @Expose
    private String RPNOREG;

    @SerializedName("RPGOL")
    @Expose
    private String RPGOL;
    // Function
    public String getRPNAMA() {
        return RPNAMA;
    }

    public void setRPNAMA(String RPNAMA) {
        this.RPNAMA = RPNAMA;
    }

    public String getRPTANGGAL() {
        return RPTANGGAL;
    }

    public void setRPTANGGAL(String RPTANGGAL) {
        this.RPTANGGAL = RPTANGGAL;
    }

    public String getRPJABATAN() {
        return RPJABATAN;
    }

    public void setRPJABATAN(String RPJABATAN) {
        this.RPJABATAN = RPJABATAN;
    }

    public String getRPCL_IN() {
        return RPCL_IN;
    }

    public void setRPCL_IN(String RPCL_IN) {
        this.RPCL_IN = RPCL_IN;
    }

    public String getRPCL_OUT() {
        return RPCL_OUT;
    }

    public void setRPCL_OUT(String RPCL_OUT) {
        this.RPCL_OUT = RPCL_OUT;
    }

    public String getRPWORKHOUR() {
        return RPWORKHOUR;
    }

    public void setRPWORKHOUR(String RPWORKHOUR) {
        this.RPWORKHOUR = RPWORKHOUR;
    }

    public String getRPSTATUS() {
        return RPSTATUS;
    }

    public void setRPSTATUS(String RPSTATUS) { this.RPSTATUS = RPSTATUS; }

    public String getRPNOREG() {
        return RPNOREG;
    }

    public void setRPNOREG(String RPNOREG) { this.RPNOREG = RPNOREG; }

    public String getRPGOL() {
        return RPGOL;
    }

    public void setRPGOL(String RPGOL) { this.RPGOL = RPGOL; }
}
