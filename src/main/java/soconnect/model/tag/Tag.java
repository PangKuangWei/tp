package soconnect.model.tag;

import static java.util.Objects.requireNonNull;
import static soconnect.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the SoConnect.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}.
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private static final String MESSAGE_TAG_TOO_LONG =
            "The Tag is too long. Keep it within %1$s characters, including whitespaces.";
    private static final int CHARACTER_LIMIT = 10;
    public static final String MESSAGE_TOO_LONG = String.format(MESSAGE_TAG_TOO_LONG, CHARACTER_LIMIT);

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidLength(tagName), MESSAGE_TOO_LONG);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string does not exceed the character limit.
     *
     * @param text The given string.
     * @return True if it does not exceed the character limit. False if otherwise.
     */
    public static boolean isValidLength(String text) {
        return !(text.length() > CHARACTER_LIMIT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
