package id.personalia.employe.Fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Model.Dashboard;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

import static java.lang.Integer.parseInt;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class DashboardFragment extends Fragment {
    SharedPreferences sharedpreferences;
    public String ENDPOINT="https://personalia.id/";
    public String PMSENDPOINT="https://personalia.id/";
    String UrlPort;
    ArrayList<Dashboard> Dashboards;
    id.personalia.employe.Model.Dashboard Dashboard;
    private RecyclerView recyclerView;
    id.personalia.employe.Adapter.DashboardAdapter DashboardAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab2, fab3;
    private Animation fab_open, fab_close, fab_close_fast, rotate_forward, rotate_backward;
    AppBarLayout appBarLayout;
    ImageView imageView;
    RecyclerView.LayoutManager mLayoutManager;

    String is_admin;
    String employee_id;
    String company_id;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(true);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        ENDPOINT = sharedpreferences.getString("URLEndPoint", "");
        UrlPort = ENDPOINT+"Androiddashboard";
        imageView = (ImageView) getActivity().findViewById(R.id.image);
        imageView.setImageResource(R.drawable.bgdashboard);

        collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.dashboard));

        toolbar = getActivity().findViewById(R.id.toolbar);
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
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fab_close_fast = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close_fast);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        fab2.startAnimation(fab_close_fast);
        fab3.startAnimation(fab_close_fast);
        //fab.startAnimation(rotate_backward);

        fab.hide();
        fab2.hide();
        fab3.hide();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    fab.hide();
                    fab2.hide();
                    fab3.hide();
                } else {
                    fab.hide();
                    fab2.hide();
                    fab3.hide();
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.list);


        dummyData();

    /*
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        */
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        DashboardAdapter = new id.personalia.employe.Adapter.DashboardAdapter(getActivity(), R.layout.dashboard_list_layout, Dashboards);
        recyclerView.setAdapter(DashboardAdapter);
        DbUserData db = new DbUserData(getActivity());
        List<UserData> UserDataList = db.getAllUserDataList();

        for (UserData userData : UserDataList) {
            if (!userData.getEMPLOYEE_ID().isEmpty()) {
                is_admin = userData.getIS_ADMIN();
                employee_id = userData.getEMPLOYEE_ID();
                company_id = userData.getCOMPANY_ID();
                new AsyncLogin().execute(is_admin, employee_id, company_id, UrlPort);
            }

        }
    }


    public void dummyData() {
        Dashboards = new ArrayList<Dashboard>();
        Dashboard = new Dashboard();

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
                        //.appendQueryParameter("user_email", "9909896")
                        //.appendQueryParameter("user_password", "12345678")
                        .appendQueryParameter("is_admin", params[0])
                        .appendQueryParameter("employee_id", params[1])
                        .appendQueryParameter("company_id", params[2]);
                Log.e("Param0", params[0]);
                Log.e("Param1", params[1]);
                Log.e("Param2", params[2]);
                Log.e("Param3", params[3]);
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
            try {
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            /*
                I will loop the json object and insert it into an array list and then the Adapter will read the array list to be display on custom listView
             */
            super.onPostExecute(result);
            Log.e("RES", result);
            JSONObject jsonObj = null;
            try {
                Dashboards.clear();
                DashboardAdapter.notifyDataSetChanged();

                jsonObj = new JSONObject(result);
                if (jsonObj.has("data")) {
                    int numData = jsonObj.getInt("recordsTotal");
                    Log.e("[ASSET] count", "there are " + numData + " rows");
                    Log.e("Log", jsonObj.toString());
                    Log.e("data", "ada datanya");
                    JSONArray NotivJson = jsonObj.getJSONArray("data");
                    if (numData > 0) {

                        for (int i = 0; i < numData; i++) {
                            JSONObject datadb = NotivJson.getJSONObject(i);
                            //int company_id = parseInt(datadb.getString("company_id"));
                            //int employee_id = parseInt(datadb.getString("employee_id"));
                            //int main_id = parseInt(datadb.getString("main_id"));
                            String main_dashboard = datadb.getString("main_dashboard");
                            String status_dashboard = datadb.getString("status_dashboard");
                            String info_dashboard = datadb.getString("info_dashboard");
                            Dashboard = new Dashboard();
                            Dashboard.setMAIN(main_dashboard);
                            Log.e("Main",main_dashboard);
                            Dashboard.setINFO(status_dashboard);
                            Dashboard.setSTATUS(info_dashboard);
                            Dashboards.add(Dashboard);
                        }
                    }
                    // The number of Columns
                    recyclerView = (RecyclerView) getActivity().findViewById(R.id.list);
                    recyclerView.setHasFixedSize(true);
                    mLayoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);

                    DashboardAdapter = new id.personalia.employe.Adapter.DashboardAdapter(getActivity(), R.layout.dashboard_list_layout, Dashboards);
                    recyclerView.setAdapter(DashboardAdapter);
                } else {
                    Log.e("DEBUG", "data ga ada");
                }

            } catch (JSONException e) {
                Log.e("Exception", e.getMessage());
            }
        }
    }
}