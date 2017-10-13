package cmpt276.g12.doctorsdirect;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PatientRecord extends Activity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);
        db = new DatabaseHelper(this);
       getPatientRecord();
    }

    private void getPatientRecord() {
        SQLiteDatabase mydb = db.getReadableDatabase();
        Cursor res = db.getPa_info(mydb);
        TableLayout table = (TableLayout) findViewById(R.id.recordLayout);

        TableRow Header= new TableRow(this);
        table.addView(Header);
        TextView firstName = new TextView(this);TextView lastName = new TextView(this);TextView username = new TextView(this);TextView password = new TextView(this);
        firstName.setText(" First Name ");lastName.setText(" Last Name ");username.setText(" Username ");password.setText(" Password ");
        firstName.setTypeface(null, Typeface.BOLD);lastName.setTypeface(null, Typeface.BOLD);username.setTypeface(null, Typeface.BOLD);password.setTypeface(null, Typeface.BOLD);
        firstName.setGravity(Gravity.CENTER);lastName.setGravity(Gravity.CENTER);username.setGravity(Gravity.CENTER);password.setGravity(Gravity.CENTER);
        Header.addView(firstName);Header.addView(lastName);Header.addView(username);Header.addView(password);



        for (int row = 0; row < res.getCount(); row++) {
            while (res.moveToNext()) {
                TableRow tableRow = new TableRow(this);
                //tableRow.setBackgroundColor(Color.GRAY);


                table.addView(tableRow);
                for (int column = 0; column < res.getColumnCount(); column++) {
                    TextView textView = new TextView(this);
                /*
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
*/
                    textView.setText(" "+res.getString(column)+" ");
                    textView.setGravity(Gravity.CENTER);

                    tableRow.addView(textView);


                }
            }
        }
    }


}
