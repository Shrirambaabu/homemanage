package srb.homemanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import srb.homemanagement.R;
import srb.homemanagement.database.DatabaseHandler;

import static srb.homemanagement.utils.Utils.showMonthName;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.material_calendar)
    MaterialCalendarView materialCalendarView;

    String selectedDate = "";
    String selectedMonth = "";
    String displayMonth = "";
    DatabaseHandler db;
    String monthRate = "", dbMonthValue = "";
    MenuItem totalAmountMenu;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = new DatabaseHandler(this);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        String monthF = "";
        if (month < 10) {
            monthF = "0" + month;
        } else {
            monthF = "" + month;
        }
        dbMonthValue = monthF + "," + year;
        Log.e("dbMonth", "" + dbMonthValue);
        monthRate = db.getMonthlyExpense(dbMonthValue);
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                int month = widget.getSelectedDate().getMonth() + 1;
                int day = widget.getSelectedDate().getDay();
                String monthFinal, dateFinal;
                if (day < 10) {
                    dateFinal = "0" + day;
                } else {
                    dateFinal = "" + day;
                }
                if (month < 10) {
                    monthFinal = "0" + month;
                } else {
                    monthFinal = "" + month;
                }
                displayMonth = monthFinal;
                selectedDate = dateFinal + "-" + monthFinal + "-" + widget.getSelectedDate().getYear();
                selectedMonth = monthFinal + "," + widget.getSelectedDate().getYear();
                Log.e("SelectedDate:", "" + selectedDate);
                Log.e("selectedMonth:", "" + selectedMonth);

            }
        });
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                int month = date.getMonth() + 1;
                String monthF = "";

                if (month < 10) {
                    monthF = "0" + month;
                } else {
                    monthF = "" + month;
                }

                Log.e("Month", "" + monthF + "," + date.getYear());
                dbMonthValue = monthF + "," + date.getYear();
                monthRate = db.getMonthlyExpense(dbMonthValue);
                totalAmountMenu.setTitle("Rs. " + monthRate);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_value, menu);//Menu Resource, Menu
        totalAmountMenu = menu.findItem(R.id.amount);
        totalAmountMenu.setTitle("Rs. " + monthRate);

        return true;
    }

    @OnClick(R.id.next_button)
    public void nextButton() {
        if (selectedDate.equals("")) {
            Toast.makeText(getApplicationContext(), "Select the date", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
            intent.putExtra("date", "" + selectedDate);
            startActivity(intent);
        }
    }

    @OnClick(R.id.month_cal)
    public void monthCalculate() {
        if (selectedDate.equals("")) {
            Toast.makeText(getApplicationContext(), "Select the Date/Month", Toast.LENGTH_LONG).show();

        } else {
            Log.e("DB", "" + db.getMonthlyExpense(selectedMonth));
            monthRate = db.getMonthlyExpense(selectedMonth);
            openAlertBox(monthRate);
        }
    }

    private void openAlertBox(String monthRate) {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Monthly Status")
                .setMessage("Your " + showMonthName(displayMonth) + " month expense is Rs." + monthRate)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.expense_logo)
                .show();
    }

}
