package id.personalia.employe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class Attendance implements Parcelable {
    private int id;
    private String ATTTANGGAL;
    private String ATTLAMA;
    private String ATTDESCRIP;
    private String ATTDIAJUKAN;
    private String ATTSTATUS;

    public Attendance() {

    }

    protected Attendance(Parcel in) {
        id = in.readInt();
        ATTTANGGAL = in.readString();
        ATTLAMA = in.readString();
        ATTDESCRIP = in.readString();
        ATTDIAJUKAN = in.readString();
        ATTSTATUS = in.readString();
    }
    public static final Parcelable.Creator<Attendance> CREATOR = new Parcelable.Creator<Attendance>() {
        @Override
        public Attendance createFromParcel(Parcel in) {
            return new Attendance(in);
        }

        @Override
        public Attendance[] newArray(int size) {
            return new Attendance[size];
        }
    };


    // Function
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(ATTTANGGAL);
        dest.writeString(ATTLAMA);
        dest.writeString(ATTDESCRIP);
        dest.writeString(ATTDIAJUKAN);
        dest.writeString(ATTSTATUS);
    }
}
