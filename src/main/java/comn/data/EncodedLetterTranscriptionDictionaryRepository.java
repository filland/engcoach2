package comn.data;


import comn.model.domain.LetterTranscriptionRecord;

import java.util.List;

/**
 * Full name - DecodedLetterTranscriptionDictRepository
 *
 * This class provides access to the encoded letters transcriptions dictionary
 *
 * Info:
 * Transcription of words are encoded using a language
 * (for examp: the word HELLO has the following transcription HH AH0 L OW1 )
 *
 * This repository allows to read a table with mapping (encoded
 * transcription = readable transcription)
 *
 *
 *
 * @note This repo does not contain create/update/delete methods as dictionary
 * is completed so no modification is needed
 *
 */
public interface EncodedLetterTranscriptionDictionaryRepository {

    LetterTranscriptionRecord find(String encodedLetterTranscription);

    List<LetterTranscriptionRecord> findAll();

}
