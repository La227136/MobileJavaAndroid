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
            try {
                isErrorTextField(itemName);
            } catch (ExceptionTextField e) {
                e.setErrorMessage(mItemNameInput);
                return;
            }
             if(createNewItem(itemName) == null)
                 return;
              T  newItem = createNewItem(itemName);
            mListener.onItemAdded(newItem);

            dismiss();
        });

        return mView;
    }

    protected void isErrorTextField(String itemName) throws ExceptionTextField {
        if (itemName.isEmpty())
            throw new ExceptionTextField("Le champ ne peut pas être vide");
    }

    private void fillText() {
        mTitleTextView.setText(getTitle());
        mAddItemButton.setText("Ajouter " + getName());
        mItemNameInput.setHint("Nom de " + getName());
    }

    protected void retrieveView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.dialog_add_item, container, false);
        mItemNameInput = mView.findViewById(R.id.itemNameInput);
        mAddItemButton = mView.findViewById(R.id.addItemButton);
        mTitleTextView = mView.findViewById(R.id.dialogTitle);
    }

    public void setAddItemListener(AddItemListener<T> listener) {
        this.mListener = listener;
    }

    protected abstract T createNewItem(String name) ;

    protected abstract String getTitle();

    protected abstract String getName();

}
