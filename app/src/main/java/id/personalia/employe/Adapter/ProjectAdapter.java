package id.personalia.employe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.personalia.employe.Model.Project;
import id.personalia.employe.R;

public class ProjectAdapter extends ArrayAdapter<Project> {
    ArrayList<Project> inputForms;
    Context context;

    public ProjectAdapter(Context context, int resource, ArrayList<Project> object) {
        super(context, resource, object);
        this.context = context;
        this.inputForms = object;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.project_layout, parent, false);
            holder = new ViewHolder();
            holder.projecttv_name = (TextView) convertView.findViewById(R.id.projecttv_name);
            holder.projectiv_status = (ImageView) convertView.findViewById(R.id.projectiv_status);
            holder.projecttv_date = (TextView) convertView.findViewById(R.id.projecttv_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.projecttv_name.setText(inputForms.get(position).getProjectName());
        holder.projecttv_date.setText(inputForms.get(position).getProjectDate());
        switch (inputForms.get(position).getProjectStatus()){
            case "On Progress":
                holder.projectiv_status.setImageResource(R.drawable.ic_clock);
                holder.projectiv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                break;
            case "Cancel":
                holder.projectiv_status.setImageResource(R.drawable.ic_clear);
                holder.projectiv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                break;
            case "Done":
                holder.projectiv_status.setImageResource(R.drawable.ic_done);
                holder.projectiv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView projectiv_status;
        TextView projecttv_name,projecttv_date;
    }
}
