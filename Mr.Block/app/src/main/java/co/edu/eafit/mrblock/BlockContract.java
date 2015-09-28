package co.edu.eafit.mrblock;

import android.provider.BaseColumns;

/**
 * Created by juan on 25/09/15.
 */
public class BlockContract {

    public BlockContract(){}

    public static abstract class BlockEntry implements BaseColumns{
        public static final String TABLE_NAME = "Block";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_NUMBER = "Number";
    }

}
