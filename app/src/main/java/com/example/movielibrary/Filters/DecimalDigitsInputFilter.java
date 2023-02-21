package com.example.movielibrary.Filters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {

    Pattern pattern;

    public DecimalDigitsInputFilter(){
        pattern = Pattern.compile("^[0-9]{0,3}[. ]*[0-9]{0,2}$");
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned dest, int i2, int i3) {

        Matcher matcher = pattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }
}
