package com.example.gestionpoints.controllers.Activities.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddLearningActivitiesDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.CommunLearningActivitesFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;

import java.util.ArrayList;
import java.util.List;

public class SettingsLearningActivitiesListActivity extends BaseActivity implements FooterFragment.FooterListener, CommunLearningActivitesFragment.Listener {

    private EvaluationManager evaluationManager;
    private GradeManager gradeManager;
    private Promotion promotion;
    private StudentManager studentManager;
    private ArrayList<Evaluation> learningActivityList = new ArrayList<>();
    ArrayList<Evaluation> selectedLearningActivity = new ArrayList<>();
    private List<Integer> studentIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        studentManager = new StudentManager(this);
        evaluationManager = new EvaluationManager(this);
        gradeManager = new GradeManager(this);
        initializeAttributes(savedInstanceState);
        studentIdList = studentManager.getStudentIdList(promotion.getId());
        super.onCreate(savedInstanceState);
    }

    private void initializeAttributes(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            promotion = (Promotion) savedInstanceState.getSerializable(IntentKeys.PROMOTION);
            learningActivityList = (ArrayList<Evaluation>) savedInstanceState.getSerializable(IntentKeys.LEARNING_ACTIVITIES);
        } else {
            promotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
            learningActivityList = getLearningActivityList();
        }
    }

    //region BaseActivity releted methods
    @Override
    public String getTitlePage() {
        return "Learning Activities";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return CommunLearningActivitesFragment.newInstance(promotion, learningActivityList);
    }

    @Override
    public void setupFooter() {
        setupFragment(R.id.footerContainer, new FooterFragment());
    }
    //endregion

    // region FooterListener related methods
    @Override
    public void onAddButtonClick() {
        AddLearningActivitiesDialogFragment dialogFragment = new AddLearningActivitiesDialogFragment(promotion.getId());
        dialogFragment.setAddItemListener(newLearningActivity -> {
            evaluationManager.addEvaluation(newLearningActivity);
            gradeManager.addGrade(newLearningActivity.getId(),studentIdList);
            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), "AddLearningActivitiesFragment");
    }

    @Override
    public void onDeleteButtonClick() {
      if(!selectedLearningActivity.isEmpty()) {
          for (Evaluation evaluation : selectedLearningActivity) {
              evaluationManager.deleteEvaluation(evaluation);
          }
          replaceFragment();
          selectedLearningActivity.clear();
      }
    }

    private void replaceFragment() {
        learningActivityList = getLearningActivityList();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, CommunLearningActivitesFragment.newInstance(promotion, learningActivityList))
                .commit();
    }
    //endregion

    //region CommunLearningActivitesFragment.Listener related methods
    @Override
    public void onLearningActivityLongClicked(Evaluation learningActivity) {
        learningActivity.setSelected(!learningActivity.isSelected());
        if (learningActivity.isSelected()) {
            selectedLearningActivity.add(learningActivity);
        } else {
            selectedLearningActivity.remove(learningActivity);
        }
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
        openEvaluationDetails(evaluation);
    }

    private void openEvaluationDetails(Evaluation evaluation) {
        Intent evalDetailsIntent = new Intent(getApplicationContext(), SettingsEvaluationsActivity.class);
        evalDetailsIntent.putExtra(IntentKeys.LEARNING_ACTIVITY, evaluation);
        startActivity(evalDetailsIntent);
    }
    //endregion

    public ArrayList<Evaluation> getLearningActivityList() {
        ArrayList<Evaluation> evaluationList = evaluationManager.getEvaluationsForPromotion(promotion);
        ArrayList<Evaluation> learningActivityList = new ArrayList<>();
        for (Evaluation evaluation : evaluationList) {
            if (evaluation.getParentId() == 0)
                learningActivityList.add(evaluation);
        }
        return learningActivityList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.LEARNING_ACTIVITIES, learningActivityList);
        outState.putSerializable(IntentKeys.PROMOTION, promotion);
    }
}
