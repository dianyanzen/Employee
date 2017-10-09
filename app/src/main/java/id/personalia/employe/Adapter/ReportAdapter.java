package id.personalia.employe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.personalia.employe.Activity.OcReport;
import id.personalia.employe.Model.Report;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_EMPTY = 2;

    private boolean loading = true;

    private List<Report> Reports;
    private ItemClickListener ItemClickListener;
    private Context context;
    public ReportAdapter(Context current) {
        this.context = current;
        Reports = new ArrayList<Report>();
    }

    private void add(Report item) {
        Reports.add(item);
        notifyItemInserted(Reports.size());
    }

    public void addAll(List<Report> Trevels) {
        for (Report report : Trevels) {
            add(report);
        }
    }

    public void remove(Report item) {
        int position = Reports.indexOf(item);
        if (position > -1) {
            Reports.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        int size = this.Reports.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.Reports.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public Report getItem(int position){
        return Reports.get(position);
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
        return position == Reports.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_layout, parent, false);
            return new reportViewHolder(view, ItemClickListener);
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

        if (holder instanceof reportViewHolder) {
            reportViewHolder reportViewHolder = (reportViewHolder) holder;

            final Report report = Reports.get(position);
            reportViewHolder.report_img.setImageResource(R.drawable.def_report);

            switch (report.getRPSTATUS()){
                case "T":
                    reportViewHolder.RPiv_status.setImageResource(R.drawable.ic_clock);
                    reportViewHolder.RPiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                    break;
                case "":
                    reportViewHolder.RPiv_status.setImageResource(R.drawable.ic_done);
                    reportViewHolder.RPiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                    break;
                case "TT":
                    reportViewHolder.RPiv_status.setImageResource(R.drawable.ic_clock);
                    reportViewHolder.RPiv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                    break;
            }

            reportViewHolder.RPiv_status.setPadding(10,10,10,10);
            reportViewHolder.RPtv_tanggal.setText(report.getRPTANGGAL());
            reportViewHolder.RPtv_clockin.setText(report.getRPCL_IN());
            reportViewHolder.RPtv_clockout.setText(report.getRPCL_OUT());
            reportViewHolder.RPtv_workhour.setText(report.getRPWORKHOUR());
            reportViewHolder.RPtv_name.setText(report.getRPNAMA());
            reportViewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    if (isLongClick) {
                        //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent intent = new Intent(activity, OcReport.class);
                        intent.putExtra("RPTANGGAL", report.getRPTANGGAL()); //you can name the keys whatever you like
                        intent.putExtra("RPCL_IN", report.getRPCL_IN()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                        intent.putExtra("RPCL_OUT", report.getRPCL_OUT());
                        intent.putExtra("RPWORKHOUR", report.getRPWORKHOUR());
                        intent.putExtra("RPNAMA", report.getRPNAMA());
                        switch (report.getRPSTATUS()){
                            //case R.drawable.ic_clock:
                            case "T":
                                intent.putExtra("RPSTATUS", "Terlambat");
                                break;
                            case "TT":
                                intent.putExtra("RPSTATUS", "Terlambat Terlambat");
                                break;
                            case "":
                                intent.putExtra("RPSTATUS", "Tepat Waktu");
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
            EmptyViewHolder.empty_text.setText("Tidak Ada Data Absensi\nYang Dapat Ditampilkan");
        }

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return Reports == null ? 0 : Reports.size() + 1;
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

    static class reportViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView RPiv_status, report_img;
        TextView RPtv_tanggal, RPtv_clockin, RPtv_clockout,RPtv_workhour,RPtv_name;


        public reportViewHolder(View itemView, ItemClickListener ItemClickListener) {
            super(itemView);


            report_img = (ImageView) itemView.findViewById(R.id.RPimageView);
            RPiv_status = (ImageView)itemView.findViewById(R.id.RPiv_status);
            RPtv_tanggal = (TextView)itemView.findViewById(R.id.RPtv_tanggal);
            RPtv_clockin = (TextView)itemView.findViewById(R.id.RPtv_clockin);
            RPtv_clockout = (TextView)itemView.findViewById(R.id.RPtv_clockout);
            RPtv_workhour = (TextView)itemView.findViewById(R.id.RPtv_total);
            RPtv_name = (TextView)itemView.findViewById(R.id.RPtv_name);

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
