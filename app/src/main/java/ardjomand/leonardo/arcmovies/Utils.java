package ardjomand.leonardo.arcmovies;

import java.util.List;

/**
 * Created by unity on 04/03/18.
 */

public class Utils {

    public static String showListAsString(List<String> items, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : items) {
            stringBuilder.append(item).append(separator);
        }
        stringBuilder.setLength(stringBuilder.length() - separator.length());
        return stringBuilder.toString();
    }
}
