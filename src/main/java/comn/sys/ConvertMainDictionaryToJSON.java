package comn.sys;

import com.google.gson.Gson;
import comn.model.domain.DictionaryRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConvertMainDictionaryToJSON {

    public static void main(String[] args) throws IOException {

        List<DictionaryRecord> records = new ArrayList<>();

        String oldSourceFile = "EngMaterials_latest.txt";
        String pathToJSONFile = "main_dictionary2.json";

        try (
                BufferedReader b = new BufferedReader(new FileReader(oldSourceFile))
        ) {

            long counter = 1;

            String category = null;
            DictionaryRecord.DictRecType type = null;

            String line;
            while ((line = b.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;
                if (line.startsWith("#")) continue;

                if (line.trim().startsWith("$1")) {
                    category = "words";
                    type = DictionaryRecord.DictRecType.WORD;
                    continue;
                }
                if (line.trim().startsWith("$2")) {
                    category = "useful things";
                    type = DictionaryRecord.DictRecType.MIXED;
                    continue;
                }
                if (line.trim().startsWith("$3")) {
                    category = "sentences from lists found on the Internet";
                    type = DictionaryRecord.DictRecType.SENTENCE;
                    continue;
                }
                if (line.trim().startsWith("$4")) {
                    category = "phrasal verbs";
                    type = DictionaryRecord.DictRecType.PHRASE;
                    continue;
                }
                if (line.trim().startsWith("$5")) {
                    category = "sentences, collected by me";
                    type = DictionaryRecord.DictRecType.SENTENCE;
                    continue;
                }
                if (line.trim().startsWith("$6")) {
                    category = "questions without translation, from the Int";
                    type = DictionaryRecord.DictRecType.SENTENCE;
                    continue;
                }
                if (line.trim().startsWith("$8")) {
                    category = "recently added sentences";
                    type = DictionaryRecord.DictRecType.SENTENCE;
                    continue;
                }

                String sys[] = line.split("-", 2);

                DictionaryRecord dictionaryRecord =
                        new DictionaryRecord(category, type, sys[0].trim(), sys[1].trim());
                dictionaryRecord.setId(counter++);

//                System.out.println(dictionaryRecord);
                records.add(dictionaryRecord);


            }

            Files.write(
                    new File(pathToJSONFile).toPath(),
                    new Gson().toJson(records).getBytes()
            );
        }


    }

}
