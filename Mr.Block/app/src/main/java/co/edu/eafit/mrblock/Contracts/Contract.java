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

    public static abstract class UbicationContract implements BaseColumns{
        public static final String TABLE_NAME = "Ubication";
        public static final String COLUMN_PLACE = "Place";
        public static final String COLUMN_LATITUDE = "Latitud";
        public static final String COLUMN_LONGITUDE = "Longitud";
    }

    public static abstract class DateContract implements BaseColumns{
        public static final String TABLE_NAME = "DateIn";
        public static final String COLUMN_NUMBER = "Number";
        public static final String COLUMN_YEAR_1 = "Year1";
        public static final String COLUMN_MONTH_1 = "Month1";
        public static final String COLUMN_DAY_1 = "Day1";
        public static final String COLUMN_HOUR_1 = "Hour1";
        public static final String COLUMN_MINUTE_1 = "Minute1";
        public static final String COLUMN_SECOND_1 = "Second1";
        public static final String COLUMN_YEAR_2 = "Year2";
        public static final String COLUMN_MONTH_2 = "Month2";
        public static final String COLUMN_DAY_2 = "Day2";
        public static final String COLUMN_HOUR_2 = "Hour2";
        public static final String COLUMN_MINUTE_2 = "Minute2";
        public static final String COLUMN_SECOND_2 = "Second2";

    }



}
