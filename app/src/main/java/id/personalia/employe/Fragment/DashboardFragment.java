package id.personalia.employe.Fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

import id.personalia.employe.Model.Dashboard;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class DashboardFragment extends Fragment {

    ArrayList<Dashboard> Dashboards;
    id.personalia.employe.Model.Dashboard Dashboard;
    RecyclerView recyclerView;
    id.personalia.employe.Adapter.DashboardAdapter DashboardAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3;
    private Animation fab_open,fab_close,fab_close_fast,rotate_forward,rotate_backward;
    AppBarLayout appBarLayout;
    ImageView imageView;
    RecyclerView.LayoutManager mLayoutManager;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.list);


        dummyData();
        /*
        DashboardAdapter = new id.personalia.employe.Adapter.DashboardAdapter(getActivity(), R.layout.dashboard_list_layout, Dashboards);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(DashboardAdapter);
        recyclerView.setHasFixedSize(true);
        */
        // Calling the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        DashboardAdapter = new id.personalia.employe.Adapter.DashboardAdapter(getActivity(), R.layout.dashboard_list_layout, Dashboards);
        recyclerView.setAdapter(DashboardAdapter);
    }


    public void dummyData(){
        Dashboards = new ArrayList<>();

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Clock In");
        Dashboard.setINFO("08:00");
        Dashboard.setSTATUS("Clockin: 08:00");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Dinas Luar");
        Dashboard.setINFO("10");
        Dashboard.setSTATUS("Disetujui: 10 Dinas Luar");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Klaim");
        Dashboard.setINFO("35");
        Dashboard.setSTATUS("Disetujui: 30 Klaim");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Izin");
        Dashboard.setINFO("1");
        Dashboard.setSTATUS("Disetujui: 1 Hari");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Lembur");
        Dashboard.setINFO("13");
        Dashboard.setSTATUS("Disetujui: 10 Jam");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Kehadiran");
        Dashboard.setINFO("10");
        Dashboard.setSTATUS("Total: 22 Hari");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Tugas");
        Dashboard.setINFO("1");
        Dashboard.setSTATUS("Total: 10 Tugas");
        Dashboards.add(Dashboard);

        Dashboard = new Dashboard();
        Dashboard.setMAIN("Kalender");
        Dashboard.setINFO("20");
        Dashboard.setSTATUS("20 Hari Libur");
        Dashboards.add(Dashboard);
    }
}