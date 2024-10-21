package com.example.gestionpoints.controllers.Activities.SettingsActivity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddEvaluationDialogFragment;
import com.example.gestionpoints.controllers.Fragments.SettingsEvaluationsFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class SettingsEvaluationsActivity extends BaseActivity implements FooterFragment.FooterListener, SettingsEvaluationsFragment.Listener {

    public static final String ADD_EVALUATION_DIALOG_FRAGMENT = "AddEvaluationDialogFragment";
    private Evaluation learningActivity;
    private EvaluationManager evaluationManager;
    private GradeManager gradeManager;
    private StudentManager studentManager;
    ArrayList<Evaluation> selectedEvaluationList = new ArrayList<>();
    ArrayList<Evaluation> directSubEvaluation;
    private List<Integer> studentIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        gradeManager = new GradeManager(this);
        studentManager = new StudentManager(this);
        learningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        studentIdList = studentManager.getStudentIdList(learningActivity.getPromotionId());
        //TODO erroor peut etre
        directSubEvaluation =  evaluationManager.getEvaluationForParentEvaluation(learningActivity);
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return SettingsEvaluationsFragment.newInstance(directSubEvaluation);
    }
    @Override
    public void setupFooter(){
        setupFragment(R.id.footerContainer, new FooterFragment());
    }

    @Override
    public void onAddButtonClick() {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(learningActivity);
        dialogFragment.setAddItemListener(newEvaluation -> {
            evaluationManager.addEvaluation(newEvaluation);
            gradeManager.addGradeWhenNewEvaluation(newEvaluation.getId(),studentIdList);
            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), ADD_EVALUATION_DIALOG_FRAGMENT);
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Evaluation evaluation : selectedEvaluationList) {
                update = true;
                evaluationManager.deleteEvaluation(evaluation);
        }
        if (update) {
            replaceFragment();
            selectedEvaluationList.clear();
        }
    }

    public void onAddSubEvaluation(Evaluation parentEvaluation) {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(parentEvaluation);
        dialogFragment.setAddItemListener(newEvaluation -> {
            evaluationManager.addEvaluation(newEvaluation);
            gradeManager.addGradeWhenNewEvaluation(newEvaluation.getId(),studentIdList);
        replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), ADD_EVALUATION_DIALOG_FRAGMENT);
    }

    @Override
    public void onLongClick(View view, Evaluation evaluation) {
        evaluation.setSelected(!evaluation.isSelected());
       if (evaluation.isSelected()) {
           selectedEvaluationList.add(evaluation);
        } else {
          selectedEvaluationList.remove(evaluation);
        }
    }
    @Override
    public ArrayList<Evaluation> getChildrenForEvaluation(Evaluation evaluation) {
        return  evaluationManager.getEvaluationForParentEvaluation(evaluation);
    }


    private void replaceFragment() {
        directSubEvaluation =  evaluationManager.getEvaluationForParentEvaluation(learningActivity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, SettingsEvaluationsFragment.newInstance(directSubEvaluation))
                .commit();
    }
}