package seedu.duke.main;

import seedu.duke.data.profile.Profile;
import seedu.duke.data.profile.attributes.ActivityFactor;
import seedu.duke.data.profile.utilities.ProfileUtils;
import seedu.duke.logic.parser.exceptions.ParamMissingException;
import seedu.duke.ui.Ui;

/**
 * Creates a ActivityFactor Class and prompts user for valid activity factor input.
 */
public class CreateActivityFactor {

    private Ui ui;
    private ActivityFactor activityFactor = new ActivityFactor();
    private static final String MESSAGE_ACTIVITY_FACTOR = "Your activity factor is %s.";
    public static final String MESSAGE_INVALID_POSITIVE_INT_INPUT = "Invalid input, "
            + "please input a valid positive whole number";
    private static final String MESSAGE_INTRO_ACTIVITY_FACTOR = "In terms of activity level, how active are you?\n"
            + "Based on the rubics below, please key in 1 to 5 based on how active you are.\n"
            + "1 -> Sedentary - Little or no exercise\n"
            + "2 -> Lightly Active - Light exercise or sports, around 1-3 days a week\n"
            + "3 -> Moderately Active - Regular exercise or sports, around 3-5 days a week\n"
            + "4 -> Very Active - Frequent exercise or sports, around 6-7 days a week\n"
            + "5 -> If you are extra active - Sports or exercising is your passion and a physical jobscope.";

    public CreateActivityFactor(Ui ui) {
        this.ui = ui;
    }

    /**
     * Creates a valid profile activity factor for the profile instance.
     *
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    public ActivityFactor createActivityFactor() throws ParamMissingException {
        while (!activityFactor.isValid()) {
            ui.formatMessageWithBottomDivider(MESSAGE_INTRO_ACTIVITY_FACTOR);
            try {
                String userInput = ui.getUserInput();
                ui.checkEmptyUserInput(userInput);
                int activityFactorInput = Integer.parseInt(userInput);
                activityFactor.setUserInput(activityFactorInput);
                if (activityFactor.isValid()) {
                    ui.formatMessageWithTopDivider(
                            String.format(MESSAGE_ACTIVITY_FACTOR,
                                    activityFactor.getUserInput()));
                } else {
                    ui.formatMessageFramedWithDivider(ProfileUtils.ERROR_ACTIVITY_FACTOR);
                }
                ;
            } catch (NumberFormatException e) {
                ui.formatMessageWithTopDivider(MESSAGE_INVALID_POSITIVE_INT_INPUT);
            }
        }
        return activityFactor;
    }
}
