package kvbdev.install;

import java.io.File;

public class InstallMain {

    public static void main(String[] args) {

        // Предварительно вручную создайте папку Games в удобном для Вас месте. Имейте в виду,
        // что у папки Games должны быть права на запись и чтение.

        String installDir = "games";
        SimpleLog logger = new SimpleLog();

        Installer installer = new Installer(installDir, logger);
        installer.install();

        logger.writeToFile(installDir + File.separator + "temp/temp.txt");
        System.out.println(logger.getLog());
    }
}
