package seedu.budgetbaby.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.budgetbaby.model.budget.Budget;
import seedu.budgetbaby.model.month.UniqueMonthList;
import seedu.budgetbaby.model.month.Month;
import seedu.budgetbaby.model.record.FinancialRecord;

/**
 * Wraps all data at the budget-tracker level
 */
public class BudgetTracker implements ReadOnlyBudgetTracker {

    private final UniqueMonthList monthList;

    {
        monthList = new UniqueMonthList();
    }

    public BudgetTracker() {
    }

    /**
     * Creates an BudgetTracker using the Months in the {@code toBeCopied}
     */
    public BudgetTracker(ReadOnlyBudgetTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the month list with {@code months}.
     * {@code months} must not contain duplicate months.
     */
    public void setMonthList(List<Month> months) {
        this.monthList.setMonths(months);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBudgetTracker newData) {
        requireNonNull(newData);

        setMonthList(newData.getMonthList());
    }

    //// month-level operations

    /**
     * Adds a month to the budget tracker.
     */
    public void addMonth(Month r) {
        monthList.add(r);
    }

    /**
     * Replaces the given month {@code target} in the list with {@code editedMonth}.
     * {@code target} must exist in the budegt tracker.
     * The month identity of {@code editedRecord} must not be the same as another existing month in the budget tracker.
     */
    public void setMonth(Month target, Month editedMonth) {
        requireNonNull(editedMonth);

        monthList.setMonth(target, editedMonth);
    }

    /**
     * Removes {@code key} from this {@code BudgetTracker}.
     * {@code key} must exist in the budget tracker.
     */
    public void removeMonth(Month key) {
        monthList.remove(key);
    }

    //// financial-record-level operations

    /**
     * Adds a financial record to the budget tracker.
     * Adds a month to the budgt tracker.
     */
    public void addFinancialRecord(FinancialRecord r) {
        monthList.addFinancialRecord(r);
    }

    /**
     * Replaces the given financial record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the budegt tracker.
     */
    public void setFinancialRecord(FinancialRecord target, FinancialRecord editedRecord) {
        requireNonNull(editedRecord);
        monthList.setFinancialRecord(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code BudgetTracker}.
     * {@code key} must exist in the budget tracker.
     */
    public void removeFinancialRecord(FinancialRecord key) {
        monthList.removeFinancialRecord(key);
    }

    /**
     * Sets the budget for the following months.
     *
     * @param budget The specified budget to be set.
     */
    public void setBudget(Budget budget) {
        monthList.setBudget(budget);
    }


    //// util methods

    @Override
    public String toString() {
        return monthList.asUnmodifiableObservableList().size() + " months";
        // TODO: refine later
    }

    @Override
    public ObservableList<Month> getMonthList() {
        return monthList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetTracker // instanceof handles nulls
            && monthList.equals(((BudgetTracker) other).monthList));
    }

    @Override
    public int hashCode() {
        return monthList.hashCode();
    }
}

