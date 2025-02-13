package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupWindowInsets();
        setupTitle();
        setupMiddlePage();
        setupFooter();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.middlePageContainer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void setupTitle() {
        ((TextView) (findViewById(R.id.pageTitle))).setText(getTitlePage());
    }

    public void setupMiddlePage() {
        setupFragment(R.id.middlePageContainer, getMiddleFragmentToLaunch());
    }

    public void setupFooter() {
    }

    public void setupFragment(int containerId, Fragment fragment) {
        Fragment existingFragment = getSupportFragmentManager().findFragmentById(containerId);
        if (existingFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(containerId, fragment)
                    .commit();
        }
    }
    public abstract Fragment getMiddleFragmentToLaunch();
    public abstract String getTitlePage();
}
