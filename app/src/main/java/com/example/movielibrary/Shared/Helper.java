package com.example.movielibrary.Shared;

import android.content.res.Resources;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    private static final NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return (Resources.getSystem().getDisplayMetrics().heightPixels / 2) + 250;
    }

    public static String formatNumber(Double number) {
        return formatter.format(number);
    }
}
