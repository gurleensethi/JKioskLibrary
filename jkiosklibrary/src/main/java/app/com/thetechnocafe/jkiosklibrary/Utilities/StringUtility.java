package app.com.thetechnocafe.jkiosklibrary.Utilities;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gurleensethi on 07/06/17.
 */

public class StringUtility {
    /*
    * Clean up the text and removing occurrences of '&nbsp;'
    * and remove ant occurrences of '\u00A0'
    * */
    public static String cleanString(String string) {
        try {
            return string.replace("&nbsp;", "").replace("\u00A0", "").trim();
        } catch (Exception e) {
            return string;
        }
    }

    /*
    * Clean up the string and then extract the subject name from it
    * and removing the subject code
    * */
    public static String getSubjectName(String completeName) {
        try {
            return cleanString(completeName).substring(0, completeName.indexOf('('));
        } catch (Exception e) {
            return completeName;
        }
    }

    /**
     * Clean up the string and extract the subject code from it
     * and removing the subject name
     */
    public static String getSubjectCode(String completeName) {
        try {
            return cleanString(completeName).substring(completeName.indexOf("(") + 1, completeName.indexOf(")"));
        } catch (Exception e) {
            return completeName;
        }
    }

    /*
    * Clean up the string and then extract the subject name from it
    * and removing the subject code
    * For subject names with format similar to : ADVANCED DATA STRUCTURES AND APPLICATIONS - 16B1NCI631
    * Split the string with the delimiter then extract the subject name from first
    * */
    public static String getSubjectNameFromAttendance(String completeName) {
        try {
            List<String> splitted = Arrays.asList(completeName.split("-"));
            return cleanString(TextUtils.join(" ", splitted.subList(0, splitted.size() - 1)));
        } catch (Exception e) {
            return completeName;
        }
    }

    /**
     * Clean up the string and extract the subject code from it
     * and removing the subject name
     * For subject names with format similar to : ADVANCED DATA STRUCTURES AND APPLICATIONS - 16B1NCI631
     * Split the string with the delimiter then extract the subject name from last
     */
    public static String getSubjectCodeFromAttendance(String completeName) {
        try {
            List<String> splitted = Arrays.asList(completeName.split("-"));
            return cleanString(splitted.get(splitted.size() - 1));
        } catch (Exception e) {
            return completeName;
        }
    }

    /*
    * Convert string attendance to Integer
    * */
    public static Integer convertStringAttendanceToInteger(String attendance) {
        try {
            return (int) Double.parseDouble(attendance);
        } catch (Exception e) {
            return null;
        }
    }

    /*
    * Convert serial number to integer
    * Format -> 1. (Remove the dot preceding)
    * */
    public static int convertStringToIntegerForDetailAttendance(String serialNumber) {
        return Integer.parseInt(cleanString(serialNumber.replace(".", "")));
    }
}
