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

import id.personalia.employe.Activity.OcAttendance;
import id.personalia.employe.Model.Attendance;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_EMPTY = 2;

    private boolean loading = true;

    private List<Attendance> Attendances;
    private ItemClickListener ItemClickListener;
    private Context context;
    public AttendanceAdapter(Context current) {
        this.context = current;
        Attendances = new ArrayList<>();
    }

    private void add(Attendance item) {
        Attendances.add(item);
        notifyItemInserted(Attendances.size());
    }

    public void addAll(List<Attendance> Trevels) {
        for (Attendance attendance : Trevels) {
            add(attendance);
        }
    }

    public void remove(Attendance item) {
        int position = Attendances.indexOf(item);
        if (position > -1) {
            Attendances.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        int size = this.Attendances.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.Attendances.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public Attendance getItem(int position){
        return Attendances.get(position);
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
        return position == Attendances.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_layout, parent, false);
            return new attendanceViewHolder(view, ItemClickListener);
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

        if (holder instanceof attendanceViewHolder) {
            attendanceViewHolder attendanceViewHolder = (attendanceViewHolder) holder;

            final Attendance attendance = Attendances.get(position);
            attendanceViewHolder.treveliv_img.setImageResource(R.drawable.def_attendance);

            switch (attendance.getATTSTATUS()){
                case "Menunggu":
                    attendanceViewHolder.attendanceiv_status.setImageResource(R.drawable.ic_clock);
                    attendanceViewHolder.attendanceiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                    break;
                case "Disetujui":
                    attendanceViewHolder.attendanceiv_status.setImageResource(R.drawable.ic_done);
                    attendanceViewHolder.attendanceiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                    break;
                case "Ditolak":
                    attendanceViewHolder.attendanceiv_status.setImageResource(R.drawable.ic_clear);
                    attendanceViewHolder.attendanceiv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                    break;
            }

            attendanceViewHolder.attendanceiv_status.setPadding(10,10,10,10);
            attendanceViewHolder.attendancetv_tanggal.setText(attendance.getATTTANGGAL());
            attendanceViewHolder.attendancetv_diajukan.setText(attendance.getATTDIAJUKAN());
            attendanceViewHolder.attendancetv_deskripsi.setText(attendance.getATTDESCRIP());
            attendanceViewHolder.attendancetv_jumlah.setText(attendance.getATTLAMA());
            attendanceViewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    if (isLongClick) {
                        //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent intent = new Intent(activity, OcAttendance.class);
                        intent.putExtra("ATTTANGGAL", Attendances.get(position).getATTTANGGAL()); //you can name the keys whatever you like
                        intent.putExtra("ATTDIAJUKAN", Attendances.get(position).getATTDIAJUKAN()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                        intent.putExtra("ATTDESCRIP", Attendances.get(position).getATTDESCRIP());
                        intent.putExtra("ATTLAMA", Attendances.get(position).getATTLAMA());
                        switch (Attendances.get(position).getATTSTATUS()){
                            case "Menunggu":
                                intent.putExtra("ATTSTATUS", "Menunggu");
                                break;
                            case "Disetujui":
                                intent.putExtra("ATTSTATUS", "Disetujui");
                                break;
                            case "Ditolak":
                                intent.putExtra("ATTSTATUS", "Ditolak");
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
            EmptyViewHolder.empty_text.setText("Tidak Ada Data Izin Kehadiran\nYang Dapat Ditampilkan");
        }

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return Attendances == null ? 0 : Attendances.size() + 1;
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

    static class attendanceViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView attendanceiv_status, treveliv_img;
        TextView attendancetv_tanggal, attendancetv_diajukan, attendancetv_deskripsi,attendancetv_jumlah;


        public attendanceViewHolder(View itemView, ItemClickListener ItemClickListener) {
            super(itemView);

            attendanceiv_status = (ImageView) itemView.findViewById(R.id.attiv_status);
            treveliv_img = (ImageView) itemView.findViewById(R.id.attimageView);
            attendancetv_tanggal = (TextView) itemView.findViewById(R.id.atttv_tanggal);
            attendancetv_diajukan = (TextView) itemView.findViewById(R.id.atttv_diajukan);
            attendancetv_deskripsi = (TextView) itemView.findViewById(R.id.atttv_descrip);
            attendancetv_jumlah = (TextView) itemView.findViewById(R.id.atttv_lama);

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
