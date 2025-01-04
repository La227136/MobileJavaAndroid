package com.example.gestionpoints.views.dialogFragment;

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
import com.example.gestionpoints.models.exception.ExceptionTextField;

public abstract class AddItemDialogFragment<T> extends DialogFragment {

    public interface AddItemListener<T> {
        void onItemAdded(T item);
    }

    private AddItemListener<T> mListener;
    protected View mView;
    protected EditText mItemNameInput;
    protected Button mAddItemButton;
    protected TextView mTitleTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retrieveView(inflater, container);
        fillText();
        mAddItemButton.setOnClickListener(v -> {
            String itemName = mItemNameInput.getText().toString().trim();
            if (!isValidInput(itemName)) {
                return;
            }
            T newItem = createNewItem(itemName);
            mListener.onItemAdded(newItem);
            dismiss();
        });
        return mView;
    }

    protected void retrieveView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.dialog_add_item, container, false);
        mItemNameInput = mView.findViewById(R.id.itemNameInput);
        mAddItemButton = mView.findViewById(R.id.addItemButton);
        mTitleTextView = mView.findViewById(R.id.dialogTitle);
    }

    private void fillText() {
        mTitleTextView.setText(getTitle());
        mAddItemButton.setText("Ajouter " + getName());
        mItemNameInput.setHint("Nom de " + getName());
    }

    private boolean isValidInput(String itemName) {
        try {
            isErrorTextField(itemName);
            return true;
        } catch (ExceptionTextField e) {
            e.setErrorMessage(mItemNameInput);
            return false;
        }
    }

    protected void isErrorTextField(String itemName) throws ExceptionTextField {
        if (itemName.isEmpty())
            throw new ExceptionTextField("Le champ ne peut pas être vide");
        if (itemName.length() > 40) {
            throw new ExceptionTextField("Le nom ne doit pas dépasser 20 caractères");
        }
        if (!itemName.matches("[a-zA-Z0-9 ]+")) {
            throw new ExceptionTextField("Caractères spéciaux non autorisés");
        }

    }

    public void setAddItemListener(AddItemListener<T> listener) {
        this.mListener = listener;
    }

    protected abstract T createNewItem(String name) ;

    protected abstract String getTitle();

    protected abstract String getName();

}
