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

import id.personalia.employe.Activity.OcAttendance;
import id.personalia.employe.Model.Attendance;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder>{
    ArrayList<Attendance> Attendances;
    Context context;

    public AttendanceAdapter(Context context, int resource, ArrayList<Attendance> object) {
        this.context = context;
        this.Attendances = object;
    }

    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_list_layout, parent, false);

        return new AttendanceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttendanceAdapter.ViewHolder holder, int position) {
        final Attendance m = Attendances.get(position);

        switch (Attendances.get(position).getATTSTATUS()){
            case "Menunggu":
                holder.attiv_status.setImageResource(R.drawable.ic_clock);
                holder.attiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                break;
            case "":
                holder.attiv_status.setImageResource(R.drawable.ic_done);
                holder.attiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                break;
        }

        holder.attiv_status.setPadding(10,10,10,10);

        holder.atttv_tanggal.setText(m.getATTTANGGAL());
        holder.atttv_descrip.setText(m.getATTDESCRIP());
        holder.atttv_lama.setText(m.getATTLAMA());
        holder.atttv_diajukan.setText(m.getATTDIAJUKAN());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                if (isLongClick) {
                    //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(activity, OcAttendance.class);
                    intent.putExtra("ATTTANGGAL", Attendances.get(position).getATTTANGGAL()); //you can name the keys whatever you like
                    intent.putExtra("ATTDESCRIP", Attendances.get(position).getATTDESCRIP()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("ATTLAMA", Attendances.get(position).getATTLAMA());
                    intent.putExtra("ATTDIAJUKAN", Attendances.get(position).getATTDIAJUKAN());
                    switch (Attendances.get(position).getATTSTATUS()){
                        case "Menunggu":
                            intent.putExtra("ATTSTATUS", "Menunggu");
                            break;
                        case "Batal":
                            intent.putExtra("ATTSTATUS", "Batal");
                            break;
                        case "":
                            intent.putExtra("ATTSTATUS", "Beres");
                            break;
                    }
                    activity.startActivity(intent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return Attendances.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView attiv_status;
        TextView atttv_tanggal, atttv_descrip, atttv_lama,atttv_diajukan;

        public ViewHolder(View itemView) {
            super(itemView);

            attiv_status = (ImageView)itemView.findViewById(R.id.attiv_status);
            atttv_tanggal = (TextView)itemView.findViewById(R.id.atttv_tanggal);
            atttv_descrip = (TextView)itemView.findViewById(R.id.atttv_descrip);
            atttv_lama = (TextView)itemView.findViewById(R.id.atttv_lama);
            atttv_diajukan = (TextView)itemView.findViewById(R.id.atttv_diajukan);

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
