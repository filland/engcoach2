package comn.model.domain;

/**
 * Full name - EncodedLetterTranscriptionDictionaryRecord
 *
 * This class stores:
 * - encoded symbol(s) that represent letter's transcription
 * - readable letter's transcription
 *
 */
public class LetterTranscriptionRecord {

    private String encodedLetterTranscription;
    private String readableLetterTranscription;

    public LetterTranscriptionRecord(String encodedTranscription, String typicalTranscription) {
        this.encodedLetterTranscription = encodedTranscription;
        this.readableLetterTranscription = typicalTranscription;
    }

    public String getEncodedLetterTranscription() {
        return encodedLetterTranscription;
    }

    public String getReadableLetterTranscription() {
        return readableLetterTranscription;
    }

    @Override
    public String toString() {
        return "LetterTranscriptionRecord{" +
                "encodedLetterTranscription='" + encodedLetterTranscription + '\'' +
                ", readableLetterTranscription='" + readableLetterTranscription + '\'' +
                '}';
    }
}
