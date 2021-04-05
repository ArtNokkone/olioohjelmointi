package com.example.viikko9;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// Container luokka sisältää tiedot kaikista elokuvateattereista, konstruktorissa luetaan serveriltä
// XML tiedosto osoitteesta "https://www.finnkino.fi/xml/TheatreAreas",
// jonka perusteella muodostetaan lista elokuvateattereista (metodi lueTeatterit
public class Container {
    Container( ) {
    try {
        // luodaan taulukko elokuvateattereista
        lueTeatterit();
    } catch (ParserConfigurationException e) {
        e.printStackTrace();
    }
}

    private String pvm = null;
    private String startTime = null;
    private String stopTime = null;

    // lista elokuvateattereista
    private ArrayList<Teatteri> teatterit = new ArrayList<>();
    private ArrayList<String> lista = new ArrayList<>();
    private ArrayList<String> elokuvat = new ArrayList<>();

    public String haeAlku( ) { return startTime; }
    public String haeLoppu( ) { return stopTime; }
    public void asetaAlku( String start) { startTime = start; }
    public void asetaLoppu( String stop) { stopTime = stop; }

    // metodi asettaa hakukriteerin päivämäärä
    public void setDate(String date) {
        pvm = date;
        System.out.println("pvm = " + pvm);
    }

    // metodi palauttaa lista elokuvateattereiden nimistä
    public ArrayList<String> listaaTeatterit() {
        lista.clear();
        for (int i = 0; i < teatterit.size(); i++) {
            lista.add(teatterit.get(i).haeNimi());
        }
        return lista;
    }

    // metodi hakee elokuvateatterin tunnuksen nimen perusteella ja palauttaa sen
    public String haeTunnus(String nimi) {
        int i = 0;
        for (i = 0; i < teatterit.size(); i++) {
            if (teatterit.get(i).haeNimi() == nimi) break;
        }
        return teatterit.get(i).haetunnus();
    }

    // metodi hakee elokuvia yhdestä elokuvateatterista joka tunnistetaan nimellä tai tunnuksella
    // Hakukriteerit kysellään käyttäjältä ja ne on tallenettu luokan sisäisiin muuttujiin
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> haeElokuvia(String teatteri, String tunnus, String elokuva) {

        System.out.println("hakukriteerit - teatterin nimi " + teatteri + " päivämäärä " + pvm);

        // hae tunnus nimen perusteella jos sitä ei ole annettu
        String id = tunnus;
        //if(tunnus == null) { id = haeTunnus(teatteri); }
        //if(id == null) return null;

        // haetaan kaikki elokuvat valitsimina teatterin tunnus ja päivämäärä
        try {
            listaaElokuvat(id,pvm);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
         return elokuvat;
    }

    // metodi listaa elokuvateatterin elokuvat, syötteenä annetaan elokuvateatterin tunnus
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void listaaElokuvat(String tunnus, String date ) throws ParserConfigurationException, ParseException {

        String area = "https://www.finnkino.fi/xml/Schedule/?area=";
        //String date = "&dt=23.03.2021";
        String url = area + tunnus + "&dt=" + date;
        System.out.println(url);

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = null;
        try {
            document = builder.parse(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        document.normalizeDocument();
        document.getDocumentElement().normalize();
        document.getDocumentElement().normalize();
        NodeList nodes = document.getElementsByTagName("Shows").item(0).getChildNodes();
        elokuvat.clear();
        System.out.println("root element : " + document.getDocumentElement().getNodeName() + "node count :" + nodes.getLength());
        for(int i=0;i<nodes.getLength();i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String title = element.getElementsByTagName("Title").item(0).getTextContent();
                /* hae esityksen alkamis aika
                String atime = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                atime.replace("T"," ");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String araja = date + " " + startTime;
                System.out.println("vertailu " + atime + araja);
                Date pvm = formatter.parse(araja);
                pvm.
                Date aika = formatter.parse(atime);
                if (aika.compareTo(pvm)<0)
                {
                    System.out.println("alkamisaika pienmpi");
                }*/
                elokuvat.add(title);
            }
        }
    }

    // etsitään Finnkinon elokuvatarjontaa osoitteesta http://www.finnkino.fi/xml.
    // ladataan  XML-tiedosto, josta parsitaan esiin lista elokuvateattereista
    // kustakin teatterista luodaan Teatteri-luokan instanssi joka lisätään taulukkoon
    // Teatteri-luokkaan talletetaan teatterin nimi ja tunnus
    private void lueTeatterit( ) throws ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        String kino = "https://www.finnkino.fi/xml/TheatreAreas";
        try {
            Document document = builder.parse(kino);
            document.normalizeDocument();
            document.getDocumentElement().normalize();
            System.out.println("root element : " + document.getDocumentElement().getNodeName());
            NodeList nodes = document.getElementsByTagName("TheatreAreas").item(0).getChildNodes();
            for(int i=0;i<nodes.getLength();i++) {
                Node node = nodes.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Teatteri teatteri = new Teatteri();
                    teatteri.nimiOn( element.getElementsByTagName("Name").item(0).getTextContent());
                    teatteri.tunnusOn(element.getElementsByTagName("ID").item(0).getTextContent());
                    teatterit.add(teatteri);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("XML operation ok");
        }
    }
}
