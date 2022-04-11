package junit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import managers.*;

public class CommandManagerTest {
    // *************************** HELP COMMAND *******************************
    @Test
    public void testValidHelpCommand() {
        String command = "help";

        assertEquals(CommandManager.parseCommand(command), CommandParseResultType.VALID_COMMAND);
    }
    @Test
    public void testInvalidHelpCommand() {
        String command = "help";

        assertEquals(CommandManager.parseCommand(command), CommandParseResultType.VALID_COMMAND);
    }

    // *************************** START COMMAND *******************************
    @Test
    public void testValidStartCommand() {
        String command = "start";

        assertEquals(CommandManager.parseCommand(command), CommandParseResultType.VALID_COMMAND);
    }
    @Test
    public void testInvalidStartCommand() {
        String command = "start stuff";

        assertEquals(CommandManager.parseCommand(command), CommandParseResultType.INVALID_COMMAND);
    }
}
