package id.personalia.employe.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import id.personalia.employe.Activity.Activity_Login;
import id.personalia.employe.Activity.DetailTravelActivity;
import id.personalia.employe.Activity.SearchTravelActivity;
import id.personalia.employe.Adapter.TravelAdapter;
import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Model.Travel;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static java.lang.Integer.parseInt;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class TravelFragment extends Fragment {

    private int PAGE_SIZE = 1;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private RecyclerView listTravel;
    private LinearLayoutManager linearLayoutManager;
    private TravelAdapter travelAdapter;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3;
    private Animation fab_open,fab_close,fab_close_fast,rotate_forward,rotate_backward;
    AppBarLayout appBarLayout;
    ImageView imageView;
    final String UrlPort = "http://192.168.4.112/cpms/Androidofficial_travel";
    String is_admin;
    String employee_id;
    String company_id;
    String param1,param2,val1,val2;
    protected Context context;

    public static TravelFragment newInstance(){
        return new TravelFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            param1 = bundle.getString("param1");
            param2 = bundle.getString("param2");
            val1 = bundle.getString("val1");
            val2 = bundle.getString("val2");

        }
        final View rootView = inflater.inflate(R.layout.fragment_travel, container, false);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(true);

        imageView = (ImageView) getActivity().findViewById(R.id.image);
        imageView.setImageResource(R.drawable.bgtravel);

        collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.travel));

        toolbar = getActivity().findViewById(R.id.toolbar);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fabaction);
        fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fabadd);
        fab3 = (FloatingActionButton) getActivity().findViewById(R.id.fabsearch);
        fab.setImageResource(R.drawable.ic_add);
        fab.setVisibility(View.VISIBLE);
        fab.setBackgroundColor(getResources().getColor(R.color.colorFAB1));
        fab2.setImageResource(R.drawable.ic_insert_drive_file);
        fab2.setBackgroundColor(getResources().getColor(R.color.colorFAB1));
        fab3.setImageResource(R.drawable.ic_search);
        fab3.setBackgroundColor(getResources().getColor(R.color.colorFAB1));
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fab_close_fast = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close_fast);
        rotate_forward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        fab2.startAnimation(fab_close_fast);
        fab3.startAnimation(fab_close_fast);
        fab.startAnimation(rotate_backward);
        isFabOpen = false;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailTravelActivity.class);
                startActivity(intent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchTravelActivity.class);
                startActivity(intent);
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    fab2.hide();
                    fab3.hide();
                    if(isFabOpen){
                        fab2.startAnimation(fab_close_fast);
                        fab3.startAnimation(fab_close_fast);
                        fab.startAnimation(rotate_backward);
                    }
                    isFabOpen = false;
                    fab.hide();
                }
                else
                {
                    fab.show();
                }
            }
        });



        listTravel = (RecyclerView) rootView.findViewById(R.id.list);
        listTravel.setHasFixedSize(true);
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
                    travelAdapter.clear();
                    new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,val1 = "",val2 = "");
                    onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(false);
                Toast.makeText(getActivity(), "List Perjalanan Dinas Telah Di Perbarui", Toast.LENGTH_LONG).show();
                //Snackbar.make(rootView, "List Perjalanan Dinas Telah Di Perbarui", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

            }

        });
        return rootView;

    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(context);
        travelAdapter = new TravelAdapter(getActivity());

        listTravel.setLayoutManager(linearLayoutManager);
        listTravel.setAdapter(travelAdapter);
        listTravel.addOnScrollListener(recyclerViewOnScrollListener);
        DbUserData db = new DbUserData(getActivity());
        List<UserData> UserDataList = db.getAllUserDataList();

        for (UserData userData : UserDataList) {
            if (!userData.getEMPLOYEE_ID().isEmpty()) {
                is_admin = userData.getIS_ADMIN();
                employee_id = userData.getEMPLOYEE_ID();
                company_id = userData.getCOMPANY_ID();
                if (param1 != null){
                    new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,val1,val2);
                }else{
                    Log.e("Kadieu","Anjay");
                    new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,val1 = "",val2 = "");
                }

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
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("is_admin", params[0])
                            .appendQueryParameter("employee_id", params[1])
                            .appendQueryParameter("company_id", params[2])
                            .appendQueryParameter("s_dt_from", params[4])
                            .appendQueryParameter("s_dt_to", params[5]);
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
                    List<Travel> Travels = new ArrayList<Travel>();
                    Travel travel;
                    isLoading = false;
                    if (numData == 0) {
                        travelAdapter.setLoading(false);
                    }else {
                        int index = travelAdapter.getItemCount() - 1;
                        int end = index + PAGE_SIZE;
                        if (end <= numData) {
                            for (int i = index; i < end; i++) {
                                travel = new Travel();

                                JSONObject datatravel = NotivJson.getJSONObject(i);
                                Log.e("Array1",datatravel.toString());
                                String status = datatravel.getString("status");
                                int travel_id = parseInt(datatravel.getString("travel_id"));
                                String date = datatravel.getString("date");
                                String destination = datatravel.getString("destination");
                                String project_name = datatravel.getString("project_name");
                                String _long = datatravel.getString("long");
                                String aproval_by = datatravel.getString("created_name");
                                String participant = datatravel.getString("jml_org");

                                travel = new Travel();

                                travel.setId(travel_id);
                                travel.setPROYEK(project_name);
                                travel.setTANGGAL(date);
                                travel.setTUJUAN(destination);
                                travel.setSTATUS(status);
                                travel.setLONG(_long);
                                travel.setAPROVAL_BY(aproval_by);
                                travel.setPARTICIPANT(participant);
                                Travels.add(travel);
                                travelAdapter.setLoading(false);
                            }
                            travelAdapter.addAll(Travels);
                            if (end >= numData) {
                                travelAdapter.setLoading(false);
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
                            if (param1 != null){
                                new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,val1,val2);
                            }else{
                                Log.e("Kadieu","Anjay");
                                new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,val1 = "",val2 = "");
                            }
                        }
                    }, 1);
                }
            }
        }
    };
}
