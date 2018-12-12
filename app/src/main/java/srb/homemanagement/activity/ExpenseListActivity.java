package srb.homemanagement.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import srb.homemanagement.R;
import srb.homemanagement.adapter.ExpenseAdapter;
import srb.homemanagement.database.DatabaseHandler;
import srb.homemanagement.model.Expense;
import srb.homemanagement.utils.EmptyRecyclerView;

import static srb.homemanagement.utils.Utils.backButtonOnToolbar;
import static srb.homemanagement.utils.Utils.showFancyDate;

public class ExpenseListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.selected_date)
    TextView dateTextView;
    ExpenseAdapter expenseAdapter;
    String selectdDate = "";
    private ArrayList<Expense> expenseArrayList = new ArrayList<Expense>();
    private ArrayList<Expense> dummyArrayList = new ArrayList<Expense>();
    DatabaseHandler db;
    MenuItem totalAmountMenu;
    String menuItemAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        db = new DatabaseHandler(this);
        getIntentValues(getIntent());
        setupRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);

        getExpenseList();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void getIntentValues(Intent intent) {

        String date = intent.getStringExtra("date");
        if (date != null) {
            selectdDate = date;
        }
    }

    @OnClick(R.id.add_expense)
    public void addExpense() {
        Intent intent = new Intent(ExpenseListActivity.this, AddExpenseActivity.class);
        intent.putExtra("date", selectdDate);
        startActivity(intent);
    }

    private void getExpenseList() {
        expenseArrayList.clear();
        expenseArrayList.addAll(db.getParticularExpense(selectdDate));
        expenseAdapter.notifyDataSetChanged();
        //    totalAmountMenu.setTitle("Rs. "+db.getDailyExpense(selectdDate));
        menuItemAmount = db.getDailyExpense(selectdDate);
        dateTextView.setText(showFancyDate(selectdDate));

    }

    private void setupRecyclerView() {
        expenseAdapter = new ExpenseAdapter(expenseArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);
        emptyRecyclerView.setEmptyView(emptyView);
        emptyRecyclerView.setAdapter(expenseAdapter);
    }

    @Override
    public void onRefresh() {
        getExpenseList();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_value, menu);//Menu Resource, Menu
        totalAmountMenu = menu.findItem(R.id.amount);
        totalAmountMenu.setTitle("Rs. " + menuItemAmount);

        return true;
    }

}
