package comn;

import comn.data.DictionaryRepositoryJSONImp;
import comn.data.ELTDictionaryRepositoryJSONImp;
import comn.data.TranscriptionDictRepJSONImp;
import comn.model.EngCoach2App;
import comn.model.services.TranscriptionService;

import java.io.IOException;

/**
 *
 * This class works as a replacement of Spring Java confi
 *
 */
public class ProjectConfiguration {

    private ELTDictionaryRepositoryJSONImp eltdRepository;
    private TranscriptionDictRepJSONImp transcriptDictRepository;
    private TranscriptionService transcriptionService;
    private DictionaryRepositoryJSONImp dictionaryRepository;
    private EngCoach2App engCoach2;

    private final static ProjectConfiguration configuration = new ProjectConfiguration();

    private ProjectConfiguration() {
    }

    public static ProjectConfiguration getInstance() {
        return configuration;
    }

    public ELTDictionaryRepositoryJSONImp getEltdRepository() throws IOException {

        if (eltdRepository == null){

            eltdRepository =
                    new ELTDictionaryRepositoryJSONImp(ProjectProperties.pathToDecodedLettersTranscriptionsJSON);
            eltdRepository.loadJSONs();
        }

        return eltdRepository;
    }

    public TranscriptionDictRepJSONImp getTranscriptDictRepository() throws IOException {

        if (transcriptDictRepository == null){

            transcriptDictRepository =
                    new TranscriptionDictRepJSONImp(ProjectProperties.pathToTranscriptionDictionaryJSON);
            transcriptDictRepository.loadJSONs();
        }

        return transcriptDictRepository;
    }

    public TranscriptionService getTranscriptionService() throws IOException {

        if (transcriptionService == null){

            transcriptionService =
                    new TranscriptionService(getTranscriptDictRepository(), getEltdRepository());
        }

        return transcriptionService;
    }

    public DictionaryRepositoryJSONImp getDictionaryRepository() throws IOException {

        if (dictionaryRepository == null){

            dictionaryRepository =
                    new DictionaryRepositoryJSONImp(ProjectProperties.pathToDictionaryJSON);
            dictionaryRepository.loadJSONs();
        }

        return dictionaryRepository;
    }

    public EngCoach2App getEngCoach2() throws IOException {

        if (engCoach2 == null){

            engCoach2 = new EngCoach2App(getDictionaryRepository(), getTranscriptionService());
        }
        return engCoach2;
    }
}
