import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class TextBuddyTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private static Vector<String> fileContent;

  @BeforeClass
  public static void setResources() {
    fileContent = new Vector<String>(Arrays.asList(
        new String[] {"submit op1", "add nobody as friend", 
                      "say hi to my dog"}));
  }

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void tearDown() {
    System.setOut(null);
    System.setErr(null);
  }

  @Test
  public void processCommand_InvalidCommand_PrintInvalid() throws IOException {
    TextBuddy.processCommand("kill me");
    assertEquals("\nInvalid Command\n\n", outContent.toString());
  }
  
  @Test
  public void printFileContent_EmptyArgument_PrintEmpty() {
    Vector<String> fileContent = new Vector<String>();
    String filePath = "/test";
    TextBuddy.printFileContent(fileContent, filePath);
    assertEquals("\n/test is empty\n\n", outContent.toString());
  }


  @Test
  public void getFileContent_RegularContent_ReturnStrings() {
    String expected = "1. submit op1\n\n2. add nobody as friend\n\n"
        + "3. say hi to my dog";
    String formattedFileContent = TextBuddy.getFileContent(fileContent);
    assertEquals(expected, formattedFileContent);
  }
  
  @Test
  public void getCommand_NormalCommand_ReturnCommand() {
    String input = TextBuddy.getCommand("add this is something new");
    String expected = "add";
    assertEquals(expected, input);
  }
  
  @Test
  public void sortFileContent_NormalFileContent_SortLines() {
    Vector<String> testContent = new Vector<String>(Arrays.asList(
        new String[]{"add maybe", "say hello", "kick me"}));
    String expected = "1. add maybe\n\n2. kick me\n\n"
        + "3. say hello";
    TextBuddy.sortFileContent(testContent);
    String input = TextBuddy.getFileContent(testContent);
    assertEquals(expected, input);
  }

}
