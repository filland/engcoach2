package comn.data;

import com.google.gson.Gson;
import comn.model.domain.TranscriptionDictRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static comn.model.utils.JSONUtils.createBackupJSONFile;

public class TranscriptionDictRepJSONImp implements TranscriptionDictionaryRepository {

    private String pathToJSONsWithTranscriptions;

    private Map<String, TranscriptionDictRecord> wordAndTranscRecordMap;

    public TranscriptionDictRepJSONImp(String pathToJSONsWithTranscriptions) {
        this.pathToJSONsWithTranscriptions = pathToJSONsWithTranscriptions;
    }

    public void loadJSONs() throws IOException {

        // create backup
        createBackupJSONFile(pathToJSONsWithTranscriptions);

        byte[] bytes = Files.readAllBytes(new File(pathToJSONsWithTranscriptions).toPath());

        String jsons = new String(bytes, StandardCharsets.UTF_8);

        if (!jsons.trim().isEmpty()) {

            wordAndTranscRecordMap = new HashMap<>();

            TranscriptionDictRecord[] dictionaryRecordsArr = new Gson().fromJson(jsons, TranscriptionDictRecord[].class);

            for (TranscriptionDictRecord record : dictionaryRecordsArr) {

                wordAndTranscRecordMap.put(record.getWord(), record);
            }

        } else {

            throw new RuntimeException("This file is empty = " + pathToJSONsWithTranscriptions);
        }

    }

    @Override
    public TranscriptionDictRecord findByWord(String word) {

        return wordAndTranscRecordMap.get(word);
    }

    @Override
    public List<TranscriptionDictRecord> findAll() {
        return new ArrayList<>(wordAndTranscRecordMap.values());
    }

}
