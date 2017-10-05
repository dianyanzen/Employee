package id.personalia.employe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Claim implements Parcelable
{

    private int id;
    private String CLNMPENGAJU;
    private String CLTTANGGAL;
    private String CLJUMLAH;
    private String CLDESKRIKSI;
    private String CLDESCRIPTION;
    private String CLSTATUS;

    public Claim() {


    }

    protected Claim(Parcel in) {
        id = in.readInt();
        CLNMPENGAJU = in.readString();
        CLTTANGGAL = in.readString();
        CLJUMLAH = in.readString();
        CLDESKRIKSI = in.readString();
        CLDESCRIPTION = in.readString();
        CLSTATUS = in.readString();
    }
        public static final Parcelable.Creator<Claim> CREATOR = new Parcelable.Creator<Claim>() {
            @Override
            public Claim createFromParcel(Parcel in) {
                return new Claim(in);
            }

            @Override
            public Claim[] newArray(int size) {
                return new Claim[size];
            }
        };

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
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

    public String getCLDESCRIPTION() {
        return CLDESCRIPTION;
    }

    public void setCLDESCRIPTION(String CLDESCRIPTION) {
        this.CLDESCRIPTION = CLDESCRIPTION;
    }

    public String getCLSTATUS() {
        return CLSTATUS;
    }

    public void setCLSTATUS(String CLSTATUS) {
        this.CLSTATUS = CLSTATUS;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(CLNMPENGAJU);
        dest.writeString(CLTTANGGAL);
        dest.writeString(CLJUMLAH);
        dest.writeString(CLDESKRIKSI);
        dest.writeString(CLDESCRIPTION);
        dest.writeString(CLSTATUS);
    }
}
