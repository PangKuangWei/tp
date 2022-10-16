package soconnect.logic.commands;

import static java.util.Objects.requireNonNull;
import static soconnect.logic.parser.CliSyntax.PREFIX_TAG;

import soconnect.logic.commands.exceptions.CommandException;
import soconnect.model.Model;
import soconnect.model.tag.Tag;

/**
 * Creates a tag in the tagList.
 */
public class TagCreateCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " "
            + COMMAND_WORD + ": Creates a tag to the SoConnect. "
            + "Parameters: "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + TagCommand.COMMAND_WORD + " "
            + COMMAND_WORD + " "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New tag added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the SoConnect";

    private final Tag toCreate;

    /**
     * Constructs an {@code TagCreateCommand} to create the specified {@code Tag} in SoConnect.
     */
    public TagCreateCommand(Tag tag) {
        requireNonNull(tag);
        this.toCreate = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTag(toCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        model.addTag(toCreate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCreate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCreateCommand // instanceof handles nulls
                && toCreate.equals(((TagCreateCommand) other).toCreate));
    }
}