package com.example.week8;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

/**
 8.1. Muuta limsa-automaatin rakennetta niin, että se noudattaa toiminnassaan Singleton-suunnitteluperiaatetta,
 * eli että on mahdollista luoda vain yksi instanssi oliosta.

 8.2. Luo graafinen käyttöliittymä limsa-automaatista. Käytä limsa-automaatin  rakennettua toiminnallisuutta mahdollisimman paljon,
 * eli ainoa muutos tulee olemaan konsolikäyttöliittymästä graafiseksi.
 * Lisäksi muuta pulloja hallinnoiva tietorakenne ArrayListiksi, ellet ole sitä jo aikaisemmin tehnyt.

 Ohjeet:

 Tehtävän tarkoituksena on rakentaa käyttöliittymä, jossa on “valuuttakolo”,
 * eli painike, josta on mahdollista lisätä rahaa koneeseen. Tämän lisäksi tarvitaan painike,
 * josta on mahdollista ostaa limsoja sekä palautuspainike, josta saa rahat ulos. Huomaathan,
 * että konsoliin tulostaminen ei ole enää toimiva keino, joten lisää käyttöliittymään myös kenttä,
 * jonne tulosteet ilmestyvät näkyviin.

 8.3. Muokkaa käyttöliittymää niin, että automaattiin on mahdollista lisätä erilaisia määriä rahaa.
 * Tähän on suotavaa käyttää SeekBar-komponenttia, jonka avulla voidaan liu’uttamalla määrittää,
 * kuinka paljon rahaa koneeseen laitetaan. Tarvitset silti kuitenkin painikkeen,
 * josta raha syötetään ja SeekBar nollaantuu.

 8.4. Lisää käyttöliittymään mahdollisuus ostaa erilaisia limsoja eri kokoisina,
 * jolloin hyödyllistä olisi käyttää pudotusvalikoita ostoksen määritykseen.
 * Käyttäjä valitsee siis pudotusvalikosta ostettavan tuotteen ja sen koon,
 * jonka jälkeen kone joko myy tuotteen tai ilmoittaa, ettei sitä ole
 * (voit käyttää esimerkiksi Android Studion Spinner-komponenttia).

 8.5. Lisää ohjelmaan toiminnallisuus, jonka avulla on mahdollista tulostaa käyttäjälle
 * kuitti viimeisimmästä ostoksesta. Kuitin tulee sisältää tieto siitä,
 * että se on kuitti sekä tieto kyseisestä ostoksesta, eli ainakin tuotteen nimi ja hinta.
 * Kuitti ei tulosteta käyttöliittymään vaan se tulostetaan suoraan tiedostoon,
 * joka on erikseen määritelty ohjelman sisällä.
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}