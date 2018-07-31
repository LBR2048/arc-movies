package ardjomand.leonardo.arcmovies;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.arcmovies.model.Genre;

/**
 * Created by unity on 04/03/18.
 */

public class Utils {

    public static String showListAsString(List<String> items, String separator) {
        if (items != null && !items.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String item : items) {
                stringBuilder.append(item).append(separator);
            }
            stringBuilder.setLength(stringBuilder.length() - separator.length());
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static List<String> getGenreNames(List<Genre> genres) {
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName());
        }
        return genreNames;
    }
}
