package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gestionpoints.R;

public class FooterFragment extends androidx.fragment.app.Fragment {

    // Définir une interface pour la communication avec l'activité
    public interface FooterListener {
        void onAddButtonClick();
        void onDeleteButtonClick();
    }

    private FooterListener listener;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        // Vérifier si l'activité implémente l'interface
        if (context instanceof FooterListener) {
            listener = (FooterListener) context;
        } else {
            throw new ClassCastException(" doit implémenter FooterListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_footer, container, false);
        v.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddButtonClick();
                } else {
                    Toast.makeText(getContext(), "Listener is null ON CREATEVIEW", Toast.LENGTH_SHORT).show();
                }
            }
        });


        v.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteButtonClick();
                } else {
                    Toast.makeText(getContext(), "Listener is null ON CREATEVIEW", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

}