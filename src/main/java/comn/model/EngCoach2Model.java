package comn.model;

import com.services.TranscriptionService;
import comn.data.DictionaryRepository;
import comn.model.domain.DictionaryRecord;
import comn.model.dto.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * The class that contains the main logic of the app
 * that is meant to show original and translation
 * of the word by click of the button
 *
 * This app provides you a simple and essential service
 * for learning any language: you can rehearse translation
 * of words and learn them in this way.
 */
public class EngCoach2Model {

    public enum TranslationOrder {
        FROM_ORIGIN_TO_TRANSLATION,
        FROM_TRANSLATION_TO_ORIGIN
    }

    /**
     * dependencies
     */
    private DictionaryRepository dictionary;
    private TranscriptionService transcriptionService;
    /**
     * dependencies
     */

    /**
     * temporary fields
     */
    private TranslationOrder currentOrder;
    private DictionaryRecord.DictRecType type;
    private String category;

    // this list is used to cache records that match by type and category
    private List<DictionaryRecord> filteredRecords;
    /**
     * temporary fields
     */

    /**
     * List of records that were used recently
     */
    private List<DictionaryRecord> recentlyShownRecords;


    public EngCoach2Model(DictionaryRepository dictionary, TranscriptionService transcriptionService) {
        this.dictionary = dictionary;
        this.transcriptionService = transcriptionService;

        currentOrder = TranslationOrder.FROM_ORIGIN_TO_TRANSLATION;
        type = null;
        category = null;

        recentlyShownRecords = new ArrayList<>();
    }


    /**
     * To clear all settings and be able to start anew
     */
    public void reset() {

        currentOrder = TranslationOrder.FROM_ORIGIN_TO_TRANSLATION;
        type = null;
        category = null;
        filteredRecords = null;
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

    /**
     * Generates a origin-translation pair to be show in GUI
     *
     * @return a instance of DTO object Pair
     */
    public Pair getPair() {

        DictionaryRecord randomRecord = getRandomRecord();

        System.out.println("random record = " + randomRecord);

        if (currentOrder == TranslationOrder.FROM_ORIGIN_TO_TRANSLATION) {

            return new Pair(randomRecord.getOriginal(), randomRecord.getTranslation());

        } else {

            return new Pair(randomRecord.getTranslation(), randomRecord.getOriginal());

        }
    }

    public String getTranscription() {

        // TODO !!!
        throw new RuntimeException();

    }

    /**
     * Returns random record. Filters can be applied
     */
    // TODO since the method is using streams Cant we place filters somewhere else ?
    private DictionaryRecord getRandomRecord() {

        DictionaryRecord randomRecord;

        // if there is at least one filter
        if (type != null || category != null) {

            if (filteredRecords == null) {

                // find records that suits the filters
                filteredRecords = dictionary.findAll()
                        .stream()
                        .filter(record -> record.getType() == type)
                        .filter(record -> record.getCategory().equals(category))
                        .collect(Collectors.toList());
            }


            // keep generating random number until getting the record that was not used yet
            do {

                int index = ThreadLocalRandom
                        .current()
                        .nextInt(0, filteredRecords.size());

                randomRecord = filteredRecords.get(index);

            } while (isInRecentlyShownRecords(randomRecord));

            // in case no filter specified
        } else {

            // keep generating random number until getting the record that was not used yet
            do {

                int index = ThreadLocalRandom
                        .current()
                        .nextInt(0, dictionary.findAll().size() + 1);

                randomRecord = dictionary.findAll().get(index);

            } while (isInRecentlyShownRecords(randomRecord));


        }

        addToRecentRecords(randomRecord);

        return randomRecord;
    }

    private boolean isInRecentlyShownRecords(DictionaryRecord record) {

        return Collections.binarySearch(this.recentlyShownRecords, record) != -1;
    }

    private void addToRecentRecords(DictionaryRecord record) {

        Objects.requireNonNull(record);

        if (recentlyShownRecords.size() == 500) {

            recentlyShownRecords.add(record);

        } else {
            recentlyShownRecords.add(record);
        }

        Collections.sort(recentlyShownRecords);
    }


    @Deprecated
    public void showRecentlyShownRecords() {
        recentlyShownRecords.forEach(System.out::println);
    }


}
