package comn.data;

import comn.model.domain.DictionaryRecord;

import java.util.List;

/**
 * Provides access to the custom-made dictionary
 */
public interface DictionaryRepository {

    DictionaryRecord findById(long id);

    List<DictionaryRecord> findAll();

    List<DictionaryRecord> findAllByType(DictionaryRecord.DictRecType type);

    void create(DictionaryRecord record);

    boolean update(DictionaryRecord record);

    boolean delete(DictionaryRecord record);

}
