package srb.homemanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import srb.homemanagement.R;
import srb.homemanagement.activity.EditExpenseActivity;
import srb.homemanagement.model.Expense;

import static srb.homemanagement.utils.Utils.showFancyDate;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private ArrayList<Expense> expenseArrayList = new ArrayList<Expense>();
    private Context context;

    public ExpenseAdapter(ArrayList<Expense> expenseArrayList,Context context) {
        this.expenseArrayList = expenseArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {

        Expense expenseModel = expenseArrayList.get(position);

        holder.nameText.setText(expenseModel.getExpenseName());
        holder.dateText.setText(showFancyDate(expenseModel.getExpenseDate()));
        holder.amountText.setText("Rs. " + expenseModel.getExpenseAmount());

    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameText, dateText, amountText;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name);
            dateText = itemView.findViewById(R.id.date);
            amountText = itemView.findViewById(R.id.amount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e("Clicked", "" + expenseArrayList.get(getAdapterPosition()).getExpenseId());

            Intent intent=new Intent(context,EditExpenseActivity.class);
            intent.putExtra("id",""+expenseArrayList.get(getAdapterPosition()).getExpenseId());
            context.startActivity(intent);

        }
    }
}
