package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gestionpoints.R;

public abstract class AddItemDialogFragment<T> extends DialogFragment {

    public interface AddItemListener<T> {
        void onItemAdded(T item);
    }

    private AddItemListener<T> listener;
    protected View view;
    protected EditText itemNameInput;
    protected Button addItemButton;
    protected TextView titleTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retrieveView(inflater, container);
        fillText();
        addItemButton.setOnClickListener(v -> {
            String itemName = itemNameInput.getText().toString();
            if (!itemName.isEmpty()) {
                T newItem = createNewItem(itemName);
                if (listener != null) {
                    listener.onItemAdded(newItem);
                }
                dismiss();
            }
        });

        return view;
    }

    private void fillText() {
        titleTextView.setText(getTitle());
        addItemButton.setText("Ajouter " + getName());
        itemNameInput.setHint("Nom de " + getName());
    }

    protected void retrieveView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        view = inflater.inflate(R.layout.dialog_add_item, container, false);
        itemNameInput = view.findViewById(R.id.itemNameInput);
        addItemButton = view.findViewById(R.id.addItemButton);
        titleTextView = view.findViewById(R.id.dialogTitle);
    }

    public void setAddItemListener(AddItemListener<T> listener) {
        this.listener = listener;
    }

    protected abstract T createNewItem(String name);

    protected abstract String getTitle();

    protected abstract String getName();
}
