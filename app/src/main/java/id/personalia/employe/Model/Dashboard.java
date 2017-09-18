package id.personalia.employe.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Dashboard {
    // VARIABLE MAIN
    @SerializedName("MAIN")
    @Expose
    private String MAIN;

    @SerializedName("INFO")
    @Expose
    private String INFO;

    @SerializedName("STATUS")
    @Expose
    private String STATUS;



    // FUNCTION MAIN
    public String getMAIN() {
        return MAIN;
    }

    public void setMAIN(String MAIN) {
        this.MAIN = MAIN;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public String getSTATUS() { return STATUS; }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }


}
