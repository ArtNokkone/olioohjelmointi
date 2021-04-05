package com.example.activitytrill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadOnly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadOnly extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView content;
    private View view;

    public ReadOnly() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadOnly.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadOnly newInstance(String param1, String param2) {
        ReadOnly fragment = new ReadOnly();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_read_only, container, false);

        return view;
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_read_only, container, false);
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle saveInstanceState) {
        try {
            // lue pääohjelmasta annettu uusi teksti ja kirjoita se näytölle
            String arg = getArguments().getString("text");
            content = view.findViewById(R.id.readonlytext);
            content.setText(arg);
            //System.out.println("teksti : " + arg);

        } catch (Exception e) {
        }
    }

}