package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class doc_review extends Activity {
    Button submit;
    EditText date;
    EditText review;
    EditText docname;
    EditText lastname;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_review);

        submit = (Button) findViewById(R.id.submit);
        date = (EditText)findViewById(R.id.date);
        review = (EditText)findViewById(R.id.reviewtext);
        docname = (EditText)findViewById(R.id.docname);
        lastname= (EditText)findViewById(R.id.docnamelast);
        db = new DatabaseHelper(getApplicationContext());
        Date today = new Date();
        date.setText(DateFormat.getDateInstance().format(today));
        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();}

    private void setup() {


        submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String fname = docname.getText().toString();
                String lname = lastname.getText().toString();
                String dat = date.getText().toString();
                String txt = review.getText().toString();


                if(db.updateReview(fname,lname,dat,txt)) {
                    Toast.makeText(doc_review.this, "success!", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(doc_review.this, "failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
