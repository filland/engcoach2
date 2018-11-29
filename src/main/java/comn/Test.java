package comn;

import comn.model.EngCoach2Model;
import comn.model.domain.DictionaryRecord;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        System.setProperty("file.encoding", "UTF-8");

        ProjectConfiguration configuration = ProjectConfiguration.getInstance();

        EngCoach2Model engCoach2 =  configuration.getEngCoach2();

        engCoach2.setOrder(EngCoach2Model.TranslationOrder.FROM_TRANSLATION_TO_ORIGIN);
//        engCoach2.setCategory("sentences, collected by me");
        engCoach2.setType(DictionaryRecord.DictRecType.WORD);

        System.out.println("Get random records:");
        for (int i = 0; i < 1; i++) {

            System.out.println(i);
            System.out.println(engCoach2.getPair());
//            System.out.println(engCoach2.getTranscription());

        }


//        engCoach2.reset();
        engCoach2.setCategory("sentences, collected by me");
        System.out.println("\n");

        System.out.println("Get random records:");
        for (int i = 0; i < 5; i++) {

            System.out.println(i);
            System.out.println(engCoach2.getPair());
//            System.out.println(engCoach2.getTranscription());

        }

        System.out.println("\nRecently shown records:");
        engCoach2.showRecentlyShownRecords();

    }
}
