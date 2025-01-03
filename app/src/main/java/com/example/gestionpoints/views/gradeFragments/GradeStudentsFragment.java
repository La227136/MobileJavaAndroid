package com.example.gestionpoints.views.gradeFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Student;

import java.util.ArrayList;

public class GradeStudentsFragment extends Fragment {

    private ArrayList<Student> mStudentList;
    private LinearLayout mStudentListContainer;
    private Listener mListener;
    private GradeManager mGradeManager;
    private Evaluation mLearningActivity;
    private TextView mFullNameTextView;
    private TextView mStudentGradeTextView;

    public interface Listener {
        void onItemClick(Student student);
    }

    public static GradeStudentsFragment newInstance(ArrayList<Student> studentList, Evaluation learningActivity) {
        GradeStudentsFragment fragment = new GradeStudentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.STUDENTLIST, studentList);
        args.putSerializable(IntentKeys.LEARNING_ACTIVITY,learningActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter BaseActivity.Listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mGradeManager = new GradeManager(getContext());
        super.onCreate(savedInstanceState);
        if (mStudentList == null) {
            mStudentList = new ArrayList<>();
        }
        if (getArguments() != null) {
            mStudentList = (ArrayList<Student>) getArguments().getSerializable(IntentKeys.STUDENTLIST);
            mLearningActivity = (Evaluation) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        mStudentListContainer = view.findViewById(R.id.fragmentContainer);
        if(mStudentList != null){
            displayStudents(inflater);
        }
        return view;
    }

    private void displayStudents(LayoutInflater inflater) {
        for (Student student : mStudentList) {
            mStudentListContainer.addView(createStudentItem(inflater, student));
        }
    }

    private @NonNull View createStudentItem(LayoutInflater inflater, Student student) {
        View studentItem = getStudentItem(inflater);
        retrieveView(studentItem);
        setStudentItemData(student);
        setUpOnClickListener(studentItem,student);
        return studentItem;
    }
    private View getStudentItem(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_student, mStudentListContainer, false);
    }
    private void retrieveView(View studentItem) {
        mFullNameTextView = studentItem.findViewById(R.id.studentFullNameTextView);
        mStudentGradeTextView = studentItem.findViewById(R.id.studentGradeTextView);
    }
    private void setUpOnClickListener(View studentItem, Student student) {
        studentItem.setOnClickListener((view) -> {
            mListener.onItemClick(student);
        });
    }

    private void setStudentItemData(Student student) {
        mStudentGradeTextView.setText(String.valueOf(mGradeManager.getRoundedGrade(mLearningActivity.getId(), student.getId())));
        mFullNameTextView.setText(student.getLastName() + " " + student.getFirstName());
    }
}
