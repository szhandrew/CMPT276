package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Pa_apManage extends Activity {


    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper myDb;
    Cursor res;

    Spinner dc_list;
    Button btn_doc_selec;
    Button btn_mySchedule;
    TextView tv_doc;

    String pa_username,pa_MyAppoint;
    String dc_username;

    /*int getSize(){
        myDb = new DatabaseHelper(getApplicationContext());
        int size=myDb.getCountRaw();
        myDb.close();
        return size;
    }*/

    List<String> doc_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pa_ap_manage);
        getDoc();


        Intent pa_appManage=getIntent();
        pa_username=pa_appManage.getStringExtra("Pa_uname");
        btn_mySchedule=(Button)findViewById(R.id.btn_MySche) ;
        btn_doc_selec = (Button) findViewById(R.id.btn_dcSel);
        tv_doc = (TextView) findViewById(R.id.tv_dc_selected);
        dc_list = (Spinner) findViewById(R.id.Spi_doc_sel);
        dc_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doc_list));
        dc_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String str = doc_list.get(position);
                tv_doc.setText("Your selection is: \n" + str);
                btn_doc_selec.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent doc_sel = new Intent(Pa_apManage.this, pa_timePick.class);
                                doc_sel.putExtra("doc_name", str);
                                doc_sel.putExtra("pa_uname", pa_username);
                                startActivity(doc_sel);
                            }
                        }
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Button_MySche();
    }


    public void Button_selDc() {
        btn_doc_selec.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent doc_sel = new Intent(Pa_apManage.this, pa_timePick.class);
                        startActivity(doc_sel);
                    }
                }
        );
    }

    void getDoc() {
        myDb = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = myDb.getReadableDatabase();
        res = myDb.getDc_info(sqLiteDatabase);
        int i = 0;
        while (res.moveToNext()) {
            doc_list.add(res.getString(2));
        }
        //doc_list[2]="dw";
    }

    public void Button_MySche()
    {
        btn_mySchedule.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pa_MyAppoint=myDb.getMyAppointStatus(pa_username);
                        dc_username=myDb.getMyNext_Appoint_Doc(pa_username);
                        showMyAppoint_Dialog("YOUR APPOINTMENT:", pa_MyAppoint + " by "+ dc_username);
                    }
                }
        );
    }

    public void showMyAppoint_Dialog(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setNegativeButton("CANCEL APPOINTMENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pa_MyAppoint=myDb.getMyAppointStatus(pa_username);
                if(!pa_MyAppoint.equals("NO APPOINTMENT")){
                    showCancel_Dialog("ARE YOU SURE TO CANCEL:",pa_MyAppoint);
                    }

                else{
                    dialog.cancel();
                    Toast.makeText(Pa_apManage.this, "You don't have an appointment!", Toast.LENGTH_SHORT).show();}
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    public void showCancel_Dialog(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("YES CANCEL NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pa_MyAppoint=myDb.getMyAppointStatus(pa_username);
                dc_username=myDb.getMyNext_Appoint_Doc(pa_username);
                myDb.updateSchedule(pa_MyAppoint, dc_username, "available");
                myDb.updateMyAppoint("NO APPOINTMENT", pa_username);
                myDb.updateMyAppoint_Doc(pa_username,"NO DOCTOR");
                Toast.makeText(Pa_apManage.this, "You have successfully cancelled your appointment. " +
                        "Now you can make a new appointment", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

