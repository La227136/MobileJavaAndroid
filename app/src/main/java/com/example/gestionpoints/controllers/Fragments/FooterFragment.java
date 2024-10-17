package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;

public class FooterFragment extends Fragment {

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
        setupButtonListeners(v);
        return v;
    }

    private void setupButtonListeners(View v) {
        v.findViewById(R.id.addBtn).setOnClickListener(this::handleAddButtonClick);
        v.findViewById(R.id.deleteBtn).setOnClickListener(this::handleDeleteButtonClick);
    }

    private void handleAddButtonClick(View v) {
        if (listener != null) {
            listener.onAddButtonClick();
        } else {
            showToast("Listener is null ON CREATEVIEW");
        }
    }

    private void handleDeleteButtonClick(View v) {
        if (listener != null) {
            listener.onDeleteButtonClick();
        } else {
            showToast("Listener is null ON CREATEVIEW");
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}