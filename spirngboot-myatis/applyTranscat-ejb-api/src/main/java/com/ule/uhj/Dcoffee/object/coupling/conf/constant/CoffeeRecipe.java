package com.ule.uhj.Dcoffee.object.coupling.conf.constant;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class CoffeeRecipe {
    public static final String packageName = "com.ule.uhj.Dcoffee";

    public static final String SHADOW = "proxy";

    public static final String SINGLE = "single";

    public enum SortType{
        ASC,
        DESC
    }

    public static final String FILTER_SCRIPT_JS = "javascript";

    public static class Filter{
        public static final String TYPE_SUFFIX = "suffix";
        public static final String TYPE_PREFIX = "prefix";
        public static final String PARAMETER_COFFEE = "coffee";
    }

    public enum AssignmentType{
        EQUAL,
        LIKE,
        LIKE_PREFIX,
        LIKE_SUFFIX
    }

    public enum SqlState{
        START(0),
        MAIN_TABLE(1),
        ADDITION_TABLE(2),
        WHERE(3);
        private int value;
        private SqlState(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }

//        public void setValue(int value) {
//            this.value = value;
//        }
    }

    public class VirtualTable{
        public static final String TYPE_DATABASE_DYNAMIC = "DataBaseExecuteTable";
    }

    public class Source{
        public class DataBaseSource{
            public static final String type = "DataBase";
            public static final String alias = "DSOURCE";
            public class Relation{
                public static final String MAIN = "0";
                public static final String LEFT = "1";
                public static final String RIGHT = "2";
            }
        }
    }

    public class DataBase{
        public static final String SCHEMA = "ULEAPP_FINANCECR";
    }

    public class DataSet{
        public static final String SUPPORT_REPORT ="report";
    }

    public class JDBC_TYPE{
        public static final String VARCHAR = "java.util.String.class";
    }

    public class Class{
        public static final String nameEnd = ".class";
    }
}