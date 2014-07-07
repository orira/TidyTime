package com.baws.tidytime.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by wadereweti on 7/07/14.
 */
@Table(name = "Child")
public class Child extends Model {
    @Column(name = "FirstName")
    public String firstName;

    @Column(name = "ProfilePicture")
    public String profilePicture;

    public List<Chore> chores() {
        return getMany(Chore.class, "Child");
    }

    public static List<Child> getAll() {
        return new Select()
                .from(Child.class)
                .execute();

    }

    public static Child create(int position) {

        Child child = new Child();
        switch (position) {
            case 0:
                child.firstName = "Tayla-Paige";
                break;
            case 1:
                child.firstName = "Kauri";
                break;
            case 2:
                child.firstName = "Nevaeh";
                break;
        }

        child.save();
        
        return child;
    }
}
