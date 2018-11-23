package comn.data;

import com.google.gson.Gson;
import comn.model.domain.DictionaryRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static comn.model.utils.JSONUtils.createBackupJSONFile;

/**
 * Using text file with jsons as a source
 */
public class DictionaryRepositoryJSONImp implements DictionaryRepository {

    private final String pathToFileWithJSONs;

    private List<DictionaryRecord> dictionaryRecords;

    private long lastID = -1;

    public DictionaryRepositoryJSONImp(String pathToFileWithJSONs) {
        this.pathToFileWithJSONs = pathToFileWithJSONs;
    }

    public void loadJSONs() throws IOException {

        // create backup
        createBackupJSONFile(pathToFileWithJSONs);

        byte[] bytes = Files.readAllBytes(new File(pathToFileWithJSONs).toPath());

        String jsons = new String(bytes, StandardCharsets.UTF_8);


        if (!jsons.trim().isEmpty()) {


            DictionaryRecord[] dictionaryRecordsArr = new Gson().fromJson(jsons, DictionaryRecord[].class);
            dictionaryRecords = new ArrayList<>(Arrays.asList(dictionaryRecordsArr));

            Collections.sort(dictionaryRecords);

//            System.out.println("first id " + this.dictionaryRecords.get(0).getId());
            lastID = this.dictionaryRecords.get(dictionaryRecords.size() - 1).getId();
        } else {

            dictionaryRecords = new ArrayList<>();

        }
    }

    public void updateFileWithJSONs() throws IOException {

        Files.write(
                new File(pathToFileWithJSONs).toPath(),
                new Gson().toJson(dictionaryRecords).getBytes()
        );
    }

    @Override
    public DictionaryRecord findById(long id) {
        return dictionaryRecords
                .stream()
                .filter(record -> record.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<DictionaryRecord> findAll() {
        return dictionaryRecords;
    }

    @Override
    public List<DictionaryRecord> findAllByType(DictionaryRecord.DictRecType type) {
        return dictionaryRecords
                .stream()
                .filter(record -> record.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public void create(DictionaryRecord record) {

        Objects.requireNonNull(record);

        record.setId(++lastID);

        dictionaryRecords.add(record);

    }

    @Override
    public boolean update(DictionaryRecord record) {

        Objects.requireNonNull(record);

        int recordIndexInList = Collections.binarySearch(dictionaryRecords, record);

        if (recordIndexInList == -1) {

            return false;

        } else {

            dictionaryRecords.set(recordIndexInList, record);
            return true;
        }
    }

    @Override
    public boolean delete(DictionaryRecord record) {

        Objects.requireNonNull(record);

        int recordIndexInList = Collections.binarySearch(dictionaryRecords, record);

        if (recordIndexInList == -1) {

            return false;

        } else {

            dictionaryRecords.remove(recordIndexInList);
            return true;
        }
    }

}
