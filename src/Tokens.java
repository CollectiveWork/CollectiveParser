import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Cristi on 2/25/14.
 */
public class Tokens {
    public static ArrayList<String> keywords = new ArrayList<>();
    public static ArrayList<String> separators = new ArrayList<>();
    public static ArrayList<String> operators = new ArrayList<>();

    public static boolean validateIdStart(char chr) {
        String id = "" + chr;
        return (CharacterSet.alphabetic.contains(id) || id.equals("$") || id.equals("_"));
    }

    public static boolean validateIdentifierChr(char chr) {
        String id = "" + chr;
        return (validateIdStart(chr) || CharacterSet.numeric.contains(id));
    }

    public static String checkIdentifier(String identifier) {
        if (validateIdStart(identifier.charAt(0))) {
            for (int i = 1; i < identifier.length(); i++) {
                if (!validateIdentifierChr(identifier.charAt(i))) {
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

    public static boolean shouldStop(char chr){
        return isSeparator(chr) || CharacterSet.isSpace(chr);

    }

    public static String validateOperator(String operator) {
        for (String op : operators) {
            if (op.equals(operator))
                return "correct|operator|" + operator + "|correctOperator";
        }

        return "wrong|operator|" + operator + "|wrongOperator";
    }

    public static boolean isSeparator(char separator) {
        for (String se : separators) {
            if (se.equals("" + separator))
                return true;
        }

        return false;
    }

    public static boolean validateOperatorChar(char chr) {
        return "=><!~?:&|+-*/^%".contains("" + chr);
    }

    public static String validateSeparator(String separator) {
        for (String se : separators) {
            if (se.equals(separator))
                return "correct|separator|" + separator + "|correctSeparator";
        }

        return "wrong|separator|" + separator + "|wrongSeparator";
    }

    public static String validateKeyword(String keyword) {
        for (String key : keywords) {
            if (key.equals(keyword))
                return "correct|" + keyword + "|keyword|correctKeyword";
        }

        return "wrong|" + keyword + "|keyword|wrongKeyword";
    }

    public static class Literals {

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
            if (literal.length() > 2 && literal.substring(0, 2).equals("0x")) {
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
            if (literal.contains("+") || literal.contains("-")) {
                if (!"+-".contains(literal.substring(1, 2))) {
                    return "wrong|literal|double|missingSignOfExponentPart";
                }
                if (!validateDigits(literal.substring(3), digits)) {
                    return "wrong|literal|double|wrongDigitsInExponentPart";
                }
            } else {
                if (!validateDigits(literal.substring(1), digits)) {
                    return "wrong|literal|double|wrongDigitsInExponentPart";
                }
            }
            return "correct|literal|double|exponentPart";
        }

        /**
         * check type 1 floating point literal (digits exponent-part)
         */
        private static String checkType1FPL(String literal) {
            String digits = "0123456789";
            int pos = 0;
            if (literal.contains("e")) {
                pos = literal.indexOf("e");
            }
            if (literal.contains("E")) {
                pos = literal.indexOf("E");
            }

            if (!validateDigits(literal.substring(0, pos), digits)) {
                return "wrong|literal|double|wrongDigitsOfFloatingPoint";
            }

            String exponent_part = literal.substring(pos);
            String response = validateExponentPart(exponent_part);
            if (response.split("\\|")[0].equals("wrong")) {
                return response;
            }

            return "correct|literal|double|floatingPointLiteral";
        }

        /**
         * check type 2 floating point literal (digits.[digits][exponent-part])
         */
        private static String checkType2FPL(String literal) {
            String digits = "0123456789";
            int dotPos = literal.indexOf(".");
            int ePos = -1;
            if (literal.contains("e")) {
                ePos = literal.indexOf("e");
            }
            if (literal.contains("E")) {
                ePos = literal.indexOf("E");
            }

            if (!validateDigits(literal.substring(0, dotPos), digits)) {
                return "wrong|literal|double|wrongDecimalPartOfFloatingPoint";
            }

            if (ePos > 0) {
                if (!validateDigits(literal.substring(dotPos + 1, ePos), digits)) {
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }

                String response = validateExponentPart(literal.substring(ePos));
                if (response.split("\\|")[0].equals("wrong")) {
                    return response;
                }
            } else {
                if (!validateDigits(literal.substring(dotPos + 1), digits)) {
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }
            }

            return "correct|literal|double|floatingPointLiteral";
        }

        /**
         * check type 3 floating point literal (.digits[exponent-part])
         */
        private static String checkType3FPL(String literal) {
            String digits = "0123456789";
            int ePos = -1;
            if (literal.contains("e")) {
                ePos = literal.indexOf("e");
            }
            if (literal.contains("E")) {
                ePos = literal.indexOf("E");
            }

            if (ePos > 0) {
                if (!validateDigits(literal.substring(1, ePos), digits)) {
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }

                String response = validateExponentPart(literal.substring(ePos));
                if (response.split("\\|")[0].equals("wrong")) {
                    return response;
                }
            } else {
                if (!validateDigits(literal.substring(1), digits)) {
                    return "wrong|literal|double|wrongDecimalExponentPartOfFloatingPoint";
                }
            }

            return "correct|literal|double|floatingPointLiteral";
        }

        public static String checkFloatingPointLiteral(String literal) {
            if (literal.substring(0, 1).equals(".")) {
                return checkType3FPL(literal);
            }
            if (literal.contains(".")) {
                return checkType2FPL(literal);
            }
            return checkType1FPL(literal);
        }

        public static boolean validateNumberChar(char chr) {
            return "0123456789+-.eEx".contains("" + chr);
        }

        public static boolean validateNumbericChar(char chr) {
            return "0123456789".contains("" + chr);
        }

        public static String checkBooleanLiteral(String literal) {
            if (literal.equals("true"))
                return "correct|literal|boolean|trueBoolean";
            if (literal.equals("false"))
                return "correct|literal|boolean|falseBoolean";
            return "wrong|literal|boolean|wrongBoolean";
        }

        private static boolean validateEscapeSequenceChar(String chr) {
            for (int i = 0; i < CharacterSet.escapeSequences.length; i++) {
                if (CharacterSet.escapeSequences[i].equals(chr))
                    return true;
            }
            return false;
        }

        public static boolean validateChar(char chr) {
            return (CharacterSet.graphic.contains("" + chr) || validateEscapeSequenceChar("" + chr));
        }

        public static String checkCharLiteral(String literal) {
            if (literal.length() < 3)
                return "wrong|literal|char|charLengthIsTooSmall";

            if (literal.length() > 3 && literal.charAt(1) != '\\')
                return "wrong|literal|char|charLengthIsTooBig";

            if (literal.charAt(0) == '\'' && literal.charAt(1) == '\'')
                return "wrong|literal|char|charCannotBeEmpty";


            String chr1 = "" + literal.charAt(0);
            String chr2, chr3;
            if(literal.charAt(1) == '\\' && literal.length() > 3){
                chr2 = "" + literal.charAt(1) + literal.charAt(2);
                chr3 = "" + literal.charAt(3);
            }
            else{
                chr2 = "" + literal.charAt(1);
                chr3 = "" + literal.charAt(2);
            }

            if (!(chr1.equals("'") && (CharacterSet.graphic.contains(chr2) || validateEscapeSequenceChar(chr2)) && chr3.equals("'")))
                return "wrong|literal|char|illegalChar";

            return "correct|literal|char|correctChar";
        }

        private static boolean validateString(String literal) {
            String chr;
            for (int i = 0; i < literal.length(); i++) {
                chr = "" + literal.charAt(i);
                if (!(CharacterSet.graphic.contains(chr) || validateEscapeSequenceChar(chr)))
                    return false;
            }
            return true;
        }

        public static String checkStringLiteral(String literal) {
            int length = literal.length();
            String firstChar = "" + literal.charAt(0);
            String lastChar = "" + literal.charAt(length - 1);

            if (!(firstChar.equals("\"") && lastChar.equals("\"") /*&& validateString(literal.substring(1, length - 1))*/ ))
                return "wrong|literal|string|illegalString";

            return "correct|literal|string|correctString";
        }

        public static String checkNullLiteral(String literal) {
            return literal.equals("null") ? "correct|literal|null|nullReference" : "wrong|literal|null|notANullReference";
        }
    }
}
