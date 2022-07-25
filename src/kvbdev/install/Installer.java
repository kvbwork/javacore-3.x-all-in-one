package kvbdev.install;

import java.io.File;
import java.io.IOException;

public class Installer {

    private final String installDir;
    private final SimpleLog logger;

    public Installer(String installDir, SimpleLog logger) {
        this.installDir = installDir;
        this.logger = logger;
    }

    public boolean install() {
        boolean success = true;

        File rootPath = new File(installDir);

        if (!(rootPath.exists() & rootPath.isDirectory())) {
            logger.log("Директория установки не существует: '", rootPath.getAbsolutePath(), "'");
            return false;
        }

        success &= makeDirectories("src", "res", "savegames", "temp");
        success &= makeDirectories("src/main", "src/test");
        success &= makeFiles("src/main", "Main.java", "Utils.java");
        success &= makeDirectories("res/drawables", "res/vectors", "res/icons");
        success &= makeFiles("temp", "temp.txt");

        if (success) {
            logger.log("Необходимые файлы и каталоги были успешно созданы.");
        } else {
            logger.log("При создании файлов и каталогов произошли ошибки.");
        }

        return success;
    }

    protected boolean makeDirectories(String... dirNames) {
        boolean success = true;

        for (String dirName : dirNames) {
            File newPath = new File(installDir, dirName);
            logger.log("Создание директории '", newPath.getAbsolutePath(), "'");

            if (newPath.exists() & newPath.isDirectory()) {
                logger.append(" - уже существует.");
                continue;
            }

            if (newPath.mkdir()) {
                logger.append(" - успешно.");
            } else {
                logger.append(" - ошибка!");
                success = false;
            }
        }

        return success;
    }

    protected boolean makeFiles(String dirName, String... fileNames) {
        boolean success = true;

        for (String fileName : fileNames) {
            File newFile = new File(installDir + File.separator + dirName, fileName);
            logger.log("Создание файла '", newFile.getAbsolutePath(), "'");

            if (newFile.exists() & newFile.isFile()) {
                logger.append(" - уже существует.");
                continue;
            }

            try {
                if (newFile.createNewFile()) {
                    logger.append(" - успешно.");
                } else {
                    logger.append(" - ошибка!");
                    success = false;
                }
            } catch (IOException ex) {
                logger.append(" - ошибка: ", ex.getMessage());
                success = false;
            }
        }

        return success;
    }

}
