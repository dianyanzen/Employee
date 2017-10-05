package id.personalia.employe.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dian Yanzen on 10/3/2017.
 */

public class SearchData implements Parcelable {
    int id;
    String paramater;
    String value;
    public SearchData(){

    }

    protected SearchData(Parcel in){
        id = in.readInt();
        paramater = in.readString();
        value = in.readString();
    }
    public static final Parcelable.Creator<SearchData> CREATOR = new Parcelable.Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel in) {
            return new SearchData(in);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };
    public int getIdSearch() { return id; }

    public void setIdSearch(int id) {this.id = id;}

    public String getParamaterSearch() { return paramater; }

    public void setParamaterSearch(String paramater) {this.paramater = paramater;}

    public String getValueSearch() { return value; }

    public void setValueSearch(String value) {this.value = value;}
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(paramater);
        dest.writeString(value);
    }
}
