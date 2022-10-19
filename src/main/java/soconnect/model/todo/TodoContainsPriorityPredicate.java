package soconnect.model.todo;

import java.util.function.Predicate;

/**
 * Tests that a {@code Todo} is of a certain {@code Priority}.
 */
public class TodoContainsPriorityPredicate implements Predicate<Todo> {

    private final Priority priority;

    public TodoContainsPriorityPredicate(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean test(Todo todo) {
        return todo.getPriority().equals(priority);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TodoContainsPriorityPredicate // instanceof handles nulls
            && priority.equals(((TodoContainsPriorityPredicate) other).priority)); // state check
    }
}
