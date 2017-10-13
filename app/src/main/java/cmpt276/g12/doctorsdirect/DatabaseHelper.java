package cmpt276.g12.doctorsdirect;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by ShanZhaohui on 2017-03-15.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="clinic.db";
    //---------------------------------------------------------Patient Table
    public static final String TABLE_NAME1="patient_table";
    public static final String PA_COL_1="f_name";
    public static final String PA_COL_2="l_name";
    public static final String PA_COL_3="username";
    public static final String PA_COL_4="p_word";
    public static final String PA_COL_5="my_NextAppoint";
    public static final String PA_COL_6="Doc_NextAppoint";

    //----------------------------------------------------------Doctor Table
    public static final String TABLE_NAME2="doctor_table";
    private static final String DC_COL_1="f_name";
    private static final String DC_COL_2="l_name";
    private static final String DC_COL_3="username";
    private static final String DC_COL_4="p_word";
    public static final String DC_COL_5="review_data";
    public static final String day[] = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
    public static final String time[] = {"10am", "12pm","2pm"};


    public static final int DATA_COL = 4;
    public static final int FNAME_COL=0;
    public static final int LNAME_COL=1;
    public static final int UNAME_COL=2;
    public static final int PW_COL=3;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1+ "(f_name TEXT, l_name TEXT, username TEXT PRIMARY KEY, p_word TEXT, my_NextAppoint TEXT, Doc_NextAppoint TEXT)");
        db.execSQL("create table " + TABLE_NAME2+ "(f_name TEXT, l_name TEXT, username TEXT PRIMARY KEY, p_word TEXT,review_data TEXT, " +
                "Monday10am TEXT, Monday12pm TEXT, Monday2pm TEXT, " +
                "Tuesday10am TEXT, Tuesday12pm TEXT, Tuesday2pm TEXT, " +
                "Wednesday10am TEXT, Wednesday12pm TEXT, Wednesday2pm TEXT, " +
                "Thursday10am TEXT, Thursday12pm TEXT, Thursday2pm TEXT, " +
                "Friday10am TEXT, Friday12pm TEXT, Friday2pm TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    //---------------------------------------------------------------Operation on PA_table
    public boolean PA_insertData(String f_name, String l_name, String username, String p_word)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PA_COL_1, f_name);
        contentValues.put(PA_COL_2, l_name);
        contentValues.put(PA_COL_3, username);
        contentValues.put(PA_COL_4, p_word);
        contentValues.put(PA_COL_5, "NO APPOINTMENT");
        contentValues.put(PA_COL_6,"NO DOCTOR");
        long result = db.insert(TABLE_NAME1,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getPa_info(SQLiteDatabase db)
    {
        Cursor res;
        String[] projections = {PA_COL_1, PA_COL_2, PA_COL_3, PA_COL_4};
        res = db.query(TABLE_NAME1, projections, null, null,null,null,null);
        return res;
    }

    //------------------------------------------------------------------Operation on DC_table
    public boolean DC_insertData(String f_name, String l_name, String username, String p_word)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DC_COL_1, f_name);
        contentValues.put(DC_COL_2, l_name);
        contentValues.put(DC_COL_3, username);
        contentValues.put(DC_COL_4, p_word);
        contentValues.put(DC_COL_5, "");
        String dayTime;

        for(int i=0;i<day.length;i++)
            for(int j =0; j<time.length;j++)
            {
                dayTime =day[i]+time[j];
                contentValues.put(dayTime, "info unavailable");
            }

        long result = db.insert(TABLE_NAME2,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDc_info(SQLiteDatabase db)
    {
        Cursor res;
        String[] projections = {DC_COL_1, DC_COL_2, DC_COL_3, DC_COL_4, DC_COL_5};
        res = db.query(TABLE_NAME2, projections, null, null,null,null,null);
        return res;
    }

    public Cursor searchByName(String f_name,String l_name){
        Cursor res = getDc_info(this.getReadableDatabase());

        while(res.moveToNext()){
            if(f_name.equals(res.getString(FNAME_COL))&&l_name.equals(res.getString(LNAME_COL))){
                return res;
            }
        }
        return null;
    }

    public boolean updateReview(String fname, String lname, String date, String txt){
        Cursor res = searchByName(fname,lname);
        if(res != null){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DC_COL_1,res.getString(FNAME_COL));
            values.put(DC_COL_2,res.getString(LNAME_COL));
            values.put(DC_COL_3,res.getString(UNAME_COL));
            values.put(DC_COL_4,res.getString(PW_COL));
            values.put(DC_COL_5,res.getString(DATA_COL)+date+"\n"+txt+"\n\n");
            String[] arg = {res.getString(UNAME_COL)};
            db.update(TABLE_NAME2,values,DC_COL_3+"=? ",arg);
            res.close();
            return true;
        }
        return false;
    }

    public boolean updateSchedule(String time, String username,String update_availability)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(time, update_availability);


        int val = db.update(TABLE_NAME2, contentValues, "username = ?", new String[]{username});
        if(val>0) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean updateMyAppoint(String time, String pa_username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PA_COL_5,time);

        int val = db.update(TABLE_NAME1, contentValues, "username = ?", new String[]{pa_username});
        if(val>0) {
            return true;
        }
        else
        {
            return false;
        }
    }


    public boolean updateMyAppoint_Doc(String pa_username, String dc_username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PA_COL_6,dc_username);
        int val = db.update(TABLE_NAME1, contentValues, "username = ?", new String[]{pa_username});
        if(val>0) {
            return true;
        }
        else
        {
            return false;
        }
    }


    public String getStatus(String selected_time, String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] time = {selected_time};
        String[] dc_username ={username};
        Cursor cursor = db.query(TABLE_NAME2, time, "username=?",
                dc_username,null,null,null);

        String status;

        if (cursor != null) {
            cursor.moveToFirst();
            status = cursor.getString(0);

        }
        else {
            status = "please pick another time";
        }

        return status;

    }

