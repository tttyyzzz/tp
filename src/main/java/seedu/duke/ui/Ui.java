package seedu.duke.ui;

import seedu.duke.logic.commands.HelpCommand;
import seedu.duke.logic.parser.exceptions.ParamMissingException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class deals with interaction with user on CLI.
 * Also helps to change color of output if required.
 */
public class Ui {

    private static final String TAB = "\t";
    public static final String DIVIDER = "___________________________________________"
            + "_______________________________________________________________";
    public static final String LS = System.lineSeparator();
    public static final String INDENTED_LS = LS + TAB;
    private static final String FITBOT_V0 = "  ______ _ _   _           _"
            + LS
            + " |  ____(_) | | |         | |"
            + LS
            + " | |__   _| |_| |__   ___ | |_"
            + LS
            + " |  __| | | __| '_ \\ / _ \\| __|"
            + LS
            + " | |    | | |_| |_) | (_) | |_"
            + LS
            + " |_|    |_|\\__|_.__/ \\___/ \\__|";
    private static final String MESSAGE_WELCOME = "Welcome to Fitbot!"
            + " Fitbot is here to help you to keep track of your calories.";
    public static final String MESSAGE_DIRECT_HELP = "You can start by typing a command or view the list of "
            + "available commands by typing " + HelpCommand.MESSAGE_COMMAND_FORMAT + ".";
    private static final String MESSAGE_FIX_PROFILE = "Fitbot realised that some of your profile "
            + "attributes are missing.\n"
            + "Please follow the guide below so that your profile can be complete.";
    private static final String MESSAGE_NEW_PROFILE = "Fitbot realised that your profile has not been created."
            + " Let's start creating a profile below!";
    private static final String MESSAGE_EMPTY_INPUT = "Input cannot be empty";

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
        this.printStartApplicationPage();
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    private static Logger logger = Logger.getLogger(Ui.class.getName());

    /**
     * Surround strings with lines for user to differentiate results.
     *
     * @param messages is the strings that need to be printed on CLI
     */
    public void formatMessageFramedWithDivider(String... messages) {
        System.out.println(DIVIDER);
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println(DIVIDER);
    }

    public void formatMessageWithTopDivider(String... messages) {
        System.out.println(DIVIDER);
        for (String message : messages) {
            System.out.println(message);
        }
    }

    public void formatMessageWithBottomDivider(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println(DIVIDER);
    }

    public void printStartApplicationPage() {
        logger.log(Level.FINE, "start of application");
        System.out.println(FITBOT_V0 + LS + MESSAGE_WELCOME);
    }

    public void printStartMessage(boolean isProfileComplete, boolean isProfilePresent) {
        if (isProfileComplete) {
            System.out.println(MESSAGE_DIRECT_HELP);
            return;
        }
        if (isProfilePresent) {
            System.out.println(MESSAGE_FIX_PROFILE);
        }

        System.out.println(MESSAGE_NEW_PROFILE);
    }

    /**
     * Checks if user input is empty.
     *
     * @param userInput input from the user.
     * @throws ParamMissingException if input length is 0 (missing).
     */
    public void checkEmptyUserInput(String userInput) throws ParamMissingException {
        if (userInput.length() == 0) {
            throw new ParamMissingException(MESSAGE_EMPTY_INPUT);
        }
    }
}
