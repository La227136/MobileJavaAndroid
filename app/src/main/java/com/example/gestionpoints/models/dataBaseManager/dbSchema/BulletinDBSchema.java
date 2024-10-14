package com.example.gestionpoints.models.dataBaseManager.dbSchema;

public abstract class BulletinDBSchema {

    public static final class PromotionTable {
        public static final String NAME = "Promotion";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
        }
    }

    public static final class StudentTable {
        public static final String NAME = "Student";  // Changed to English

        public static final class Cols {
            public static final String ID = "id";
            public static final String LAST_NAME = "last_name";
            public static final String FIRST_NAME = "first_name";
            public static final String PROMOTION_ID = "promotion_id";
        }
    }

    public static final class EvaluationTable {
        public static final String NAME = "Evaluation";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String MAX_POINTS = "max_points";
            public static final String PARENT_TYPE = "parent_type";
            public static final String PARENT_ID = "parent_id";
            public static final String PROMOTION_ID = "promotion_id";  // Added the promotion_id as in the helper class
        }
    }

    public static final class PointsTable {
        public static final String NAME = "Points";

        public static final class Cols {
            public static final String STUDENT_ID = "student_id";  // Changed to English
            public static final String EVALUATION_ID = "evaluation_id";
            public static final String POINTS_OBTAINED = "points_obtained";  // Changed to English
        }
    }
}
