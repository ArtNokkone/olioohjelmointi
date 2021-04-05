package com.example.viikko9;

import java.util.ArrayList;

public class Teatteri { Teatteri( ) { }

    // elokuvateatterin nimi
    private String nimi;
    // elokuvateatterin tunnus
    private String tunnus;

    // vastaavat accessorit ja mutatorit
    public void nimiOn(String name) { nimi = name; }
    public String haeNimi( ) { return nimi; }
    public void tunnusOn(String id) { tunnus = id; }
    public String haetunnus( ) { return tunnus; }
}
