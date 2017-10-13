package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadReview extends Activity {

    DatabaseHelper dbhlpr;
    Cursor res;
    EditText fname;
    EditText lname;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);
        dbhlpr  = new DatabaseHelper(this);
        fname= (EditText) findViewById(R.id.fname);
        lname= (EditText) findViewById(R.id.lname);
        data  = (TextView)findViewById(R.id.text_review);
        res = dbhlpr.getDc_info(dbhlpr.getReadableDatabase());
        if(res.moveToFirst()){
            String dat = res.getString(DatabaseHelper.DATA_COL);
            fname.setText(res.getString(DatabaseHelper.FNAME_COL));
            lname.setText(res.getString(DatabaseHelper.LNAME_COL));
            data.setText(dat);
        }

        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        res.close();
        dbhlpr.close();
    }

    private void setup() {
        Button write = (Button) findViewById(R.id.btn_review);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent write_review = new Intent(ReadReview.this,doc_review.class);
                startActivity(write_review);
                finish();
            }
        });
        Button next = (Button)findViewById(R.id.btnnxt);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(res.isLast()){
                    Toast.makeText(ReadReview.this,"no more entries",Toast.LENGTH_SHORT).show();
                }else if(res.moveToNext()){
                    fname.setText(res.getString(DatabaseHelper.FNAME_COL));
                    lname.setText(res.getString(DatabaseHelper.LNAME_COL));
                    data.setText(res.getString(DatabaseHelper.DATA_COL));
                }
            }
        });
        Button prev = (Button)findViewById(R.id.btnprev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(res.isFirst()){
                    Toast.makeText(ReadReview.this,"first entry already",Toast.LENGTH_SHORT).show();
                }else if(res.moveToPrevious()){
                    fname.setText(res.getString(DatabaseHelper.FNAME_COL));
                    lname.setText(res.getString(DatabaseHelper.LNAME_COL));
                    data.setText(res.getString(DatabaseHelper.DATA_COL));
                }
            }
        });
        Button search = (Button)findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByName(res,fname.getText().toString(),lname.getText().toString());
                fname.setText(res.getString(DatabaseHelper.FNAME_COL));
                lname.setText(res.getString(DatabaseHelper.LNAME_COL));
                data.setText(res.getString(DatabaseHelper.DATA_COL));
            }

            private void searchByName(Cursor res, String fname, String lname) {
                if(res.moveToFirst()){
                    do{
                        if(fname.equals(res.getString(DatabaseHelper.FNAME_COL))&&lname.equals(res.getString(DatabaseHelper.LNAME_COL))){
                            return;
                        }
                    }while(res.moveToNext());
                }
                res.moveToFirst();
                Toast.makeText(ReadReview.this,"Doctor not found",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
