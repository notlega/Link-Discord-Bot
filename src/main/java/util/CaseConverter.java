package util;

public class CaseConverter {

    /**
     * Converts a string to snakeCase.
     * @param camelCaseString The string to convert.
     * @return The string in snakeCase.
     */
    public static String toSnakeCase(String camelCaseString) {
        StringBuilder snakeCaseString = new StringBuilder(Capitalisation.decapitalise(camelCaseString));

        for (int i = 0; i < snakeCaseString.length(); i++) {
            if (Character.isUpperCase(snakeCaseString.charAt(i))) {
                snakeCaseString.setCharAt(i, Character.toLowerCase(snakeCaseString.charAt(i)));
                snakeCaseString.insert(i, "_");
                i--;
            }
        }
        return snakeCaseString.toString();
    }

    /**
     * Converts a string to camelCase.
     * @param snakeCaseString The string to convert.
     * @param capitaliseFirstLetter Whether to capitalise the first letter of the string.
     * @return The string in camelCase.
     */
    public static String toCamelCase(String snakeCaseString, boolean capitaliseFirstLetter) {
        StringBuilder camelCaseString;

        if (capitaliseFirstLetter) {
            camelCaseString = new StringBuilder(Capitalisation.capitalise(snakeCaseString));
        } else {
            camelCaseString = new StringBuilder(Capitalisation.decapitalise(snakeCaseString));
        }

        for (int i = 0; i < camelCaseString.length(); i++) {
            if (camelCaseString.charAt(i) == '_') {
                camelCaseString.setCharAt(i + 1, Character.toUpperCase(camelCaseString.charAt(i + 1)));
                camelCaseString.deleteCharAt(i);
                i--;
            }
        }
        return camelCaseString.toString();
    }
}
