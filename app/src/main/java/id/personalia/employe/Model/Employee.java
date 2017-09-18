package id.personalia.employe.Model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Employee {
    @SerializedName("PHOTO")
    @Expose
    private Drawable PHOTO;

    @SerializedName("ID")
    @Expose
    private String ID;

    @SerializedName("FULLNAME")
    @Expose
    private String FULLNAME;

    public Drawable getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(Drawable PHOTO) {
        this.PHOTO = PHOTO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

}
