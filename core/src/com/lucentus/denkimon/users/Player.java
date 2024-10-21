package com.lucentus.denkimon.users;

import com.lucentus.denkimon.entities.Denkimon;

import java.util.ArrayList;

/**
 * Class to represent each User/Player
 * and all information associated w/ that Player
 */
public class Player {

    /*
     * Properties
     */
    private String name;
    private String userID;

    private ArrayList<Denkimon> denkimon = new ArrayList<>();


    /*
     * Constructors
     */
    public Player() {

    }


    /*
     * Methods
     */

    public String getName() {
        return this.name;
    }

    public ArrayList<Denkimon> getDenkimon() {
        return this.denkimon;
    }
}
