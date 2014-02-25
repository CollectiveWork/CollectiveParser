import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Cristi on 2/25/14.
 */
public class CharacterSet {
    public static String[] lower_case;
    public static String[] upper_case;
    public static String[] alphabetic;
    public static String[] numeric;
    public static String[] alphanumeric;
    public static String[] special;
    public static String[] graphic;


    public static void readCharacterSet(String filePath){
        String lower_case_raw = "";
        String upper_case_raw = "";
        String numeric_raw = "";
        String special_raw = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader textReader = new BufferedReader(fileReader);

            lower_case_raw = textReader.readLine();
            upper_case_raw = textReader.readLine();
            numeric_raw = textReader.readLine();
            special_raw = textReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String alphabetic_raw = lower_case_raw + " " + upper_case_raw;
        String alphanumeric_raw = alphabetic_raw + " " + numeric_raw;
        String graphic_raw = alphanumeric_raw + " " + special_raw;

        lower_case = lower_case_raw.split(" ");
        upper_case = upper_case_raw.split(" ");
        alphabetic = alphabetic_raw.split(" ");
        numeric = numeric_raw.split(" ");
        alphanumeric = alphanumeric_raw.split(" ");
        special = special_raw.split(" ");
        graphic = graphic_raw.split(" ");

    }
}
