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

    @Column(name = "ProfilePictureOrientation")
    public int profilePictureOrientation;

    @Column(name = "ProfileColour")
    public String profileColour;

    public List<Chore> chores() {
        return getMany(Chore.class, "Child");
    }

    public static List<Child> getAll() {
        return new Select()
                .from(Child.class)
                .execute();
    }

    public static Child get() {
        Child child = new Child();
        String profileColour;

        List<Child> children = Child.getAll();
        switch (children.size()) {
            case 0:
                profileColour = RED;
                break;
            case 1:
                profileColour = PINK;
                break;
            case 2:
                profileColour = PURPLE;
                break;
            case 3:
                profileColour = LIGHT_BLUE;
                break;
            case 4:
                profileColour = CYAN;
                break;
            case 5:
                profileColour = TEAL;
                break;
            case 6:
                profileColour = GREEN;
                break;
            case 7:
                profileColour = LIGHT_GREEN;
                break;
            case 8:
                profileColour = LIME;
                break;
            default:
                profileColour = YELLOW;
                break;
        }

        child.profileColour = profileColour;

        return child;
    }

    private static final String RED = "#e51c23";
    private static final String PINK = "#e91e63";
    private static final String PURPLE = "#9c27b0";
    private static final String LIGHT_BLUE = "#03a9f4";
    private static final String CYAN = "#00bcd4";
    private static final String TEAL = "#009688";
    private static final String GREEN = "#259b24";
    private static final String LIGHT_GREEN = "#8bc34a";
    private static final String LIME = "#cddc39";
    private static final String YELLOW = "#ffeb3b";

    /*public static Child create(int position) {

        Child child = new Child();
        switch (position) {
            case 0:
                child.firstName = "Tayla-Paige";
                child.profileColour = RED;
                break;
            case 1:
                child.firstName = "Kauri";
                child.profileColour = BLUE;
                break;
            case 2:
                child.firstName = "Nevaeh";
                child.profileColour = GREEN;
                break;
        }

        child.save();

        return child;
    }*/
}
