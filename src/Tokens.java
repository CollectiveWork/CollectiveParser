import java.util.ArrayList;

/**
 * Created by Cristi on 2/25/14.
 */
public class Tokens {
    public static ArrayList<ArrayList<String>> tokens = new ArrayList<ArrayList<String>>();

   // public static ArrayList<String> identifiers = new ArrayList<String>();
    public static ArrayList<String> keywords = new ArrayList<String>();
    public static ArrayList<String> separators = new ArrayList<String>();
    public static ArrayList<String> operators = new ArrayList<String>();
    public static ArrayList<String> literals = new ArrayList<String>();
    public static ArrayList<String> comments = new ArrayList<String>();

    private static boolean validateIdStart(char chr) {
        String id = "" + chr;
        return (CharacterSet.alphabetic.contains(id) || id.equals("$") || id.equals("_"));
    }

    private static boolean validateIdentifier(char chr) {
        String id = "" + chr;
        return (validateIdStart(chr) || CharacterSet.numeric.contains(id));
    }

    public static boolean checkIdentifier(String identifier) {
        if (validateIdStart(identifier.charAt(0))) {
            for (int i = 1; i < identifier.length(); i++) {
                if (!validateIdentifier(identifier.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static void readKeywords() {
        String[] keywords_raw = Reader.readFile("resources//java//keywords.txt", null).split(" ");

        for (String keyword : keywords_raw) {
            keywords.add(keyword);
        }
    }

    public static void readSeparators(){
        String[] separators_raw = Reader.readFile("resources//java//separators.txt", null).split(" ");

        for (String separator : separators_raw) {
            separators.add(separator);
        }
    }
    public static void readOperators(){
        String[] operators_new = Reader.readFile("resources//java//operators.txt", null).split(" ");

        for (String operator : operators_new) {
            operators.add(operator);
        }

    }

    public static void readTokens() {
        readKeywords();
        readSeparators();
        readOperators();
    }


    private static class Literals{

        public static ArrayList<String> integer_literal = new ArrayList<String>();
        public static ArrayList<String> floating_point_literal = new ArrayList<String>();
        public static ArrayList<String> boolean_literal = new ArrayList<String>();
        public static ArrayList<String> character_literal = new ArrayList<String>();
        public static ArrayList<String> string_literal = new ArrayList<String>();
        public static ArrayList<String> null_literal = new ArrayList<String>();


    }
}
