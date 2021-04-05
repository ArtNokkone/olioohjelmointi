package com.example.activitytrill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings<PlainText> extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    // asetukset
    Switch  switchButton;                // readonly kytkin
    Button  confirm;                     // asetusten confirm nappula
    boolean settingschanged = false;     // asetuksia on muutettu
    String settingvalue;
    String fontti = "oletus";
    String fonttikoko = "oletus";
    String fonttivari = "oletus";
    String fonttityyli = "oletus";
    String nimimerkki = "oletus";
    String kieli = "oletus";
    String  readonly = "off";

    private Bundle savedInstanceState;
    private boolean readonlyflag = false;
    public Settings() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        try {
            // lue pääohjelmasta annettu uusi teksti ja kirjoita se näytölle
            String arg = getArguments().getString("readonly");
            if(arg.compareTo("on") == 0) {
                readonlyflag = true;
                switchButton.setChecked(readonlyflag);
                System.out.println("received setting : " + arg);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        // readonly kytkin
        switchButton = (Switch) view.findViewById(R.id.switch1);
        switchButton.setChecked(readonlyflag);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // jos readonlykytkimen tila muuttunut, päivitä tieto
                if(switchButton.isChecked() == true && readonlyflag == false) {
                    readonlyflag = true;
                    readonly = "on";
                    settingschanged = true;

                } else if(switchButton.isChecked() == false && readonlyflag == true) {
                    readonlyflag = false;
                    readonly = "off";
                    settingschanged = true;
                }
                switchButton.setChecked(readonlyflag);
                // System.out.println("readonly flag status : " + readonly);
            }
        });
        // confirm nappula
        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingschanged == true) {
                    sendToActivity();
                    settingschanged = false;
                    System.out.println("settings changed");
                }
            }
        });

        // textstin fontin asetus
        EditText text = view.findViewById(R.id.fontti);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontti = text.getText().toString();
                closeKeyBoard();
                settingschanged = true;
                //System.out.println("fontti : " + fontti);
            }
        });

        EditText textm = view.findViewById(R.id.merkkikoko);
        textm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fonttikoko = textm.getText().toString();
                settingschanged = true;
                closeKeyBoard();
            }
        });

        EditText textt = view.findViewById(R.id.fonttityyppi);
        textt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fonttityyli = textt.getText().toString();
                settingschanged = true;
                closeKeyBoard();
            }
        });

        EditText textv = view.findViewById(R.id.fontinvari);
        textv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fonttivari = textv.getText().toString();
                settingschanged = true;
                closeKeyBoard();
            }
        });

        EditText textnm = view.findViewById(R.id.nimimerkki);
        textnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nimimerkki = textnm.getText().toString();
                settingschanged = true;
                closeKeyBoard();
            }
        });

        EditText textki = view.findViewById(R.id.kieli);
        textki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kieli = textki.getText().toString();
                settingschanged = true;
                closeKeyBoard();
            }
        });

        return view;
    }

    public void sendToActivity( ) {
        Intent intent = new Intent(getActivity().getBaseContext(),MainActivity.class);

        intent.putExtra("readonly",readonly);
        intent.putExtra("fontti",fontti);
        intent.putExtra("fonttikoko",fonttikoko);
        intent.putExtra("fonttityyli",fonttityyli);
        intent.putExtra("fonttivari",fonttivari);
        intent.putExtra("nimimerkki",nimimerkki);
        intent.putExtra("kieli",kieli);

        startActivity(intent);
        System.out.println("lähetty asetukset päänäyttöön");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    // sulkee softa näppäimistön kun teksti on syötett
    private void closeKeyBoard(){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}