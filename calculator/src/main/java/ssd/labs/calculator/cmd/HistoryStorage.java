package ssd.labs.calculator.cmd;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class HistoryStorage {

    int historyLimit;
    List<Entry> history;

    public HistoryStorage(int historyLimit) {
        this.historyLimit = historyLimit;
        history = new ArrayList<>(historyLimit + 1);
    }

    public void pushEntry(String input, String result) {
        history.add(new Entry(input, result));
        if (history.size() > historyLimit) {
            history.remove(0);
        }
    }

    public void clear() {
        history.clear();
    }

    public List<Entry> getHistoryEntries() {
        return getHistoryEntries(5);
    }

    public List<Entry> getHistoryEntries(int maxCount) {
        var outList = new ArrayList<Entry>(maxCount);
        for (var i = 0; i < maxCount && i < history.size(); i++) {
            outList.add(history.get(i));
        }
        return outList;
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class Entry {
        String input;
        String result;
    }

    public static class Printer {

        @Cmd(value = "history")
        public static int print(Environment env, String[] args) {
            var historyStorage = env.getHistoryStorage();
            print(historyStorage, System.out);
            return 0;
        }

        public static void print(HistoryStorage storage, PrintStream stream) {
            var historyStr = storage.getHistoryEntries()
                    .stream()
                    .map(entry -> entry.input + "  = " + entry.result)
                    .collect(Collectors.joining("\n"));
            stream.println(historyStr);
            stream.flush();
        }
    }

}
