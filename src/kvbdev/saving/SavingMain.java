package kvbdev.saving;

import kvbdev.GameProgress;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SavingMain {

    public static void main(String[] args) throws IOException {

        String savedGamesDir = "games/savegames";
        String archivePathStr = savedGamesDir + File.separator + "archive.zip";
        AtomicInteger saveNum = new AtomicInteger();

        Supplier<String> filePathFactory = () ->
                savedGamesDir + File.separator + "save" + saveNum.incrementAndGet() + ".dat";

        List<GameProgress> gameStates = List.of(
                new GameProgress(100, 1, 1, 0.0),
                new GameProgress(50, 3, 2, 333.33),
                new GameProgress(25, 5, 3, 5678.90)
        );

        List<String> savedGames = saveGamesList(gameStates, filePathFactory);

        zipFiles(archivePathStr, savedGames);

        savedGames.stream()
                .map(File::new)
                .forEach(File::delete);

    }

    public static List<String> saveGamesList(Iterable<GameProgress> gameStates, Supplier<String> filePathFactory)
            throws IOException {
        List<String> savedGamesFileNames = new LinkedList<>();

        for (GameProgress game : gameStates) {
            String filePath = filePathFactory.get();
            saveGame(filePath, game);
            savedGamesFileNames.add(filePath);
        }
        return savedGamesFileNames;
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

                try (FileInputStream fis = new FileInputStream(srcFile);
                     BufferedInputStream input = new BufferedInputStream(fis)) {
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
