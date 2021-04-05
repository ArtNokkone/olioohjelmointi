package com.example.harjoitusviikko11;

/*
11.1. Tee ohjelma jossa on sivumenu (side menu / side bar / navigation drawer) ja sieltä löytyy asetukset
 (joko sivumenusta itsestään löytyy kaikki asetukset TAI sieltä löytyy linkki asetukset-aktiviteettiin / ikkunaan).

11.2. Luo kaksi tekstikenttää. Toinen on "luettava" tekstikenttä ja toinen on "kirjoitettava" tekstikenttä.
 Luo asetuksiin vaihtoehto tekstikentän muokkauksesta (sallittu - estetty), joka päättää voiko "kirjoitettavaan" tekstikenttää kirjoittaa.
  Aseta "kirjoitettavan" tekstikentän teksti "luettavaan" tekstikentään, kun muokkaus on estetty.

11.3. Luo asetuksiin vähintään 4 erilaista vaihtoehtoa (kuten fonttikoko, leveys, korkeus, rivimäärä jne.),
 jolla voi muokata "luettavan" tekstikentän ulkoasua. (yksi piste puolikkaan sijaan)

11.4. Luo asetuksiin "Display text" (nimen saa muuttaa) tekstikenttä, jonka sisältö näytetään pääohjelmassa
 (eli siis asetuksissa kysytään käyttäjältä jokin lyhyt tekstipätkä, vaikkapa nimi tai nimimerkki,
  joka näytetään pääohjelmassa). Luo asetuksiin myös kieli-lista, josta voi valita oman näyttökielensä
   (suomi, englanti, ruotsi). Tekstejä ei tarvitse kääntää.

11.5. Luo tekstit suomen- ja englanninkielelle ja vaihda applikaatio käyttämään niitä asetuksen perusteella.
 Alla hieman linkkejä kuinka asia voidaan toteuttaa. (yksi piste puolikkaan sijaan)

        https://www.tutlane.com/tutorial/android/android-localization-multi-language-with-examples
        https://developer.android.com/guide/topics/resources/string-resource
        https://developer.android.com/training/basics/supporting-devices/languages
*/

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        //mToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.setSupportActionBar(supportActionBar);
    }

    public ActionBar getSupportActionBar() {
        ActionBar supportActionBar = null;
        return supportActionBar;
    }
}