package com.baws.tidytime.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
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

    @Column(name = "ProfileColourTranslucent")
    public String profileColourTranslucent;

    public List<Chore> chores() {
        return getMany(Chore.class, "Child");
    }

    public List<Chore> assignedChores() {
        List<Chore> assignedChores = new ArrayList<Chore>();
        for (Chore chore : getMany(Chore.class, "Child")) {
            if (!chore.complete) {
                assignedChores.add(chore);
            }
        }

        return assignedChores;
    }

    public static List<Child> getAll() {
        return new Select()
                .from(Child.class)
                .execute();
    }

    public static Child get() {
        Child child = new Child();
        String profileColour;
        String profileColourTranslucent;

        List<Child> children = Child.getAll();
        switch (children.size()) {
            case 0:
                profileColour = RED;
                profileColourTranslucent = RED_TRANSLUCENT;
                break;
            case 1:
                profileColour = YELLOW;
                profileColourTranslucent = YELLOW_TRANSLUCENT;
                break;
            case 2:
                profileColour = PURPLE;
                profileColourTranslucent = PURPLE_TRANSLUCENT;
                break;
            case 3:
                profileColour = LIGHT_BLUE;
                profileColourTranslucent = LIGHT_BLUE_TRANSLUCENT;
                break;
            case 4:
                profileColour = CYAN;
                profileColourTranslucent = CYAN_TRANSLUCENT;
                break;
            case 5:
                profileColour = TEAL;
                profileColourTranslucent = TEAL_TRANSLUCENT;
                break;
            case 6:
                profileColour = GREEN;
                profileColourTranslucent = GREEN_TRANSLUCENT;
                break;
            case 7:
                profileColour = LIGHT_GREEN;
                profileColourTranslucent = LIGHT_GREEN_TRANSLUCENT;
                break;
            case 8:
                profileColour = LIME;
                profileColourTranslucent = LIME_TRANSLUCENT;
                break;
            default:
                profileColour = PINK;
                profileColourTranslucent = PINK_TRANSLUCENT;
                break;
        }

        child.profileColour = profileColour;
        child.profileColourTranslucent = profileColourTranslucent;

        return child;
    }

    private static final String OPACITY = "#7f";
    private static final String RED = "#e51c23";
    private static final String RED_TRANSLUCENT = OPACITY + "e51c23";
    private static final String PINK = "#e91e63";
    private static final String PINK_TRANSLUCENT = OPACITY + "e91e63";
    private static final String PURPLE = "#9c27b0";
    private static final String PURPLE_TRANSLUCENT = OPACITY + "9c27b0";
    private static final String LIGHT_BLUE = "#03a9f4";
    private static final String LIGHT_BLUE_TRANSLUCENT = OPACITY + "03a9f4";
    private static final String CYAN = "#00bcd4";
    private static final String CYAN_TRANSLUCENT = OPACITY + "00bcd4";
    private static final String TEAL = "#009688";
    private static final String TEAL_TRANSLUCENT = OPACITY + "009688";
    private static final String GREEN = "#259b24";
    private static final String GREEN_TRANSLUCENT = OPACITY + "259b24";
    private static final String LIGHT_GREEN = "#8bc34a";
    private static final String LIGHT_GREEN_TRANSLUCENT = OPACITY + "8bc34a";
    private static final String LIME = "#cddc39";
    private static final String LIME_TRANSLUCENT = OPACITY + "cddc39";
    private static final String YELLOW = "#ffeb3b";
    private static final String YELLOW_TRANSLUCENT = OPACITY + "ffeb3b";
}
