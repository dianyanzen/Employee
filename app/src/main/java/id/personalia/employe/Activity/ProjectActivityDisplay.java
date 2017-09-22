package id.personalia.employe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import id.personalia.employe.Adapter.ProjectAdapter;
import id.personalia.employe.Model.Project;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/20/2017.
 */

public class ProjectActivityDisplay extends AppCompatActivity {

    ArrayList<id.personalia.employe.Model.Project> Projects;
    Project Project;
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        setTitle("Project");

        populateData();

        listView = (ListView) findViewById(R.id.list);
        adapter = new ProjectAdapter(this, R.layout.project_layout_display, Projects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                Intent intent = new Intent();
                intent.putExtra("FULLNAME", Projects.get(i).getProjectName());
                intent.putExtra("ID", Projects.get(i).getProjectID());
                intent.putExtra("DATE", Projects.get(i).getProjectDate());
                intent.putExtra("STATUS", Projects.get(i).getProjectStatus());
                setResult(RESULT_OK, intent);
                */
                Snackbar.make(view, "Project : "+Projects.get(i).getProjectName()+"\nStatus : "+Projects.get(i).getProjectStatus(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    public void populateData() {
        Projects = new ArrayList<Project>();


        Project = new Project();
        Project.setProjectID("1");
        Project.setProjectName("TMMIN WOI");
        Project.setProjectDate("01-09-2011");
        Project.setProjectStatus("On Progress");
        Projects.add(Project);

        Project = new Project();
        Project.setProjectID("2");
        Project.setProjectName("PT INTI");
        Project.setProjectDate("01-09-2017");
        Project.setProjectStatus("Cancel");
        Projects.add(Project);

        Project = new Project();
        Project.setProjectID("3");
        Project.setProjectName("Depobuild");
        Project.setProjectDate("01-09-2016");
        Project.setProjectStatus("Done");
        Projects.add(Project);

        Project = new Project();
        Project.setProjectID("4");
        Project.setProjectName("Personalia.id");
        Project.setProjectDate("01-09-2014");
        Project.setProjectStatus("On Progress");
        Projects.add(Project);

        for(int i=5;i<=20;i++){
            Project = new Project();
            Project.setProjectID(String.valueOf(i));
            Project.setProjectName("Project " + String.valueOf(i));
            if(i<=9){
                Project.setProjectDate("0"+String.valueOf(i)+"-09-2017");
            }else{
                Project.setProjectDate(String.valueOf(i)+"-09-2017");
            }
            if(i%2 == 0) {
                Project.setProjectStatus("Done");
            }else{
                Project.setProjectStatus("On Progress");
            }
            Projects.add(Project);
        }

    }
}
