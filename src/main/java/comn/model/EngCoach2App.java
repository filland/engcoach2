package comn.model;

import comn.data.DictionaryRepository;
import comn.model.domain.DictionaryRecord;
import comn.model.dto.Pair;
import comn.model.services.TranscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The class that contains the main logic of the app
 * that is meant to show original and translation
 * of the word on the click of the button
 * <p>
 * This app provides you a simple and essential service
 * for learning any language: you can rehearse translation
 * of words and learn them this way.
 */
public class EngCoach2App {

    private final Random random = new Random();

    public enum TranslationOrder {
        FROM_ORIGIN_TO_TRANSLATION(1),
        FROM_TRANSLATION_TO_ORIGIN(2);

        private int order;

        TranslationOrder(int _order) {
            order = _order;
        }

        public int getOrder() {
            return order;
        }
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
    private DictionaryRecord currentRecord;
    private TranslationOrder currentOrder;
    private DictionaryRecord.DictRecType type;
    private String category;

    // this list is used to cache records that match by type and category
    private List<DictionaryRecord> filteredRecords;

    private Predicate<DictionaryRecord> filterByType;
    private Predicate<DictionaryRecord> filterByCategory;
    /**
     * temporary fields
     */

    // specifies max number of cached recently shown records
    private static final int MAX_NUMBER_OF_CACHED_DICTIONARY_RECORDS = 500;
    /**
     * List of records that were used recently
     */
    private List<DictionaryRecord> recentlyShownRecords;

    public EngCoach2App(DictionaryRepository dictionary,
                        TranscriptionService transcriptionService) {

        this.dictionary = dictionary;
        this.transcriptionService = transcriptionService;

        currentOrder = TranslationOrder.FROM_ORIGIN_TO_TRANSLATION;
        type = null;
        category = null;
        filteredRecords = null;

        filterByType = record -> {

            if (type != null) {

                return record.getType() == type;
            } else {
                return true;
            }

        };

        filterByCategory = record -> {

            if (category != null) {

                return record.getCategory().equals(category);
            } else {
                return true;
            }

        };

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

    /**
     * @param type is the type of the record from the dict to search for
     */
    public void setType(DictionaryRecord.DictRecType type) {

        Objects.requireNonNull(type);

        filteredRecords = null;
        this.type = type;
    }

    /**
     * @param category is the category of the record from the dict to search for
     */
    public void setCategory(String category) {

        Objects.requireNonNull(category);

        filteredRecords = null;
        this.category = category;
    }

    /**
     * Generates a origin-translation pair to be show in GUI
     *
     * @return a instance of DTO object Pair
     */
    public Pair getPair() {

        currentRecord = getRandomRecord();

//        System.out.println("random record = " + currentRecord);

        if (currentRecord == null) {
            return null;
        }

        if (currentOrder == TranslationOrder.FROM_ORIGIN_TO_TRANSLATION) {

            return new Pair(
                    currentRecord.getOriginal(),
                    currentRecord.getTranslation(),
                    transcriptionService.getTranscription(currentRecord.getOriginal()));

        } else {

            return new Pair(
                    currentRecord.getTranslation(),
                    currentRecord.getOriginal(),
                    transcriptionService.getTranscription(currentRecord.getOriginal()));
        }
    }

    private String getTranscription() {

        return transcriptionService.getTranscription(currentRecord.getOriginal());
    }

    /**
     * Returns random record. Filters can be applied
     */
    private DictionaryRecord getRandomRecord() {

        DictionaryRecord randomRecord;

        // if there is at least one filter
        if (type != null || category != null) {

            if (filteredRecords == null) {

                // find records that suits the filters
                filteredRecords = dictionary.findAll()
                        .stream()
                        .filter(filterByType)
                        .filter(filterByCategory)
                        .collect(Collectors.toList());
            }

            if (filteredRecords.isEmpty()) {
                return null;
            }

            // keep generating random number until getting the record that was not used yet
            do {

                int index = random.nextInt(filteredRecords.size());

                randomRecord = filteredRecords.get(index);

            } while (isInRecentlyShownRecords(randomRecord));

            // in case no filter specified
        } else {

            // keep generating random number until getting the record that was not used yet
            do {

                int index = random.nextInt(dictionary.findAll().size());

                randomRecord = dictionary.findAll().get(index);

            } while (isInRecentlyShownRecords(randomRecord));


        }

        addToRecentRecords(randomRecord);

        return randomRecord;
    }

    private boolean isInRecentlyShownRecords(DictionaryRecord record) {

//        return Collections.binarySearch(recentlyShownRecords, record) != -1;
        return recentlyShownRecords.contains(record);
    }

    private void addToRecentRecords(DictionaryRecord record) {

        Objects.requireNonNull(record);

        if (recentlyShownRecords.size() == MAX_NUMBER_OF_CACHED_DICTIONARY_RECORDS) {
            recentlyShownRecords.clear();
        }

        recentlyShownRecords.add(record);

//        Collections.sort(recentlyShownRecords);
    }


    public TranslationOrder getCurrentOrder() {
        return currentOrder;
    }

    @Deprecated
    public void showRecentlyShownRecords() {
        recentlyShownRecords.forEach(System.out::println);
    }


}
