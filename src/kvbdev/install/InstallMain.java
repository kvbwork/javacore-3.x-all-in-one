package kvbdev.install;

import java.util.List;

public class InstallMain {

    public static void main(String[] args) {

        // Предварительно вручную создайте папку Games в удобном для Вас месте. Имейте в виду,
        // что у папки Games должны быть права на запись и чтение.

        String installDir = "games";
        SimpleLog logger = new SimpleLog();

        List<String> paths = List.of(
                "src/",
                "src/main/",
                "src/main/Main.java",
                "src/main/Utils.java",
                "src/test/",
                "res/",
                "res/drawables/",
                "res/vectors/",
                "res/icons/",
                "savegames/",
                "temp/",
                "temp/temp.txt"
        );

        Installer installer = new Installer(installDir, logger);
        installer.install(paths);
        installer.writeFile("temp/temp.txt", logger.getLog());

        System.out.println(logger.getLog());
    }
}
