package com.example.gestionpoints.models.exception;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class ExceptionTextField extends Exception {
    public ExceptionTextField(String message) {
        super(message);
    }
    public void setErrorMessage(EditText itemName) {
        itemName.setError(getMessage());
    }

    public void ShowToast(Context context) {
        Toast.makeText(context, getMessage(), Toast.LENGTH_SHORT).show();
    }
}
