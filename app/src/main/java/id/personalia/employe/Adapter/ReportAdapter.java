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

import id.personalia.employe.Activity.OcReport;
import id.personalia.employe.Model.Report;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{
    ArrayList<Report> Reports;
    Context context;

    public ReportAdapter(Context context, int resource, ArrayList<Report> object) {
        this.context = context;
        this.Reports = object;
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_layout, parent, false);

        return new ReportAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ViewHolder holder, int position) {
        final Report m = Reports.get(position);

        switch (Reports.get(position).getRPSTATUS()){
            case "T":
                holder.RPiv_status.setImageResource(R.drawable.ic_clock);
                holder.RPiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                break;
            case "":
                holder.RPiv_status.setImageResource(R.drawable.ic_done);
                holder.RPiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                break;
        }

        holder.RPiv_status.setPadding(10,10,10,10);

        holder.RPtv_tanggal.setText(m.getRPTANGGAL());
        holder.RPtv_clockin.setText(m.getRPCL_IN());
        holder.RPtv_clockout.setText(m.getRPCL_OUT());
        holder.RPtv_workhour.setText(m.getRPWORKHOUR());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                if (isLongClick) {
                    //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(activity, OcReport.class);
                    intent.putExtra("RPTANGGAL", Reports.get(position).getRPTANGGAL()); //you can name the keys whatever you like
                    intent.putExtra("RPCL_IN", Reports.get(position).getRPCL_IN()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("RPCL_OUT", Reports.get(position).getRPCL_OUT());
                    intent.putExtra("RPWORKHOUR", Reports.get(position).getRPWORKHOUR());
                    switch (Reports.get(position).getRPSTATUS()){
                        case "T":
                            intent.putExtra("RPSTATUS", "Terlambat");
                            break;
                        case "":
                            intent.putExtra("RPSTATUS", "Tepat Waktu");
                            break;
                    }
                    activity.startActivity(intent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return Reports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView RPiv_status;
        TextView RPtv_tanggal, RPtv_clockin, RPtv_clockout,RPtv_workhour;

        public ViewHolder(View itemView) {
            super(itemView);

            RPiv_status = (ImageView)itemView.findViewById(R.id.RPiv_status);
            RPtv_tanggal = (TextView)itemView.findViewById(R.id.RPtv_tanggal);
            RPtv_clockin = (TextView)itemView.findViewById(R.id.RPtv_clockin);
            RPtv_clockout = (TextView)itemView.findViewById(R.id.RPtv_clockout);
            RPtv_workhour = (TextView)itemView.findViewById(R.id.RPtv_workhour);

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
