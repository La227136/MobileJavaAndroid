package com.example.gestionpoints.Utils;

import android.view.View;
import android.widget.LinearLayout;

public class MarginUtils {

    public static void setMargin(View classeView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 32, 16, 0); // Margins

        classeView.setLayoutParams(params);
    }

}