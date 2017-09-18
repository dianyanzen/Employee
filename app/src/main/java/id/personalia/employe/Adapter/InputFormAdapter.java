package id.personalia.employe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.personalia.employe.Model.InputForm;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class InputFormAdapter extends ArrayAdapter<InputForm> {
    ArrayList<InputForm> inputForms;
    Context context;

    public InputFormAdapter(Context context, int resource, ArrayList<InputForm> object) {
        super(context, resource, object);
        this.context = context;
        this.inputForms = object;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.input_form_layout, parent, false);
            holder = new ViewHolder();

            holder._icon = (ImageView) convertView.findViewById(R.id._icon);
            holder._label = (TextView) convertView.findViewById(R.id._label);
            holder._value = (TextView) convertView.findViewById(R.id._value);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder._icon.setImageDrawable(inputForms.get(position).getICON());
        holder._label.setText(inputForms.get(position).getLABEL());
        holder._value.setText(inputForms.get(position).getVALUE());

        return convertView;
    }

    static class ViewHolder {
        ImageView _icon;
        TextView _label, _value;
    }
}
