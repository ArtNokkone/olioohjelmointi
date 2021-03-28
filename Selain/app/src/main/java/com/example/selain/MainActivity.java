package com.example.selain;

/*Viikon 10 harjoitukset / Week 10 exercises

        10.1 Tee verkkoselain, jonka avulla on mahdollista käyttää verkkoa sekä syöttää haluamiaan osoitteita osoitepalkkiin.
         Tee ohjelma niin, että käyttäjän ei tarvitse itse syöttää osoitteen alkuosaa http://.

        10.2. Luo selaimeesi myös päivitys-painike (Refresh), jonka avulla voit ladata verkkosivun uudelleen.
         Painike siis etsii senhetkisen verkkosivun ja lataa sen uudelleen.

        10.3. Ota käyttöön tiedosto “index.html” ja lisää se Androidin assets-kansioon.
         Luo painike, joka suorittaa JavaScript-rajapintakutsun, jossa halutaan käyttää metodia “shoutOut()”.
         Luo myös toinen painike, jonka avulla on mahdollista muuttaa teksti takaisin alkuperäiseksi JavaScript-metodin initialize() avulla.
        Jotta saat index.html-tiedoston käyttöösi, tee lisäys osoitepalkin toiminnallisuuteen, jolloin käyttäjän kirjoittaessa “index.html”
        ladataankin resursseissa oleva tiedosto.

        index.html -tiedoston sisältö löytyy alapuolelta. Voitte luoda Android Studiossa uuden tiedoston ja nimetä sen "index.html".

        <html>
        <head>
        <script type="text/javascript">
        function initialize() {
        document.getElementById("random").innerHTML = "Moi Maailma!";
        }

        function shoutOut() {
        document.getElementById("random").innerHTML = "Hello World!";
        }
        </script>
        </head>
        <body onload="initialize()">
        <p id="random"></p>
        </body>
        </html>


        10.4. Luo verkkoselaimeesi painikkeet, joista on mahdollista päästä edelliselle ja seuraavalle verkkosivulle.
        Tarvitset siis tallennuspaikan sekä edelliselle että seuraavalle osoitteelle ja lataa ne painikkeita painettaessa.

        10.5. Laajenna verkkoselaimen edellinen- ja seuraava-toimintoja niin, että välimuistia on mahdollista matkustaa
                kauemmas eteen- tai taaksepäin 10 askeleen päähän. Tarvitset muuttujien sijasta siis tietorakenteen,
                johon tallennat osoitteita talteen, jos niille on tarvetta. Tämän lisäksi, muokkaa historiaa niin
                että jos käyttäjä syöttää uuden osoitteen sen jälkeen, kun on mennyt taaksepäin, "seuraavat" sivut
                 nollaantuvat ja käyttäjä on jälleen uusimmassa sivussa (kuten oikeassa selainhistoriassa).
 */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WebView selain = null;
    EditText url;
    String current;
    int historycounter = 0;
    boolean fromhistory = false;
    ArrayList<String > history = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // alusta URL kysely
        url = (EditText)findViewById(R.id.editTextWebAddress);

        // käynnistä selain
        selain = findViewById(R.id.selain);
        selain.setWebViewClient(new WebViewClient());
        selain.getSettings().setJavaScriptEnabled(true);

       // lataa aloitussivu
        selain.loadUrl("http://www.google.fi");

    }

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    // luetaan käyttäjän antama URL
    public void readText(View v) {
        // lue osoite näytöltä
        current = url.getText().toString();
        if(current.contains("index.html")) {
            selain.loadUrl("file:///android_asset/index.html");
        } else {
            if (current.contains("http://") == false) current = "http://" + current;
            if(fromhistory == true) {
                // poista historiasta ylimääräiset
                while(++historycounter < history.size()) history.remove(historycounter);
                historycounter = 0;
                fromhistory = false;
            } else {
                // päivitä historiavektoria
                history.add(current);
            }
            System.out.println( " historycounter : " + historycounter  + " history size : " + history.size());

            // lataa sivu
            selain.loadUrl(current);
            // sulje näppäimistö
            closeKeyBoard();
        }
    }

    public void scrollForward( View v) {
        if(history.size() > 0) {
            if (historycounter < (history.size() - 1)) historycounter++;
            url.setText(history.get(historycounter));
            fromhistory = true;
            System.out.println( " historycounter : " + historycounter  + " history size : " + history.size());
        } else System.out.println("list empty");
    }

    public void scrollBack(View v) {
        if(history.size() > 0 ) {
            if (historycounter > 0) historycounter--;
            url.setText(history.get(historycounter));
            fromhistory = true;
            System.out.println( " historycounter : " + historycounter  + " history size : " + history.size());
        } else System.out.println("list empty");
    }

    // tehtävä 3 init
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initialize(View v) {

        selain.evaluateJavascript("javascript:initialize()",null);
    }

    // tehtävä 3 shoutOut funktion
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void shoutOut(View v) {
        selain.evaluateJavascript("javascript:shoutOut()",null);
    }

    // URL refresh
    public void refresh(View v) {
        selain.loadUrl(current);
    }
}