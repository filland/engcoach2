package comn.data;

import comn.ProjectProperties;
import comn.model.domain.LetterTranscriptionRecord;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class ELTDictionaryRepositoryJSONImpTest {

    private final static String pathToJSON = ProjectProperties.pathToDecodedLettersTranscriptionsJSON;

    private ELTDictionaryRepositoryJSONImp imp;

    @BeforeMethod
    public void setUp() {

        imp = new ELTDictionaryRepositoryJSONImp(pathToJSON);
    }

    @AfterMethod
    public void tearDown() {

        imp = null;

    }

    @Test
    public void testLoadJSONs() throws IOException {


        imp.loadJSONs();

    }

    @Test
    public void testFind() throws IOException {

        imp.loadJSONs();
        LetterTranscriptionRecord eltdRecord = imp.find("AE0");

        assertEquals(eltdRecord.getReadableLetterTranscription(), "æ");

    }

    @Test
    public void testFindAll() throws IOException {

        imp.loadJSONs();
        assertNotNull(imp.findAll());

    }
}