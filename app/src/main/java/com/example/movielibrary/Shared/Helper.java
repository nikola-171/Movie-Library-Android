package com.example.movielibrary.Shared;

import android.content.res.Resources;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    private static final int ITEM_BREAKPOINT = 400;
    private static final int ITEM_BREAKPOINT_CAST_MEMBERS = 480;
    private static final int MOBILE_BREAKPOINT = 1080;

    private static final NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return (Resources.getSystem().getDisplayMetrics().heightPixels / 2) + 250;
    }

    public static int getGridItemsCount() {
        return (Resources.getSystem().getDisplayMetrics().widthPixels ) / ITEM_BREAKPOINT;
    }

    public static int getGridItemsCountForCastMembers() {
        int currentWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        return currentWidth <= MOBILE_BREAKPOINT ? 1 : currentWidth / ITEM_BREAKPOINT_CAST_MEMBERS;
    }

    public static String formatNumber(Double number) {
        return formatter.format(number);
    }
}
