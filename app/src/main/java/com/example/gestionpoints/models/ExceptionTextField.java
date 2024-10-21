package com.example.gestionpoints.models;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;



public class ExceptionTextField extends Exception {
    public ExceptionTextField(String message) {
        super(message);
    }

    public void ShowToast(Context context) {
        Toast.makeText(context, getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void setErrorMessage(EditText itemName) {
        itemName.setError(getMessage());
    }
}
