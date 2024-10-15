package com.example.gestionpoints.models.dataBaseManager.dbSchema;

public abstract class BulletinDBSchema {

    public static final class PromotionTable {
        public static final String NAME = "Promotion";

        public static final class Cols {
            public static final String ID = "id";
            public static final String PROMOTION_NAME ="promotion_name";
        }
    }

    public static final class StudentTable {
        public static final String NAME = "Student";

        public static final class Cols {
            public static final String ID = "id";
            public static final String PROMOTION_ID = "promotion_id";
            public static final String LAST_NAME ="last_name";
            public static final String FIRST_NAME ="first_name";

        }
    }

    public static final class EvaluationTable {
        public static final String NAME = "Evaluation";

        public static final class Cols {
            public static final String EVALUATION_NAME = "evaluation_name";
            public static final String ID = "id";
            public static final String PARENT_ID = "parent_id";
            public static final String PROMOTION_ID = "promotion_id";
            public static final String MAX_GRADE = "max_grade";
        }
    }

    public static final class GradeTable {
        public static final String NAME = "Grade";

        public static final class Cols {
            public static final String EVALUATION_ID = "evaluation_id";
            public static final String STUDENT_ID = "student_id";
            public static final String GRADE = "grade";
        }
    }
}
