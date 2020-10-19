package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;


public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testMain() throws Exception {
        Main.main(new String[] { "input.xml" });
        Assert.assertEquals("Input ==> input.xml\n" +
                "Output ==> output.sax.xml\n" +
                "Output ==> output.dom.xml\n" +
                "Output ==> output.stax.xml\n", outContent.toString());
        File myObj1 = new File("output.dom.xml");
        myObj1.delete();
        File myObj2 = new File("output.sax.xml");
        myObj2.delete();
        File myObj3 = new File("output.stax.xml");
        myObj3.delete();
    }
}