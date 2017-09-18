package id.personalia.employe.Helper;

import android.content.Context;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class ArkaHelper {

    Context context;

    public ArkaHelper(Context context){
        this.context = context;
    }

    public String zeroPadLeft(int i){

        if(i<=9){
            return "0" + String.valueOf(i);
        }else{
            return String.valueOf(i);
        }
    }
}
