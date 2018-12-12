package srb.homemanagement.utils;

import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String captialiseString(String normalString) {

        String newString = "";

        if (normalString.length() > 1) {
            newString = normalString.substring(0, 1).toUpperCase() + normalString.substring(1);
        } else {
            newString = normalString;
        }

        return newString;
    }

    public static String getMonthYear(String selectedDate) {
        String newDate = "";

        newDate = selectedDate.substring(3, 5) + "," + selectedDate.substring(6, 10);

        return newDate;
    }

    public static String showFancyDate(String normalDate) {

        String date = "";
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyy");
        Date newDate = null;
        try {
            newDate = spf.parse(normalDate);
            spf = new SimpleDateFormat("dd MMM yyyy");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;
    }

    public static String showMonthName(String normalDate) {

        String date = "";
        SimpleDateFormat spf = new SimpleDateFormat("MM");
        Date newDate = null;
        try {
            newDate = spf.parse(normalDate);
            spf = new SimpleDateFormat("MMMM");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;
    }

    public static void backButtonOnToolbar(AppCompatActivity mActivity) {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
