package seedu.duke;

import seedu.duke.data.DataManager;
import seedu.duke.logic.LogicManager;
import seedu.duke.logic.commands.CommandResult;
import seedu.duke.main.StartState;
import seedu.duke.storage.StorageManager;
import seedu.duke.storage.exceptions.UnableToWriteFileException;
import seedu.duke.ui.Ui;


/**
 * Main class of Fitbot.
 * Initialises the application and starts interaction with user.
 */
public class Main {
    private DataManager dataManager;
    private Ui ui;
    private StorageManager storageManager;
    private LogicManager logicManager;


    /**
     * Entry point of the application.
     */
    public static void main(String[] args) throws UnableToWriteFileException {
        new Main().run(args);
    }

    /**
     * Runs the application until command is given to exit it.
     **/
    private void run(String[] args) throws UnableToWriteFileException {
        start();
        checkAndCreateProfile();
        enterTaskModeUntilByeCommand();
        exit();
    }

    //@@author tttyyzzz
    private void checkAndCreateProfile() {
        dataManager.setProfile(new StartState(dataManager.getProfile(), storageManager, ui).checkAndCreateProfile());
    }
    //@@author

    /**
     * Initialises the application by creating the required objects and loading data from the
     * storage file, then showing the welcome message.
     */
    private void start() {
        this.storageManager = new StorageManager();
        this.ui = new Ui();
        this.dataManager = storageManager.loadAll();
        this.logicManager = new LogicManager(storageManager, dataManager);
        ui.printStartMessage(
                dataManager.getProfile().checkProfileComplete(),
                dataManager.getProfile().checkProfilePresent());
    }

    /**
     * Reads the user input and executes appropriate command.
     * Runs indefinitely until user inputs the Bye command.
     */
    private void enterTaskModeUntilByeCommand() {
        CommandResult result;
        do {
            String userInput = ui.getUserInput();
            result = logicManager.execute(userInput);
            ui.formatMessageFramedWithDivider(result.toString());
        } while (!result.isBye());
    }


    /**
     * Exits the application.
     */
    private void exit() {
        System.exit(0);
    }

}
