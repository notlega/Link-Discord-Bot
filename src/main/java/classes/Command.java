package classes;

public class Command {

    private int id;
    private String command;
    private String commandSyntax;
    private String commandDescription;

    public Command(int id, String command, String commandSyntax, String commandDescription) {
        this.id = id;
        this.command = command;
        this.commandSyntax = commandSyntax;
        this.commandDescription = commandDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandSyntax() {
        return commandSyntax;
    }

    public void setCommandSyntax(String commandSyntax) {
        this.commandSyntax = commandSyntax;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public void setCommandDescription(String commandDescription) {
        this.commandDescription = commandDescription;
    }
}
