package seedu.duke.logic.parser;

import seedu.duke.logic.commands.Command;
import seedu.duke.logic.commands.CommandMessages;
import seedu.duke.logic.commands.EditExerciseBankCommand;
import seedu.duke.logic.commands.EditFoodBankCommand;
import seedu.duke.logic.commands.EditFutureExerciseCommand;
import seedu.duke.logic.commands.InvalidCommand;
import seedu.duke.logic.parser.exceptions.ItemNotSpecifiedException;
import seedu.duke.logic.parser.exceptions.ParamInvalidException;
import seedu.duke.logic.parser.exceptions.ParamMissingException;

import java.time.LocalDate;
import java.util.logging.Logger;

//@@author xingjie99
/**
 * Parses input arguments for Edit commands.
 */
public class EditCommandParser implements Parser {

    protected static final Logger logger = Logger.getLogger(EditCommandParser.class.getName());

    @Override
    public Command parse(String params) {
        try {
            final String itemTypePrefix = ParserUtils.extractItemTypePrefix(params);
            switch (itemTypePrefix) {
            case Command.COMMAND_PREFIX_EXERCISE_BANK:
                //Fallthrough
            case Command.COMMAND_PREFIX_FOOD_BANK:
                return parseEditBank(params, itemTypePrefix);
            case Command.COMMAND_PREFIX_UPCOMING_EXERCISE:
                return parseEditUpcomingExercise(params, itemTypePrefix);
            default:
                throw new ItemNotSpecifiedException();
            }
        } catch (ItemNotSpecifiedException e) {
            return new InvalidCommand(
                    CommandMessages.MESSAGE_EDIT_COMMAND_INVALID_FORMAT);
        }
    }

    protected Command parseEditBank(String params, String itemTypePrefix)
            throws ItemNotSpecifiedException {
        try {
            final int itemIndex = ParserUtils.extractItemIndex(params, itemTypePrefix);
            if (ParserUtils.getNumberOfCorrectParamsDetected(params,
                    Command.COMMAND_PREFIX_NAME, Command.COMMAND_PREFIX_CALORIES) == 0) {
                return new InvalidCommand(CommandMessages.MESSAGE_EDIT_BANK_NEED_DETAILS);
            }
            final String description = ParserUtils.extractName(params);
            final Integer calories = ParserUtils.extractItemCalories(params, false);
            switch (itemTypePrefix) {
            case Command.COMMAND_PREFIX_EXERCISE_BANK:
                if (ParserUtils.hasExtraDelimiters(params, EditExerciseBankCommand.EXPECTED_PREFIXES)) {
                    return new InvalidCommand(ParserMessages.MESSAGE_ERROR_TOO_MANY_DELIMITERS);
                }
                return new EditExerciseBankCommand(itemIndex, description, calories);
            case Command.COMMAND_PREFIX_FOOD_BANK:
                if (ParserUtils.hasExtraDelimiters(params, EditFoodBankCommand.EXPECTED_PREFIXES)) {
                    return new InvalidCommand(ParserMessages.MESSAGE_ERROR_TOO_MANY_DELIMITERS);
                }
                return new EditFoodBankCommand(itemIndex, description, calories);
            default:
                throw new ItemNotSpecifiedException();
            }
        } catch (ParamInvalidException | ParamMissingException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    protected Command parseEditUpcomingExercise(String params, String itemTypePrefix)
            throws ItemNotSpecifiedException {
        try {
            final int itemIndex = ParserUtils.extractItemIndex(params, itemTypePrefix);
            if (ParserUtils.getNumberOfCorrectParamsDetected(params,
                    Command.COMMAND_PREFIX_NAME, Command.COMMAND_PREFIX_CALORIES, Command.COMMAND_PREFIX_DATE) == 0) {
                return new InvalidCommand(CommandMessages.MESSAGE_EDIT_UPCOMING_EXERCISE_LIST_NEED_DETAILS);
            }

            final String description = ParserUtils.extractName(params);
            final Integer calories = ParserUtils.extractItemCalories(params, false);
            final LocalDate date = ParserUtils.hasRequiredParams(params, Command.COMMAND_PREFIX_DATE)
                    ? ParserUtils.extractDate(params, false)
                    : null;
            switch (itemTypePrefix) {
            case Command.COMMAND_PREFIX_UPCOMING_EXERCISE:
                if (ParserUtils.hasExtraDelimiters(params, EditFutureExerciseCommand.EXPECTED_PREFIXES)) {
                    return new InvalidCommand(ParserMessages.MESSAGE_ERROR_TOO_MANY_DELIMITERS);
                }
                return new EditFutureExerciseCommand(itemIndex, description, calories, date);
            default:
                throw new ItemNotSpecifiedException();
            }
        } catch (ParamInvalidException | ParamMissingException e) {
            return new InvalidCommand(e.getMessage());
        }
    }
}
