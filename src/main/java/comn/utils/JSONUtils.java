package comn.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONUtils {

    public static void createBackup(String pathToJSONFile) throws IOException {

        String nameWithoutExtension = pathToJSONFile.split(".json")[0];
        Files.copy(
                new File(pathToJSONFile).toPath(),
                new File(
                        nameWithoutExtension +
                                "_" +
                                new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date()) +
                                ".json").toPath()
        );
    }

}
