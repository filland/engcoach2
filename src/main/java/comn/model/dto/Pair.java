package comn.model.dto;

/**
 *
 * DTO for passing content of dictionary record from EndCoach2 to Controller
 *
 */
public class Pair {

    // show this string first
    private String first;

    // and then show this one
    private String second;

    private String transcription;

    public Pair(String first, String second, String transcription) {
        this.first = first;
        this.second = second;
        this.transcription = transcription;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public String getTranscription() {
        return transcription;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", transcription='" + transcription + '\'' +
                '}';
    }
}
