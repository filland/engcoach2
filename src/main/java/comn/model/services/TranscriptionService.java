package comn.model.services;

import comn.data.EncodedLetterTranscriptionDictionaryRepository;
import comn.data.TranscriptionDictionaryRepository;
import comn.model.domain.TranscriptionDictRecord;

/**
 * The class provides transcriptions of english words
 */
public class TranscriptionService {

    private TranscriptionDictionaryRepository transcriptionDictionaryRepository;
    private EncodedLetterTranscriptionDictionaryRepository encodedLetterTranscriptionDictionaryRepository;

    public TranscriptionService(
            TranscriptionDictionaryRepository transcriptionDictionaryRepository,
            EncodedLetterTranscriptionDictionaryRepository encodedLetterTranscriptionDictionaryRepository) {
        this.transcriptionDictionaryRepository = transcriptionDictionaryRepository;
        this.encodedLetterTranscriptionDictionaryRepository = encodedLetterTranscriptionDictionaryRepository;
    }

    public String getTranscription(String wordInEnglish) {

        TranscriptionDictRecord transcription =
                transcriptionDictionaryRepository.findByWord(wordInEnglish.toLowerCase());

        if (transcription == null){
            return null;
        }

        return decodeTranscription(transcription.getTranscription(), " ");
    }

    /**
     * @param decodedTranscription word's transcription that consists of encoded letter's transcrips
     * @param delimiter            the string (whitespace, comma, dash, etc.)
     *                             that separates encoded letters of the transcription
     * @return readable transcription
     */
    private String decodeTranscription(String decodedTranscription, String delimiter) {

        String[] strings = decodedTranscription.split(delimiter);

        StringBuilder readableTranscription = new StringBuilder();

        for (String s : strings) {
            readableTranscription.append(
                    encodedLetterTranscriptionDictionaryRepository.find(s).getReadableLetterTranscription()
            );
        }

        return readableTranscription.toString();
    }

}
