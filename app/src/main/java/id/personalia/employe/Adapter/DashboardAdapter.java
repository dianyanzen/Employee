package id.personalia.employe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.personalia.employe.Activity.DetailReportActivity;
import id.personalia.employe.Activity.EmployeeActivity;
import id.personalia.employe.Activity.ProjectActivity;
import id.personalia.employe.Activity.ProjectActivityDisplay;
import id.personalia.employe.Fragment.AttendanceFragment;
import id.personalia.employe.Fragment.ClaimFragment;
import id.personalia.employe.Fragment.OvertimeFragment;
import id.personalia.employe.Fragment.ReportFragment;
import id.personalia.employe.Fragment.TravelFragment;
import id.personalia.employe.Model.Dashboard;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>{
    ArrayList<Dashboard> Dashboards;
    Context context;
    private CaldroidFragment dialogCaldroidFragment;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            Toast.makeText(context, formatter.format(date),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChangeMonth(int month, int year) {
            String text = "Bulan: " + month + " Tahun: " + year;
            Toast.makeText(context, text,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLongClickDate(Date date, View view) {
            Toast.makeText(context,
                    "Long click " + formatter.format(date),
                    Toast.LENGTH_SHORT).show();
        }



    };

    public DashboardAdapter(Context context, int resource, ArrayList<Dashboard> object) {
        this.context = context;
        this.Dashboards = object;
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_list_layout, parent, false);

        return new DashboardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder holder, int position) {


        final Dashboard m = Dashboards.get(position);
        // holder main
        switch (Dashboards.get(position).getMAIN()){
            case "Clock In":
                holder.dbimage.setImageResource(R.mipmap.ic_clockin);
                holder.tv_dbmain.setText(R.string.clock_in);
                break;
            case "Dinas Luar":
                holder.dbimage.setImageResource(R.mipmap.ic_plane);
                holder.tv_dbmain.setText(R.string.travel);
                break;
            case "Klaim":
                holder.dbimage.setImageResource(R.mipmap.ic_money);
                holder.tv_dbmain.setText(R.string.claim);
                break;
            case "Izin":
                holder.dbimage.setImageResource(R.mipmap.ic_gavel);
                holder.tv_dbmain.setText(R.string.attendance);
                break;
            case "Lembur":
                holder.dbimage.setImageResource(R.mipmap.ic_coffe);
                holder.tv_dbmain.setText(R.string.overtime);
                break;
            case "Kehadiran":
                holder.dbimage.setImageResource(R.mipmap.ic_idbadge);
                holder.tv_dbmain.setText(R.string.report);
                break;
            case "Tugas":
                holder.dbimage.setImageResource(R.mipmap.ic_suitcase);
                holder.tv_dbmain.setText(R.string.project);
                break;
            case "Kalender":
                holder.dbimage.setImageResource(R.mipmap.ic_calendar_work);
                holder.tv_dbmain.setText(R.string.calendar_work);
                break;
        }

        holder.tv_dbinfo.setText(m.getINFO());
        holder.tv_dbstatus.setText(m.getSTATUS());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                if (isLongClick) {
                    switch (Dashboards.get(position).getMAIN()){
                        case "Clock In":
                            Intent intent = new Intent(activity, DetailReportActivity.class);
                            activity.startActivity(intent);
                            break;
                        case "Dinas Luar":
                            TravelFragment fragmenttravel = new TravelFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionTravel = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionTravel.replace(R.id.fragment_container, fragmenttravel, "TRAVEL");
                            fragmentTransactionTravel.addToBackStack("TRAVEL");
                            fragmentTransactionTravel.commit();
                            break;
                        case "Klaim":
                            ClaimFragment fragmentClaim = new ClaimFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionClaim = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionClaim.replace(R.id.fragment_container, fragmentClaim, "CLAIM");
                            fragmentTransactionClaim.addToBackStack("CLAIM");
                            fragmentTransactionClaim.commit();
                            break;
                        case "Izin":
                            AttendanceFragment fragmentAttendance = new AttendanceFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionAttendance = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionAttendance.replace(R.id.fragment_container, fragmentAttendance, "ATTENDANCE");
                            fragmentTransactionAttendance.addToBackStack("ATTENDANCE");
                            fragmentTransactionAttendance.commit();
                            break;
                        case "Lembur":
                            OvertimeFragment fragmentOvertime = new OvertimeFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionOvertime = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionOvertime.replace(R.id.fragment_container, fragmentOvertime, "OVERTIME");
                            fragmentTransactionOvertime.addToBackStack("OVERTIME");
                            fragmentTransactionOvertime.commit();
                            break;
                        case "Kehadiran":
                            ReportFragment fragmentReport = new ReportFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionReport = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionReport.replace(R.id.fragment_container, fragmentReport, "REPORT");
                            fragmentTransactionReport.addToBackStack("REPORT");
                            fragmentTransactionReport.commit();
                            break;
                        case "Tugas":
                            Intent intentproject = new Intent(activity, ProjectActivityDisplay.class);
                            activity.startActivity(intentproject);
                            break;
                        case "Kalender":
                            try {
                                dialogCaldroidFragment = new CaldroidFragment();
                                dialogCaldroidFragment.setCaldroidListener(listener);

                                // If activity is recovered from rotation
                                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";

                                Bundle bundle = new Bundle();
                                // Setup dialogTitle
                                dialogCaldroidFragment.setArguments(bundle);
                                dialogCaldroidFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                                        //dialogCaldroidFragment.show((context).getSupportFragmentManager(),
                                        dialogTag);
                            } catch (Exception e){

                            }
                            break;
                    }
                } else {
                    switch (Dashboards.get(position).getMAIN()) {
                        case "Clock In":
                            Intent intent = new Intent(activity, DetailReportActivity.class);
                            activity.startActivity(intent);
                            break;
                        case "Dinas Luar":
                            TravelFragment fragmenttravel = new TravelFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionTravel = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionTravel.replace(R.id.fragment_container, fragmenttravel, "TRAVEL");
                            fragmentTransactionTravel.addToBackStack("TRAVEL");
                            fragmentTransactionTravel.commit();
                            break;
                        case "Klaim":
                            ClaimFragment fragmentClaim = new ClaimFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionClaim = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionClaim.replace(R.id.fragment_container, fragmentClaim, "CLAIM");
                            fragmentTransactionClaim.addToBackStack("CLAIM");
                            fragmentTransactionClaim.commit();
                            break;
                        case "Izin":
                            AttendanceFragment fragmentAttendance = new AttendanceFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionAttendance = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionAttendance.replace(R.id.fragment_container, fragmentAttendance, "ATTENDANCE");
                            fragmentTransactionAttendance.addToBackStack("ATTENDANCE");
                            fragmentTransactionAttendance.commit();
                            break;
                        case "Lembur":
                            OvertimeFragment fragmentOvertime = new OvertimeFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionOvertime = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionOvertime.replace(R.id.fragment_container, fragmentOvertime, "OVERTIME");
                            fragmentTransactionOvertime.addToBackStack("OVERTIME");
                            fragmentTransactionOvertime.commit();
                            break;
                        case "Kehadiran":
                            ReportFragment fragmentReport = new ReportFragment();
                            android.support.v4.app.FragmentTransaction fragmentTransactionReport = activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransactionReport.replace(R.id.fragment_container, fragmentReport, "REPORT");
                            fragmentTransactionReport.addToBackStack("REPORT");
                            fragmentTransactionReport.commit();
                            break;
                        case "Tugas":
                            //Toast.makeText(context, "Saya Pilih Tugas", Toast.LENGTH_SHORT).show();
                            Intent intentproject = new Intent(activity, ProjectActivityDisplay.class);
                            activity.startActivity(intentproject);
                            break;
                        case "Kalender":
                            try {
                                dialogCaldroidFragment = new CaldroidFragment();
                                dialogCaldroidFragment.setCaldroidListener(listener);

                                // If activity is recovered from rotation
                                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";

                                Bundle bundle = new Bundle();
                                // Setup dialogTitle
                                dialogCaldroidFragment.setArguments(bundle);
                                dialogCaldroidFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                                        //dialogCaldroidFragment.show((context).getSupportFragmentManager(),
                                        dialogTag);
                            } catch (Exception e){

                            }

                            //Toast.makeText(context, "Saya Pilih Kalender", Toast.LENGTH_SHORT).show();

                            break;
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Dashboards.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ItemClickListener clickListener;
        ImageView dbimage;
        TextView tv_dbmain, tv_dbinfo, tv_dbstatus ;
        private CaldroidFragment dialogCaldroidFragment;

        public ViewHolder(View itemView) {
            super(itemView);
            // set main
            dbimage = (ImageView)itemView.findViewById(R.id.dbimage);
            tv_dbmain = (TextView)itemView.findViewById(R.id.tv_dbmain);
            tv_dbinfo = (TextView)itemView.findViewById(R.id.tv_dbinfo);
            tv_dbstatus = (TextView)itemView.findViewById(R.id.tv_dbstatus);
            // set info
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
            Log.e("Tag","Jalan 2");
        }

        @Override
        public void onClick(View view) {
            Log.e("Tag","Jalan 4");
            clickListener.onClick(view, getPosition(),  false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            Log.e("Tag","Jalan 3");
            return true;

        }


    }
    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);

    }

}
