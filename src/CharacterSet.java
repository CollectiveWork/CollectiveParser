import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Cristi on 2/25/14.
 */
public class CharacterSet {
    public static String lower_case;
    public static String upper_case;
    public static String alphabetic;
    public static String numeric;
    public static String alphanumeric;
    public static String special;
    public static String graphic;
    public static String[] spaces;
    public static String[] escapeSequences;


    public static void readCharacterSet(String filePath){
        String lower_case_raw = "";
        String upper_case_raw = "";
        String numeric_raw = "";
        String special_raw = "";
        String escape_sequences_raw = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader textReader = new BufferedReader(fileReader);

            lower_case_raw = textReader.readLine();
            upper_case_raw = textReader.readLine();
            numeric_raw = textReader.readLine();
            special_raw = textReader.readLine();
            escape_sequences_raw = textReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String alphabetic_raw = lower_case_raw + upper_case_raw;
        String alphanumeric_raw = alphabetic_raw + numeric_raw;
        String graphic_raw = alphanumeric_raw + special_raw;
        String spaces_raw = " |\t|\013|\n|\f";
        escape_sequences_raw += "|\n|\t|\b|\r|\f|\\|\'|\"| ";
        escape_sequences_raw += "|\\n|\\t|\\b|\\r|\\f|\\|\'|\"| ";

        lower_case = lower_case_raw;
        upper_case = upper_case_raw;
        alphabetic = alphabetic_raw;
        numeric = numeric_raw;
        alphanumeric = alphanumeric_raw;
        special = special_raw;
        graphic = graphic_raw;
        spaces = spaces_raw.split("\\|");
        escapeSequences = escape_sequences_raw.split("\\|");
    }

    public static boolean isSpace(char chr){
        for(String sp : spaces){
            if(sp.equals("" + chr))
                return true;
        }
        return false;
    }
}
