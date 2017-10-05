package id.personalia.employe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Report implements Parcelable {
    // Variable
    private int id;
    private String RPNAMA;
    private String RPTANGGAL;
    private String RPJABATAN;
    private String RPCL_IN;
    private String RPCL_OUT;
    private String RPWORKHOUR;
    private String RPSTATUS;
    private String RPNOREG;
    private String RPGOL;

    public Report(){

    }
    protected Report(Parcel in){
        id = in.readInt();
        RPNAMA = in.readString();
        RPTANGGAL = in.readString();
        RPJABATAN = in.readString();
        RPCL_IN = in.readString();
        RPCL_OUT = in.readString();
        RPWORKHOUR = in.readString();
        RPSTATUS = in.readString();
        RPNOREG = in.readString();
        RPGOL = in.readString();

    }
    public static final Parcelable.Creator<Report> CREATOR = new Parcelable.Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    // Function
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getRPNAMA() { return RPNAMA; }

    public void setRPNAMA(String RPNAMA) { this.RPNAMA = RPNAMA; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(RPNAMA);
        dest.writeString(RPTANGGAL);
        dest.writeString(RPJABATAN);
        dest.writeString(RPCL_IN);
        dest.writeString(RPCL_OUT);
        dest.writeString(RPWORKHOUR);
        dest.writeString(RPSTATUS);
        dest.writeString(RPNOREG);
        dest.writeString(RPGOL);
    }
}
