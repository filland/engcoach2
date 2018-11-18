package com.newModel;

import com.newModel.domain.DictionaryRecord;
import com.newModel.dto.Pair;
import com.services.TranscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class EngCoach2 {

    public enum TranslationOrder {
        FROM_ORIG_TO_TRANSLATION,
        FROM_TRANSLATION_TO_ORIGN
    }

    /**
     * dependencies
     */
    private DictionaryRepository dictionary;
    private TranscriptionService transcriptionService;
    /**
     * dependencies
     */

    private TranslationOrder currentOrder;
    private DictionaryRecord.DictRecType type;
    private String category;

    /**
     * List of records that were used recently
     */
    private List<DictionaryRecord> recentRecords;


    public EngCoach2(DictionaryRepository dictionary, TranscriptionService transcriptionService) {
        this.dictionary = dictionary;
        this.transcriptionService = transcriptionService;

        currentOrder = TranslationOrder.FROM_ORIG_TO_TRANSLATION;
        type = null;
        category = null;

        recentRecords = new ArrayList<>();
    }


    public void reset() {

        currentOrder = TranslationOrder.FROM_ORIG_TO_TRANSLATION;
        type = null;
        category = null;
    }

    /**
     * Specifies current translation order
     */
    public void setOrder(TranslationOrder order) {

        Objects.requireNonNull(order);
        currentOrder = order;
    }

    public void setType(DictionaryRecord.DictRecType type) {

        Objects.requireNonNull(type);
        this.type = type;
    }

    public void setCategory(String category) {

        Objects.requireNonNull(category);
        this.category = category;
    }

    public Pair getPair() {

        DictionaryRecord randomRecord = getNewRandomRecord();

        System.out.println("random record = " + randomRecord);

        if (currentOrder == TranslationOrder.FROM_ORIG_TO_TRANSLATION) {

            return new Pair(randomRecord.getOriginal(), randomRecord.getTranslation());

        } else {

            return new Pair(randomRecord.getTranslation(), randomRecord.getOriginal());

        }
    }

    public String getTranscription() {

        throw new RuntimeException();

    }

    /**
     * Returns random record. Filters can be applied
     */
    private DictionaryRecord getNewRandomRecord() {

        DictionaryRecord randomRecord;

        // if there is at least one filter
        if (type != null || category != null) {

            // find records that suits the filters
            List<DictionaryRecord> foundRecords = dictionary.findAll()
                    .stream()
                    .filter(record -> {

                        if (type != null) {

                            return record.getType().equals(type);

                        } else {

                            return true;
                        }
                    })
                    .filter(record -> {

                        if (category != null) {

                            return record.getCategory().equals(category);

                        } else {

                            return true;
                        }

                    })
                    .collect(Collectors.toList());

            // keep generating random number until getting the record that was not used yet
            do {

                int index = ThreadLocalRandom
                        .current()
                        .nextInt(0, foundRecords.size() + 1);

                randomRecord = foundRecords.get(index);

            } while (recentRecords.contains(randomRecord));

            // in case no filter specified
        } else {

            // keep generating random number until getting the record that was not used yet
            do {

                int index = ThreadLocalRandom
                        .current()
                        .nextInt(0, dictionary.findAll().size() + 1);

                randomRecord = dictionary.findAll().get(index);

            } while (recentRecords.contains(randomRecord));


        }

        addToRecentRecords(randomRecord);

        return randomRecord;
    }

    private void addToRecentRecords(DictionaryRecord record) {

        Objects.requireNonNull(record);

        if (recentRecords.size() == 500) {

            recentRecords.add(record);

        } else {

            recentRecords.add(record);

        }

    }


}
