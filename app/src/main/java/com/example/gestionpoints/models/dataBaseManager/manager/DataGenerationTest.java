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

        // Générer la promotion BAC2
        Promotion promotion1 = new Promotion("BAC1",1);
        promotionManager.addPromotion(promotion1);

        // Générer la promotion BAC2
        Promotion promotion2 = new Promotion("BAC2", 2);
        promotionManager.addPromotion(promotion2);

        // Évaluation ProgrammationBAC2 (id: 1) sans parent
        Evaluation programmationBAC2 = new Evaluation(1, null, 2, 20, "ProgrammationBAC2");
        Evaluation electroniqueBAC2 = new Evaluation(14, null, 2, 20, "Electronique");
        Evaluation caoBAC2 = new Evaluation(15, null, 2, 20, "caoBAC2");

        evaluationManager.addEvaluation(programmationBAC2);
        evaluationManager.addEvaluation(electroniqueBAC2);
        evaluationManager.addEvaluation(caoBAC2);


        // Évaluations JavaB2 (id: 2) et WebB2 (id: 3) avec parent ProgrammationBAC2
        Evaluation javaB2 = new Evaluation(2, 1, 2, 20, "JavaB2");
        Evaluation webB2 = new Evaluation(3, 1, 2, 20, "WebB2");
        evaluationManager.addEvaluation(javaB2);
        evaluationManager.addEvaluation(webB2);

        // Générer la promotion BAC3
        Promotion promotion3 = new Promotion("BAC3", 3);
        promotionManager.addPromotion(promotion3);

        // Évaluation MobileBAC3 (id: 4) sans parent
        Evaluation mobileBAC3 = new Evaluation(4, null, 3, 20, "MobileBAC3");
        evaluationManager.addEvaluation(mobileBAC3);

        // Évaluations AndroidB3 (id: 5) et FlutterB3 (id: 6) avec parent MobileBAC3
        Evaluation androidB3 = new Evaluation(5, 4, 3, 20, "AndroidB3");
        Evaluation flutterB3 = new Evaluation(6, 4, 3, 20, "FlutterB3");
        evaluationManager.addEvaluation(androidB3);
        evaluationManager.addEvaluation(flutterB3);

        // Évaluations ORALAndroidB3 (id: 7) et ECRITAndroidB3 (id: 8) avec parent AndroidB3
        Evaluation oralAndroidB3 = new Evaluation(7, 5, 3, 20, "ORALAndroidB3");
        Evaluation ecritAndroidB3 = new Evaluation(8, 5, 3, 20, "ECRITAndroidB3");
        evaluationManager.addEvaluation(oralAndroidB3);
        evaluationManager.addEvaluation(ecritAndroidB3);

        // Évaluations ORALFlutterB3 (id: 9) et ECRITFlutterB3 (id: 10) avec parent FlutterB3
        Evaluation oralFlutterB3 = new Evaluation(9, 6, 3, 20, "ORALFlutterB3");
        Evaluation ecritFlutterB3 = new Evaluation(10, 6, 3, 20, "ECRITFlutterB3");
        evaluationManager.addEvaluation(oralFlutterB3);
        evaluationManager.addEvaluation(ecritFlutterB3);

        // Ajout des étudiants à BAC3
        Student student1 = new Student( "Dupont", "Alice", promotion3);
        Student student2 = new Student( "Martin", "Pierre", promotion3);
        Student student3 = new Student( "Bernard", "Lucie", promotion3);

        studentManager.addStudent(student1);
        studentManager.addStudent(student2);
        studentManager.addStudent(student3);

        gradeManager.addGrade(7, 1, 15.5f); // Note de 15.5 pour Dupont Alice à l'évaluation ORALAndroidB3
        gradeManager.addGrade(7, 2, 14.0f); // Note de 14.0 pour Martin Pierre à l'évaluation ORALAndroidB3
        gradeManager.addGrade(7, 3, 16.5f); // Note de 16.5 pour Bernard Lucie à l'évaluation ORALAndroidB3

        gradeManager.addGrade(8, 1, 13.0f); // Note de 13.0 pour Dupont Alice à l'évaluation ECRITAndroidB3
        gradeManager.addGrade(8, 2, 15.0f); // Note de 15.0 pour Martin Pierre à l'évaluation ECRITAndroidB3
        gradeManager.addGrade(8, 3, 14.5f); // Note de 14.5 pour Bernard Lucie à l'évaluation ECRITAndroidB3

   }
}