import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Cristi on 2/25/14.
 */
public class Tokens {
    public static ArrayList<String> keywords = new ArrayList<>();
    public static ArrayList<String> separators = new ArrayList<>();
    public static ArrayList<String> operators = new ArrayList<>();
    public static ArrayList<String> comments = new ArrayList<>();

    private static boolean validateIdStart(char chr) {
        String id = "" + chr;
        return (CharacterSet.alphabetic.contains(id) || id.equals("$") || id.equals("_"));
    }

    private static boolean validateIdentifier(char chr) {
        String id = "" + chr;
        return (validateIdStart(chr) || CharacterSet.numeric.contains(id));
    }

    public static String checkIdentifier(String identifier) {
        if (validateIdStart(identifier.charAt(0))) {
            for (int i = 1; i < identifier.length(); i++) {
                if (!validateIdentifier(identifier.charAt(i))) {
                    return "wrong|identifier|wrongChar";
                }
            }
            return "correct|identifier";
        } else {
            return "wrong|identifier|wrongStartChar";
        }
    }

    public static void readKeywords() {
        String[] keywords_raw = Reader.readFile("resources//java//keywords.txt", null).split(" ");

        Collections.addAll(keywords, keywords_raw);
    }

    public static void readSeparators() {
        String[] separators_raw = Reader.readFile("resources//java//separators.txt", null).split(" ");

        Collections.addAll(separators, separators_raw);
    }

    public static void readOperators() {
        String[] operators_new = Reader.readFile("resources//java//operators.txt", null).split(" ");

        Collections.addAll(operators, operators_new);

    }

    public static void readTokens() {
        readKeywords();
        readSeparators();
        readOperators();
    }


    public static class Literals {

        public static ArrayList<String> integer_literal = new ArrayList<>();
        public static ArrayList<String> floating_point_literal = new ArrayList<>();
        public static ArrayList<String> boolean_literal = new ArrayList<>();
        public static ArrayList<String> character_literal = new ArrayList<>();
        public static ArrayList<String> string_literal = new ArrayList<>();
        public static ArrayList<String> null_literal = new ArrayList<>();

        private static boolean validateDigits(String literal, String digit) {
            String chr;
            for (int i = 0; i < literal.length(); i++) {
                chr = "" + literal.charAt(i);
                if (!digit.contains(chr)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean validateDecimalNumeral(String literal) {
            String non_zero_digit = "123456789";
            String digit = "0" + non_zero_digit;

            if (literal.equals("0")) {
                return true;
            } else {
                String firstChar = "" + literal.charAt(0);
                return non_zero_digit.contains(firstChar) && validateDigits(literal.substring(1), digit);
            }
        }

        private static boolean validateOctalNumeral(String literal) {
            String non_zero_digit = "1234567";
            String digit = "0" + non_zero_digit;
            if (literal.equals("0")) {
                return true;
            } else {
                String firstChar = "" + literal.charAt(0);
                return firstChar.equals("0") && validateDigits(literal.substring(1), digit);
            }
        }

        private static boolean validateHexidecimalNumeral(String literal) {
            String non_zero_digit = "123456789ABCDEF";
            String digit = "0" + non_zero_digit;
            if (literal.equals("0")) {
                return true;
            } else {
                String firstChar = "" + literal.substring(0, 2);
                return firstChar.equals("0x") && validateDigits(literal.substring(2), digit);
            }
        }

        public static String checkIntegerLiteral(String literal) {
            if (literal.substring(0, 2).equals("0x")) {
                if (validateHexidecimalNumeral(literal))
                    return "correct|literal|int|hexidecimalNumeral";
                else
                    return "wrong|literal|int|wringHexidecimalNumeral";
            } else if (literal.substring(0, 1).equals("0")) {
                if (validateOctalNumeral(literal))
                    return "correct|literal|int|octalNumeral";
                else
                    return "wrong|literal|int|wrongOctalNumeral";
            } else {
                if (validateDecimalNumeral(literal))
                    return "correct|literal|int|decimalNumeral";
                else
                    return "wrong|literal|int|wrongDecimalNumeral";
            }
        }

        static String exponent_indicator = "eE";

        private static String validateExponentPart(String literal) {
            String digits = "0123456789";

            if (!exponent_indicator.contains(literal.substring(0, 1))) {
                return "wrong|literal|double|wrongExponentPart";
            }
            if(literal.contains("+") || literal.contains("-")){
                if (!"+-".contains(literal.substring(1, 2))) {
                    return "wrong|literal|double|missingSignOfExponentPart";
                }
                if (!validateDigits(literal.substring(3), digits)) {
                    return "wrong|literal|double|wrongDigitsInExponentPart";
                }
            }else{
                if (!validateDigits(literal.substring(1), digits)) {
                    return "wrong|literal|double|wrongDigitsInExponentPart";
                }
            }
            return "correct|literal|double|exponentPart";
        }

        /**
         * check type 1 floating point literal (digits exponent-part)
         */
        private static String checkType1FPL(String literal){
            String digits = "0123456789";
            int pos = 0;
            if(literal.contains("e")){
                pos = literal.indexOf("e");
            }
            if(literal.contains("E")){
                pos = literal.indexOf("E");
            }

            if(!validateDigits(literal.substring(0, pos), digits)){
                return "wrong|literal|double|wrongDigitsOfFloatingPoint";
            }

            String exponent_part = literal.substring(pos);
            String response = validateExponentPart(exponent_part);
            if(response.split("\\|")[0].equals("wrong")){
                return response;
            }

            return "correct|literal|double|floatingPointLiteral";
        }

        /**
         * check type 2 floating point literal (digits.[digits][exponent-part])
         */
        private static String checkType2FPL(String literal){
            String digits = "0123456789";
            int dotPos = literal.indexOf(".");
            int ePos = -1;
            if(literal.contains("e")){
                ePos = literal.indexOf("e");
            }
            if(literal.contains("E")){
                ePos = literal.indexOf("E");
            }

            if(!validateDigits(literal.substring(0, dotPos), digits)){
                return "wrong|literal|double|wrongDecimalPartOfFloatingPoint";
            }

            if(ePos > 0){
                if(!validateDigits(literal.substring(dotPos + 1, ePos), digits)){
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }

                String response = validateExponentPart(literal.substring(ePos));
                if(response.split("\\|")[0].equals("wrong")){
                    return response;
                }
            }
            else{
                if(!validateDigits(literal.substring(dotPos + 1), digits)){
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }
            }

            return "correct|literal|double|floatingPointLiteral";
        }

        /**
         * check type 3 floating point literal (.digits[exponent-part])
         */
        private static String checkType3FPL(String literal){
            String digits = "0123456789";
            int ePos = -1;
            if(literal.contains("e")){
                ePos = literal.indexOf("e");
            }
            if(literal.contains("E")){
                ePos = literal.indexOf("E");
            }

            if(ePos > 0){
                if(!validateDigits(literal.substring(1, ePos), digits)){
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }

                String response = validateExponentPart(literal.substring(ePos));
                if(response.split("\\|")[0].equals("wrong")){
                    return response;
                }
            }else{
                if(!validateDigits(literal.substring(1), digits)){
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }
            }

            return "correct|literal|double|floatingPointLiteral";
        }

        public static String checkFloatingPointLiteral(String literal) {
            if(literal.substring(0,1).equals(".")){
                return checkType3FPL(literal);
            }
            if(literal.contains(".")){
                return checkType2FPL(literal);
            }
            return checkType1FPL(literal);
        }

    }
}
