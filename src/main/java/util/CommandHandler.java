package util;

import exceptions.FolderNotFoundException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import records.CommandContainer;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    private static final Map<String, Command> commands = new HashMap<>();

    public static Map<String, Command> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    public static void initialiseCommands() {
        try {
            Reflections reflections = new Reflections("commands");
            Set<Class<? extends Command>> commandSet = reflections.getSubTypesOf(Command.class);

            if (commandSet == null) {
                throw new FolderNotFoundException("Folder containing commands was not found!");
            }

            for (Class<? extends Command> command : commandSet) {
                logger.info("Command: " + command.getSimpleName());
                commands.put(command.getSimpleName(), command.getDeclaredConstructor().newInstance());
            }

            logger.info("Successfully added all commands to \"commands\" HashMap!");
        } catch (FolderNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    public static void handleCommand(CommandContainer commandContainer) throws Exception {
        logger.info("Command: " + commandContainer.command());
        Command command = getCommands().get(commandContainer.command());
        command.executeCommand(commandContainer);
    }
}
