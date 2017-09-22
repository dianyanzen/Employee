package id.personalia.employe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.personalia.employe.Activity.OcTravel;
import id.personalia.employe.Model.Travel;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder>{
    ArrayList<Travel> travels;
    Context context;

    public TravelAdapter(Context context, int resource, ArrayList<Travel> object) {
        this.context = context;
        this.travels = object;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_list_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Travel m = travels.get(position);

        switch (travels.get(position).getSTATUS()){
            case 0:
                holder.traveliv_status.setImageResource(R.drawable.ic_clock);
                holder.traveliv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                break;
            case 1:
                holder.traveliv_status.setImageResource(R.drawable.ic_clear);
                holder.traveliv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                break;
            case 2:
                holder.traveliv_status.setImageResource(R.drawable.ic_done);
                holder.traveliv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                break;
        }

        holder.traveliv_status.setPadding(10,10,10,10);

        holder.traveltv_lokasi.setText(m.getTUJUAN());
        holder.traveltv_tanggal.setText(m.getTANGGAL());
        holder.traveltv_project.setText(m.getPROYEK());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                if (isLongClick) {
                    //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(activity, OcTravel.class);
                    intent.putExtra("travel_tujuan", travels.get(position).getTUJUAN()); //you can name the keys whatever you like
                    intent.putExtra("travel_tanggal", travels.get(position).getTANGGAL()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("travel_proyek", travels.get(position).getPROYEK());
                    switch (travels.get(position).getSTATUS()){
                        case 0:
                            intent.putExtra("travel_status", "Menunggu");
                            break;
                        case 1:
                            intent.putExtra("travel_status", "Batal");
                            break;
                        case 2:
                            intent.putExtra("travel_status", "Beres");
                            break;
                    }
                    activity.startActivity(intent);
                    }
                }

            });
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView traveliv_status;
        TextView traveltv_tanggal, traveltv_lokasi, traveltv_project;

        public ViewHolder(View itemView) {
            super(itemView);

            traveliv_status = (ImageView) itemView.findViewById(R.id.traveliv_status);
            traveltv_tanggal = (TextView) itemView.findViewById(R.id.traveltv_tanggal);
            traveltv_lokasi = (TextView) itemView.findViewById(R.id.traveltv_lokasi);
            traveltv_project = (TextView) itemView.findViewById(R.id.traveltv_project);

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
    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);

    }
}
