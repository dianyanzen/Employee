package id.personalia.employe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Travel implements Parcelable
    {

        private int id;
        private String TANGGAL;
        private String TUJUAN;
        private String PROYEK;
        private String STATUS;
        private String _LONG;
        private String APROVAL_BY;
        private String PARTICIPANT;

	public Travel() {

    }

    protected Travel(Parcel in) {
        id = in.readInt();
        TANGGAL = in.readString();
        TUJUAN = in.readString();
        PROYEK = in.readString();
        STATUS = in.readString();
        _LONG = in.readString();
        APROVAL_BY = in.readString();
        PARTICIPANT = in.readString();
    }
        public static final Creator<Travel> CREATOR = new Creator<Travel>() {
            @Override
            public Travel createFromParcel(Parcel in) {
                return new Travel(in);
            }

            @Override
            public Travel[] newArray(int size) {
                return new Travel[size];
            }
        };

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getLONG() { return _LONG; }

    public void setLONG(String _LONG) { this._LONG = _LONG; }

    public String getAPROVAL_BY() { return APROVAL_BY; }

    public void setAPROVAL_BY(String APROVAL_BY) { this.APROVAL_BY = APROVAL_BY; }

    public String getPARTICIPANT() { return PARTICIPANT; }

    public void setPARTICIPANT(String PARTICIPANT) { this.PARTICIPANT = PARTICIPANT; }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(TANGGAL);
            dest.writeString(TUJUAN);
            dest.writeString(PROYEK);
            dest.writeString(STATUS);
            dest.writeString(_LONG);
            dest.writeString(APROVAL_BY);
            dest.writeString(PARTICIPANT);
        }

}
