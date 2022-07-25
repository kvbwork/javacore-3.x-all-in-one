package kvbdev.loading;

import kvbdev.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LoadingMain {

    public static void main(String[] args) throws Exception {
        openZip("games/savegames/zip.zip", "games/savegames");

        File[] savedGames = new File("games/savegames").listFiles((dir, name) -> name.endsWith(".dat"));
        for (File gameFile : savedGames) {
            GameProgress game = openProgress(gameFile.getPath());
            System.out.println(game);
        }
    }

    public static void openZip(String zipFilePath, String outputDir) throws IOException {
        File zipFile = new File(zipFilePath);
        try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipInput.getNextEntry()) != null) {
                File outFile = new File(outputDir, entry.getName());

                try (FileOutputStream out = new FileOutputStream(outFile)) {
                    zipInput.transferTo(out);
                }
                zipInput.closeEntry();
            }
        }

    }

    public static GameProgress openProgress(String filePath) throws IOException, ClassNotFoundException {
        File srcFile = new File(filePath);
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(srcFile))) {
            return (GameProgress) input.readObject();
        }
    }

}
