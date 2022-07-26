package kvbdev;

import kvbdev.install.InstallMain;
import kvbdev.loading.LoadingMain;
import kvbdev.saving.SavingMain;

public class Launcher {

    public static void main(String[] args) throws Exception {

        System.out.println("Задача 1: Установка");
        InstallMain.main(args);

        System.out.println("Задача 2: Сохранение");
        SavingMain.main(args);

        System.out.println("Задача 3: Загрузка");
        LoadingMain.main(args);
    }

}
