package io.shellee.movies.repository;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalTime;

public class LocalTimeConverter {
    @TypeConverter
    public static LocalTime toDate(Long timestamp) {
        return timestamp == null ? null : LocalTime.ofNanoOfDay(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(LocalTime localTime) {
        return localTime == null ? null : localTime.toNanoOfDay();
    }
}
