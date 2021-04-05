package com.example.bottledispenser;

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
// TODO - selvitä mistä johtuu että pitää tehdä aliluokalla
// TODO - kysy seekbarin käytöstä - mainitse ristiriita kolikon syötön kanssa

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context context = null;
    TextView text;
    TextView seekBarText;
    SeekBar seekBar;
    //TextInputEditText filename;
    //TextInputEditText textinput;

    BottleDispenser bottleDispenser = new BottleDispenser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        text = (TextView) findViewById(R.id.textView);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bottles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        seekBarControl();
    }


    public void writeFile(View v ) {

        String file = "kuitti.txt";
        try { OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            String rivi = "kuitti : Coca Cola drink hinta 1.80 € osto pvm 2021 03 11 ";
            osw.write(rivi);
            osw.close();
        } catch (IOException err) {
            Log.e("IOException", "virhe syötteessä");
        } finally {
            System.out.println("kuitti saatavana");
        }
    }

    public void seekBarControl() {
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBarText = (TextView)findViewById(R.id.textView);

        seekBarText.setText("sum : " + seekBar.getProgress() + " / " + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        seekBarText.setText("pay using personal id card " + progress + " 10 cents / maximum credit " + 10 * seekBar.getMax());
                        Toast.makeText(MainActivity.this,"seek bar progress",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(MainActivity.this,"seek bar start tracking",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekBarText.setText("sum : " + progressValue + " / " + seekBar.getMax());
                        Toast.makeText(MainActivity.this,"seek bar stop tracking",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void addEuro(View v ) { bottleDispenser.addEuro(); }
    public void add50cent(View v) { bottleDispenser.add50cent(); }
    public void add10cent(View v) {bottleDispenser.add10cent(); }
    public void buyBottle(View v) { bottleDispenser.buyBottle(); }
    public void returnMoney(View v) { bottleDispenser.returnMoney(); }
    public void select(View v) { bottleDispenser.test(); }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
        System.out.println(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class BottleDispenser {
        private int bottles;
        private int money;

        public void test( ) {
            System.out.println("test executed");
        }
        public BottleDispenser() {
            bottles = 5;
            money = 0;
        }

        public void addEuro() {
            money += 100;
            text.setText("Total Money : " + money);
            System.out.println("Klink! Money was added into the machine!");
        }

        public void add50cent() {
            money += 50;
            text.setText("Total Money : " + money);
            System.out.println("Klink! Money was added into the machine!");
        }

        public void add10cent() {
            money += 10;
            text.setText("Total Money : " + money);
            System.out.println("Klink! Money was added into the machine!");
        }

        public void buyBottle() {
            bottles -= 1;
            money -= 200;
            text.setText("left over money : " + money);
            System.out.println("KACHUNK! Bottle appeared from the machine!");
        }

        public void returnMoney() {
            money = 0;
            text.setText("Insert coin : ");
            System.out.println("Klink klink!! All money gone!");
        }
    }
 }