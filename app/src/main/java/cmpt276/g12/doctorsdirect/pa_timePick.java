package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class pa_timePick extends Activity {

    Button  Confirm_time;
    String dc_status,time_availability,pa_username;
    String pa_MyAppoint;
    String username = "22";
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper myDb;
    Cursor res;
    TextView tv_status;
    TextView tv_docname;
    TextView tv_feedback;
    String dc_name;
    Spinner spi_day;
    Spinner spi_time;
    String text_day,text_time, selected_time;
    List<String> sta_list = new ArrayList<String>();

    //newly added stuff
    ArrayAdapter<CharSequence> dayAdapter;
    ArrayAdapter<CharSequence> timeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pa_time_pick);
        //---------------------------------------------------------
        Intent doc_sel=getIntent();

        dc_name=doc_sel.getStringExtra("doc_name");
        pa_username=doc_sel.getStringExtra("pa_uname");
        tv_docname=(TextView)findViewById(R.id.tv_docName);
        tv_docname.setText("Doctor "+ dc_name+"'s Schedule");

        tv_status = (TextView) findViewById(R.id.status);

        Confirm_time=(Button) findViewById(R.id.btn_TimeConf);


        spi_day=(Spinner)findViewById(R.id.spi_dayPick);
        spi_time=(Spinner)findViewById(R.id.spi_timePick);


        //new stuff
        setSpinnerAdapter();

        myDb = new DatabaseHelper(this);

        btn_TimeConfirm();

        tv_feedback=(TextView)findViewById(R.id.tv_feedback);
    }

    public void setSpinnerAdapter() {
        dayAdapter = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_day.setAdapter(dayAdapter);

        spi_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_SHORT).show();
                if(getSpinnerValues())
                {
                    getStatus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeAdapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_time.setAdapter(timeAdapter);

        spi_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_SHORT).show();
                if(getSpinnerValues())
                {
                    getStatus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private boolean getSpinnerValues()
    {
        text_day=spi_day.getSelectedItem().toString();
        text_time=spi_time.getSelectedItem().toString();
        selected_time=text_day+text_time;
        if(text_day.equals("Day") || text_time.equals("Time")){
            return false;
        }
        else{
            return true;
        }
    }



    public void btn_TimeConfirm()
    {

        Confirm_time.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getSpinnerValues()) {
                            time_availability = dc_status = myDb.getStatus(selected_time, dc_name);

                            if (time_availability.equals("available")) {
                                pa_MyAppoint=myDb.getMyAppointStatus(pa_username);

                                if(pa_MyAppoint.equals("NO APPOINTMENT")) {
                                    myDb.updateSchedule(selected_time, dc_name, "occupied by " + pa_username);
                                    myDb.updateMyAppoint(selected_time, pa_username);
                                    myDb.updateMyAppoint_Doc(pa_username,dc_name);

                                    finish();
                                    Toast.makeText(pa_timePick.this, "Appointment confirmed successfully!", Toast.LENGTH_SHORT).show();
                                }

                                else
                                    Toast.makeText(pa_timePick.this, "You already had an appointment", Toast.LENGTH_SHORT).show();
                            }
                            else if(text_day.equals("Day") || text_time.equals("Time")){
                                Toast.makeText(pa_timePick.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            }

                            else
                                Toast.makeText(pa_timePick.this, "You cannot register at this time:(", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    void getStatus()
    {
        dc_status= myDb.getStatus(selected_time, dc_name);


        tv_status.setText(dc_status);


        if(dc_status.equals("available"))
            tv_feedback.setText("You can register at this time");
        else
            tv_feedback.setText("Please select other time");
    }

}
