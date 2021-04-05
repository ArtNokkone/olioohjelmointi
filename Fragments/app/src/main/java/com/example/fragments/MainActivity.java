package com.example.fragments;
//
//11.1. Tee ohjelma jossa on sivumenu (side menu / side bar / navigation drawer) ja sieltä löytyy asetukset
// (joko sivumenusta itsestään löytyy kaikki asetukset TAI sieltä löytyy linkki asetukset-aktiviteettiin / ikkunaan).
//
// 11.2. Luo kaksi tekstikenttää. Toinen on "luettava" tekstikenttä ja toinen on "kirjoitettava" tekstikenttä.
//  Luo asetuksiin vaihtoehto tekstikentän muokkauksesta (sallittu - estetty), joka päättää voiko "kirjoitettavaan" tekstikenttää kirjoittaa.
//  Aseta "kirjoitettavan" tekstikentän teksti "luettavaan" tekstikentään, kun muokkaus on estetty.
//
//11.3. Luo asetuksiin vähintään 4 erilaista vaihtoehtoa (kuten fonttikoko, leveys, korkeus, rivimäärä jne.),
// jolla voi muokata "luettavan" tekstikentän ulkoasua. (yksi piste puolikkaan sijaan)
//
//11.4. Luo asetuksiin "Display text" (nimen saa muuttaa) tekstikenttä, jonka sisältö näytetään pääohjelmassa
// (eli siis asetuksissa kysytään käyttäjältä jokin lyhyt tekstipätkä, vaikkapa nimi tai nimimerkki,
//  joka näytetään pääohjelmassa). Luo asetuksiin myös kieli-lista, josta voi valita oman näyttökielensä (suomi, englanti, ruotsi).
//   Tekstejä ei tarvitse kääntää.
//
//11.5. Luo tekstit suomen- ja englanninkielelle ja vaihda applikaatio käyttämään niitä asetuksen perusteella.
// Alla hieman linkkejä kuinka asia voidaan toteuttaa. (yksi piste puolikkaan sijaan)
//
//        https://www.tutlane.com/tutorial/android/android-localization-multi-language-with-examples
//        https://developer.android.com/guide/topics/resources/string-resource
//        https://developer.android.com/training/basics/supporting-devices/languages
//
//

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;

                if(v == findViewById(R.id.buttonBlank)) {
                    System.out.println("Blank Fragment Selected");
                    fragment = new BlankFragment();
                }
                else {
                    fragment = new SettingsFragment( );
                    System.out.println("Settings Fragment Selected");
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentWindow,fragment);
                transaction.commit();
            }
        };

        Button button = findViewById(R.id.settingsFragment);
        button.setOnClickListener(listener);
        Button button2 = findViewById(R.id.buttonBlank);
        button2.setOnClickListener(listener);

    }
}