public String getMyAppointStatus(String username)
{
    SQLiteDatabase db = this.getReadableDatabase();
    String[] pa_username={username};
    String[] myAppoint={"my_NextAppoint"};
    Cursor cursor = db.query(TABLE_NAME1, myAppoint, "username=?", pa_username,null,null,null);
    String status;

    if (cursor != null) {
        cursor.moveToFirst();
        status = cursor.getString(0);

    }
    else {
        status = "not available";
    }

    return status;
}

public String getMyNext_Appoint_Doc(String username)
{
    SQLiteDatabase db = this.getReadableDatabase();
    String[] pa_username={username};
    String[] myAppointDoc={"Doc_NextAppoint"};
    Cursor cursor = db.query(TABLE_NAME1, myAppointDoc, "username=?", pa_username,null,null,null);
    String DocName;

    if (cursor != null) {
        cursor.moveToFirst();
        DocName = cursor.getString(0);

    }
    else {
        DocName = "null";
    }

    return DocName;
}

    public int getCountRaw() {
        String countQuery = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getDc_schedule(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 +" WHERE username = " + "'" +username +"'";
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public String getReview(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "";
        String query = "SELECT review_data FROM " + TABLE_NAME2 + " WHERE username =  " + "'"+username + "'";
        Cursor res = db.rawQuery(query, null);
        if (res.moveToNext())
        {
            result = res.getString(res.getColumnIndex("review_data"));
            if(result.isEmpty()){
                result = "No result found...";
            }
            return result;
        }
        return "Error";
    }

    boolean  clearFeedback(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("review_data", "");
        int val = db.update(TABLE_NAME2, contentValues, "username = ?", new String[]{username});
        if (val > 0) {
            return true;
        } else {
            return false;
        }
    }




}