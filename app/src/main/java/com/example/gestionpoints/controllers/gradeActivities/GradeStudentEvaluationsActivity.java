package com.example.gestionpoints.controllers.gradeActivities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.gradeFragments.GradeStudentEvaluationsFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Grade;
import com.example.gestionpoints.models.Student;

import java.util.ArrayList;
import java.util.List;

public class GradeStudentEvaluationsActivity extends BaseActivity implements GradeStudentEvaluationsFragment.Listener {
    private Student mStudent;
    private Evaluation mLearningActivity;
    private GradeManager mGradeManager;
    private EvaluationManager mEvaluationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEvaluationManager = new EvaluationManager(this);
        mGradeManager = new GradeManager(this);
        mStudent = (Student) getIntent().getSerializableExtra(IntentKeys.STUDENT);
        mLearningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        super.onCreate(savedInstanceState);
    }

    //region BaseActivity related methods
    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return GradeStudentEvaluationsFragment.newInstance(mStudent, mLearningActivity);
    }
    @Override
    public String getTitlePage() {
        return "Points: "+ mStudent.getFirstName() + " " + mStudent.getLastName();
    }
    @Override
    public void setupFooter() {
        // Pas de footer
    }
    //endregion

    // region GradeStudentEvaluationsFragment.Listener related methods
    @Override
    public Grade createGrade(Student student, Evaluation evaluation) {
        return new Grade(student,evaluation, getRoundGrade(student, evaluation));
    }

    @Override
    public void updateGrade(Grade grade, float newGrade) {
        mGradeManager.updateGrade(grade,newGrade);
        updateParentGrades(grade.getEvaluation());
    }

    private void updateParentGrades(Evaluation evaluation) {
        // Obtenir l'évaluation parente
        Evaluation parentEvaluation = mEvaluationManager.getParentEvaluation(evaluation);
        if (parentEvaluation != null) {
            // Recalculer la note pour l'évaluation parente
            float newGradeValue = calculateGradeForEvaluation(parentEvaluation);

            // Mettre à jour la note dans la base de données
            Grade parentGrade = new Grade(mStudent, parentEvaluation, newGradeValue);
            mGradeManager.updateGrade(parentGrade, newGradeValue);

            // Appeler récursivement pour les parents supérieurs
            updateParentGrades(parentEvaluation);
        }
    }

    public float calculateGradeForEvaluation(Evaluation evaluation) {
        // Obtenir les sous-évaluations de l'évaluation actuelle
        List<Evaluation> subEvaluations = mEvaluationManager.getEvaluationForParentEvaluation(evaluation);
        if (subEvaluations == null || subEvaluations.isEmpty()) {
            // Si pas de sous-évaluations, retourner la note actuelle de l'évaluation
            float gradeValue = getRoundGrade(mStudent, evaluation);
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
        return mEvaluationManager.getEvaluationForParentEvaluation(evaluation);
    }
    private float getRoundGrade(Student student, Evaluation evaluation) {
        return mGradeManager.getRoundedGrade(evaluation.getId(), student.getId());
    }
    //endregion

    @Override
    public void replaceFragment() {
        // Vérif si l'activité est toujours active
        // Si on fait pas cette vérif si l'utilisateur appuie sur le btn back
        // alors que il a tjrs son curseur actif sur une note
        // l'application va crasher
        // Quand le utilisateur appuie sur le btn back, il y a un onFocusChange donc ca appelle la méthode onFocusChange qui appelle replaceFragment
        if (!isFinishing() && !isDestroyed()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.middlePageContainer, GradeStudentEvaluationsFragment.newInstance(mStudent, mLearningActivity))
                    .commit();
        }
    }

}