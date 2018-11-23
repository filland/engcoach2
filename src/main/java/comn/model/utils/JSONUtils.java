package comn.model.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import static comn.ProjectProperties.createBackupsOfDictionaries;

public class JSONUtils {

    private static long counter = 0;

    public static void createBackupJSONFile(String pathToJSONFile) throws IOException {

        if (!createBackupsOfDictionaries) {
            return;
        }

        String nameWithoutExtension = pathToJSONFile.split(".json")[0];
        Files.copy(
                new File(pathToJSONFile).toPath(),
                new File(
                        nameWithoutExtension +
                                "_" +
                                new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss_"+(++counter)).format(new Date()) +
                                ".json").toPath()
        );
    }

}
