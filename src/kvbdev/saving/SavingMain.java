package kvbdev.saving;

import kvbdev.GameProgress;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SavingMain {

    public static void main(String[] args) throws IOException {

        GameProgress game1 = new GameProgress(100, 1, 1, 0);
        GameProgress game2 = new GameProgress(50, 3, 2, 1000);
        GameProgress game3 = new GameProgress(25, 5, 3, 5000);

        saveGame("games/savegames/game1.dat", game1);
        saveGame("games/savegames/game2.dat", game2);
        saveGame("games/savegames/game3.dat", game3);

        List<String> savedGames = List.of(
                "games/savegames/game1.dat",
                "games/savegames/game2.dat",
                "games/savegames/game3.dat");

        zipFiles("games/savegames/zip.zip", savedGames);

        savedGames.stream()
                .map(File::new)
                .forEach(File::delete);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) throws IOException {
        File outFile = new File(filePath);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile))) {
            out.writeObject(gameProgress);
        }
    }

    public static void zipFiles(String zipFilePath, List<String> files) throws IOException {
        File outZipFile = new File(zipFilePath);

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outZipFile))) {
            for (String filePath : files) {
                File srcFile = new File(filePath);
                ZipEntry entry = new ZipEntry(srcFile.getName());
                zipOut.putNextEntry(entry);

                // Было бы эффективнее использовать методы transferTo() или readAllBytes()
                // но в условии сказано применять методы read() и write()

                try (FileInputStream input = new FileInputStream(srcFile)) {
                    int b;
                    while ((b = input.read()) != -1) {
                        zipOut.write(b);
                    }
                }

                zipOut.closeEntry();
            }
        }

    }
}
