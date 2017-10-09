package id.personalia.employe.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.personalia.employe.Activity.OcTravel;
import id.personalia.employe.Helper.OnLoadMoreListener;
import id.personalia.employe.Model.Travel;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class TravelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_EMPTY = 2;

    private boolean loading = true;

    private List<Travel> Travels;
    private ItemClickListener ItemClickListener;
    private Context context;
    public TravelAdapter(Context current) {
        this.context = current;
        Travels = new ArrayList<Travel>();
    }

    private void add(Travel item) {
        Travels.add(item);
        notifyItemInserted(Travels.size());
    }

    public void addAll(List<Travel> Trevels) {
        for (Travel travel : Trevels) {
            add(travel);
        }
    }

    public void remove(Travel item) {
        int position = Travels.indexOf(item);
        if (position > -1) {
            Travels.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        int size = this.Travels.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.Travels.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public Travel getItem(int position){
        return Travels.get(position);
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (0)) {
            return VIEW_TYPE_EMPTY;
        }
        else if (isPositionFooter(position)) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == Travels.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_list_layout, parent, false);
            return new travelViewHolder(view, ItemClickListener);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            return new emptyViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof travelViewHolder) {
            travelViewHolder travelViewHolder = (travelViewHolder) holder;

            final Travel travel = Travels.get(position);
            travelViewHolder.treveliv_img.setImageResource(R.drawable.def_travel);

            switch (travel.getSTATUS()){
                case "Menunggu":
                    travelViewHolder.traveliv_status.setImageResource(R.drawable.ic_clock);
                    travelViewHolder.traveliv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                    break;
                case "Disetujui":
                    travelViewHolder.traveliv_status.setImageResource(R.drawable.ic_done);
                    travelViewHolder.traveliv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                    break;
                case "Ditolak":
                    travelViewHolder.traveliv_status.setImageResource(R.drawable.ic_clear);
                    travelViewHolder.traveliv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                    break;
            }

            travelViewHolder.traveliv_status.setPadding(10,10,10,10);
            travelViewHolder.traveltv_tanggal.setText(travel.getTANGGAL());
            travelViewHolder.traveltv_lokasi.setText(travel.getTUJUAN());
            travelViewHolder.traveltv_project.setText(travel.getPROYEK());
            travelViewHolder.traveltv_long.setText(travel.getLONG());
            travelViewHolder.traveltv_aproval.setText(travel.getAPROVAL_BY());
            travelViewHolder.traveltv_participant.setText(travel.getPARTICIPANT());
            travelViewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    if (isLongClick) {
                        //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent intent = new Intent(activity, OcTravel.class);
                        intent.putExtra("travel_tujuan", travel.getTUJUAN()); //you can name the keys whatever you like
                        intent.putExtra("travel_tanggal", travel.getTANGGAL()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                        intent.putExtra("travel_proyek", travel.getPROYEK());
                        intent.putExtra("travel_long", travel.getLONG());
                        intent.putExtra("travel_aproval", travel.getAPROVAL_BY());
                        intent.putExtra("travel_participant", travel.getPARTICIPANT());
                        switch (travel.getSTATUS()){
                            //case R.drawable.ic_clock:
                            case "Menunggu":
                                intent.putExtra("travel_status", "Menunggu");
                                break;
                            case "Disetujui":
                                intent.putExtra("travel_status", "Disetujui");
                                break;
                            case "Ditolak":
                                intent.putExtra("travel_status", "Ditolak");
                                break;
                        }
                        activity.startActivity(intent);
                    }
                }

            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        } else if (holder instanceof emptyViewHolder) {
            emptyViewHolder EmptyViewHolder = (emptyViewHolder) holder;
            EmptyViewHolder.empty_text.setText("Tidak Ada Data Perjalanan Dinas\nYang Dapat Ditampilkan");
    }

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return Travels == null ? 0 : Travels.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
    static class emptyViewHolder extends RecyclerView.ViewHolder {
        TextView empty_text;
        public emptyViewHolder(View itemView) {
            super(itemView);
            empty_text = (TextView) itemView.findViewById(R.id.empty_text);
        }
    }

    static class travelViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView traveliv_status, treveliv_img;
        TextView traveltv_tanggal, traveltv_lokasi, traveltv_project,traveltv_long,traveltv_aproval,traveltv_participant;


        public travelViewHolder(View itemView, ItemClickListener ItemClickListener) {
            super(itemView);

            traveliv_status = (ImageView) itemView.findViewById(R.id.traveliv_status);
            treveliv_img = (ImageView) itemView.findViewById(R.id.travel_list_image);
            traveltv_tanggal = (TextView) itemView.findViewById(R.id.traveltv_tanggal);
            traveltv_lokasi = (TextView) itemView.findViewById(R.id.traveltv_lokasi);
            traveltv_project = (TextView) itemView.findViewById(R.id.traveltv_project);
            traveltv_long = (TextView) itemView.findViewById(R.id.traveltv_longtravel);
            traveltv_aproval = (TextView) itemView.findViewById(R.id.traveltv_aproval);
            traveltv_participant = (TextView) itemView.findViewById(R.id.traveltv_participant);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;

        }

    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }


    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);

    }
}
