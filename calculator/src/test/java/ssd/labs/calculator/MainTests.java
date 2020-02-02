package ssd.labs.calculator;

import org.junit.Test;

import java.io.ByteArrayInputStream;

public class MainTests {

    @Test
    public void testJustExit() {
        var inBackup = System.in;
        var exitBytes = "/exit\n".getBytes();
        System.setIn(new ByteArrayInputStream(exitBytes));
        Main.main(new String[]{"ALL"});

        System.setIn(null);
        Main.main(new String[0]);
        System.setIn(inBackup);
    }

}
