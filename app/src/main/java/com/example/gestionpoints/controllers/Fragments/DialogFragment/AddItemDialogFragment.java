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
import com.example.gestionpoints.models.ExceptionTextField;

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
            String itemName = itemNameInput.getText().toString().trim();
            try {
                isErrorTextField(itemName);
            } catch (ExceptionTextField e) {
                e.setErrorMessage(itemNameInput);
                return;
            }
             if(createNewItem(itemName) == null)
                 return;
              T  newItem = createNewItem(itemName);
            listener.onItemAdded(newItem);

            dismiss();
        });

        return view;
    }

    protected void isErrorTextField(String itemName) throws ExceptionTextField {
        if (itemName.isEmpty())
            throw new ExceptionTextField("Le champ ne peut pas être vide");
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

    protected abstract T createNewItem(String name) ;

    protected abstract String getTitle();

    protected abstract String getName();

}
