package com.androidexample.todo;

/**
 * Created by Sneha on 10/24/2015.
 */
public class Task {
String task,desc,date;
    int status;
    int id;
    Task(Integer id,String task,String desc,String date,int status){
        this.id=id;
        this.task=task;
        this.desc=desc;
        this.date=date;
        this.status=status;

    }
    public int getId(){return id;}
    public String getTask() {
        return task;
    }

    public String getDesc() {
        return desc;
    }
    public int getStatus(){ return status;}

    public String getDate() {
        return date;
    }
}

