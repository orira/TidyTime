package com.baws.tidytime.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wadereweti on 7/07/14.
 */
@Table(name = "Chore")
public class Chore extends Model{
    @Column(name = "Description")
    public String description;

    @Column(name = "AssignedDate")
    public Date assignedDate;

    @Column(name = "DueDate")
    public Date dueDate;

    @Column(name = "Complete")
    public boolean complete;

    @Column(name = "Child")
    public Child child;

    public static void createChores(Child child, int amount) {
        if (amount == 0) {
            amount = 1;
        }
        for (int i = 0; i < amount * 4; i++) {
            Chore chore = new Chore();
            chore.description = "Wash Dishes";
            chore.assignedDate = new Date();
            chore.dueDate = getDate();
            chore.complete = false;
            chore.child = child;

            chore.save();
        }
    }

    private static Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        try {
            date = sdf.parse("21/08/2014");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}



