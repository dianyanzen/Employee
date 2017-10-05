package id.personalia.employe.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import id.personalia.employe.Adapter.InputFormAdapter;
import id.personalia.employe.Helper.ArkaHelper;
import id.personalia.employe.Model.InputForm;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class SearchAttendanceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    Calendar calendar;
    DatePickerDialog datePicker;
    SimpleDateFormat simpleDateFormat;
    String time;
    int year, month, day, _index;
    ArkaHelper helper;
    ListView listView;
    ArrayAdapter adapter;
    InputForm inputForm;
    ArrayList<InputForm> inputForms;
    AlertDialog.Builder builder;
    EditText popupEditText;
    SearchView sv;

    int EMPLOYEE_ID, PROJECT_ID, SUPERVISOR_ID = 0;

    private static int EMPLOYEE_REQUEST = 1000;
    private static int PROJECT_REQUEST = 2000;
    private static int SUPERVISOR_REQUEST = 3000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_attendance);

        helper = new ArkaHelper(SearchAttendanceActivity.this);
        currentTime();
        currentDate();
        generateData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Drawable navIcon = getResources().getDrawable(R.drawable.ic_chevron_left);
        navIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationIcon(navIcon);

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setTitle(getResources().getString(R.string.search_attendance));

        fab = (FloatingActionButton) findViewById(R.id.btnSave);
        fab.setImageResource(R.drawable.ic_search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from_dt = inputForms.get(0).getLABEL().toString();
                String to_dt = inputForms.get(1).getLABEL().toString();
                String vfrom_dt = inputForms.get(0).getVALUE().toString();
                String vto_dt = inputForms.get(1).getVALUE().toString();
                Snackbar.make(view, from_dt+": "+vfrom_dt+"\n"+to_dt+": "+vto_dt, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(SearchAttendanceActivity.this, Activity_Main.class);
                intent.putExtra("FragmentNM", "Attendance");
                intent.putExtra("param1", from_dt);
                intent.putExtra("param2", to_dt);
                intent.putExtra("val1", vfrom_dt);
                intent.putExtra("val2", vto_dt);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.list);
        adapter = new InputFormAdapter(this, R.layout.input_form_layout, inputForms);

        datePicker = new DatePickerDialog(SearchAttendanceActivity.this, R.style.ArkaCalendar, this, year, month, day);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                _index = i;

                LinearLayout linearLayout = new LinearLayout(SearchAttendanceActivity.this);
                linearLayout.setLayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                );
                linearLayout.setPadding(70,30,70,30);

                if(inputForms.get(i).getTYPE().equals("DATEPICKER")){
                    datePicker.show();
                }else if(inputForms.get(i).getTYPE().equals("EDITTEXT")){

                    builder = new AlertDialog.Builder(SearchAttendanceActivity.this);

                    TextView tvTitle = new TextView(SearchAttendanceActivity.this);
                    tvTitle.setText(inputForms.get(i).getLABEL());
                    tvTitle.setTextSize(18);
                    tvTitle.setTypeface(null, Typeface.BOLD);
                    tvTitle.setPadding(70, 40, 70, 40);

                    builder.setCustomTitle(tvTitle);

                    popupBuilderString(linearLayout, i);

                    builder.show();
                }else if(inputForms.get(i).getTYPE().equals("EMPLOYEE")){
                    Intent intent = new Intent(SearchAttendanceActivity.this, EmployeeActivity.class);
                    startActivityForResult(intent, 1000);
                }else if(inputForms.get(i).getTYPE().equals("PROJECT")){
                    Intent intent = new Intent(SearchAttendanceActivity.this, ProjectActivity.class);
                    startActivityForResult(intent, 1000);
                }
            }
        });
    }

    public void currentDate(){
        calendar = Calendar.getInstance(TimeZone.getDefault());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        setDate(_index,  helper.zeroPadLeft(i2)+ "-" + helper.zeroPadLeft(i1+1) + "-"+helper.zeroPadLeft(i) );
    }

    public void setDate(int position, String date){
        inputForms.get(position).setVALUE(date);
        adapter.notifyDataSetChanged();
    }
    public void currentTime(){
        calendar = Calendar.getInstance(TimeZone.getDefault());
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        time = simpleDateFormat.format(calendar.getTime());
    }

    public void popupBuilderString(View v, int position){

        popupEditText = new EditText(SearchAttendanceActivity.this);
        popupEditText.setMaxLines(1);
        popupEditText.setBackgroundColor(Color.TRANSPARENT);
        popupEditText.setPadding(30,30,30,30);
        popupEditText.setBackgroundResource(R.drawable.popup_edittext);
        popupEditText.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        );
        popupEditText.requestFocus();
        popupEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(popupEditText, 0);
            }
        },200);

        ((LinearLayout)v).addView(popupEditText);

        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                inputForms.get(_index).setVALUE(popupEditText.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        builder.setView(v);
        builder.create().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    public void generateData(){
        inputForms = new ArrayList<InputForm>();

        inputForm = new InputForm();
        inputForm.setICON(getResources().getDrawable(R.drawable.ic_date_range));
        inputForm.setTYPE("DATEPICKER");
        inputForm.setLABEL("Dari Tanggal");
        inputForm.setVALUE(helper.zeroPadLeft(day)+ "-" + helper.zeroPadLeft(month+1) + "-" + helper.zeroPadLeft(year));
        inputForms.add(inputForm);

        inputForm = new InputForm();
        inputForm.setICON(getResources().getDrawable(R.drawable.ic_date_range));
        inputForm.setTYPE("DATEPICKER");
        inputForm.setLABEL("Sampai Tanggal");
        inputForm.setVALUE(helper.zeroPadLeft(day)+ "-" + helper.zeroPadLeft(month+1) + "-" + helper.zeroPadLeft(year));
        inputForms.add(inputForm);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EMPLOYEE_REQUEST) {
            if (resultCode == RESULT_OK) {
                inputForms.get(_index).setVALUE(data.getStringExtra("FULLNAME"));
                EMPLOYEE_ID = Integer.parseInt(data.getStringExtra("ID"));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
