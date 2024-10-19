package com.example.gestionpoints.controllers;

import android.view.View;

import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

public interface OnItemClickListener {

    default void onItemClick(View view, Evaluation evaluation) {
        // Implémentation par défaut (peut être vide)
    }

    default void onItemClick(Student student) {
        // Implémentation par défaut (peut être vide)
    }
}
