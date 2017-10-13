package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Db_select_time extends Activity {

    Button showBtn;
    Button confirmBtn;
    Spinner day;
    Spinner time;
    Spinner availability;
    String text_day;
    String text_time;
    String text_availability;
    String username;
    String selectedTime;
    public static final String weekDay[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final String weekTime[] = {"10am", "12pm", "2pm"};

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_select_time);
        confirmBtn = (Button) findViewById(R.id.confirm_btn);
        showBtn = (Button) findViewById(R.id.showDialog);
        setBtn();
        //Toast.makeText(Db_select_time.this, "oncreate working" ,Toast.LENGTH_LONG).show();
        db = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");
    }

    public void setBtn() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm();

            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }


    public void showDialog() {
        Cursor res = db.getDc_schedule(username);
        int k = 5;

        if (res.getCount() == 0) {
            showSchedule("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            for (int i = 0; i < weekDay.length; i++) {
                buffer.append(weekDay[i] + "\n\n");
                for (int j = 0; j < weekTime.length; j++) {
                    buffer.append(weekTime[j] + " - " + res.getString(k) + "\n");
                    k += 1;
                }
                buffer.append("\n\n");
            }
        }
        showSchedule("Current Schedule", buffer.toString());

    }


    private void showSchedule(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });



        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    private boolean getSpinnerValues() {
        day = (Spinner) findViewById(R.id.spinner1);
        time = (Spinner) findViewById(R.id.spinner2);
        availability = (Spinner) findViewById(R.id.spinner3);
        text_day = day.getSelectedItem().toString();
        text_time = time.getSelectedItem().toString();
        text_availability = availability.getSelectedItem().toString();
        selectedTime = text_day + text_time;
        if (text_day.equals("Day") || text_time.equals("Time") || text_availability.equals("Availability")) {
            Toast.makeText(Db_select_time.this, "Invalid input", Toast.LENGTH_LONG).show();
            return false;
        } else {
          //  Toast.makeText(Db_select_time.this, username + "selected time: " + selectedTime + text_availability, Toast.LENGTH_LONG).show();
            return true;
        }
    }


    public void update_data() {

        if (getSpinnerValues()) {
            if (db.updateSchedule(selectedTime, username, text_availability)) {
                Toast.makeText(Db_select_time.this, "Update successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Db_select_time.this, "Please Try again", Toast.LENGTH_LONG).show();

            }
        }
    }


    public void confirm() {
        if (getSpinnerValues()) {
            String str = "The selected time is " + text_day + " " + text_time + " \n" + "Update status: " + text_availability;
            showConfirmDialog("Please Confirm", str);
        }
    }


    public void showConfirmDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(Db_select_time.this, "Update cancelled", Toast.LENGTH_LONG).show();
                }
            });
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    update_data();
                    Toast.makeText(Db_select_time.this, "Update successful", Toast.LENGTH_LONG).show();
                }
            });

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

}

