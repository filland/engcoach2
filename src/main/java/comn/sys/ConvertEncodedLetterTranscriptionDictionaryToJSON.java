package comn.sys;

import com.google.gson.Gson;
import comn.model.domain.LetterTranscriptionRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class for converting old property-dictionary to json format
 *
 */
public class ConvertEncodedLetterTranscriptionDictionaryToJSON {

    public static void main(String[] args) throws IOException {

        List<LetterTranscriptionRecord> records = new ArrayList<>();

        // =============================
        try (
                BufferedReader b = new BufferedReader(new FileReader("decoding of sound acronyms"))
        ){

            String line;

            while ((line = b.readLine()) != null) {
                if (line.isEmpty()) continue;
                if (line.startsWith("#")) continue;

                String sys[] = line.split(" = ", 2);

                records.add(new LetterTranscriptionRecord(sys[0].trim(), sys[1].trim()));

                Files.write(
                        new File("decoded_letters_transcriptions.json").toPath(),
                        new Gson().toJson(records).getBytes()
                );

            }

        }
        // =============================



    }

}
