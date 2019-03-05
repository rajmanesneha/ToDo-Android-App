package com.androidexample.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Sneha on 10/22/2015.
 */
class CustomAdapter extends ArrayAdapter<Task> {
    Context context;
    ArrayList<Task> objects;

    public CustomAdapter(Context baseContext, int task_details, ArrayList<Task> objects) {
        super(baseContext, task_details);
        this.context = baseContext;
        this.objects = objects;
        Log.i("TAG", "OBJ:" + objects);
    }

    @Override
    public Task getItem(int position) {
        Log.i("TAG", "GetItem:" + objects.get(position));
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("TAG", "Inside GetView");
        int status;
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.task_details, null);
        Log.i("TAG", "ConvertView:" + convertView);
        TextView textTask = (TextView) convertView.findViewById(R.id.textTask);
        TextView textDesc = (TextView) convertView.findViewById(R.id.textDesc);
        TextView textDate = (TextView) convertView.findViewById(R.id.textDate);
        ImageView imgStatus= (ImageView) convertView.findViewById(R.id.imageStatus);
        status=getItem(position).getStatus();
        if(status!=0){
            imgStatus.setImageResource(R.drawable.thumsup_dark);
        }
        Log.i("TAG", "Task:" + objects.get(position).getTask());
        textTask.setText(objects.get(position).getTask());
        textDesc.setText(objects.get(position).getDesc());
        textDate.setText((CharSequence) objects.get(position).getDate());
        return convertView;
    }

    public ArrayList<Task> getObjects() {
        return objects;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        ArrayList<Task> AllTasks;
        Log.i("TAG", "Inside custom notifydatasetchng");
        AllTasks=getObjects();
        Collections.sort(AllTasks, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });
    }
}