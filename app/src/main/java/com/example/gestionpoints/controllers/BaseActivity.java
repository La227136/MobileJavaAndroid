package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionpoints.R;

public abstract class BaseActivity extends AppCompatActivity implements OnItemClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(getLayoutResId());

        setupWindowInsets();

        setupHeader();

        setupMiddlePage();

        setupFooter();
    }


    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(getViewResId()), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    public abstract int getViewResId();

        public abstract int getLayoutResId();

    public abstract void setupFooter();

    public abstract void setupHeader();

    public abstract void setupMiddlePage();

}
