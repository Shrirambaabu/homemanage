package srb.homemanagement.model;

public class Expense {

    private int expenseId;
    private String expenseName;
    private String expenseAmount;
    private String expenseDate;
    private String expenseMonth;

    public Expense() {
    }

    public Expense(int expenseId, String expenseName, String expenseAmount, String expenseDate, String expenseMonth) {
        this.expenseId = expenseId;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.expenseMonth = expenseMonth;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseMonth() {
        return expenseMonth;
    }

    public void setExpenseMonth(String expenseMonth) {
        this.expenseMonth = expenseMonth;
    }
}
