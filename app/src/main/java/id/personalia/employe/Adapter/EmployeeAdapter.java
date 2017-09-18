package id.personalia.employe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.personalia.employe.Model.Employee;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    ArrayList<Employee> inputForms;
    Context context;

    public EmployeeAdapter(Context context, int resource, ArrayList<Employee> object) {
        super(context, resource, object);
        this.context = context;
        this.inputForms = object;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.employee_layout, parent, false);
            holder = new ViewHolder();

//            holder._photo = (ImageView) convertView.findViewById(R.id._photo);
            holder._fullname = (TextView) convertView.findViewById(R.id._fullname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder._photo.setImageDrawable(inputForms.get(position).getPHOTO());
        holder._fullname.setText(inputForms.get(position).getFULLNAME());

        return convertView;
    }

    static class ViewHolder {
        ImageView _photo;
        TextView _fullname;
    }
}
