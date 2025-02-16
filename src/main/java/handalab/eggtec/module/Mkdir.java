package handalab.eggtec.module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mkdir {
    public static String mkdir(String csvPath) throws IOException {
        // path
        String totalPath = Paths.get("").toAbsolutePath() + File.separator + "csv" + File.separator + csvPath + File.separator;
        Path directoryPath = Paths.get(totalPath);

        // mkdir
        try {
            Files.createDirectories(directoryPath); // create dir if not exists
        } catch (IOException e) {
            throw new IOException(e); // fail mkdir
        }
        return totalPath;
    }
}
