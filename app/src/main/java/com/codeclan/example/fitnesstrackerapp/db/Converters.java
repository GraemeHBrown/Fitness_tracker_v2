package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by graemebrown on 27/01/2018.
 */

class Converters {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
