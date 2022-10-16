package soconnect.commons.util;

import static java.util.Objects.requireNonNull;
import static soconnect.commons.util.AppUtil.checkArgument;
import static soconnect.commons.util.CollectionUtil.requireAllNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence Cannot be null.
     * @param word Cannot be null, cannot be empty, must be a single word.
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireAllNonNull(sentence, word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code keywords}.
     *   Ignores case, but full keywords match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "abc DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence Cannot be null.
     * @param keywords Cannot be null, cannot be empty, can be multiple words.
     */
    public static boolean containsKeywordsIgnoreCase(String sentence, String keywords) {
        requireAllNonNull(sentence, keywords);

        List<String> preppedKeywords = Arrays.asList(keywords.toUpperCase().trim().split("\\s+"));
        checkArgument(preppedKeywords.size() > 0, "Word parameter cannot be empty");

        List<String> wordsInPreppedSentence = Arrays.asList(sentence.toUpperCase().split("\\s+"));

        return wordsInPreppedSentence.containsAll(preppedKeywords);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer,
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE}. <br>
     * Will return false for any other non-null string input,
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters).
     *
     * @throws NullPointerException If {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}