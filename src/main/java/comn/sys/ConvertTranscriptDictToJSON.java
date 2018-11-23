package comn.sys;

import com.google.gson.Gson;
import comn.model.domain.TranscriptionDictRecord;

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
public class ConvertTranscriptDictToJSON {

    public static void main(String[] args) {

        String pathToJSONsWithTranscriptions = "src\\main\\resources\\engcoach2\\transcription_dict.json";

        List<TranscriptionDictRecord> transcriptionRecords = new ArrayList<>();

        try {

            try (
                    BufferedReader b = new BufferedReader(new FileReader("transcription_dictionary.txt"))
            ){

                String line;

                while ((line = b.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    if (line.startsWith("#")) continue;

                    String sys[] = line.split(" ", 2);

                    transcriptionRecords.add(new TranscriptionDictRecord(sys[0].trim().toLowerCase(), sys[1].trim()));
                }

                Files.write(
                        new File(pathToJSONsWithTranscriptions).toPath(),
                        new Gson().toJson(transcriptionRecords).getBytes()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
