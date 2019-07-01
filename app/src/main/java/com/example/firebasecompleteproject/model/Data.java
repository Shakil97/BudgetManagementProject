package com.example.firebasecompleteproject.model;

public class Data {

    String title;
    String Description;
    String Budget;
    String id;
    String date;


    public Data(){


    }

    public Data(String title, String description, String budget, String id, String date) {
        this.title = title;
        Description = description;
        Budget = budget;
        this.id = id;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
