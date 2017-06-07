package app.com.thetechnocafe.jkiosklibrary.Utilities;

/**
 * Created by gurleensethi on 07/06/17.
 */

public class StringUtility {
    /*
    * Clean up the text and removing occurrences of '&nbsp;'
    * and remove ant occurrences of '\u00A0'
    * */
    public static String cleanString(String string) {
        return string.replace("&nbsp;", "").replace("\u00A0", "").trim();
    }

    /*
    * Clean up the string and then extract the subject name from it
    * and removing the subject code
    * */
    public static String getSubjectName(String completeName) {
        return cleanString(completeName)
                .substring(0, completeName.indexOf('('));
    }

    /**
     * Clean up the string and extract the subject code from it
     * and removing the subject name
     */
    public static String getSubjectCode(String completeName) {
        return cleanString(completeName)
                .substring(completeName.indexOf("(") + 1, completeName.indexOf(")"));
    }
}
