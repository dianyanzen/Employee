package id.personalia.employe.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import id.personalia.employe.Activity.DetailTravelActivity;
import id.personalia.employe.Activity.SearchTravelActivity;
import id.personalia.employe.Adapter.TravelAdapter;
import id.personalia.employe.Model.Travel;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class TravelFragment extends Fragment {

    ArrayList<Travel> travels;
    Travel travel;
    RecyclerView recyclerView;
    TravelAdapter travelAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3;
    private Animation fab_open,fab_close,fab_close_fast,rotate_forward,rotate_backward;
    AppBarLayout appBarLayout;
    ImageView imageView;
    RecyclerView.LayoutManager mLayoutManager;
    SearchView sv;

    public TravelFragment() {
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
        return inflater.inflate(R.layout.fragment_travel, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
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

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        // Generate dummy data
        dummyData();

        travelAdapter = new TravelAdapter(getActivity(), R.layout.travel_list_layout, travels);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(travelAdapter);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sv = new SearchView(getActivity());

        ImageView v = (ImageView) sv.findViewById(android.support.v7.appcompat.R.id.search_button);
        v.setImageResource(R.drawable.ic_search);
        v.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        ImageView v2 = (ImageView) sv.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        v2.setImageResource(R.drawable.ic_clear);
        v2.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setQuery("", false);
                sv.onActionViewCollapsed();
            }
        });

        // modifying the text inside edittext component
        TextView textView = (TextView) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHint("Cari");
        textView.setHintTextColor(Color.GRAY);
        textView.setTextColor(Color.WHITE);

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // Todo: Perform search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        item.setActionView(sv);
    }

    public void dummyData(){
        travels = new ArrayList<>();

        travel = new Travel();
        travel.setTANGGAL("2017-05-06");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("EVLS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-05-20");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("VITS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-05-27");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("VITS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-06-01");
        travel.setTUJUAN("Jakarta");
        travel.setPROYEK("FAMS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-06-10");
        travel.setTUJUAN("Jakarta");
        travel.setPROYEK("FAMS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-06-21");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("JUNBIKI");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-07-01");
        travel.setTUJUAN("Jakarta");
        travel.setPROYEK("FAMS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-07-15");
        travel.setTUJUAN("Jakarta");
        travel.setPROYEK("FAMS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-07-17");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("JUNBIKI");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-07-19");
        travel.setTUJUAN("Jakarta");
        travel.setPROYEK("EVLS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-07-22");
        travel.setTUJUAN("Jakarta");
        travel.setPROYEK("EHS");
        travel.setSTATUS(2);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-07-23");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("PRC");
        travel.setSTATUS(1);
        travels.add(travel);

        travel = new Travel();
        travel.setTANGGAL("2017-08-03");
        travel.setTUJUAN("Karawang");
        travel.setPROYEK("EVLS");
        travel.setSTATUS(0);
        travels.add(travel);
    }
}
