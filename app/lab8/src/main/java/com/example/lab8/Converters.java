package com.example.lab8;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    // Conversie Date -> Long (pentru stocare in baza de date)
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    // Conversie Long -> Date (pentru citire din baza de date)
    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    // Conversie TipPanel -> String
    @TypeConverter
    public static String tipPanelToString(TipPanel tipPanel) {
        return tipPanel == null ? null : tipPanel.name();
    }

    // Conversie String -> TipPanel
    @TypeConverter
    public static TipPanel stringToTipPanel(String value) {
        return value == null ? null : TipPanel.valueOf(value);
    }
}
