package com.example.android.sleeptight.data;

import android.provider.BaseColumns;

/**
 * Sleep Tight created by JCoupier on 04/07/2017.
 *
 * API Contract for the Sleep Tight app.
 */
public final class SleepContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private SleepContract() {}

    /**
     * Inner class that defines constant values for the sleep habit database table.
     * Each entry in the table represents a single sleep period.
     */
    public static final class SleepEntry implements BaseColumns {

        /** Name of database table for sleep periods */
        public final static String TABLE_NAME = "asleep";

        /**
         * Unique ID number for the sleep period (only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Type of the sleep period (e.g. power nap, night sleep, etc.).
         * Type: TEXT
         */
        public final static String COLUMN_ASLEEP_TYPE ="type";

        /**
         * Date of the sleep period.
         * Type: TEXT
         */
        public final static String COLUMN_ASLEEP_DATE = "date";

        /**
         * Duration of the sleep period (in min).
         * Type: INTEGER
         */
        public final static String COLUMN_ASLEEP_DURATION = "duration";

        /**
         * Feeling after the sleep period.
         * The only possible values are {@link #FEELING_VERY_BAD}, {@link #FEELING_BAD},
         * {@link #FEELING_NEUTRAL}, {@link #FEELING_WELL}, or {@link #FEELING_VERY_WELL}.
         * Type: INTEGER
         */
        public final static String COLUMN_ASLEEP_FEELING = "feeling";

        /**
         * Comment about the sleep period (e.g. specific dream, noise problem, etc.).
         * Type: TEXT
         */
        public final static String COLUMN_ASLEEP_COMMENT = "comment";

        /**
         * Possible values for the feeling after the sleep period.
         */
        public static final int FEELING_VERY_BAD = -2;
        public static final int FEELING_BAD = -1;
        public static final int FEELING_NEUTRAL = 0;
        public static final int FEELING_WELL = 1;
        public static final int FEELING_VERY_WELL = 2;
    }
}
