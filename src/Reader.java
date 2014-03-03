import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cristi on 2/25/14.
 */
public class Reader {

    public static String readFile(String path, Charset encoding) {
        if (encoding == null)
            encoding = StandardCharsets.UTF_8;

        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public ArrayList<HashMap<String, String>> parseFile(String file) {
        ArrayList<HashMap<String, String>> tokens = new ArrayList<>();

        int i = 0;
        int line = 1;
        String currentTknType = ""; // guess of current token
        StringBuilder currentTkn = new StringBuilder();
        String[] returnStmt;
        char currentChr;
        HashMap<String, String> temp;
        do{
            currentChr = file.charAt(i);
            if(currentTknType.equals("") && Tokens.validateIdStart(currentChr)){
                currentTknType = "identifier";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if(currentTknType.equals("identifier") && Tokens.validateIdentifierChr(currentChr)){
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if(currentTknType.equals("identifier") && !Tokens.validateIdentifierChr(currentChr)){
                    returnStmt = Tokens.validateKeyword(currentTkn.toString()).split("\\|");
                if(returnStmt[0].equals("correct")){
                    temp = new HashMap<>();
                    temp.put("type", "keyword");
                }else{
                    returnStmt = Tokens.checkIdentifier(currentTkn.toString()).split("\\|");

                    temp = new HashMap<>();
                    temp.put("type", "identifier");
                }

                temp.put("token", currentTkn.toString());
                temp.put("length", "?"); // ??? dafaq... de intrebat
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(i - currentTkn.toString().length()));

                if(returnStmt[0] == "wrong"){
                    temp.put("error", returnStmt[returnStmt.length - 1]);
                }
                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";
                i++;
                continue;
            }

            if(currentChr == ' '){
                i++;
                continue;
            }

            if(currentChr == '\n'){
                line++;
                i++;
                continue;
            }

            i++;
        }while(i < file.length());

        return tokens;
    }
}
