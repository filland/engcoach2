package comn.model.domain;

import java.util.Objects;

/**
 * A record from dictionary. It can be a word,
 * a phrase or a sentence
 */
public class DictionaryRecord implements Comparable<DictionaryRecord> {

    public enum DictRecType {
        WORD("word"),
        SENTENCE("sentence"),
        PHRASE("phrase"),
        MIXED("mixed");

        private final String typeName;

        DictRecType(String type) {
            this.typeName = type;
        }

        public String getTypeName() {
            return typeName;
        }

    }

    private long id;

    /**
     * User himself chooses what will be stored in this field
     */
    private String category;
    private DictRecType type;
    private String original;
    private String translation;

    public DictionaryRecord() {
    }

    public DictionaryRecord(String category, DictRecType type, String original, String translation) {
        this.category = category;
        this.type = type;
        this.original = original;
        this.translation = translation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public DictRecType getType() {
        return type;
    }

    public String getOriginal() {
        return original;
    }

    public String getTranslation() {
        return translation;
    }

    /**
     * From smallest to biggest
     */
    @Override
    public int compareTo(DictionaryRecord record) {

        if (this.getId() == record.getId()) {
            return 0;
        }

        return (this.getId() > record.getId()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "DictionaryRecord{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", type=" + type +
                ", original='" + original + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryRecord that = (DictionaryRecord) o;
        return id == that.id &&
                Objects.equals(category, that.category) &&
                type == that.type &&
                Objects.equals(original, that.original) &&
                Objects.equals(translation, that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, type, original, translation);
    }
}
