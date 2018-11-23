package comn.model.services;

import comn.ProjectProperties;
import comn.data.ELTDictionaryRepositoryJSONImp;
import comn.data.EncodedLetterTranscriptionDictionaryRepository;
import comn.data.TranscriptionDictRepJSONImp;
import comn.data.TranscriptionDictionaryRepository;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TranscriptionServiceTest {

    private TranscriptionService transcriptionService;
    private EncodedLetterTranscriptionDictionaryRepository eltdRepository;
    private TranscriptionDictionaryRepository transcriptDictRepository;

    @BeforeClass
    public void init() throws IOException {

        eltdRepository = new ELTDictionaryRepositoryJSONImp(ProjectProperties.pathToDecodedLettersTranscriptionsJSON);
        ((ELTDictionaryRepositoryJSONImp) eltdRepository).loadJSONs();
        transcriptDictRepository = new TranscriptionDictRepJSONImp(ProjectProperties.pathToTranscriptionDictionaryJSON);
        ((TranscriptionDictRepJSONImp) transcriptDictRepository).loadJSONs();

        transcriptionService = new TranscriptionService(transcriptDictRepository, eltdRepository);

    }

    @AfterClass
    public void destroy() {

        eltdRepository = null;
        transcriptDictRepository = null;
        transcriptionService = null;
    }

    @Test
    public void testGetTranscription() {

        String transcription = transcriptionService.getTranscription("hello");
        Assert.assertNotNull(transcription);

    }
}