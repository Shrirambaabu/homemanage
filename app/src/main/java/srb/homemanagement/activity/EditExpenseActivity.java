package srb.homemanagement.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import srb.homemanagement.R;
import srb.homemanagement.database.DatabaseHandler;
import srb.homemanagement.model.Expense;

import static srb.homemanagement.utils.Utils.backButtonOnToolbar;

public class EditExpenseActivity extends AppCompatActivity {
    DatabaseHandler db;
    private ArrayList<Expense> expenseArrayList = new ArrayList<Expense>();
    String id, expenseDate, expenseMonth;

    @BindView(R.id.textInputLayoutName)
    TextInputLayout textInputLayoutName;
    @BindView(R.id.textInputLayoutAmount)
    TextInputLayout textInputLayoutAmount;

    @BindView(R.id.textInputEditTextName)
    TextInputEditText textInputEditTextName;
    @BindView(R.id.textInputEditTextAmount)
    TextInputEditText textInputEditTextAmount;
    @BindView(R.id.update_expense)
    Button updateButton;
    @BindView(R.id.del_expense)
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        db = new DatabaseHandler(this);
        getIntentValues(getIntent());
    }

    private void getIntentValues(Intent intent) {
        String id = intent.getStringExtra("id");
        if (id != null) {
            this.id = id;
            getExpenseDetails(id);
        }
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

    private void getExpenseDetails(String id) {
        expenseArrayList = db.getExpenseEdit(id);
        textInputEditTextName.setText(expenseArrayList.get(0).getExpenseName());
        textInputEditTextAmount.setText(expenseArrayList.get(0).getExpenseAmount());
        expenseDate = expenseArrayList.get(0).getExpenseDate();
        expenseMonth = expenseArrayList.get(0).getExpenseMonth();

    }

    @OnClick(R.id.update_expense)
    public void updateExpenseButton() {
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
        updateExpenseToDb();
    }

    private void updateExpenseToDb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(updateButton.getWindowToken(), 0);

        Expense expenseModel = new Expense();
        expenseModel.setExpenseId(Integer.parseInt(id));
        expenseModel.setExpenseName(textInputEditTextName.getText().toString());
        expenseModel.setExpenseAmount(textInputEditTextAmount.getText().toString());
        expenseModel.setExpenseDate(expenseDate);
        expenseModel.setExpenseMonth(expenseMonth);

        int var = db.updateExpense(expenseModel);

        Log.e("Var", "" + var);
        if (var == 1) {
            Toast.makeText(getApplicationContext(), "Expense Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditExpenseActivity.this, ExpenseListActivity.class);
            intent.putExtra("date", expenseDate);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

    @OnClick(R.id.del_expense)
    public void deleteExpense() {


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(delButton.getWindowToken(), 0);

        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert")
                .setMessage("Do you want to delete this record?")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteRecord(id);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.expense_logo)
                .show();
    }

    private void deleteRecord(String id) {
        db.deleteExpense(id);
        Toast.makeText(getApplicationContext(), "Record Deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EditExpenseActivity.this, ExpenseListActivity.class);
        intent.putExtra("date", expenseDate);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
