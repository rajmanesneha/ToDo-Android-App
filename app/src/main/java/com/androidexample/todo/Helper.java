package com.androidexample.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sneha on 10/21/2015.
 */
public class Helper  extends SQLiteOpenHelper {

    public static final  String DB_NAME="TODO_DB";
    public static final  int DB_VERSION=1;
    private static final String TABLE_NAME = "TASK";
    private static final String COL_ID = "ID";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_DATE = "DATE";
    private static final String COL_STATUS = "STATUS";
    private SQLiteDatabase mydb;

    String task,desc,date;
    int id;
    int status;

    public Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.i("TAG", "Inside Helper COnst");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_Table="CREATE TABLE " + TABLE_NAME+"("+ COL_ID + " INTEGER PRIMARY KEY,"
                + COL_TITLE + " STRING," +COL_DESCRIPTION + " STRING," + COL_DATE +" STRING,"+COL_STATUS+" INTEGER)";
        Log.i("TAG", "query:" + create_Table);
         db.execSQL(create_Table);
        Log.i("TAG","table created successfully");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertTask(String task,String desc,String date,int status){
        //status=false; it has initialized in MainActivity Class where it has been called;
        Log.i("TAG","Insert Task");
        mydb= getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL_TITLE,task);
        value.put(COL_DESCRIPTION,desc);
        value.put(COL_DATE,date);
        value.put(COL_STATUS,status);
        mydb.insert(TABLE_NAME,null,value);
        mydb.close();
        Log.i("TAG","Inserted Successfully");
    }

    public ArrayList<Task> getallTasks(){
        String selectQuery;
        ArrayList<Task> Tasks=new ArrayList<>();
        mydb=getReadableDatabase();
        selectQuery= "SELECT * FROM " +  TABLE_NAME + ";";
        Cursor cursor=mydb.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                id=cursor.getInt(0);
                task=cursor.getString(1);
                desc=cursor.getString(2);
                date=cursor.getString(3);
                status=cursor.getInt(4);
                //status= Boolean.parseBoolean(cursor.getString(4));
                Task NewTask=new Task(id,task,desc,date,status);
                Tasks.add(NewTask); //Fetch dat afrom Internal Storage to RAM
            }while (cursor.moveToNext());
        }
        return Tasks;
    }

    public ArrayList<Task> getcOMPLETEDTasks(){
        String selectQuery;
        Log.i("TAG","GET COMP TASK ");
        ArrayList<Task> Tasks=new ArrayList<>();
        mydb=getReadableDatabase();
        selectQuery= "SELECT * FROM " +  TABLE_NAME + " WHERE STATUS = 1 ;";
        Log.i("TAG","QUERY: "+selectQuery);
        Cursor cursor=mydb.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Log.i("TAG","Rec Found:"+cursor.getInt(0));
                id=cursor.getInt(0);
                task=cursor.getString(1);
                desc=cursor.getString(2);
                date=cursor.getString(3);
                status=cursor.getInt(4);
                //status= Boolean.parseBoolean(cursor.getString(4));
                Task NewTask=new Task(id,task,desc,date,status);
                Tasks.add(NewTask); //Fetch dat afrom Internal Storage to RAM
            }while (cursor.moveToNext());
        }
        return Tasks;
    }
    public void deleteTask(int Id){
        mydb=getWritableDatabase();
        String deleteQuery="DELETE FROM "+TABLE_NAME + " WHERE ID="+Id +";";
        mydb.execSQL(deleteQuery);
        Log.i("TAG", deleteQuery + " Record DELeted");
    }

    public void updateStatus(int ID,ImageView image)
    {
        status=1;
        mydb=getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL_STATUS, status);
        int rowsUpdated=mydb.update(TABLE_NAME,value,COL_ID + "=?",new String[]{String.valueOf(ID)});
        Log.i("TAG", "Status UPDATED: " + rowsUpdated);
        mydb.close();
        //image.setImageResource(R.drawable.thumsup_dark);
    }

    public void updateTask(int Id,String task, String desc, String date, int status) {
        mydb=getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL_TITLE,task);
        value.put(COL_DESCRIPTION,desc);
        value.put(COL_DATE, date);
        int update=mydb.update(TABLE_NAME,value,COL_ID + "=?",new String[]{String.valueOf(Id)});
        Log.i("TAG"," UPDATED:" + update);
        mydb.close();
    }
}
