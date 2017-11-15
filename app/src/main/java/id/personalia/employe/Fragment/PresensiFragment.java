package id.personalia.employe.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import id.personalia.employe.Activity.DetailReportActivity;
import id.personalia.employe.Activity.EmployeeActivity;
import id.personalia.employe.Activity.ProjectActivity;
import id.personalia.employe.Activity.SearchAttendanceActivity;
import id.personalia.employe.Activity.SearchReportActivity;
import id.personalia.employe.Adapter.InputFormAdapter;
import id.personalia.employe.Adapter.PresensiAdapter;
import id.personalia.employe.Adapter.ReportAdapter;
import id.personalia.employe.Helper.ArkaHelper;
import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Model.InputForm;
import id.personalia.employe.Model.Report;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

import static android.app.Activity.RESULT_OK;
import static id.personalia.employe.R.id.imageView;
import static java.lang.Integer.parseInt;

/**
 * Created by Dian Yanzen on 9/22/2017.
 */

public class PresensiFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private int PAGE_SIZE = 1;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private RecyclerView listReport;
    private LinearLayoutManager linearLayoutManager;
    private PresensiAdapter reportAdapter;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3;
    private Animation fab_open,fab_close,fab_close_fast,rotate_forward,rotate_backward;
    AppBarLayout appBarLayout;
    ImageView imageView;
    SharedPreferences sharedpreferences;
    public String ENDPOINT="https://personalia.id/";
    public String PMSENDPOINT="https://personalia.id/";
    String UrlPort;
    String is_admin;
    String employee_id;
    String company_id;
    Calendar calendar;
    DatePickerDialog datePicker;
    SimpleDateFormat simpleDateFormat;
    String time;
    int year, month, day, _index;
    ArkaHelper helper;
    ListView listView;
    ArrayAdapter adapter;
    InputForm inputForm;
    ArrayList<InputForm> inputForms;
    android.app.AlertDialog.Builder builder;
    EditText popupEditText;
    SearchView sv;
    String param1,param2,val1,val2;
    String TodayVal;
    AppCompatButton clockin, clockout;
    int EMPLOYEE_ID, PROJECT_ID, SUPERVISOR_ID = 0;

    private static int EMPLOYEE_REQUEST = 1000;
    private static int PROJECT_REQUEST = 2000;
    private static int SUPERVISOR_REQUEST = 3000;


    protected Context context;

    public static PresensiFragment newInstance(){
        return new PresensiFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_presensi, container, false);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(false);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        ENDPOINT = sharedpreferences.getString("URLEndPoint", "");
        UrlPort = ENDPOINT+"Androidattendance";
        helper = new ArkaHelper(getActivity());
        currentTime();
        currentDate();
        generateData();
        TodayVal = helper.zeroPadLeft(day) + "-" + helper.zeroPadLeft(month + 1) + "-" + helper.zeroPadLeft(year);
        imageView = (ImageView) getActivity().findViewById(R.id.image);
        imageView.setImageResource(R.drawable.bgattend);

        collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.report));

        //toolbar = getActivity().findViewById(R.id.toolbar);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fabaction);
        fab.setImageResource(R.drawable.ic_add);
        fab.setVisibility(View.INVISIBLE);
        fab.setBackgroundColor(getResources().getColor(R.color.colorFAB1));

        fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fabadd);
        fab2.setImageResource(R.drawable.ic_add);
        fab2.setVisibility(View.INVISIBLE);
        fab2.setBackgroundColor(getResources().getColor(R.color.colorFAB1));

        fab3 = (FloatingActionButton) getActivity().findViewById(R.id.fabsearch);
        fab3.setImageResource(R.drawable.ic_add);
        fab3.setVisibility(View.INVISIBLE);
        fab3.setBackgroundColor(getResources().getColor(R.color.colorFAB1));

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fab_close_fast = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close_fast);
        rotate_forward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        fab2.startAnimation(fab_close_fast);
        fab3.startAnimation(fab_close_fast);
        //fab.startAnimation(rotate_backward);

        fab.hide();
        fab2.hide();
        fab3.hide();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    fab.hide();
                    fab2.hide();
                    fab3.hide();
                }
                else
                {
                    fab.hide();
                    fab2.hide();
                    fab3.hide();
                }
            }
        });
        clockin = (AppCompatButton) rootView.findViewById(R.id.clock_in);
        clockout = (AppCompatButton) rootView.findViewById(R.id.clock_out);
        clockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(getActivity(), DetailReportActivity.class);
                mainIntent.putExtra("FragmentNM", "ClockIn");
                startActivity(mainIntent);
                Log.e("Goto", "ClockOut");
            }
        });
        clockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), DetailReportActivity.class);
                mainIntent.putExtra("FragmentNM", "ClockOut");
                startActivity(mainIntent);
                Log.e("Goto", "ClockOut");
            }
        });
        listReport = (RecyclerView) rootView.findViewById(R.id.list);
        listReport.setHasFixedSize(true);

        listView = (ListView) rootView.findViewById(R.id.lstbutton);
        adapter = new InputFormAdapter(getActivity(), R.layout.input_form_layout, inputForms);

        datePicker = new DatePickerDialog(getActivity(), R.style.ArkaCalendar, this, year, month, day);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                _index = i;

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setLayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                );
                linearLayout.setPadding(70,30,70,30);

                if(inputForms.get(i).getTYPE().equals("DATEPICKER")){
                    datePicker.show();
                }else if(inputForms.get(i).getTYPE().equals("EDITTEXT")){

                    builder = new android.app.AlertDialog.Builder(getActivity());

                    TextView tvTitle = new TextView(getActivity());
                    tvTitle.setText(inputForms.get(i).getLABEL());
                    tvTitle.setTextSize(18);
                    tvTitle.setTypeface(null, Typeface.BOLD);
                    tvTitle.setPadding(70, 40, 70, 40);

                    builder.setCustomTitle(tvTitle);

                    popupBuilderString(linearLayout, i);

                    builder.show();
                }else if(inputForms.get(i).getTYPE().equals("EMPLOYEE")){
                    Intent intent = new Intent(getActivity(), EmployeeActivity.class);
                    startActivityForResult(intent, 1000);
                }else if(inputForms.get(i).getTYPE().equals("PROJECT")){
                    Intent intent = new Intent(getActivity(), ProjectActivity.class);
                    startActivityForResult(intent, 1000);
                }
            }
        });
        final SwipeRefreshLayout dorefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);
        dorefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        dorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshItem();
            }

            void refreshItem() {
                val1 = "";
                val2 = "";
                reportAdapter.clear();
                TodayVal = helper.zeroPadLeft(day) + "-" + helper.zeroPadLeft(month + 1) + "-" + helper.zeroPadLeft(year);
                new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,TodayVal,TodayVal);
                onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(false);
                Toast.makeText(getActivity(), "List Absensi Telah Di Perbarui", Toast.LENGTH_LONG).show();
            }

        });
        return rootView;
    }
    public void currentDate(){
        calendar = Calendar.getInstance(TimeZone.getDefault());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        setDate(_index,   helper.zeroPadLeft(i2)+ "-" + helper.zeroPadLeft(i1+1) + "-" +helper.zeroPadLeft(i));
        TodayVal = helper.zeroPadLeft(i2)+ "-" + helper.zeroPadLeft(i1+1) + "-" +helper.zeroPadLeft(i);
        //Toast.makeText(getActivity(), is_admin+"\n"+employee_id+"\n"+company_id+"\n"+UrlPort+"\n"+TodayVal+"\n"+TodayVal, Toast.LENGTH_LONG).show();
        reportAdapter.clear();
        new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,TodayVal,TodayVal);
    }
    public void setDate(int position, String date){
        inputForms.get(position).setVALUE(date);
        adapter.notifyDataSetChanged();
    }
    public void currentTime(){
        calendar = Calendar.getInstance(TimeZone.getDefault());
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        time = simpleDateFormat.format(calendar.getTime());
    }
    public void popupBuilderString(View v, int position) {

        popupEditText = new EditText(getActivity());
        popupEditText.setMaxLines(1);
        popupEditText.setBackgroundColor(Color.TRANSPARENT);
        popupEditText.setPadding(30, 30, 30, 30);
        popupEditText.setBackgroundResource(R.drawable.popup_edittext);
        popupEditText.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        );
        popupEditText.requestFocus();
        popupEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(popupEditText, 0);
            }
        }, 200);
        ((LinearLayout) v).addView(popupEditText);
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                //Toast.makeText(getActivity(), is_admin+"\n"+employee_id+"\n"+company_id+"\n"+UrlPort+"\n"+TodayVal+"\n"+TodayVal, Toast.LENGTH_LONG).show();

                inputForms.get(_index).setVALUE(popupEditText.getText().toString());
                adapter.notifyDataSetChanged();



            }
        });
        builder.setView(v);
        builder.create().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }
    public void generateData() {
        inputForms = new ArrayList<InputForm>();

        inputForm = new InputForm();
        inputForm.setICON(getResources().getDrawable(R.drawable.ic_date_range));
        inputForm.setTYPE("DATEPICKER");
        inputForm.setLABEL("Tanggal");
        inputForm.setVALUE(helper.zeroPadLeft(day) + "-" + helper.zeroPadLeft(month + 1) + "-" + helper.zeroPadLeft(year));
        inputForms.add(inputForm);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(context);
        reportAdapter = new PresensiAdapter(getActivity());

        listReport.setLayoutManager(linearLayoutManager);
        listReport.setAdapter(reportAdapter);
        listReport.addOnScrollListener(recyclerViewOnScrollListener);
        DbUserData db = new DbUserData(getActivity());
        List<UserData> UserDataList = db.getAllUserDataList();

        for (UserData userData : UserDataList) {
            if (!userData.getEMPLOYEE_ID().isEmpty()) {
                is_admin = userData.getIS_ADMIN();
                employee_id = userData.getEMPLOYEE_ID();
                company_id = userData.getCOMPANY_ID();
                new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,TodayVal,TodayVal);
            }

        }

    }

    private class AsyncLogin extends AsyncTask<String, Void, String> {
        //ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

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
                        .appendQueryParameter("is_admin", params[0])
                        .appendQueryParameter("employee_id", params[1])
                        .appendQueryParameter("company_id", params[2])
                        .appendQueryParameter("dt_from", params[4])
                        .appendQueryParameter("dt_to", params[5]);
                Log.e("Param0",params[0]);
                Log.e("Param1",params[1]);
                Log.e("Param2",params[2]);
                Log.e("Param3",params[3]);
                Log.e("Param4",params[4]);
                Log.e("Param5",params[5]);
                String query = builder.build().getEncodedQuery();

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
            //pdLoading.dismiss();
            Log.e("RES", result);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(result);
                if(jsonObj.has("data"))
                {
                    int numData = jsonObj.getInt("recordsTotal");
                    Log.e("[ASSET] count", "there are "+numData+" rows");
                    Log.e("Log",jsonObj.toString());
                    Log.e("data", "ada datanya");
                    JSONArray NotivJson = jsonObj.getJSONArray("data");
                    List<Report> Reports = new ArrayList<Report>();
                    Report report;
                    isLoading = false;
                    if (numData == 0) {
                        reportAdapter.setLoading(false);
                    }else {
                        int index = reportAdapter.getItemCount() - 1;
                        int end = index + PAGE_SIZE;
                        if (end <= numData) {
                            for (int i = index; i < end; i++) {
                                report = new Report();

                                JSONObject datareport = NotivJson.getJSONObject(i);
                                Log.e("Array1",datareport.toString());
                                String status = datareport.getString("status");
                                int attendance_id = parseInt(datareport.getString("attendance_id"));
                                String attendance_dt = datareport.getString("attendance_dt");
                                String employee_name = datareport.getString("employee_name");
                                String clock_in = datareport.getString("clock_in");
                                String clock_out = datareport.getString("clock_out");
                                String work_hour = datareport.getString("work_hour");

                                report = new Report();

                                report.setID(attendance_id);
                                report.setRPNAMA(employee_name);
                                report.setRPTANGGAL(attendance_dt);
                                report.setRPCL_IN(clock_in);
                                report.setRPCL_OUT(clock_out);
                                report.setRPWORKHOUR(work_hour);
                                report.setRPSTATUS(status);
                                Reports.add(report);
                                reportAdapter.setLoading(false);
                            }
                            reportAdapter.addAll(Reports);
                            if (end >= numData) {
                                reportAdapter.setLoading(false);
                            }
                        }
                    }

                } else {
                    //Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Log.e("Exception",e.getMessage());
                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true;
                    //Log.e("Jalan Sini",getString(totalItemCount));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,TodayVal,TodayVal);
                        }
                    }, 1);
                }
            }
        }
    };
}
