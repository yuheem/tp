package seedu.budgetbaby.logic.statistics;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.budgetbaby.model.BudgetBabyModel;
import seedu.budgetbaby.model.month.Month;
import seedu.budgetbaby.model.record.Category;
import seedu.budgetbaby.model.record.FinancialRecord;

public class Statistics {
    private final ObservableList<Month> monthList;
    private BudgetBabyModel model;

    /**
     * Instantiates the Statistics object by taking in a BudgetBabyModel during initialisation.
     * @param model BudgetBabyModel object.
     */
    public Statistics(BudgetBabyModel model) {
        this.model = model;
        monthList = model.getFilteredMonthList();
        assert monthList.size() == 1;
    }


    protected boolean fillPastMonths() {
        for (int i = 1; i < 6; i++) {
            YearMonth curr = this.monthList.get(0).getMonth().minusMonths(i);
            model.findMonth(curr);
        }
        return true;
    }

    protected List<Month> getPastMonths() {
        fillPastMonths();
        List<Month> monthList = new ArrayList<Month>(model.getFullMonthList());
        monthList = monthList.stream()
                .filter(month -> month.getMonth().isBefore(this.monthList.get(0).getMonth().plusMonths(1))
                        && month.getMonth().isAfter(this.monthList.get(0).getMonth().minusMonths(6)))
                .collect(Collectors.toList());
        Collections.sort(monthList);
        return monthList.stream().limit(6).collect(Collectors.toList());
    }

    /**
     * Obtains statistics of past 6 {@code Month}s as a list and returns it.
     * @return List of MonthStatistics of past 6 months.
     */
    public List<MonthStatistics> getPastMonthStatistics() {
        List<MonthStatistics> list = new ArrayList<>();
        for (Month m : getPastMonths()) {
            list.add(new MonthStatistics(m));
        }
        return list;
    }

    protected List<CategoryStatistics> allCategories() {
        assert monthList.size() == 1;
        Month currMonth = monthList.get(0);

        ObservableList<FinancialRecord> list = currMonth.getFinancialRecordList();
        HashMap<Category, CategoryStatistics> map = new HashMap<>();
        for (FinancialRecord fr : list) {
            for (Category c : fr.getCategories()) {
                if (map.containsKey(c)) {
                    map.put(c, new CategoryStatistics(c, map.get(c).getAmount() + fr.getAmount().getValue()));
                } else {
                    map.put(c, new CategoryStatistics(c, fr.getAmount().getValue()));
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<CategoryStatistics> getAllUnsortedCategories() {
        List<CategoryStatistics> list = allCategories();
        Collections.sort(list, new Comparator<CategoryStatistics>() {
            @Override
            public int compare(CategoryStatistics cs1, CategoryStatistics cs2) {
                return cs1.getCategory().getCategory().toLowerCase()
                        .compareTo(cs2.getCategory().getCategory().toLowerCase());
            }
        });
        return list;
    }

    /**
     * Obtains top 5 categories which the user spends on
     */
    public List<CategoryStatistics> getTopCategories() {
        List<CategoryStatistics> list = allCategories();
        Collections.sort(list);
        return list.stream().limit(5).collect(Collectors.toList());
    }
}
