package com.example.gestionpoints.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;

public class FooterFragment extends Fragment {

    public interface FooterListener {
        void onAddButtonClick();

        void onDeleteButtonClick();
    }

    private FooterListener mListener;
    private Button mAddBtn;
    private Button mDeleteBtn;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        // Vérifier si l'activité implémente l'interface
        if (context instanceof FooterListener) {
            mListener = (FooterListener) context;
        } else {
            throw new ClassCastException(" doit implémenter FooterListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_footer, container, false);
        retrieveView(v);
        setButtonssText();
        setupButtonsListeners(mAddBtn, mDeleteBtn);
        return v;
    }

    private void setButtonssText() {
        mAddBtn.setText("Ajouter");
        mDeleteBtn.setText("Supprimer");
    }

    private void retrieveView(View v) {
        mAddBtn = v.findViewById(R.id.addBtn);
        mDeleteBtn = v.findViewById(R.id.deleteBtn);
    }

    private void setupButtonsListeners(Button addBtn, Button deleteBtn) {
        addBtn.setOnClickListener(v -> handleAddButtonClick(v));
        deleteBtn.setOnClickListener(v -> handleDeleteButtonClick(v));
    }

    private void handleAddButtonClick(View v) {
        if (mListener != null) {
            mListener.onAddButtonClick();
        } else {
            showToast("Listener is null ON CREATEVIEW");
        }
    }

    private void handleDeleteButtonClick(View v) {
        if (mListener != null) {
            mListener.onDeleteButtonClick();
        } else {
            showToast("Listener is null ON CREATEVIEW");
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}