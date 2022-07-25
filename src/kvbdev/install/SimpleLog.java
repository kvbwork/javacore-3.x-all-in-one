package kvbdev.install;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleLog {
    private final StringBuilder sb;

    public SimpleLog() {
        sb = new StringBuilder();
    }

    public void log(Object... parts) {
        sb.append("\n");
        append(parts);
    }

    public SimpleLog append(Object... parts) {
        for (Object obj : parts) {
            sb.append(obj);
        }
        return this;
    }

    public String getLog() {
        return sb.toString();
    }

    public boolean writeToFile(String filePath) {
        File logFile = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Невозможно записать файл журнала: " + e.getMessage());
            return false;
        }
        return true;
    }

}
