package managers;

import java.util.StringTokenizer;

public class CommandManager {
    public static CommandParseResultType parseCommand(String data) {
        // Format the string to all upper-case to avoid case-sensitivity
        data = data.toUpperCase();

        String[] splitData = data.split(" ");

        // If there's no data, then return invalid
        if (splitData.length < 1) {
            return CommandParseResultType.INVALID_COMMAND;
        }

        // Parse command
        if (splitData[CommandLocations.COMMAND_TYPE] == "help") {
            if (splitData.length == 1) {
                return CommandParseResultType.VALID_COMMAND;
            }

            // Check if anything past this part of the command is in the list of valid commands (to return help info for specific commands
            return CommandParseResultType.INVALID_COMMAND;
        }
        else if (splitData[CommandLocations.COMMAND_TYPE] == "start") {
            if (splitData.length == 1) {
                return CommandParseResultType.VALID_COMMAND;
            }

            // In this case there are too many arguments for the command
            return CommandParseResultType.INVALID_ARGUMENTS;
        }
        else {
            // If the command type is not a current command, then return invalid
            return CommandParseResultType.INVALID_COMMAND;
        }
    }
}
