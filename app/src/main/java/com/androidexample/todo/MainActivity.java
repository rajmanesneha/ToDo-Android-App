package com.androidexample.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    Helper db;
    ArrayList<Task> AllTasks;
    int status;
    EditText task,desc;
    String Date;
    ListView listview;
    CustomAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new Helper(this);
        AllTasks=db.getallTasks();
        Log.i("TAG", "Obj Created");

        myAdapter=new CustomAdapter(getApplicationContext(),R.layout.task_details,AllTasks);
        listview= (ListView) findViewById(R.id.ToDoList);
        listview.setAdapter(myAdapter);
        //myAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
       /* Collections.sort(AllTasks, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });*/
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("TAG", "PostResu called");
        AllTasks=db.getallTasks();
        myAdapter=new CustomAdapter(getApplicationContext(),R.layout.task_details,AllTasks);
        listview= (ListView) findViewById(R.id.ToDoList);
        listview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInf=getMenuInflater();
        menuInf.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addTask) {
            Log.i("TAG","Call Task Input ");
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.input_task, null);
            task= (EditText) alertLayout.findViewById(R.id.textTask);
            desc= (EditText) alertLayout.findViewById(R.id.textDesc);
            final DatePicker date= (DatePicker) alertLayout.findViewById(R.id.datePicker);
            Log.i("TAG","Date:"+date.toString());
            Log.i("TAG","DATA:"+task.getText().toString()+desc.getText().toString()+Date);
            AlertDialog.Builder addTaskDialog=new AlertDialog.Builder(this);
            addTaskDialog.setView(alertLayout);
            addTaskDialog.setCancelable(false);
            addTaskDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int month=date.getMonth()+1;
                    Date=month+ "/" + date.getDayOfMonth() + "/" + date.getYear();
                    Log.i("TAG", "Save Clicked");
                    db.insertTask(task.getText().toString(), desc.getText().toString(), Date, status);
                    dialog.dismiss();
                    //myAdapter.notifyDataSetChanged();
                    AllTasks=db.getallTasks();
                    myAdapter=new CustomAdapter(getApplicationContext(),R.layout.task_details,AllTasks);
                    listview= (ListView) findViewById(R.id.ToDoList);
                    Log.i("TAG", "List:" + listview.toString());
                    listview.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            });
            addTaskDialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("TAG", "Cancle Clicked");
                }
            });
            AlertDialog alertDialog=addTaskDialog.create();
            alertDialog.show();

        }else
        if (id== R.id.action_showCompleted){
            Log.i("TAG","Inside Show Completed");
            Intent intent=new Intent(this,completeTask.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String task,desc,date;
        final int currentId;
        Log.i("TAG", "TASX DONE");
        Task currentTask=AllTasks.get(position);
        currentId=currentTask.getId();
        if(currentTask.getStatus()==0) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.input_task, null);
            task = (EditText) alertLayout.findViewById(R.id.textTask);
            desc = (EditText) alertLayout.findViewById(R.id.textDesc);
            final DatePicker date = (DatePicker) alertLayout.findViewById(R.id.datePicker);
            AlertDialog.Builder addTaskDialog = new AlertDialog.Builder(this);
            addTaskDialog.setView(alertLayout);
            addTaskDialog.setCancelable(false);
            addTaskDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("TAG", "Save Clicked");
                    int month = date.getMonth() + 1;
                    Date = month + "/" + date.getDayOfMonth() + "/" + date.getYear();
                    db.updateTask(currentId, task.getText().toString(), desc.getText().toString(), Date, status);
                    //dialog.dismiss();
                    //to reflect the changes applied on Dialog box to Task List.
                    AllTasks = db.getallTasks();
                    myAdapter = new CustomAdapter(getApplicationContext(), R.layout.task_details, AllTasks);
                    listview = (ListView) findViewById(R.id.ToDoList);
                    Log.i("TAG", "List:" + listview.toString());
                    listview.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            });
            addTaskDialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("TAG", "Cancle Clicked");
                }
            });
            AlertDialog alertDialog = addTaskDialog.create();
            alertDialog.show();
            task.setText(currentTask.getTask().toString());
            desc.setText(currentTask.getDesc().toString());
        }else
            Toast.makeText(getApplicationContext(), "TASK IS ALREADY COMPLETED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int Id;
        Task task;
        int taskStatus;
        task=AllTasks.get(position);
        taskStatus=task.getStatus();
        Id=task.getId();
        ImageView image= (ImageView) view.findViewById(R.id.imageStatus);
        Log.i   ("TAG", "Status: " + taskStatus);
        if(taskStatus==0) {
            Log.i("Tag","status is zero");
            db.updateStatus(Id, image);
            image.setImageResource(R.drawable.thumsup_dark);
        }else
            Toast.makeText(getApplicationContext(), "TASK IS ALREADY COMPLETED", Toast.LENGTH_SHORT).show();
        Log.i("Tag:","Status updated is"+task.getStatus());
        Toast.makeText(getApplicationContext(),task.getTask()+" StATUS:"+task.getStatus(),Toast.LENGTH_SHORT).show();
        return true;
    }



}
