package com.example.wk7t1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static java.lang.System.err;

//  7.1. Luo käyttöliittymä, joka pitää sisällään yhden painikkeen (Button).
//  Painikkeen avulla tulisi olla mahdollista tulostaa “Hello World!” konsoliin.

//7.2. Jatka edellistä tehtävää luomalla käyttöliittymän Label eli tekstikenttä, johon alustat vapaavalintaisen tekstin.
// Kehitä sitten painiketta niin, että se muuttaa tekstikentässä olevan tekstin merkkijonoksi “Hello World!”.

// 7.3. Lisää käyttöliittymään tekstin syöttökenttä. Muokkaa käyttöliittymää nyt niin,
// että voit syöttää tekstin syöttökenttään tekstiä ja painikkeen painalluksella teksti muuttuu tekstikenttään.

// 7.4. Muokkaa tekstin syöttökenttää niin, että sisältö voidaan kerätä ilman erillistä painiketta.
// Tekstiä siis pitäisi saada ulos syöttämällä teksti tekstin syöttökenttään ja painamalla enteriä
// (tai jopa koko ajan reaaliaikaisesti). Tekstikentän teksti päivittää edelleen vain tekstikenttää.

// 7.5. Tehdään lopuksi tekstieditori. Luo ohjelma, joka pitää sisällään tekstialueen ja kaksi painiketta:
// lataus- ja tallennus. Tarkoituksena on tehdä tehtävä niin,
// että tekstialueeseen syötetty tieto voidaan tallentaa tiedostoon (tiedoston nimi kysytään käyttäjältä,
// voidaan kysyä erillisessä tekstikentässä) tai tiedostosta voidaan ladata tekstiä
// (tiedoston nimi kysytään käyttäjältä) ja se päivittyy tekstialueelle heti latauksen jälkeen.



// Tehtävät 1,2,3 ja 5 viikko 7...tehtävä 4 puuttuu


public class MainActivity extends AppCompatActivity {

    Context context = null;
    TextView text;
    TextInputEditText textinput;
    TextInputEditText filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        text = (TextView) findViewById(R.id.textView);
        textinput = (TextInputEditText) findViewById(R.id.TextInput);
        filename = (TextInputEditText) findViewById(R.id.filename);
    }

    public void task1(View v) {

        System.out.println("Tehtävä 1 - Hello world !");
    }

    public void task2(View v) {
        text.setText("Hello world !");
        System.out.println("task 2 executed");
    }

    public void task3(View v) {
        text.setText(textinput.getEditableText());
        System.out.println("task 3 executed");
    }

    public void readFile(View v ) {

        try {
        InputStream input = context.openFileInput(filename.getEditableText().toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String rivi = reader.readLine();
            while ( rivi != null) {
                text.setText(rivi);
                //System.out.println(rivi);
                rivi = reader.readLine();
            }
            input.close();
        } catch (IOException err) {
            Log.e("IOException", "lukeminen epäonnistui");
        } finally {
            System.out.println("teksti ladattu");
        }
  }

    public void writeFile(View v ) {

        String file = filename.getEditableText().toString();
        try { OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            String rivi = textinput.getEditableText().toString();
            osw.write(rivi);
            osw.close();
        } catch (IOException err) {
            Log.e("IOException", "virhe syötteessä");
        } finally {
            System.out.println("teksti tallennettu");
        }
    }
}