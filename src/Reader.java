import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Cristi on 2/25/14.
 */
public class Reader {


    public Reader() {
    }

    static String readFile(String path, Charset encoding) {
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

    static ArrayList<String> parseFile() {

        return null;
    }
}
