package com.example.gestionpoints.models.dataBaseManager.manager;


import android.content.Context;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;
import com.example.gestionpoints.models.evaluation.Evaluation;

public class DataGenerationTest {

    private PromotionManager promotionManager;
    private StudentManager studentManager;
    private EvaluationManager evaluationManager;
    private GradeManager gradeManager;

    public DataGenerationTest(Context context) {
        promotionManager = new PromotionManager(context);
        studentManager = new StudentManager(context);
        evaluationManager = new EvaluationManager(context);
        gradeManager = new GradeManager(context);  // Initialise le GradeManager

    }

    public void generateTestData() {
        // Generate promotions
        Promotion promotion1 = new Promotion("BAC1", 1);
        Promotion promotion2 = new Promotion("BAC2", 2);
        promotionManager.addPromotion(promotion1);
        promotionManager.addPromotion(promotion2);

        // Generate evaluations
        Evaluation android = new Evaluation(1, null, 1, 20, "Android");
        Evaluation flooter = new Evaluation(2, null, 2, 20, "Flooter");
        evaluationManager.addEvaluation(android);
        evaluationManager.addEvaluation(flooter);

        Evaluation oral = new Evaluation(3, 1, 1, 20, "Oral");
        Evaluation written = new Evaluation(4, 1, 1, 20, "Written");
        evaluationManager.addEvaluation(oral);
        evaluationManager.addEvaluation(written);



        // Generate students
        Student student1 = new Student(1, "Doe", "John", 1);
        Student student2 = new Student(2, "Smith", "Jane", 1);
        Student student3 = new Student(3, "Brown", "Michael", 1);
        studentManager.addStudent(student1);
        studentManager.addStudent(student2);
        studentManager.addStudent(student3);

        // Ajouter des notes pour les étudiants pour les évaluations "Oral" et "Written"
        gradeManager.addGrade(3, 1, 15.5f);  // John Doe, Oral
        gradeManager.addGrade(4, 1, 18.0f);  // John Doe, Written

        gradeManager.addGrade(3, 2, 12.0f);  // Jane Smith, Oral
        gradeManager.addGrade(4, 2, 14.5f);  // Jane Smith, Written

        gradeManager.addGrade(3, 3, 17.0f);  // Michael Brown, Oral
        gradeManager.addGrade(4, 3, 16.0f);  // Michael Brown, Written

    }
}
