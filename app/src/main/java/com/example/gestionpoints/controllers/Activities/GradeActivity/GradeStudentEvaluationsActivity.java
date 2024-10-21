package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeStudentEvaluationsFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.grade.Grade;
import com.example.gestionpoints.models.student.Student;

import java.util.ArrayList;
import java.util.List;

public class GradeStudentEvaluationsActivity extends BaseActivity implements GradeStudentEvaluationsFragment.Listener {
private Student student;
private Evaluation learningActivity;
private GradeManager gradeManager;
private EvaluationManager evaluationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        gradeManager = new GradeManager(this);
        student = (Student) getIntent().getSerializableExtra(IntentKeys.STUDENT);
       learningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return GradeStudentEvaluationsFragment.newInstance(student, learningActivity);
    }

    @Override
    public String getTitlePage() {
        return "Points: "+student.getFirstName() + " " + student.getLastName();
    }
    @Override
    public void setupFooter() {
        // Pas de footer
    }

    @Override
    public Grade getGrade(Student student, Evaluation evaluation) {
        gradeManager = new GradeManager(this);
        return new Grade(student,evaluation, gradeManager.getGrade(evaluation.getId(),student.getId()));
    }

    @Override
    public void updateGrade(Grade grade, float editableGrade) {
        //grade.setGrade(editableGrade);
        gradeManager.updateGrade(grade,editableGrade);

        updateParentGrades(grade.getEvaluation());


    }

    private void updateParentGrades(Evaluation evaluation) {
        // Obtenir l'évaluation parente
        Evaluation parentEvaluation = evaluationManager.getParentEvaluation(evaluation);
        if (parentEvaluation != null) {
            // Recalculer la note pour l'évaluation parente
            float newGradeValue = calculateGradeForEvaluation(parentEvaluation);

            // Mettre à jour la note dans la base de données
            Grade parentGrade = new Grade(student, parentEvaluation, newGradeValue);
            gradeManager.updateGrade(parentGrade, newGradeValue);

            // Appeler récursivement pour les parents supérieurs
            updateParentGrades(parentEvaluation);
        }
    }

    public float calculateGradeForEvaluation(Evaluation evaluation) {
        // Obtenir les sous-évaluations de l'évaluation actuelle
        List<Evaluation> subEvaluations = evaluationManager.getEvaluationForParentEvaluation(evaluation);
        if (subEvaluations == null || subEvaluations.isEmpty()) {
            // Si pas de sous-évaluations, retourner la note actuelle de l'évaluation
            float gradeValue = gradeManager.getGrade(evaluation.getId(), student.getId());
            return gradeValue >= 0 ? gradeValue : 0;
        } else {
            // Calculer la note en fonction des sous-évaluations
            float totalGrade = 0;
            float totalMaxGrade = 0;
            for (Evaluation subEval : subEvaluations) {
                float subGrade = calculateGradeForEvaluation(subEval);
                totalGrade += subGrade;
                totalMaxGrade += subEval.getMaxGrade();
            }
            if (totalMaxGrade == 0) return 0;
            // Calculer la note pondérée proportionnellement à la note maximale de l'évaluation parente
            return (totalGrade / totalMaxGrade) * evaluation.getMaxGrade();
        }
    }


    @Override
    public ArrayList<Evaluation> getEvalutionForParentEvaluation(Evaluation evaluation) {
        return evaluationManager.getEvaluationForParentEvaluation(evaluation);
    }
}