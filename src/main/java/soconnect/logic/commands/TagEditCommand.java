package soconnect.logic.commands;

import static java.util.Objects.requireNonNull;
import static soconnect.logic.parser.CliSyntax.PREFIX_TAG;

import soconnect.logic.commands.exceptions.CommandException;
import soconnect.logic.parser.exceptions.ParseException;
import soconnect.model.Model;
import soconnect.model.tag.Tag;

/**
 * Edits the details of an existing tag in the SoConnect.
 */
public class TagEditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " "
            + COMMAND_WORD + ": Edits a tag to the taglist in the SoConnect. "
            + "Parameters: "
            + PREFIX_TAG + "TAG"
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + TagCommand.COMMAND_WORD + " "
            + COMMAND_WORD + " "
            + PREFIX_TAG + "friend"
            + PREFIX_TAG + "bestFriend";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Tag has changed from %1$s to %2$s";
    public static final String MESSAGE_NOT_EDITED = "Both tags need to be provided";
    public static final String MESSAGE_DUPLICATE_TAG = "This new tag already exists";
    public static final String MESSAGE_TAG_NOT_FOUND = "This old tag does not exist";

    private final Tag oldTag;
    private final Tag newTag;

    /**
     * Constructs a {@code TagEditCommand} to edit the details of an existing {@code Tag}.
     *
     * @param oldTag The tag to be changed.
     * @param newTag The tag that is changed to.
     */
    public TagEditCommand(Tag oldTag, Tag newTag) {
        this.newTag = newTag;
        this.oldTag = oldTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);

        if (!model.hasTag(oldTag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        } else if (model.hasTag(newTag)) {
            throw new CommandException((MESSAGE_DUPLICATE_TAG));
        } else {
            model.editTag(oldTag, newTag);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, oldTag, newTag));
        }
    }

}