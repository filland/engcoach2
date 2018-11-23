package comn.data;

import comn.model.domain.TranscriptionDictRecord;

import java.util.List;

public interface TranscriptionDictionaryRepository {

    TranscriptionDictRecord findByWord(String word);

    List<TranscriptionDictRecord> findAll();

}
