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
import com.example.gestionpoints.controllers.Fragments.FooterFragment;

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


    public  void setupHeader(){
        ((TextView)(findViewById(R.id.pageTitle))).setText("dfsfsd");
    }



    public void setupMiddlePage() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.middlePageContainer);
        if (fragment == null) {
            fragment = getFragmentToLaunch();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.middlePageContainer, fragment)
                    .commit();
        }
    }

    public void setupFooter() {

        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.footerContainer);
        if (fragment == null) {
            fragment = new FooterFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .add(R.id.footerContainer, fragment)
                    .commit();
        }
    }

    public abstract Fragment getFragmentToLaunch();


}
