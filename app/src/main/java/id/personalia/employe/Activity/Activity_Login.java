package id.personalia.employe.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Activity_Login  extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String KEY_UserId_SP = "MyUserid";
    public static final String KEY_Sessid_SP = "MySessid";
    public static final String KEY_UserName_SP = "MyUsername";
    public static final String KEY_Password_SP = "MyPassword";
    public static final String KEY_Token_SP = "MyToken";
    TextView regis;
    private EditText etloginUsername;
    private EditText etloginPassword;
    AppCompatButton login;
    private int pressbuttonback = 0;
    DbUserData db = new DbUserData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etloginUsername = (EditText) findViewById(R.id.email);
        etloginPassword = (EditText) findViewById(R.id.password);
        login = (AppCompatButton)findViewById(R.id.email_sign_in_button);
        regis  = (TextView)findViewById(R.id.register);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(mainIntent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent mainIntent = new Intent(Activity_Login.this, Activity_Main.class);
                //startActivity(mainIntent);
                //finish();
                final String username = etloginUsername.getText().toString();
                final String password = etloginPassword.getText().toString();
                final String token = FirebaseInstanceId.getInstance().getToken();
                final String UrlPort = "http://192.168.4.112/cpms/Androidlogin";
                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(username, password, token, UrlPort);
                Log.e("Alur1", "Awal");

            }
        });
    }
    private void launchMenu(){
        Intent mainIntent = new Intent(Activity_Login.this, Activity_Main.class);
        mainIntent.putExtra("FragmentNM", "Dashboard");
        startActivity(mainIntent);
        finish();
    }
    private class AsyncLogin extends AsyncTask<String, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(Activity_Login.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[3]);
                Log.e("Alur", url.toString());
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e("Alur", "url error");
                return "exception";
            }
            try {

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        //.appendQueryParameter("user_email", "9909896")
                        //.appendQueryParameter("user_password", "12345678")
                        .appendQueryParameter("user_email", params[0])
                        .appendQueryParameter("user_password", params[1])
                        .appendQueryParameter("token", params[2]);
                String query = builder.build().getEncodedQuery();
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(Activity_Login.this);
                SharedPreferences.Editor prefEditor = sharedpreferences.edit();
                prefEditor.putString(KEY_UserName_SP, params[0]);
                prefEditor.putString(KEY_Password_SP, params[1]);
                prefEditor.putString(KEY_Token_SP, params[2]);
                prefEditor.commit();
                /*
                SharedPreferences loginpreferance = PreferenceManager.getDefaultSharedPreferences(Activity_Login.this);
                SharedPreferences.Editor PrefUsername = loginpreferance.edit();
                PrefUsername.putString(KEY_UserName, params[0]);
                PrefUsername.commit();
                SharedPreferences.Editor PrefPassword = loginpreferance.edit();
                PrefPassword.putString(KEY_Password, params[1]);
                PrefPassword.commit();
                */
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                Log.e("Alur", "Kirim Url");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";

            }
            try{
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();
            Log.e("RES", result);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(result);
                if(jsonObj.has("status"))
                {
                    Log.e("Log",jsonObj.toString());
                    Log.e("data", "ada datanya");
                    //JSONObject resp = jsonObj.getJSONObject("status");
                    Boolean respon = jsonObj.getBoolean("status");
                    Log.e("Last",respon.toString());

                    if(respon == true)
                    {
                        //NotifModel NotM = new NotifModel();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Activity_Login.this);
                        JSONArray JData = new JSONArray(jsonObj.getString("data"));
                        Log.e("JData", JData.toString());
                        //Toast.makeText(Activity_Login.this, jsonObj.getString("data"), Toast.LENGTH_LONG).show();
                        JSONObject data = new JSONObject(JData.getString(0));
                        Log.e("JData2", data.toString());
                        String employee_id = new String(data.getString("employee_id"));
                        String atasan_id = new String(data.getString("atasan_id"));
                        String company_id = new String(data.getString("company_id"));
                        String user_email = new String(data.getString("user_email"));
                        String position_id = new String(data.getString("position_id"));
                        String role_id = new String(data.getString("role_id"));
                        String role_salary_id = new String(data.getString("role_salary_id"));
                        String no_reg = new String(data.getString("no_reg"));
                        String employee_name = new String(data.getString("employee_name"));
                        String start_working_dt = new String(data.getString("start_working_dt"));
                        String born_place = new String(data.getString("born_place"));
                        String born_dt = new String(data.getString("born_dt"));
                        String address = new String(data.getString("address"));
                        String address_street = new String(data.getString("address_street"));
                        String address_region = new String(data.getString("address_region"));
                        String address_sub_district = new String(data.getString("address_sub_district"));
                        String address_province = new String(data.getString("address_province"));
                        String address_country = new String(data.getString("address_country"));
                        String address_postal_code = new String(data.getString("address_postal_code"));
                        String handphone1 = new String(data.getString("handphone1"));
                        String handphone2 = new String(data.getString("handphone2"));
                        String closed_person_name = new String(data.getString("closed_person_name"));
                        String closed_person_phone = new String(data.getString("closed_person_phone"));
                        String marital = new String(data.getString("marital"));
                        String npwp_number = new String(data.getString("npwp_number"));
                        String bank_account_number = new String(data.getString("bank_account_number"));
                        String work_email = new String(data.getString("work_email"));
                        String is_active = new String(data.getString("is_active"));
                        String photo = new String(data.getString("photo"));
                        String id_att = new String(data.getString("id_att"));
                        String religion = new String(data.getString("religion"));
                        String gender = new String(data.getString("gender"));
                        String married_status = new String(data.getString("married_status"));
                        String married_since = new String(data.getString("married_since"));
                        String npwp_dt = new String(data.getString("npwp_dt"));
                        String bpjs_ketenagakerjaan = new String(data.getString("bpjs_ketenagakerjaan"));
                        String bpjs_kesehatan = new String(data.getString("bpjs_kesehatan"));
                        String work_unit_id = new String(data.getString("work_unit_id"));
                        String status = new String(data.getString("status"));
                        String boleh_cuti = new String(data.getString("boleh_cuti"));
                        String identity_no = new String(data.getString("identity_no"));
                        String created_by = new String(data.getString("created_by"));
                        String created_dt = new String(data.getString("created_dt"));
                        String changed_by = new String(data.getString("changed_by"));
                        String changed_dt = new String(data.getString("changed_dt"));
                        String role_name = new String(data.getString("role_name"));
                        String position_name = new String(data.getString("position_name"));
                        String user_group_description = new String(data.getString("user_group_description"));
                        String user_group_id = new String(data.getString("user_group_id"));
                        String is_admin = new String(data.getString("is_admin"));
                        String atasan_name = new String(data.getString("atasan_name"));

                        Log.e("employee_id", employee_id.toString());
                        Log.e("atasan_id", atasan_id.toString());
                        Log.e("company_id", company_id.toString());
                        Log.e("user_email", user_email.toString());
                        Log.e("position_id", position_id.toString());
                        Log.e("role_id", role_id.toString());
                        Log.e("role_salary_id", role_salary_id.toString());
                        Log.e("no_reg", no_reg.toString());
                        Log.e("employee_name", employee_name.toString());
                        Log.e("start_working_dt", start_working_dt.toString());
                        Log.e("born_place", born_place.toString());
                        Log.e("born_dt", born_dt.toString());
                        Log.e("address", address.toString());
                        Log.e("address_street", address_street.toString());
                        Log.e("address_region", address_region.toString());
                        Log.e("address_sub_district", address_sub_district.toString());
                        Log.e("address_province", address_province.toString());
                        Log.e("address_country", address_country.toString());
                        Log.e("address_postal_code", address_postal_code.toString());
                        Log.e("handphone1", handphone1.toString());
                        Log.e("handphone2", handphone2.toString());
                        Log.e("closed_person_name", closed_person_name.toString());
                        Log.e("closed_person_phone", closed_person_phone.toString());
                        Log.e("marital", marital.toString());
                        Log.e("npwp_number", npwp_number.toString());
                        Log.e("bank_account_number", bank_account_number.toString());
                        Log.e("work_email", work_email.toString());
                        Log.e("is_active", is_active.toString());
                        Log.e("photo", photo.toString());
                        Log.e("id_att", id_att.toString());
                        Log.e("religion", religion.toString());
                        Log.e("gender", gender.toString());
                        Log.e("married_status", married_status.toString());
                        Log.e("married_since", married_since.toString());
                        Log.e("npwp_dt", npwp_dt.toString());
                        Log.e("bpjs_ketenagakerjaan", bpjs_ketenagakerjaan.toString());
                        Log.e("bpjs_kesehatan", bpjs_kesehatan.toString());
                        Log.e("work_unit_id", work_unit_id.toString());
                        Log.e("status", status.toString());
                        Log.e("boleh_cuti", boleh_cuti.toString());
                        Log.e("identity_no", identity_no.toString());
                        Log.e("created_by", created_by.toString());
                        Log.e("created_dt", created_dt.toString());
                        Log.e("changed_by", changed_by.toString());
                        Log.e("changed_dt", changed_dt.toString());
                        Log.e("role_name", role_name.toString());
                        Log.e("position_name", position_name.toString());
                        Log.e("user_group_description", user_group_description.toString());
                        Log.e("user_group_id", user_group_id.toString());
                        Log.e("is_admin", is_admin.toString());
                        Log.e("atasan_name", atasan_name.toString());
                        Log.d("Insert: ", "Inserting ..");
                        db.addUserData(new UserData(employee_id,atasan_id,company_id,user_email,position_id,role_id,role_salary_id,
                                no_reg,employee_name,start_working_dt,born_place,born_dt,address,address_street,address_region,address_sub_district,
                                address_province,address_country,address_postal_code,handphone1,handphone2,closed_person_name,closed_person_phone,marital,
                                npwp_number,bank_account_number,work_email,is_active,photo,id_att,religion,gender,married_status,married_since,npwp_dt,
                                bpjs_ketenagakerjaan,bpjs_kesehatan,work_unit_id,status,boleh_cuti,identity_no,created_by,created_dt,changed_by,changed_dt,
                                role_name,position_name,user_group_description,user_group_id,is_admin,atasan_name));


                        Log.d("Reading: ", "Reading all Data..");
                        List<UserData> UserDataList = db.getAllUserDataList();

                        for (UserData userData : UserDataList) {
                            String log1 = "employee_id: " + userData.getEMPLOYEE_ID() +
                                    " ,atasan_id: " + userData.getATASAN_ID() +
                                    " ,company_id: " + userData.getCOMPANY_ID() +
                                    " ,user_email: " + userData.getUSER_EMAIL() +
                                    " ,position_id: " + userData.getPOSITION_ID() +
                                    " ,role_id: " + userData.getROLE_ID() +
                                    " ,role_salary_id: " + userData.getROLE_SALARY_ID() +
                                    " ,no_reg: " + userData.getNO_REG() +
                                    " ,employee_name: " + userData.getEMPLOYEE_NAME() +
                                    " ,start_working_dt: " + userData.getSTART_WORKING_DT() ;

                            String log2 = "born_place: " + userData.getBORN_PLACE() +
                                    " ,born_dt: " + userData.getBORN_DT() +
                                    " ,address: " + userData.getADDRESS() +
                                    " ,address_street: " + userData.getADDRESS_STREET() +
                                    " ,address_region: " + userData.getADDRESS_REGION() +
                                    " ,address_sub_district: " + userData.getADDRESS_SUB_DISTRICT() +
                                    " ,address_province: " + userData.getADDRESS_PROVINCE() +
                                    " ,address_country: " + userData.getADDRESS_COUNTRY() +
                                    " ,address_postal_code: " + userData.getADDRESS_POSTAL_CODE() +
                                    " ,handphone1: " + userData.getHANDPHONE1() ;

                            String log3 = "handphone2: " + userData.getHANDPHONE2() +
                                    " ,closed_person_name: " + userData.getCLOSED_PERSON_NAME() +
                                    " ,closed_person_phone: " + userData.getCLOSED_PERSON_PHONE() +
                                    " ,marital: " + userData.getMARITAL() +
                                    " ,npwp_number: " + userData.getNPWP_NUMBER() +
                                    " ,bank_account_number: " + userData.getBANK_ACCOUNT_NUMBER() +
                                    " ,work_email: " + userData.getWORK_EMAIL() +
                                    " ,is_active: " + userData.getIS_ACTIVE() +
                                    " ,photo: " + userData.getPHOTO() +
                                    " ,id_att: " + userData.getID_ATT() ;

                            String log4 = "religion: " + userData.getRELIGION() +
                                    " ,gender: " + userData.getGENDER() +
                                    " ,married_status: " + userData.getMARRIED_SINCE() +
                                    " ,married_since: " + userData.getMARRIED_SINCE() +
                                    " ,npwp_dt: " + userData.getNPWP_DT() +
                                    " ,bpjs_ketenagakerjaan: " + userData.getBPJS_KETENAGAKERJAAN() +
                                    " ,bpjs_kesehatan: " + userData.getBPJS_KESEHATAN() +
                                    " ,work_unit_id: " + userData.getWORK_UNIT_ID() +
                                    " ,status: " + userData.getSTATUS() +
                                    " ,boleh_cuti: " + userData.getBOLEH_CUTI() ;
                            String log5 = "identity_no: " + userData.getIDENTITY_NO() +
                                    " ,created_by: " + userData.getCREATED_BY() +
                                    " ,created_dt: " + userData.getCREATED_DT() +
                                    " ,changed_by: " + userData.getCHANGED_BY() +
                                    " ,changed_dt: " + userData.getCHANGED_DT() +
                                    " ,role_name: " + userData.getROLE_NAME() +
                                    " ,position_name: " + userData.getPOSITION_NAME() +
                                    " ,user_group_description: " + userData.getUSER_GROUP_DESCRIPTION() +
                                    " ,user_group_id: " + userData.getUSER_GROUP_ID() +
                                    " ,atasan_name: " + userData.getATASAN_NAME() +
                                    " ,is_admin: " + userData.getIS_ADMIN();
                            Log.d("List1: : ", log1);
                            Log.d("List2: : ", log2);
                            Log.d("List3: : ", log3);
                            Log.d("List4: : ", log4);
                            Log.d("List5: : ", log5);
                            //Toast.makeText(Activity_Login.this, log1, Toast.LENGTH_LONG).show();
                        }
                        launchMenu();

                        /*

                            */
                        //Toast.makeText(Activity_Login.this, jsonObj.getString(JData.getString(0)), Toast.LENGTH_LONG).show();
                        /*
                        JSONObject JUser = new JSONObject(JData.getString("User"));
                        Log.e("JUser", JUser.toString());
                        String JUserID = JUser.getString("UserId");
                        Log.e("JUserID", JUserID.toString());
                        String JUserSes = JData.getString("SessionId");
                        Log.e("JUserSes", JUserSes.toString());
                        /*
                        SharedPreferences.Editor UserIdkey = sharedPreferences.edit();
                        UserIdkey.putString(KEY_UserId, JUserID);
                        UserIdkey.commit();


                        //NotM.setUserId(JUserID);
                        //NotM.setUserId(JUserSes);
                        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(Activity_Login.this);
                        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
                        prefEditor.putString(KEY_UserId, JUserID);
                        prefEditor.putString(KEY_Sessid, JUserSes);
                        prefEditor.commit();
                        Log.e("result", KEY_UserId.toString());
                        Log.e("result", KEY_Sessid.toString());
                        //Log.e("ModelUserId", NotM.getUserId());
                        //Log.e("ModelSessionId", NotM.getSessionId());
                        //launchMenu();
                        */
                    }else if (respon == false){
                        String message = jsonObj.getString("message");
                        //JSONObject JMessage = new JSONObject(jsonObj.getString("message"));
                        Log.e("DEBUG", jsonObj.getString("message"));
                        // If username and password does not match display a error message
                        String TostMessage = message;
                        Log.e("ToastMessage", TostMessage);
                        if (TostMessage != "null" && TostMessage != null && !TostMessage.isEmpty() ){
                            Toast.makeText(Activity_Login.this, jsonObj.getString("message"), Toast.LENGTH_LONG).show();
                            Log.e("LastMessage", jsonObj.getString("message"));
                            SharedPreferences loginpreferance = PreferenceManager.getDefaultSharedPreferences(Activity_Login.this);
                            SharedPreferences.Editor PrefUsername = loginpreferance.edit();
                            PrefUsername.putString(KEY_UserName_SP, null);
                            PrefUsername.commit();
                            SharedPreferences.Editor PrefPassword = loginpreferance.edit();
                            PrefPassword.putString(KEY_Password_SP, null);
                            PrefPassword.commit();
                        }else{
                            Toast.makeText(Activity_Login.this, "Invalid Username or password", Toast.LENGTH_LONG).show();
                            SharedPreferences loginpreferance = PreferenceManager.getDefaultSharedPreferences(Activity_Login.this);
                            SharedPreferences.Editor PrefUsername = loginpreferance.edit();
                            PrefUsername.putString(KEY_UserName_SP, null);
                            PrefUsername.commit();
                            SharedPreferences.Editor PrefPassword = loginpreferance.edit();
                            PrefPassword.putString(KEY_Password_SP, null);
                            PrefPassword.commit();
                        }
                    } else {
                        Log.e("result", respon.toString());
                        Toast.makeText(Activity_Login.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                Log.e("Exception",e.getMessage());
                Toast.makeText(Activity_Login.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    @Override
    public void onBackPressed()
    {


        pressbuttonback++;
        if (pressbuttonback > 1){
            finish();
        }else{
            Toast.makeText(Activity_Login.this, "Press Back Again To Exit", Toast.LENGTH_LONG).show();
        }

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}