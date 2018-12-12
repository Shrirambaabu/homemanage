package srb.homemanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import srb.homemanagement.R;
import srb.homemanagement.database.DatabaseHandler;
import srb.homemanagement.model.Expense;

import static srb.homemanagement.utils.Utils.backButtonOnToolbar;
import static srb.homemanagement.utils.Utils.getMonthYear;

public class AddExpenseActivity extends AppCompatActivity {

    String selectedDate = "";

    @BindView(R.id.textInputLayoutName)
    TextInputLayout textInputLayoutName;
    @BindView(R.id.textInputLayoutAmount)
    TextInputLayout textInputLayoutAmount;

    @BindView(R.id.textInputEditTextName)
    TextInputEditText textInputEditTextName;
    @BindView(R.id.textInputEditTextAmount)
    TextInputEditText textInputEditTextAmount;
    @BindView(R.id.add_expense)
    Button addButton;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        db = new DatabaseHandler(this);
        getIntentValues(getIntent());

    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    private void getIntentValues(Intent intent) {
        String date = intent.getStringExtra("date");

        if (date != null) {
            selectedDate = date;
        }
    }

    @OnClick(R.id.add_expense)
    public void addExpenses() {
        if (textInputEditTextName.getText().toString().equals("") || textInputEditTextName.getText().toString().length() < 3) {
            textInputLayoutName.setError("Enter Expense Name");
            textInputEditTextName.requestFocus();
            return;
        }
        textInputLayoutName.setErrorEnabled(false);
        if (textInputEditTextAmount.getText().toString().equals("")) {
            textInputLayoutAmount.setError("Enter Expense Amount");
            textInputEditTextAmount.requestFocus();
            return;
        }
        textInputLayoutAmount.setErrorEnabled(false);
        addExpenseToDb();
    }

    private void addExpenseToDb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(addButton.getWindowToken(), 0);

        Log.e("ss", "expense");
        Log.e("textInputEditTextName", "" + textInputEditTextName.getText().toString());
        Log.e("textInputEditTextAmount", "" + textInputEditTextAmount.getText().toString());
        Log.e("selectedDate", selectedDate);
        Log.e("getMonthYear", getMonthYear(selectedDate));

        Expense expenseModel = new Expense();
        expenseModel.setExpenseName(textInputEditTextName.getText().toString());
        expenseModel.setExpenseAmount(textInputEditTextAmount.getText().toString());
        expenseModel.setExpenseMonth(getMonthYear(selectedDate));
        expenseModel.setExpenseDate(selectedDate);


        long rowInserted = db.addExpense(expenseModel);

        if (rowInserted != -1) {
            Toast.makeText(getApplicationContext(), "Expense Added", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(AddExpenseActivity.this,ExpenseListActivity.class);
            intent.putExtra("date",selectedDate);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

}
