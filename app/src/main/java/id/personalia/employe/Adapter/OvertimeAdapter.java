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

import id.personalia.employe.Activity.OcOvertime;
import id.personalia.employe.Model.Overtime;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class OvertimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_EMPTY = 2;

    private boolean loading = true;

    private List<Overtime> Overtimes;
    private ItemClickListener ItemClickListener;
    private Context context;
    public OvertimeAdapter(Context current) {
        this.context = current;
        Overtimes = new ArrayList<Overtime>();
    }

    private void add(Overtime item) {
        Overtimes.add(item);
        notifyItemInserted(Overtimes.size());
    }

    public void addAll(List<Overtime> Overtimes) {
        for (Overtime Overtime : Overtimes) {
            add(Overtime);
        }
    }

    public void remove(Overtime item) {
        int position = Overtimes.indexOf(item);
        if (position > -1) {
            Overtimes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        int size = this.Overtimes.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.Overtimes.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public Overtime getItem(int position){
        return Overtimes.get(position);
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
        return position == Overtimes.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.overtime_list_layout, parent, false);
            return new OvertimeViewHolder(view, ItemClickListener);
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

        if (holder instanceof OvertimeViewHolder) {
            OvertimeViewHolder OvertimeViewHolder = (OvertimeViewHolder) holder;

            final Overtime Overtime = Overtimes.get(position);
            OvertimeViewHolder.otiv_image.setImageResource(R.drawable.def_overtime);

            switch (Overtime.getOTSTATUS()){
                case "Menunggu":
                    OvertimeViewHolder.otiv_status.setImageResource(R.drawable.ic_clock);
                    OvertimeViewHolder.otiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                    break;
                case "Disetujui":
                    OvertimeViewHolder.otiv_status.setImageResource(R.drawable.ic_done);
                    OvertimeViewHolder.otiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                    break;
                case "Ditolak":
                    OvertimeViewHolder.otiv_status.setImageResource(R.drawable.ic_clear);
                    OvertimeViewHolder.otiv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                    break;
            }

            OvertimeViewHolder.otiv_status.setPadding(10,10,10,10);
            OvertimeViewHolder.ottv_tanggal.setText(Overtime.getOTTANGGAL());
            OvertimeViewHolder.ottv_diajukan.setText(Overtime.getOTDIAJUKAN());
            OvertimeViewHolder.ottv_ket.setText(Overtime.getOTKETERANGAN());
            OvertimeViewHolder.ottv_jamlembur.setText(Overtime.getOTJAMLEMBUR());
            OvertimeViewHolder.ottv_kalkulasi.setText(Overtime.getOTKALKULASI());
            OvertimeViewHolder.ottv_jumlah.setText(Overtime.getOTJUMLAH());
            OvertimeViewHolder.ottv_uang.setText(Overtime.getOTUANG());
            OvertimeViewHolder.setClickListener(new ItemClickListener() {
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
                        intent.putExtra("OTUANG", Overtimes.get(position).getOTUANG());
                        switch (Overtimes.get(position).getOTSTATUS()){
                            case "Menunggu":
                                intent.putExtra("OTSTATUS", "Menunggu");
                                break;
                            case "Disetujui":
                                intent.putExtra("OTSTATUS", "Disetujui");
                                break;
                            case "Ditolak":
                                intent.putExtra("OTSTATUS", "Ditolak");
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
            EmptyViewHolder.empty_text.setText("Tidak Ada Data Lembur\nYang Dapat Ditampilkan");
        }

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return Overtimes == null ? 0 : Overtimes.size() + 1;
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

    static class OvertimeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView otiv_status, otiv_image;
        TextView ottv_tanggal, ottv_diajukan, ottv_ket,ottv_jamlembur,ottv_kalkulasi,ottv_jumlah,ottv_uang ;


        public OvertimeViewHolder(View itemView, ItemClickListener ItemClickListener) {
            super(itemView);

            otiv_status = (ImageView)itemView.findViewById(R.id.ottiv_status);
            otiv_image = (ImageView)itemView.findViewById(R.id.otimageView);
            ottv_tanggal = (TextView)itemView.findViewById(R.id.ottv_tanggal);
            ottv_diajukan = (TextView)itemView.findViewById(R.id.ottv_diajukan);
            ottv_ket = (TextView)itemView.findViewById(R.id.ottv_ket);
            ottv_jamlembur = (TextView)itemView.findViewById(R.id.ottv_jamlembur);
            ottv_kalkulasi = (TextView)itemView.findViewById(R.id.ottv_kalkulasi);
            ottv_jumlah = (TextView)itemView.findViewById(R.id.ottv_jumlah);
            ottv_uang = (TextView)itemView.findViewById(R.id.ottv_uang);

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
