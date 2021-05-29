
import org.apache.commons.io.FileUtils;

import java.io.*;

public class Driver {

    public static final String DICTIONARY_TXT = "dictionary.txt";
    public static final String THESAURUS_TXT = "thesaurus-really-really-short.txt";

    public static void main(String[] args) {
        removeEveryOtherLineFromFile(FileUtils.toFile(Driver.class.getResource(THESAURUS_TXT)));
    }

    public static void removeEveryOtherLineFromFile(File file) {

        try {

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(String.valueOf(FileUtils.toFile(Driver.class.getResource("thesaurus-really-really-really-short.txt"))));

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line;
            int count = 1;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (count++ % 2 == 0) {
                    pw.println(line);
                }
            }
            System.out.println(count);
            pw.close();
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
