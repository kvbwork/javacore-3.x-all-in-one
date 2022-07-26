package kvbdev.install;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLog {
    private final StringBuilder sb;
    private final DateTimeFormatter dateTimeFormatter;

    public SimpleLog() {
        sb = new StringBuilder();
        dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public void log(Object... parts) {
        sb.append("\n");
        sb.append(LocalDateTime.now().format(dateTimeFormatter));
        sb.append(" : ");
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

}
