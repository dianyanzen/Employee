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

import java.util.ArrayList;

import id.personalia.employe.Activity.OcOvertime;
import id.personalia.employe.Model.Overtime;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class OvertimeAdapter extends RecyclerView.Adapter<OvertimeAdapter.ViewHolder>{
    ArrayList<Overtime> Overtimes;
    Context context;

    public OvertimeAdapter(Context context, int resource, ArrayList<Overtime> object) {
        this.context = context;
        this.Overtimes = object;
    }

    @Override
    public OvertimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overtime_list_layout, parent, false);

        return new OvertimeAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OvertimeAdapter.ViewHolder holder, int position) {
        final Overtime m = Overtimes.get(position);

        switch (Overtimes.get(position).getOTSTATUS()){
            case "Menunggu":
                holder.ottiv_status.setImageResource(R.drawable.ic_clock);
                holder.ottiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                break;
            case "":
                holder.ottiv_status.setImageResource(R.drawable.ic_done);
                holder.ottiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                break;
        }

        holder.ottiv_status.setPadding(10,10,10,10);

        holder.ottv_tanggal.setText(m.getOTTANGGAL());
        holder.ottv_diajukan.setText(m.getOTDIAJUKAN());
        holder.ottv_ket.setText(m.getOTKETERANGAN());
        holder.ottv_jamlembur.setText(m.getOTJAMLEMBUR());
        holder.ottv_kalkulasi.setText(m.getOTKALKULASI());
        holder.ottv_jumlah.setText(m.getOTJUMLAH());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                if (isLongClick) {
                    //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(activity, OcOvertime.class);
                    intent.putExtra("OTTANGGAL", Overtimes.get(position).getOTTANGGAL()); //you can name the keys whatever you like
                    intent.putExtra("OTDIAJUKAN", Overtimes.get(position).getOTDIAJUKAN()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("OTKETERANGAN", Overtimes.get(position).getOTKETERANGAN());
                    intent.putExtra("OTJAMLEMBUR", Overtimes.get(position).getOTJAMLEMBUR());
                    intent.putExtra("OTKALKULASI", Overtimes.get(position).getOTKALKULASI());
                    intent.putExtra("OTJUMLAH", Overtimes.get(position).getOTJUMLAH());
                    switch (Overtimes.get(position).getOTSTATUS()){
                        case "Menunggu":
                            intent.putExtra("OTSTATUS", "Menunggu");
                            break;
                        case "Batal":
                            intent.putExtra("OTSTATUS", "Batal");
                            break;
                        case "":
                            intent.putExtra("OTSTATUS", "Beres");
                            break;
                    }
                    activity.startActivity(intent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return Overtimes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ItemClickListener clickListener;
        ImageView ottiv_status;
        TextView ottv_tanggal, ottv_diajukan, ottv_ket,ottv_jamlembur,ottv_kalkulasi,ottv_jumlah ;

        public ViewHolder(View itemView) {
            super(itemView);

            ottiv_status = (ImageView)itemView.findViewById(R.id.ottiv_status);
            ottv_tanggal = (TextView)itemView.findViewById(R.id.ottv_tanggal);
            ottv_diajukan = (TextView)itemView.findViewById(R.id.ottv_diajukan);
            ottv_ket = (TextView)itemView.findViewById(R.id.ottv_ket);
            ottv_jamlembur = (TextView)itemView.findViewById(R.id.ottv_jamlembur);
            ottv_kalkulasi = (TextView)itemView.findViewById(R.id.ottv_kalkulasi);
            ottv_jumlah = (TextView)itemView.findViewById(R.id.ottv_jumlah);

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
