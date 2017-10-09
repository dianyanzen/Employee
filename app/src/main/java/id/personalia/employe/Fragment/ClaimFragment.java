package id.personalia.employe.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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

import id.personalia.employe.Activity.DetailClaimActivity;
import id.personalia.employe.Activity.SearchClaimActivity;
import id.personalia.employe.Adapter.ClaimAdapter;
import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Model.Claim;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

import static java.lang.Integer.parseInt;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class ClaimFragment extends Fragment {

    private int PAGE_SIZE = 1;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private RecyclerView listClaim;
    private LinearLayoutManager linearLayoutManager;
    private ClaimAdapter claimAdapter;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3;
    private Animation fab_open,fab_close,fab_close_fast,rotate_forward,rotate_backward;
    AppBarLayout appBarLayout;
    ImageView imageView;
    final String UrlPort = "http://192.168.4.112/cpms/Androidreimburse";
    String is_admin;
    String employee_id;
    String company_id;
    String param1,param2,val1,val2;
    protected Context context;

    public static ClaimFragment newInstance(){
        return new ClaimFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_claim, container, false);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(true);

        imageView = (ImageView) getActivity().findViewById(R.id.image);
        imageView.setImageResource(R.drawable.bgclaim);

        collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.claim));

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
                Intent intent = new Intent(getActivity(), DetailClaimActivity.class);
                startActivity(intent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchClaimActivity.class);
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


        listClaim = (RecyclerView) rootView.findViewById(R.id.list);
        listClaim.setHasFixedSize(true);
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
                claimAdapter.clear();
                new AsyncLogin().execute(is_admin, employee_id, company_id,UrlPort,val1 = "",val2 = "");
                onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(false);
                Toast.makeText(getActivity(), "List Klaim Telah Di Perbarui", Toast.LENGTH_LONG).show();
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
        claimAdapter = new ClaimAdapter(getActivity());

        listClaim.setLayoutManager(linearLayoutManager);
        listClaim.setAdapter(claimAdapter);
        listClaim.addOnScrollListener(recyclerViewOnScrollListener);
        DbUserData db = new DbUserData(getActivity());
        List<UserData> UserDataList = db.getAllUserDataList();
        for (UserData userData : UserDataList) {
            if (!userData.getEMPLOYEE_ID().isEmpty()) {
                is_admin = userData.getIS_ADMIN();
                employee_id = userData.getEMPLOYEE_ID();
                company_id = userData.getCOMPANY_ID();
                if (param1 != null){
                    Log.e("Kadieu",param1);
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

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("is_admin", params[0])
                        .appendQueryParameter("employee_id", params[1])
                        .appendQueryParameter("company_id", params[2])
                        .appendQueryParameter("reimburse_dt_from", params[4])
                        .appendQueryParameter("reimburse_dt_to", params[5]);
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
                    JSONArray MainJson = jsonObj.getJSONArray("data");
                    List<Claim> Claims = new ArrayList<Claim>();
                    Claim claim;
                    isLoading = false;
                    if (numData == 0) {
                        claimAdapter.setLoading(false);
                    }else {
                        int index = claimAdapter.getItemCount() - 1;
                        int end = index + PAGE_SIZE;
                        if (end <= numData) {
                            for (int i = index; i < end; i++) {
                                claim = new Claim();

                                JSONObject dataclaim = MainJson.getJSONObject(i);
                                Log.e("Array1",dataclaim.toString());
                                int reimburse_id =parseInt(dataclaim.getString("reimburse_id"));
                                String date = dataclaim.getString("date");
                                String amount = dataclaim.getString("amount");
                                String reimburse_type = dataclaim.getString("reimburse_type");
                                String reimburse_description = dataclaim.getString("reimburse_description");
                                String employee_name = dataclaim.getString("employee_name");
                                String status = dataclaim.getString("status");

                                claim = new Claim();

                                claim.setId(reimburse_id);
                                claim.setCLTTANGGAL(date);
                                claim.setCLJUMLAH(amount);
                                claim.setCLDESKRIKSI(reimburse_type);
                                claim.setCLDESCRIPTION(reimburse_description);
                                claim.setCLNMPENGAJU(employee_name);
                                claim.setCLSTATUS(status);
                                Claims.add(claim);
                                claimAdapter.setLoading(false);
                            }
                            claimAdapter.addAll(Claims);
                            if (end >= numData) {
                                claimAdapter.setLoading(false);
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
