package comn;

import comn.data.DictionaryRepositoryJSONImp;
import comn.data.ELTDictionaryRepositoryJSONImp;
import comn.data.TranscriptionDictRepJSONImp;
import comn.model.EngCoach2Model;
import comn.model.domain.DictionaryRecord;
import comn.model.services.TranscriptionService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.setProperty("file.encoding", "UTF-8");

        ELTDictionaryRepositoryJSONImp eltdRepository = new ELTDictionaryRepositoryJSONImp(ProjectProperties.pathToDecodedLettersTranscriptionsJSON);
        eltdRepository.loadJSONs();

        TranscriptionDictRepJSONImp transcriptDictRepository = new TranscriptionDictRepJSONImp(ProjectProperties.pathToTranscriptionDictionaryJSON);
        transcriptDictRepository.loadJSONs();

        TranscriptionService transcriptionService = new TranscriptionService(transcriptDictRepository, eltdRepository);

        DictionaryRepositoryJSONImp dictionaryRepository = new DictionaryRepositoryJSONImp(ProjectProperties.pathToDictionaryJSON);
        dictionaryRepository.loadJSONs();

        EngCoach2Model engCoach2 = new EngCoach2Model(dictionaryRepository, transcriptionService);

        engCoach2.setOrder(EngCoach2Model.TranslationOrder.FROM_TRANSLATION_TO_ORIGIN);
//        engCoach2.setCategory("sentences, collected by me");
        engCoach2.setType(DictionaryRecord.DictRecType.WORD);

        System.out.println("Get random records:");
        for (int i = 0; i < 5; i++) {

            System.out.println(engCoach2.getPair());
            System.out.println(engCoach2.getTranscription());

        }

        System.out.println("\nRecently shown records:");
        engCoach2.showRecentlyShownRecords();

    }
}
