package id.personalia.employe.Model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/19/2017.
 */

public class Project {
    @SerializedName("ProjectStatus")
    @Expose
    private String ProjectStatus;

    @SerializedName("ProjectID")
    @Expose
    private String ProjectID;

    @SerializedName("ProjectName")
    @Expose
    private String ProjectName;

    @SerializedName("ProjectDate")
    @Expose
    private String ProjectDate;

    public String getProjectStatus() {
        return ProjectStatus;
    }

    public void setProjectStatus(String ProjectStatus) {
        this.ProjectStatus = ProjectStatus;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String ProjectID) {
        this.ProjectID = ProjectID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getProjectDate() {
        return ProjectDate;
    }

    public void setProjectDate(String ProjectDate) {
        this.ProjectDate = ProjectDate;
    }

}