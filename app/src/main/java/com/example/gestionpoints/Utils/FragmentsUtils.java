package com.example.gestionpoints.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;

public class FragmentsUtils {

    public static void displayFooterFragment(FragmentManager fragmentManager, int containerId) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.footerContainer);
        if (fragment == null) {
            fragment = new FooterFragment();
            fragmentManager.beginTransaction()
                    .add(containerId, fragment)
                    .commit();
        }
    }



}