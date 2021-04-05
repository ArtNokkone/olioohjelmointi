package com.example.juoma_automaatti;
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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // text views
    TextView infotext = null;
    SeekBar seekBar;
    TextView seekBarText;


    float creditTransfer;
    Context context = null;
    String selected = null;

    // create bottle dispenser singleton
    BottleDispenser bd = BottleDispenser.GetInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        // add information text
        infotext = (TextView) findViewById(R.id.infotext);

        // create spinner for drink selection
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bottles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // seekbar control
        seekBarControl();
    }

    // drink iten selection
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),selected,Toast.LENGTH_SHORT).show();
        System.out.println(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // insert coin functions
    public void insertEuro(View v) { bd.insertEuro( infotext); }
    public void insert50cent(View v ) { bd.insert50cent( infotext ); }
    public void insert10cent(View v ) { bd.insert10cent( infotext ); }

    // dispenser control functions
    public void buyDrink(View v) { bd.buyBottle(selected, infotext);}
    public void moneyReturn(View v) { bd.returnMoney(infotext);}
    public void confirm(View v) { bd.confirm(infotext, creditTransfer);}

    // write a receipt to file
    public void writeReceipt(View v ) {

        String file = "kuitti.txt";
        try { OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            String rivi = " Special Drinks Virvoitusjuomat \n\nKUITTI\n\n" + selected + "\n\nosto pvm 2021 03 11 aika 08:01:22";
            osw.write(rivi);
            osw.close();
        } catch (IOException err) {
            Log.e("IOException", "virhe syötteessä");
        } finally {
            System.out.println("kuitti saatavana");
        }
    }

    // seekbar control
    public void seekBarControl() {
        // max credit 10 €
        float maxCredit = 10;
        String bartext = "Transfer money from credit card ";

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBarText = (TextView)findViewById(R.id.seekBarText);
        seekBarText.setText(bartext + seekBar.getProgress() + " / " + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    float limit;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        creditTransfer = (float)progress;
                        creditTransfer /= 10;
                        limit = maxCredit * seekBar.getMax() / 100;
                        seekBarText.setText(bartext + creditTransfer + " / " + limit + " € ");
                        Toast.makeText(MainActivity.this,"seek bar progress",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(MainActivity.this,"seek bar start tracking",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekBarText.setText(bartext + creditTransfer + " / " + limit + " € ");
                        Toast.makeText(MainActivity.this,"seek bar stop tracking",Toast.LENGTH_LONG).show();
                    }
                });
    }

    // bottle dispenser class - should be a singleton
    public static class BottleDispenser {

        // constructors
        public BottleDispenser() { fillSystem(); money = 0; }
        private static BottleDispenser bd = null;

        // private members
        private ArrayList<Bottle> bottles = new ArrayList<>();
        private float money;

        // fill machine with 5 bottles for testing software
        private void fillSystem( ) {
            Bottle bottle = new Bottle("Coca Cola - 0.5 l - 1.80 €", (float) 1.8);
            bottles.add(bottle);
            bottle = new Bottle("Coca Cola - 1.5 l - 3.80 €", (float) 3.8);
            bottles.add(bottle);
            bottle = new Bottle("Sprite - 1.5 l - 3.50 €", (float) 3.5);
            bottles.add(bottle);
            bottle = new Bottle("Fanta - 0.3 l - 1.20 €", (float) 1.2);
            bottles.add(bottle);
            bottle = new Bottle("Fanta - 0.5 l - 1.80 €", (float) 1.8);
            bottles.add(bottle);
            }

        // static accessors
        public static BottleDispenser GetInstance( ) {
            if( bd == null)  bd = new BottleDispenser();
            return bd;
        }

        // test function for UI activities
        public void testFunction( ) {
            System.out.println("test function executed");
        }

        // insert 1 euro coin
        public void insertEuro(TextView text) {
            money += 1;
            text.setText("Money Inserted: " + money + " € ");
            //System.out.println("Klink! Money was added into the machine!");
        }

        // insert 50 cent coin
        public void insert50cent(TextView text) {
            money += 0.50;
            text.setText("Money Inserted : " + money + " € ");
            //System.out.println("Klink! Money was added into the machine!");
        }

        // insert 10 cent coin
        public void insert10cent(TextView text) {
            money += 0.10;
            text.setText("Money Inserted : " + money + " € ");
            //System.out.println("Klink! Money was added into the machine!");
        }

        // buy a bottle button
        public void buyBottle(String selected, TextView text) {
            if(!bottles.isEmpty()) {
                int i;
                for (i = 0; i < bottles.size(); i++) {
                    if (selected.equals(bottles.get(i).getItem())) {
                        if (money >= bottles.get(i).getPrice()) {
                            money -= bottles.get(i).getPrice();
                            bottles.remove(i);
                            text.setText("here are sir ! - one " + selected + "saldo : " + money);
                            return;
                        } else text.setText("insert more money - saldo : " + money + " € ");
                        return;
                    }
                }
                System.out.println(" bottles left ." + bottles.size());
                text.setText("sorry - item run out - select another item");
            } else text.setText(("sorry - machine empty !"));
        }

        // return extra money
        public void returnMoney(TextView text) {
            money = 0;
            text.setText("buy a drink - insert money - saldo : " + money + " € ");
        }

        //  confirm button - this was not needed after all
        public void confirm(TextView text, float credit) {
            money += credit;
            text.setText("Money Inserted : " + money + " € ");
        }
    }

    // Bottle class
    public static class Bottle {
        private String item;
        private float  bottlePrice;
        public Bottle(){}
        public Bottle(String identifier, float price){ item = identifier; bottlePrice = price; }

        public String getItem( ) { return item; }
        public float getPrice( ) { return bottlePrice; }

    }
}