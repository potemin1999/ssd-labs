package ssd.labs.calculator.cmd;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HistoryStorageTests {

    @Test
    public void testAppend() {
        var storage = new HistoryStorage(5);
        storage.pushEntry("i1", "r1");
        storage.pushEntry("i2", "r2");
        var expectedEntries = List.of(
                new HistoryStorage.Entry("i1", "r1"),
                new HistoryStorage.Entry("i2", "r2")
        );
        var actualEntries = storage.getHistoryEntries(3);
        Assert.assertArrayEquals(expectedEntries.toArray(), actualEntries.toArray());
    }

    @Test
    public void testCleanUp() {
        var storage = new HistoryStorage(2);
        storage.pushEntry("i1", "r1");
        storage.pushEntry("i2", "r2");
        storage.pushEntry("i3", "r3");
        var expectedEntries = List.of(
                new HistoryStorage.Entry("i2", "r2"),
                new HistoryStorage.Entry("i3", "r3")
        );
        var actualEntries = storage.getHistoryEntries(5);
        Assert.assertArrayEquals(expectedEntries.toArray(), actualEntries.toArray());
        storage.clear();
        var actualEntries2 = storage.getHistoryEntries(5);
        Assert.assertArrayEquals(new HistoryStorage.Entry[0], actualEntries2.toArray());
    }

    @Test
    public void testPrinter() {
        var env = new CommandProcessor.DefaultEnvironment();
        var storage = env.getHistoryStorage();
        storage.pushEntry("i11", "r22");
        storage.getHistoryEntries().forEach(Object::toString);
        HistoryStorage.Printer.print(env, new String[0]);
        //noinspection ResultOfMethodCallIgnored
        new HistoryStorage.Printer().toString();
    }

    @Test
    public void testEntry() {
        var entry = new HistoryStorage.Entry("i", "r");
        entry.setInput(entry.getResult());
        Assert.assertEquals("r", entry.getInput());
        entry.setResult("i");
        Assert.assertEquals("i", entry.getResult());
        //noinspection ResultOfMethodCallIgnored
        entry.toString();
        //noinspection ResultOfMethodCallIgnored
        entry.hashCode();

        var entry2 = new HistoryStorage.Entry("r", "i");
        Assert.assertTrue(entry.canEqual(entry2));
        Assert.assertEquals(entry, entry2);
    }
}
