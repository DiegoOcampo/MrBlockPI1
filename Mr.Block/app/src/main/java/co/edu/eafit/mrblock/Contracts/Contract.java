package co.edu.eafit.mrblock.Contracts;

import android.provider.BaseColumns;

/**
 * Created by juan on 25/09/15.
 */
public class Contract {

    public Contract(){}

    public static abstract class ContactInContract implements BaseColumns{
        public static final String TABLE_NAME = "ContactsIn";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_NUMBER = "Number";
    }

    public static abstract class CallInContract implements BaseColumns{
        public static final String TABLE_NAME = "CallsIn";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_NUMBER = "Number";
    }

    public static abstract class DateContract implements BaseColumns{
        public static final String TABLE_NAME = "DateIn";
        public static final String COLUMN_YEAR = "Year";
        public static final String COLUMN_MONTH = "Month";
        public static final String COLUMN_DAY = "Day";
        public static final String COLUMN_HOUR = "Hour";
        public static final String COLUMN_MINUTE = "Minute";
        public static final String COLUMN_SECOND = "Second";
        public static final String COLUMN_NUMBER = "Number";

    }



}
