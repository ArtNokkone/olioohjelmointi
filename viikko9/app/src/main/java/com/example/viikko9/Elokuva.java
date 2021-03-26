package com.example.viikko9;

import java.util.ArrayList;

public class Elokuva {
    public Elokuva( ) { }

    private ArrayList<String> elokuvat = new ArrayList<>();
    //int pvm, int kellonaika, String nimi, String paikka
    public ArrayList<String>  listaaElokuvat() {
        return elokuvat;
    }

}
