package id.personalia.employe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.personalia.employe.Activity.OcClaim;
import id.personalia.employe.Model.Claim;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class ClaimAdapter extends RecyclerView.Adapter<ClaimAdapter.ViewHolder>{
    ArrayList<Claim> Claims;
    Context context;

    public ClaimAdapter(Context context, int resource, ArrayList<Claim> object) {
        this.context = context;
        this.Claims = object;
    }

    @Override
    public ClaimAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.claim_list_layout, parent, false);

        return new ClaimAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClaimAdapter.ViewHolder holder, int position) {
        final Claim m = Claims.get(position);

        switch (Claims.get(position).getCLSTATUS()){
            case "Menunggu":
                holder.cliv_status.setImageResource(R.drawable.ic_clock);
                holder.cliv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                break;
            case "":
                holder.cliv_status.setImageResource(R.drawable.ic_done);
                holder.cliv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                break;
        }

        holder.cliv_status.setPadding(10,10,10,10);

        holder.cltv_tanggal.setText(m.getCLTTANGGAL());
        holder.cltv_diajukan.setText(m.getCLNMPENGAJU());
        holder.cltv_deskripsi.setText(m.getCLDESKRIKSI());
        holder.cltv_jumlah.setText(m.getCLJUMLAH());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                if (isLongClick) {
                    //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(activity, OcClaim.class);
                    intent.putExtra("CLNMPENGAJU", Claims.get(position).getCLNMPENGAJU()); //you can name the keys whatever you like
                    intent.putExtra("CLTTANGGAL", Claims.get(position).getCLTTANGGAL()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("CLDESKRIKSI", Claims.get(position).getCLDESKRIKSI());
                    intent.putExtra("CLJUMLAH", Claims.get(position).getCLJUMLAH());
                    switch (Claims.get(position).getCLSTATUS()){
                        case "Menunggu":
                            intent.putExtra("CLSTATUS", "Menunggu");
                            break;
                        case "Batal":
                            intent.putExtra("CLSTATUS", "Batal");
                            break;
                        case "Beres":
                            intent.putExtra("CLSTATUS", "Beres");
                            break;
                    }
                    activity.startActivity(intent);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return Claims.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView cliv_status;
        TextView cltv_tanggal, cltv_diajukan, cltv_deskripsi,cltv_jumlah;

        public ViewHolder(View itemView) {
            super(itemView);

            cliv_status = (ImageView)itemView.findViewById(R.id.cliv_status);
            cltv_tanggal = (TextView)itemView.findViewById(R.id.cltv_tanggal);
            cltv_diajukan = (TextView)itemView.findViewById(R.id.cltv_diajukan);
            cltv_deskripsi = (TextView)itemView.findViewById(R.id.cltv_deskripsi);
            cltv_jumlah = (TextView)itemView.findViewById(R.id.cltv_jumlah);

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