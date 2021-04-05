
package com.example.viikko9;

/* Viikon 9 harjoitustehtävät / Week 9 exercises
        9.1. Tee käyttöliittymä, jossa on mahdollista listata kaikki Finnkinon teatterit pudotusvalikossa.
         Pudotusvalikosta voi siis valita teatterin ja teatterin valinnan jälkeen ohjelmaan
         listataan kyseisessä teatterissa näytettävät elokuvat. Toiminnallisuuksia ei tarvitse vielä toteuttaa,
         tärkeintä on saada komponentit paikalleen. Mukana pitäiis olla erilaiseet hakukriteerit kuten päivämäärä,
         kellonaika, elokuvan nimi, ja paikka.

        9.2. Lähde etsimään Finnkinon elokuvatarjontaa osoitteesta http://www.finnkino.fi/xml.
         Tee käyttöliittymän taustalle ohjelma, jonka avulla on mahdollista ladata XML-tiedosto,
         missä käsitellään toimipaikkoja (areas XML). Luo myös erillinen luokka, jonka sisälle säilöt jokaisen elokuvateatterin tiedot.

        Tarvitset siis luokan, joka pitää kirjaa kaikista elokuvateattereista tietorakenteen avulla,
        sekä luokan, joka sisältää elokuvateatterin tiedot (Paikka ja ID). Pääluokan voit sitten integroida käyttöliittymään.
        Käyttöliittymään päivitetään aluksi pudotusvalikko niin, että käyttäjä voi valita haluamansa elokuvateatterin listasta.

        9.3. Lähdetään seuraavaksi hakemaan tietoa päivän näytöksistä teatterikohtaisesti.
         Tiedon hakemiseen tarvitaan haluttu päivämäärä (tämä päivä) sekä teatterin ID-numero.
          Näistä tiedoista muodostuu haluttu URL muotoon

        https://www.finnkino.fi/xml/Schedule/?area=<teatterinID>&dt=<päivämäärä pp.kk.vvvv>
        http://www.finnkino.fi/xml/Schedule/?area=<teatterinID>&dt=<päivämäärä pp.kk.vvvv>
        <- ja >-merkit eivät kuulu urliin, vaan kuvastavat parametriä! Yllä kaksi vaihtoehtoa,
         https ja http. Kokeilkaa ensin https ja jos ei toimi, niin http.


        Parsi saadut esitystiedot aina, kun teatteri on valittu ja esitykset halutaan katsoa.
         Käytä esittämiseen jotain lista-komponenttia (esim. RecyclerView, ScrollView tai ListView),
         johon lisäät elokuvat omille rivilleen. Kuten edellisessä tehtävässä, käyttöliittymä vain esittää tiedot,
         mutta ei itse sisällä mitään parsimiseen tai tiedon säilömiseen liittyvää dataa.
        Hetkellinen tietorakenteen käyttö on kuitenkin sallittua, jos on mahdollista osoittaa sen olevan tarpeellista.


        9.4. Lisää ohjelmaan toiminnallisuus, jonka avulla käyttäjä voi syöttää päivämäärän sekä kellonaikavälin,
         jolta haluaa katsella elokuvatarjontaa. Päivämäärä vaikuttaa suoraan datan hakemiseen ja kellonaika vaikuttaa siihen,
          mitä dataa valitaan näytettäväksi. Tee siis vapaavalintaisia kenttiä, joihin käyttäjä voi syöttää päivämäärän
           ja aloita jälkeen sekä aloita ennen, miltä väliltä elokuvia haetaan (väli, josta elokuvat alkavat).
          Jos kaikki kentät jätetään tyhjiksi, haetaan päivän kaikki elokuvat valitussa teatterissa.  (yksi piste puolikkaan sijaan)


        9.5. Lisää hakutoiminto, jolla voidaan hakea elokuvan nimen avulla kaikki ne paikat ja ajat, jolloin elokuvaa näytetään.
         Hakutulosten pitäisi listata elokuvan nimi otsikkona ja sen jälkeen kaikkien esittävien teatterien nimet
          ja esitysajat käyttäjän syötteen mukaan (eli hakutoiminto etsii edellisten tehtävien mukaisten rajoitusten
          mukaan JA jos teatteria ei ole annettu, haku etsii kaikista teattereista.
          Huom: Eri teatterialueita on 9 kun huomioit, että osa id-numeroista käy useamman teatterin läpi.
           ID 1029 ei näytä kaikkia. ).  (yksi piste puolikkaan sijaan)

 */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Context context = null;
    String selected = null;
    ArrayList<String > teatterit= new ArrayList<>();
    Container container;
    private Button pickDate;
    private TimePicker timePicker;
    private String format;
    TextView textView = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //context = MainActivity.this;

        // XML reader setup
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // luodaan luokka elokuva teatterien hallintaan
        container = new Container();

        //timePicker
        timePicker = (TimePicker) findViewById(R.id.timePicker1);
        //TextView time = (TextView) findViewById(R.id.textView1);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        // luodaan spinneri pudotusvalikkoa varten
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> lista = container.listaaTeatterit();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // päivämäärä hakukriteerin antaminen
        final Button pickDate;
        pickDate = (Button) findViewById(R.id.pick_date);
        textView = (TextView) findViewById(R.id.date);
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                String myFormat = "yyyy.MM.dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String text = "valittu päivämäärä : " + sdf.format(myCalendar.getTime());
                textView.setText(text);
            }
        };

        pickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear, mMonth, mDay);
                                String pvm = "pvm = " + (year + ":"  + (monthOfYear + 1) + ":" + dayOfMonth);
                                textView.setText(pvm);
                                //textView.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                container.setDate(pvm);
                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });
    }

    public void setTime(View view) {
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        String text = showTime(hour,min).toString();
        if(container.haeAlku() == null)
        {
            container.asetaAlku(text);
            textView.setText("klo " + text + " alkaen : ");
        }
        else if(container.haeLoppu() == null) {
            container.asetaLoppu(text);
            textView.setText("klo " +text + "...asti");
        }
        else { container.asetaAlku(null); container.asetaLoppu(null); textView.setText("anna alku ja loppuaika"); }
        //textView.setText(text);
        System.out.println("aika :" + hour + min);
    }

    public StringBuilder showTime(int hour, int min) {
        if (hour == 0) {
            //hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            // hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        StringBuilder builder = new StringBuilder().append(hour).append(" : ").append(min).append(":").append("00");
        return builder;
    }

    // pudotusvalikon valitsin
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selected, Toast.LENGTH_SHORT).show();
        System.out.println(selected);

        // selvitä elokuvateatterin nimeä vastaava teatterin tunnus (ID)
        String tunnus = container.haeTunnus(selected);
        //System.out.println("elokuvateatteri : " + selected + " päivämäärä" + pvm);
        ArrayList<String> elokuvat = container.haeElokuvia(selected,tunnus,null);
        //if (shows != null) { for (int i = 0; i < shows.size(); i++) System.out.println(shows.get(i)); }

        // luodaan ListView näyttö elokuvateatterin ohjelmiston listaamiseksi
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.activity_listview, elokuvat);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapt);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showDatePickerDialog(View view) {
    }
}