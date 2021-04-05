package seedu.budgetbaby.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.budgetbaby.testutil.TypicalBudgetTracker.getTypicalBudgetTracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.budgetbaby.logic.commands.exceptions.CommandException;
import seedu.budgetbaby.logic.parser.FindFrCommandParser;
import seedu.budgetbaby.logic.parser.exceptions.ParseException;
import seedu.budgetbaby.model.BudgetBabyModel;
import seedu.budgetbaby.model.BudgetBabyModelManager;
import seedu.budgetbaby.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindFrCommand}.
 */
public class FindFrCommandTest {
    private BudgetBabyModel model = new BudgetBabyModelManager(getTypicalBudgetTracker(), new UserPrefs());
    private FindFrCommandParser find = new FindFrCommandParser();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate currDate = LocalDate.now();

    //    @Test
    //    public void execute_withoutParams() throws ParseException, CommandException {
    //        model.addFinancialRecord(new FinancialRecord(description, amount, categories));
    //        model.addFinancialRecord(new FinancialRecord(description2, amount2, categories2));
    //        String expectedOutput = "[01-04-2021 | Breakfast | 5.0; Categories: "
    //                + "[Food], 01-04-2021 | Lunch | 6.0; Categories: [Food]]";
    //        find.parse("").execute(model);
    //        assertEquals(expectedOutput, model.getFilteredFinancialRecordList().toString());
    //    }

    @Test
    public void result_none() throws ParseException, CommandException {
        String expectedOutput = "[]";
        find.parse(" d/Dinner").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findD() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" d/Lunch").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findA() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" a/6").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findC() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Breakfast | 5.00; Categories: "
                + "[Food], " + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" c/Food").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findDA() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" d/Lunch a/6").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findDC() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" d/Lunch c/Food").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findAC() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" a/6 c/Food").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_findAll() throws ParseException, CommandException {
        String expectedOutput = "[" + dtf.format(currDate) + " | Lunch | 6.00; Categories: [Food]]";
        find.parse(" d/Lunch a/6 c/Food").execute(model);
        String actualOutput = model.getFilteredFinancialRecordList().toString();
        assertEquals(expectedOutput, actualOutput);
    }

}
