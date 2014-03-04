import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Butnaru Andrei-Madalin on 2/25/14.
 */

// TODO de facut sa scaneze pana la separator(Tokens.separators) sau spatiu(CharacterSet.spaces) ( exemplu: nu recunoaste gresit "time += 4a;" )
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
        int column = 0;
        String currentTknType = ""; // guess of current token
        StringBuilder currentTkn = new StringBuilder();
        String[] returnStmt;
        char currentChr;
        int tknLength;
        HashMap<String, String> temp;
        do {

            currentChr = file.charAt(i);
            column++;

            if (currentTknType.equals("") && currentChr == '/') {
                currentTknType = "comment";
                i++;
                continue;
            }

            if (currentTknType.equals("comment") && (currentChr != '/' && currentChr != '*')) {
                currentTknType = "operator";
                i--;
                continue;
            }

            if (currentTknType.equals("comment") && currentChr == '/') {
                temp = new HashMap<>();

                int j = i;
                while (file.charAt(j) != '\n')
                    j++;


                String comment = file.substring(i - 1, j);
                temp.put("type", "comment");
                temp.put("token", comment);
                temp.put("length", Integer.toString(comment.length()));
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column - 1));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";

                line++;
                column = 0;
                i = j + 1;
                continue;
            }

            if (currentTknType.equals("comment") && currentChr == '*') {
                temp = new HashMap<>();

                int commentsStart = 1;
                int j = i;
                int startLine = line;
                int startColumn = column;
                while (commentsStart != 0) {
                    if (file.charAt(j) == '/' && file.charAt(j + 1) == '*')
                        commentsStart++;
                    if (file.charAt(j) == '*' && file.charAt(j + 1) == '/')
                        commentsStart--;
                    j++;
                    if (file.charAt(j) == '\n') {
                        line++;
                        column = 0;
                    }
                }

                String comment = file.substring(i - 1, j + 1);
                temp.put("type", "comment");
                temp.put("token", comment);
                temp.put("length", Integer.toString(comment.length()));
                temp.put("line", Integer.toString(startLine));
                temp.put("pointer", Integer.toString(startColumn - 1));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";

                i = j + 1;
                continue;
            }

            if (currentTknType.equals("") && Tokens.validateIdStart(currentChr)) {
                currentTknType = "identifier";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("identifier") && Tokens.validateIdentifierChr(currentChr)) {
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("identifier") && !Tokens.validateIdentifierChr(currentChr)) {
                temp = new HashMap<>();

                returnStmt = Tokens.validateKeyword(currentTkn.toString()).split("\\|");
                if (returnStmt[0].equals("correct")) {
                    temp.put("type", "keyword");
                } else {
                    returnStmt = Tokens.Literals.checkBooleanLiteral(currentTkn.toString()).split("\\|");
                    if (returnStmt[0].equals("correct")) {
                        temp.put("type", "boolean");
                    } else {
                        returnStmt = Tokens.Literals.checkNullLiteral(currentTkn.toString()).split("\\|");
                        if (returnStmt[0].equals("correct")) {
                            temp.put("type", "null");
                        } else {
                            returnStmt = Tokens.checkIdentifier(currentTkn.toString()).split("\\|");
                            if (returnStmt[0].equals("correct")) {
                                temp.put("type", "identifier");
                            }
                        }
                    }
                }

                tknLength = currentTkn.toString().length();
                temp.put("token", currentTkn.toString());
                temp.put("length", Integer.toString(tknLength));
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column - tknLength));

                if (returnStmt[0] == "wrong") {
                    temp.put("type", returnStmt[2]);
                    temp.put("error", returnStmt[returnStmt.length - 1]);
                }
                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";
                column--;
                continue;
            }

            if (currentTknType.equals("") && CharacterSet.numeric.contains("" + currentChr)) {
                currentTknType = "number";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("number") && Tokens.Literals.validateNumberChar(currentChr)) {
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("number") && !Tokens.Literals.validateNumberChar(currentChr)) {
                temp = new HashMap<>();
                String currentTknString = currentTkn.toString();
                if (currentTknString.contains(".") || currentTknString.contains("e") || currentTknString.contains("E")) {
                    returnStmt = Tokens.Literals.checkFloatingPointLiteral(currentTkn.toString()).split("\\|");
                    temp.put("type", "double");
                    if (returnStmt[0].equals("wrong")) {
                        temp.put("error", returnStmt[returnStmt.length - 1]);
                    }
                } else {
                    returnStmt = Tokens.Literals.checkIntegerLiteral(currentTkn.toString()).split("\\|");
                    temp.put("type", "int");
                    if (returnStmt[0].equals("wrong")) {
                        temp.put("error", returnStmt[returnStmt.length - 1]);
                    }
                }

                tknLength = currentTknString.length();
                temp.put("token", currentTknString);
                temp.put("length", Integer.toString(tknLength));
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column - tknLength));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";
                column--;
                continue;
            }

            if (currentTknType.equals("") && currentChr == '\'') {
                currentTknType = "char";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("char") && currentChr != '\'') {
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("char") && currentChr == '\'') {
                currentTkn.append(currentChr);

                temp = new HashMap<>();
                String currentTknString = currentTkn.toString();

                returnStmt = Tokens.Literals.checkCharLiteral(currentTkn.toString()).split("\\|");
                temp.put("type", "char");
                if (returnStmt[0].equals("wrong")) {
                    temp.put("error", returnStmt[returnStmt.length - 1]);
                }

                tknLength = currentTknString.length();
                temp.put("token", currentTknString);
                temp.put("length", Integer.toString(tknLength));
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column - tknLength));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";
                i++;
                continue;
            }

            if (currentTknType.equals("") && currentChr == '\"') {
                currentTknType = "string";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("string") && currentChr != '\"') {
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("string") && currentChr == '\"') {
                currentTkn.append(currentChr);

                temp = new HashMap<>();
                String currentTknString = currentTkn.toString();

                returnStmt = Tokens.Literals.checkStringLiteral(currentTkn.toString()).split("\\|");
                temp.put("type", "string");
                if (returnStmt[0].equals("wrong")) {
                    temp.put("error", returnStmt[returnStmt.length - 1]);
                }

                tknLength = currentTknString.length();
                temp.put("token", currentTknString);
                temp.put("length", Integer.toString(tknLength));
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column - tknLength));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";
                i++;
                continue;
            }

            if (currentTknType.equals("") && currentChr == '.') {
                currentTknType = "point";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("point") && Tokens.Literals.validateNumbericChar(currentChr)) {
                currentTknType = "number";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("point") && !Tokens.Literals.validateNumbericChar(currentChr)) {
                temp = new HashMap<>();
                column--;
                temp.put("type", "separator");
                temp.put("token", ".");
                temp.put("length", "1");
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";

                continue;
            }

            if (currentTknType.equals("") && Tokens.isSeparator(currentChr)) {
                temp = new HashMap<>();
                temp.put("type", "separator");
                temp.put("token", "" + currentChr);
                temp.put("length", "1");
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column));

                tokens.add(temp);
                i++;
                continue;
            }

            if (CharacterSet.isSpace(currentChr)) {
                if (currentChr == '\n') {
                    line++;
                    column = 0;
                }
                i++;
                continue;
            }


            if (currentTknType.equals("") && Tokens.validateOperatorChar(currentChr)) {
                currentTknType = "operator";
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("operator") && Tokens.validateOperatorChar(currentChr)) {
                currentTkn.append(currentChr);
                i++;
                continue;
            }

            if (currentTknType.equals("operator") && !Tokens.validateOperatorChar(currentChr)) {
                temp = new HashMap<>();
                String currentTknString = currentTkn.toString();

                returnStmt = Tokens.validateOperator(currentTkn.toString()).split("\\|");
                temp.put("type", "operator");
                if (returnStmt[0].equals("wrong")) {
                    temp.put("error", returnStmt[returnStmt.length - 1]);
                }

                tknLength = currentTknString.length();
                temp.put("token", currentTknString);
                temp.put("length", Integer.toString(tknLength));
                temp.put("line", Integer.toString(line));
                temp.put("pointer", Integer.toString(column - tknLength));

                tokens.add(temp);
                currentTkn.delete(0, currentTkn.length());
                currentTknType = "";
                column--;
                continue;
            }

            i++;
        } while (i < file.length());

        return tokens;
    }
}
