package id.personalia.employe.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import id.personalia.employe.Adapter.InputFormAdapter;
import id.personalia.employe.Helper.ArkaHelper;
import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Helper.GPSTracker;
import id.personalia.employe.Helper.PermissionUtils;
import id.personalia.employe.Model.InputForm;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class DetailReportActivity extends AppCompatActivity
        implements
        DatePickerDialog.OnDateSetListener,
        OnMapReadyCallback,
        AdapterView.OnItemSelectedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {
        /**
         * Request code for location permission request.
         *
         * @see #onRequestPermissionsResult(int, String[], int[])
         */
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        /**
         * Flag indicating whether a requested permission has been denied after returning in
         * {@link #onRequestPermissionsResult(int, String[], int[])}.
         */
        private boolean mPermissionDenied = false;
        private static String is_admin,employee_id,employee_name,company_id,atasan_name,atasan_id;
        private GoogleMap mMap;
        private Spinner mSpinner;
        private LocationManager locationManager;
        private static final long MIN_TIME = 400;
        private static final float MIN_DISTANCE = 1000;
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
        String stringLatitude, stringLongitude,country,city,postalCode,addressLine;
        double Latitude, Longitude;
        int EMPLOYEE_ID, PROJECT_ID, SUPERVISOR_ID = 0;

        private static int EMPLOYEE_REQUEST = 1000;
        private static int PROJECT_REQUEST = 2000;
        private static int SUPERVISOR_REQUEST = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_clockin);

                helper = new ArkaHelper(DetailReportActivity.this);
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

                setTitle(getResources().getString(R.string.new_report));

                fab = (FloatingActionButton) findViewById(R.id.btnSave);
                fab.setImageResource(R.drawable.ic_save);
                fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Snackbar.make(view, "Latitude : "+stringLatitude+"\nLongitude : "+stringLongitude, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        }
                });

                listView = (ListView) findViewById(R.id.list);
                adapter = new InputFormAdapter(this, R.layout.input_form_layout, inputForms);

                datePicker = new DatePickerDialog(DetailReportActivity.this, R.style.ArkaCalendar, this, year, month, day);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                _index = i;

                                LinearLayout linearLayout = new LinearLayout(DetailReportActivity.this);
                                linearLayout.setLayoutParams(
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                                );
                                linearLayout.setPadding(70, 30, 70, 30);

                                if (inputForms.get(i).getTYPE().equals("DATEPICKER")) {
                                        datePicker.show();
                                } else if (inputForms.get(i).getTYPE().equals("EDITTEXT")) {

                                        builder = new AlertDialog.Builder(DetailReportActivity.this);

                                        TextView tvTitle = new TextView(DetailReportActivity.this);
                                        tvTitle.setText(inputForms.get(i).getLABEL());
                                        tvTitle.setTextSize(18);
                                        tvTitle.setTypeface(null, Typeface.BOLD);
                                        tvTitle.setPadding(70, 40, 70, 40);

                                        builder.setCustomTitle(tvTitle);

                                        popupBuilderString(linearLayout, i);

                                        builder.show();
                                } else if (inputForms.get(i).getTYPE().equals("EMPLOYEE")) {
                                        Intent intent = new Intent(DetailReportActivity.this, EmployeeActivity.class);
                                        startActivityForResult(intent, 1000);
                                } else if (inputForms.get(i).getTYPE().equals("PROJECT")) {
                                        Intent intent = new Intent(DetailReportActivity.this, ProjectActivity.class);
                                        startActivityForResult(intent, 1000);
                                }
                        }
                });
                // check if GPS enabled
                GPSTracker gpsTracker = new GPSTracker(this);
                if (gpsTracker.getIsGPSTrackingEnabled())
                {
                        stringLatitude = String.valueOf(gpsTracker.getLatitude());
                        Latitude = Double.valueOf(gpsTracker.getLatitude());
                        stringLongitude = String.valueOf(gpsTracker.getLongitude());
                        Longitude = Double.valueOf(gpsTracker.getLongitude());
                        country = gpsTracker.getCountryName(this);
                        city = gpsTracker.getLocality(this);
                        postalCode = gpsTracker.getPostalCode(this);
                        addressLine = gpsTracker.getAddressLine(this);
                }
                else
                {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gpsTracker.showSettingsAlert();
                }
                mSpinner = (Spinner) findViewById(R.id.layers_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        this, R.array.layers_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                mSpinner.setOnItemSelectedListener(this);
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        }
        public void currentDate(){
                calendar = Calendar.getInstance(TimeZone.getDefault());
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                setDate(_index, helper.zeroPadLeft(i) + "-" + helper.zeroPadLeft(i1+1) + "-" + helper.zeroPadLeft(i2));
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

                popupEditText = new EditText(DetailReportActivity.this);
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
                DbUserData db = new DbUserData(DetailReportActivity.this);
                List<UserData> UserDataList = db.getAllUserDataList();
                for (UserData userData : UserDataList) {
                        if (!userData.getEMPLOYEE_ID().isEmpty()) {
                                is_admin = userData.getIS_ADMIN();
                                employee_id = userData.getEMPLOYEE_ID();
                                employee_name = userData.getEMPLOYEE_NAME();
                                atasan_name = userData.getATASAN_NAME();
                                atasan_id = userData.getATASAN_ID();
                                //Toast.makeText(this, atasan_name, Toast.LENGTH_LONG).show();


                        }
                }
                inputForms = new ArrayList<InputForm>();

                inputForm = new InputForm();
                inputForm.setICON(getResources().getDrawable(R.drawable.ic_date_range));
                inputForm.setTYPE("TIMEPICKER");
                inputForm.setLABEL("Dari Jam");
                inputForm.setVALUE(time);
                inputForms.add(inputForm);

                inputForm = new InputForm();
                inputForm.setICON(getResources().getDrawable(R.drawable.ic_date_range));
                inputForm.setTYPE("TIMEPICKER");
                inputForm.setLABEL("Sampai Jam");
                inputForm.setVALUE(time);
                inputForms.add(inputForm);


                inputForm = new InputForm();
                inputForm.setICON(getResources().getDrawable(R.drawable.ic_person));
                inputForm.setTYPE("EMPLOYEE");
                inputForm.setLABEL("Diajukan Oleh");
                inputForm.setVALUE(employee_name);
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
        @Override
        public void onMapReady(GoogleMap map) {
                mMap = map;
                updateMapType();
                mMap.setTrafficEnabled(true);
                //mMap.addMarker(new MarkerOptions().position(new LatLng(-6.9638597, 107.6316968)).title("PT Arkamaya"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 10));
                mMap.setOnMyLocationButtonClickListener(this);
                enableMyLocation();
        }
       // @Override
        //public void onMapReady(GoogleMap map) {
             //   mMap = map;

              //  mMap.setOnMyLocationButtonClickListener(this);
               // enableMyLocation();
       // }

        /**
         * Enables the My Location layer if the fine location permission has been granted.
         */
        private void enableMyLocation() {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                        // Permission to access the location is missing.
                        PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                                Manifest.permission.ACCESS_FINE_LOCATION, true);
                } else if (mMap != null) {
                        // Access to the location has been granted to the app.
                        mMap.setMyLocationEnabled(true);
                }
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateMapType();
        }

        private void updateMapType() {
                // No toast because this can also be called by the Android framework in onResume() at which
                // point mMap may not be ready yet.
                if (mMap == null) {
                        return;
                }

                String layerName = ((String) mSpinner.getSelectedItem());
                if (layerName.equals(getString(R.string.normal))) {
                        mMap.setMapType(MAP_TYPE_NORMAL);
                } else if (layerName.equals(getString(R.string.hybrid))) {
                        mMap.setMapType(MAP_TYPE_HYBRID);


                } else if (layerName.equals(getString(R.string.satellite))) {
                        mMap.setMapType(MAP_TYPE_SATELLITE);
                } else if (layerName.equals(getString(R.string.terrain))) {
                        mMap.setMapType(MAP_TYPE_TERRAIN);

                } else {
                        Log.i("LDA", "Error setting layer with name " + layerName);
                }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing.
        }
        @Override
        public boolean onMyLocationButtonClick() {
                //Toast.makeText(this, "Lokasi Saya", Toast.LENGTH_SHORT).show();
                Toast.makeText(DetailReportActivity.this, "Address : "+addressLine, Toast.LENGTH_SHORT).show();
                // Return false so that we don't consume the event and the default behavior still occurs
                // (the camera animates to the user's current position).
                return false;
        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
                if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
                        return;
                }

                if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                        // Enable the my location layer if the permission has been granted.
                        enableMyLocation();
                } else {
                        // Display the missing permission error dialog when the fragments resume.
                        mPermissionDenied = true;
                }
        }

        @Override
        protected void onResumeFragments() {
                super.onResumeFragments();
                if (mPermissionDenied) {
                        // Permission was not granted, display error dialog.
                        showMissingPermissionError();
                        mPermissionDenied = false;
                }
        }

        /**
         * Displays a dialog with error message explaining that the location permission is missing.
         */
        private void showMissingPermissionError() {
                PermissionUtils.PermissionDeniedDialog
                        .newInstance(true).show(getSupportFragmentManager(), "dialog");
        }

        @Override
        public void onLocationChanged(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                mMap.animateCamera(cameraUpdate);
                locationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
}

