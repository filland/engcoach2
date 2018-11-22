package comn.data;

import com.google.gson.Gson;
import comn.model.domain.TranscriptionDictRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static comn.utils.JSONUtils.createBackup;

public class TranscriptionRepositoryJSONImp implements TranscriptionRepository {

    private String pathToJSONsWithTranscriptions;

    private Map<String, TranscriptionDictRecord> wordAndTranscRecordMap;
    private List<TranscriptionDictRecord> transcriptionRecords;

    public TranscriptionRepositoryJSONImp(String pathToJSONsWithTranscriptions) {
        this.pathToJSONsWithTranscriptions = pathToJSONsWithTranscriptions;

        transcriptionRecords = new ArrayList<>();
    }

    public void loadJSONs() throws IOException {

        // create backup
        createBackup(pathToJSONsWithTranscriptions);

        byte[] bytes = Files.readAllBytes(new File(pathToJSONsWithTranscriptions).toPath());

        String jsons = new String(bytes, StandardCharsets.UTF_8);

        if (!jsons.trim().isEmpty()) {

            TranscriptionDictRecord[] dictionaryRecordsArr = new Gson().fromJson(jsons, TranscriptionDictRecord[].class);

            for (TranscriptionDictRecord record : dictionaryRecordsArr) {

                wordAndTranscRecordMap.put(record.getWord(), record);
            }

//            transcriptionRecords = new ArrayList<>(Arrays.asList(dictionaryRecordsArr));
//
//            Collections.sort(transcriptionRecords);
//
//            System.out.println("first word = " + transcriptionRecords.get(0));

        } else {

            transcriptionRecords = new ArrayList<>();

        }

    }

    @Override
    public TranscriptionDictRecord findByWord(String word) {

        // TODO how to use binary search algorithm if i
        // TODO have only word not the whole TranscriptionDictRecord object
//        Collections.binarySearch(transcriptionRecords, )

        throw new RuntimeException();
    }

    @Override
    public List<TranscriptionDictRecord> findAll() {
        return transcriptionRecords;
    }

    public void createAll() throws IOException {

        // =============================
        try (
                BufferedReader b = new BufferedReader(new FileReader("pronunciation dictionary.txt"))
        ){

            String line;

            while ((line = b.readLine()) != null) {
                if (line.isEmpty()) continue;
                if (line.startsWith("#")) continue;

                String sys[] = line.split(" ", 2);

                transcriptionRecords.add(new TranscriptionDictRecord(sys[0].trim(), sys[1].trim()));

                Files.write(
                        new File(pathToJSONsWithTranscriptions).toPath(),
                        new Gson().toJson(transcriptionRecords).getBytes()
                );

            }

        }
        // =============================

    }
}
