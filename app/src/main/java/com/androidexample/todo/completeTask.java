package com.androidexample.todo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Sneha on 10/27/2015.
 */
public class
completeTask extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    Helper db;
    ArrayList<Task> completedTask;
    ArrayList<Task> AllTasks;
    CustomAdapter myAdapter;
   // ArrayAdapter<Task> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("TAG", "OnCreate COmplete Task");
        db=new Helper(this);
        completedTask = db.getcOMPLETEDTasks();

       // ArrayAdapter<Task> myAdapter;

        myAdapter = new CustomAdapter(getApplicationContext(), R.layout.task_details, completedTask);
        ListView listview = (ListView) findViewById(R.id.ToDoList);
        Log.i("TAG", "setAdapter for ompleted Task:");
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Task currentTask;
        currentTask=completedTask.get(position);
        int Id=currentTask.getId();
        Toast.makeText(getApplicationContext(),"Task Will be Deleted",Toast.LENGTH_LONG).show();
        db.deleteTask(Id);
        //LIST  SHOULD GET REFRESHED
        completedTask=db.getcOMPLETEDTasks();
        myAdapter=new CustomAdapter(getApplicationContext(),R.layout.task_details,completedTask);
        ListView listview= (ListView) findViewById(R.id.ToDoList);
        listview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        return false;
    }
}
