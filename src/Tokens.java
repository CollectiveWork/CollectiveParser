import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cristi on 2/25/14.
 */
public class Tokens {
    public static ArrayList<ArrayList<String>> tokens = new ArrayList<ArrayList<String>>();

    public static ArrayList<String> identifiers = new ArrayList<String>();
    public static ArrayList<String> keywords = new ArrayList<String>();
    public static ArrayList<String> separators = new ArrayList<String>();
    public static ArrayList<String> operators = new ArrayList<String>();
    public static ArrayList<String> literals = new ArrayList<String>();
    public static ArrayList<String> comments = new ArrayList<String>();

    public static void readKeywords(){
        String[] keywords_raw = Reader.readFile("resources//keywords.txt", null).split(" ");

        for(String keyword : keywords_raw){
            keywords.add(keyword);
        }
    }

    public static void readTokens(){
        readKeywords();

    }
}
