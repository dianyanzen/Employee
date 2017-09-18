package id.personalia.employe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import id.personalia.employe.Adapter.EmployeeAdapter;
import id.personalia.employe.Model.Employee;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class EmployeeActivity extends AppCompatActivity {

    ArrayList<Employee> employees;
    Employee employee;
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        setTitle("Employee");

        populateData();

        listView = (ListView) findViewById(R.id.list);
        adapter = new EmployeeAdapter(this, R.layout.employee_layout, employees);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("ID", employees.get(i).getID());
                intent.putExtra("FULLNAME", employees.get(i).getFULLNAME());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void populateData() {
        employees = new ArrayList<Employee>();

        for(int i=1;i<=20;i++){
            employee = new Employee();
            employee.setID(String.valueOf(i));
            employee.setFULLNAME("Employee " + String.valueOf(i));
            employees.add(employee);
        }
    }
}