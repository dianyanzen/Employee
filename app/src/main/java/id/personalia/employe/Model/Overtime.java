package id.personalia.employe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Overtime implements Parcelable {
    private  int id;
    private String OTTANGGAL;
    private String OTDIAJUKAN;
    private String OTKETERANGAN;
    private String OTJAMLEMBUR;
    private String OTKALKULASI;
    private String OTJUMLAH;
    private String OTSTATUS;
    private String OTUANG;

    public Overtime(){

    }

    protected Overtime(Parcel in){
        id = in.readInt();
        OTTANGGAL = in.readString();
        OTDIAJUKAN = in.readString();
        OTKETERANGAN = in.readString();
        OTJAMLEMBUR = in.readString();
        OTKALKULASI = in.readString();
        OTJUMLAH = in.readString();
        OTSTATUS = in.readString();
        OTUANG = in.readString();

    }
    public static final Parcelable.Creator<Overtime> CREATOR = new Parcelable.Creator<Overtime>() {
        @Override
        public Overtime createFromParcel(Parcel in) {
            return new Overtime(in);
        }

        @Override
        public Overtime[] newArray(int size) {
            return new Overtime[size];
        }
    };
    // Function
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

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

    public void setOTUANG(String OTUANG) {
        this.OTUANG = OTUANG;
    }

    public String getOTUANG() {
        return OTUANG;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(OTTANGGAL);
        dest.writeString(OTDIAJUKAN);
        dest.writeString(OTKETERANGAN);
        dest.writeString(OTJAMLEMBUR);
        dest.writeString(OTKALKULASI);
        dest.writeString(OTJUMLAH);
        dest.writeString(OTSTATUS);
        dest.writeString(OTUANG);
    }

}