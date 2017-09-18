package id.personalia.employe.Model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class InputForm {
    @SerializedName("ICON")
    @Expose
    private Drawable ICON;

    @SerializedName("LABEL")
    @Expose
    private String LABEL;

    @SerializedName("TYPE")
    @Expose
    private String TYPE;

    @SerializedName("VALUE")
    @Expose
    private String VALUE;

    public Drawable getICON() {
        return ICON;
    }

    public void setICON(Drawable ICON) {
        this.ICON = ICON;
    }

    public String getLABEL() {
        return LABEL;
    }

    public void setLABEL(String LABEL) {
        this.LABEL = LABEL;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

}
