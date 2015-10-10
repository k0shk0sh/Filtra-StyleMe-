package com.style.me.hd.global.helper;

import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kosh20111 on 3/11/2015. CopyRights @ Innov8tif Input Helper to validate stuff related to input fields.
 */
public class InputHelper {

    public static void showHidePassword(boolean show, EditText... editTexts) {
        for (EditText e : editTexts) {
            if (show) {
                e.setTransformationMethod(null);
                e.setSelection(e.getText().length());
            } else {
                e.setTransformationMethod(new PasswordTransformationMethod());
                e.setSelection(e.getText().length());
            }
        }
    }

    public static boolean isEmail(String text) {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    public static boolean isWeb(String text) {
        return Patterns.WEB_URL.matcher(text).matches();
    }

    public static boolean isPhone(String text) {
        return Patterns.PHONE.matcher(text).matches();
    }

    public static boolean isEmpty(String text) {
        return text == null || TextUtils.isEmpty(text) || isWhiteSpaces(text) || text.equalsIgnoreCase("null");
    }

    public static boolean isEmpty(Object text) {
        return text == null || text.toString().equalsIgnoreCase("{}") || TextUtils.isEmpty(text.toString())
                || isWhiteSpaces(text.toString()) || text.toString().equalsIgnoreCase("null");
    }

    public static boolean isHintEmpty(String text) {
        return text == null || TextUtils.isEmpty(text) || isWhiteSpaces(text) || text.equalsIgnoreCase("null");
    }

    public static boolean isEmpty(EditText text) {
        return !(!TextUtils.isEmpty(text.getText().toString()) && !isWhiteSpaces(text.getText().toString()));
    }

    public static boolean isEmpty(TextView text) {
        return !(!TextUtils.isEmpty(text.getText().toString()) && !isWhiteSpaces(text.getText().toString()));
    }

    public static boolean isDigit(String text) {
        return TextUtils.isDigitsOnly(text);
    }

    public static boolean isAlphaNumeric(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        if (s.matches(pattern)) {
            return true;
        }
        return false;
    }

    public static boolean isStartWithChar(String s) {
        String pattern = "^[a-zA-Z]*$";
        if (s.substring(0, 1).matches(pattern)) {
            return true;
        }
        return false;
    }

    public static boolean isValidIc(String icNumber) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(icNumber.substring(0, 6));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static List<String> matchStrightLine(String value) {
        List<String> val = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\|(.+?)\\|");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            val.add(matcher.group(1));
        }
        return val;
    }

    private static boolean isWhiteSpaces(String s) {
        return s != null && s.matches("\\s+");
    }

    public static String maskEmail(String txt) {
        try {
            String[] parts = txt.split("@");
            String first = parts[0];
            String second = parts[1];
            return maskExceptLastFour(first) + second;
        } catch (Exception e) {
            e.printStackTrace();
            return asteriskText(txt);
        }
    }

    public static String maskExceptLastFour(String txt) {
        char[] ca = new char[Math.max(0, txt.length() - 4)];
        Arrays.fill(ca, '*');
        return new String(ca) + "" + txt.substring(Math.max(0, txt.length() - 4));
    }

    public static String asteriskText(String text) {
        return text.replaceAll("(?s).", "*");
    }

    public static String getSecondLetter(String word) {
        Pattern pattern = Pattern.compile("\\s([A-Za-z]+)");
        Matcher matcher = pattern.matcher(word);
        if (matcher.find()) {
            return String.valueOf(matcher.group(1).charAt(0));
        }
        return "";
    }

    public static String ellipsis(final String text, int length) {
        length += Math.ceil(text.replaceAll("[^iIl]", "").length() / 2.0d);
        if (text.length() > length + 20) {
            return text.substring(0, length - 3) + "...";
        }
        return text;
    }

    public static String replaceLast(String text, String value) {
        try {
            return text.replace(text.substring(text.length() - 1), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String gcmActualId(String id) {
        if (!isEmpty(id))
            return id.replace("[", "").replace("]", "").replace("\"", "");
        else
            return id;
    }
}
