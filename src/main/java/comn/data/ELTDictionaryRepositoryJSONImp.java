package comn.data;

import com.google.gson.Gson;
import comn.model.domain.LetterTranscriptionRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static comn.model.utils.JSONUtils.createBackupJSONFile;

public class ELTDictionaryRepositoryJSONImp implements EncodedLetterTranscriptionDictionaryRepository {

    private String pathToFileWithJSONs;
    private Map<String, LetterTranscriptionRecord> decodedLTAndRecordMap;

    // cache records
    private List<LetterTranscriptionRecord> letterTranscriptionRecords;

    public ELTDictionaryRepositoryJSONImp(String pathToFileWithJSONs) {
        this.pathToFileWithJSONs = pathToFileWithJSONs;

        decodedLTAndRecordMap = new HashMap<>();
    }

    public void loadJSONs() throws IOException {

        // create backup
        createBackupJSONFile(pathToFileWithJSONs);

        byte[] bytes = Files.readAllBytes(new File(pathToFileWithJSONs).toPath());

        String jsons = new String(bytes, StandardCharsets.UTF_8);


        if (!jsons.trim().isEmpty()) {

            LetterTranscriptionRecord[] eltdRecords = new Gson().fromJson(jsons, LetterTranscriptionRecord[].class);

            for (LetterTranscriptionRecord record : eltdRecords) {

                decodedLTAndRecordMap.put(record.getEncodedLetterTranscription(), record);
            }

        } else {

            throw new RuntimeException("This file is empty = " + pathToFileWithJSONs);
        }
    }

    @Override
    public LetterTranscriptionRecord find(String encodedLetterTranscription) {
        return decodedLTAndRecordMap.get(encodedLetterTranscription);
    }

    @Override
    public List<LetterTranscriptionRecord> findAll() {

        if (letterTranscriptionRecords == null) {

            letterTranscriptionRecords = new ArrayList<>(decodedLTAndRecordMap.values());
        }

        return letterTranscriptionRecords;
    }
}
