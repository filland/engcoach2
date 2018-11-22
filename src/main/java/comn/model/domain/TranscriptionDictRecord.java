package comn.model.domain;

public class TranscriptionDictRecord implements Comparable<TranscriptionDictRecord>{

    private String word;
    private String transcription;

    public TranscriptionDictRecord(String word, String transcription) {
        this.word = word;
        this.transcription = transcription;
    }

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    @Override
    public int compareTo(TranscriptionDictRecord record) {

        return this.getWord().compareTo(record.getWord());
    }
}
