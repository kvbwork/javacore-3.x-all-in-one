package kvbdev.install;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Installer {

    protected final String installDir;
    protected final SimpleLog logger;

    public Installer(String installDir, SimpleLog logger) {
        this.installDir = installDir;
        this.logger = logger;
    }

    public boolean install(Iterable<String> paths) {
        boolean success = true;
        File rootPath = new File(installDir);

        if (!(rootPath.exists() & rootPath.isDirectory())) {
            logger.log("Директория установки не существует: '", rootPath.getAbsolutePath(), "'.");
            return false;
        }

        for (String path : paths) {
            File newPath = new File(rootPath, path);
            if (path.endsWith("/")) {
                success &= makeDirectory(newPath);
            } else {
                success &= makeFile(newPath);
            }
        }

        if (success) {
            logger.log("Файлы и каталоги были успешно созданы.");
        } else {
            logger.log("При создании файлов и каталогов произошли ошибки.");
        }

        return success;
    }

    protected boolean makeDirectory(File newPath) {
        logger.log("Создание директории '", newPath.getAbsolutePath(), "'");

        if (newPath.exists() & newPath.isDirectory()) {
            logger.append(" - уже существует.");
        } else {
            if (newPath.mkdir()) {
                logger.append(" - успешно.");
            } else {
                logger.append(" - ошибка!");
                return false;
            }
        }
        return true;
    }

    protected boolean makeFile(File newFile) {
        logger.log("Создание файла '", newFile.getAbsolutePath(), "'");

        if (newFile.exists() & newFile.isFile()) {
            logger.append(" - уже существует.");
        } else {
            try {
                if (newFile.createNewFile()) {
                    logger.append(" - успешно.");
                } else {
                    logger.append(" - ошибка!");
                    return false;
                }
            } catch (IOException ex) {
                logger.append(" - ошибка: ", ex.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean writeFile(String filePath, String content) {
        File newFile = new File(installDir, filePath);
        logger.log("Запись в файл '", newFile.getAbsolutePath(), "'");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            writer.write(content);
            logger.append(" - успешно.");
        } catch (IOException e) {
            logger.log("При записи файла произошла ошибка: ", e.getMessage());
            return false;
        }
        return true;
    }
}
