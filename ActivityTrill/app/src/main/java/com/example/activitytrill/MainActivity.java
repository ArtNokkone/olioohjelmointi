package com.example.activitytrill;
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
  joka näytetään pääohjelmassa). Luo asetuksiin myös kieli-lista, josta voi valita oman näyttökielensä (suomi, englanti, ruotsi).
   Tekstejä ei tarvitse kääntää.

11.5. Luo tekstit suomen- ja englanninkielelle ja vaihda applikaatio käyttämään niitä asetuksen perusteella.
 Alla hieman linkkejä kuinka asia voidaan toteuttaa. (yksi piste puolikkaan sijaan)

        https://www.tutlane.com/tutorial/android/android-localization-multi-language-with-examples
        https://developer.android.com/guide/topics/resources/string-resource
        https://developer.android.com/training/basics/supporting-devices/languages
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button text, setting;
    private boolean readonly = true;

    private boolean display = true;

    // asetukset
    private static String fontti = new String("default" );
    private static String fonttikoko = new String("default");
    private static String fonttivari = new String("default");
    private static String fonttityyli = new String("default");
    private static String nimimerkki = new String("NN");
    private static String kieli = new String("suomi");
    private String settingvalue;

    private static String content = new String("tässä vähän tekstin tynkää testausta varten....");

    // fragment pointers
    Fragment settings = null;
    Fragment textFragment = null;
    Fragment readtextFragment = null;
    Fragment current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // valitsinnappulat
        text = (Button) findViewById(R.id.blankfragment);
        setting = (Button) findViewById(R.id.settings);

        // create fragments
        readtextFragment = new ReadOnly();
        textFragment = new BlankFragment();
        settings = new Settings();

        //String text = "My favourite food is Biriyani";
        //SpannableString ss = new SpannableString(text);
        //StyleSpan boldSpan = new StyleSpan();
        //ss.setSpan(boldSpan, 21, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //TextView textView = new TextView();
        //textView.setTextSize(2);

        // perform setOnClickListener event on First Button
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String teksti;
                // lataa teksti
                if (readonly == true) {
                    current = readtextFragment;
                    System.out.println("readonly : " + readonly);
                    teksti = "lukumoodi";
                } else {
                    current = textFragment;
                    System.out.println("readonly : " + readonly);
                    teksti = "kirjoitusmoodi";
                }

                // lataa teksti valitun fragmentin tekstinäytölle
                loadFragment(current);
                sendValueToFragment("text", content);
                display = true;
                System.out.println("näyttö = " + display);
                // näytä tekstiasetukset
                TextView view = findViewById(R.id.textView);
                view.setText("nimimerkki : " + nimimerkki + "\ntekstiasetukset : " + teksti + " kieli : " + kieli + "\nfontti : (" + fontti + ")-koko(" + fonttikoko + ")-väri(" + fonttivari + ")-tyyppi(" + fonttityyli + ")");
            }
        });

        // perform setOnClickListener event on Second Button
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load Settings Fragment
                loadFragment(settings);
                Bundle bundle = new Bundle();
                if (readonly == true) bundle.putString("readonly", "on");
                else bundle.putString("readonly", "off");
                settings.setArguments(bundle);
                display = false;
                System.out.println("settings display opened : " + display);
            }
        });
    }

    @Override
    public void onStart () {
            super.onStart();
            try {

                if(readonly == false) {
                    // tekstiä editorilta
                    content = getIntent().getExtras().get("text").toString();
                    System.out.println("tekstiä editorilta : " + content);
                }
                // asetusmuutos - readonly
                    settingvalue = getIntent().getExtras().get("readonly").toString();
                    if (settingvalue.compareTo("on") == 0) readonly = true;
                    if (settingvalue.compareTo("off") == 0) readonly = false;
                    System.out.println("new readonly setting received " + settingvalue);

                    // asetusmuutos - fontti
                    String text = getIntent().getExtras().get("fontti").toString();
                    if(text.compareTo("oletus") != 0) fontti = text;
                    System.out.println("new font setting received " + fontti);

                    // asetusmuutos - fontinkoko
                    text = getIntent().getExtras().get("fonttikoko").toString();
                    if(text.compareTo("oletus") != 0) fonttikoko = text;
                    System.out.println("new font size setting received " + fonttikoko);

                    // asetusmuutos - fontin väri
                    text = getIntent().getExtras().get("fonttivari").toString();
                    if(text.compareTo("oletus") != 0) fonttivari = text;
                    System.out.println("new font color setting received " + fonttivari);

                    // asetusmuutos - fontin tyyli
                    text = getIntent().getExtras().get("fonttityyli").toString();
                    if(text.compareTo("oletus") != 0) fonttityyli = text;
                    System.out.println("new font style setting received " + fonttityyli);

                    // asetusmuutos - kieli
                    text = getIntent().getExtras().get("kieli").toString();
                    if(text.compareTo("oletus") != 0) kieli = text;
                    System.out.println("new language setting received " + kieli);

                    // asetusmuutos - nimimerkki
                    text = getIntent().getExtras().get("nimimerkki").toString();
                    if(text.compareTo("oletus") != 0) nimimerkki = text;
                    System.out.println("new nimimerkki setting received " + nimimerkki);

                //}

            } catch (Exception e) {

            }
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.menu, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public void sendValueToFragment( String key, String value ) {
        Bundle bundle = new Bundle();
        bundle.putString(key,value);
        current.setArguments(bundle);
    }

}

