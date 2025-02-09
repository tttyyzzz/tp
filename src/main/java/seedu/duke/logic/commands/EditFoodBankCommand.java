package seedu.duke.logic.commands;

import seedu.duke.data.item.Item;
import seedu.duke.data.item.exceptions.DuplicateItemInBankException;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author xingjie99
/**
 * Represents the command that when executed, edits an item in the Food Bank.
 */
public class EditFoodBankCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Item number %d in the food bank has been changed to:"
            + CommandMessages.INDENTED_LS + "%s";
    public static final String[] EXPECTED_PREFIXES = {
            COMMAND_PREFIX_FOOD_BANK,
            COMMAND_PREFIX_NAME,
            COMMAND_PREFIX_CALORIES
    };

    private static Logger logger = Logger.getLogger(EditFoodBankCommand.class.getName());

    private final int itemIndex;
    private final String newName;
    private final Integer newCalories;

    public EditFoodBankCommand(int itemIndex, String newName, Integer newCalories) {
        this.itemIndex = itemIndex;
        this.newName = newName;
        this.newCalories = newCalories;
    }

    @Override
    public CommandResult execute() {
        if (super.foodBank.getSize() == 0) {
            logger.log(Level.WARNING, "Food bank list is empty.");
            return new CommandResult(CommandMessages.MESSAGE_EMPTY_FOOD_BANK);
        }
        try {
            Item item = super.foodBank.getItem(this.itemIndex);
            if (this.newName != null) {
                super.foodBank.checkNoDuplicateItemName(this.newName);
                item.setName(this.newName);
            }
            if (this.newCalories != null) {
                if (this.newCalories < 0) {
                    logger.log(Level.WARNING, "Detected negative food calorie");
                    return new CommandResult(CommandMessages.MESSAGE_INVALID_FOOD_CALORIES);
                }
                item.setCalories(this.newCalories);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.itemIndex + 1,
                    item.toStringWithoutDateAndTime()));
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Detected invalid food bank item index.");
            if (super.foodBank.getSize() == 1) {
                return new CommandResult(CommandMessages.MESSAGE_ONLY_ONE_IN_LIST);
            }
            return new CommandResult(String.format(
                    CommandMessages.MESSAGE_LIST_OUT_OF_BOUNDS, super.foodBank.getSize()));
        } catch (DuplicateItemInBankException e) {
            return new CommandResult(String.format(
                    CommandMessages.MESSAGE_FOOD_ALREADY_EXISTS_IN_BANK, this.newName));
        }
    }
}
