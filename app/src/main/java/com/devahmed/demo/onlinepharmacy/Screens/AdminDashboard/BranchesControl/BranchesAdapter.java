package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BranchesControl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Models.Branch;
import com.devahmed.demo.onlinepharmacy.R;

import java.util.List;

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.ViewHolder> {

    private List<Branch> BranchsList;

    public BranchesAdapter(List<Branch> BranchsList) {
        this.BranchsList = BranchsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_branch_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Branch model = BranchsList.get(position);
        holder.branchLocationTextView.setText(model.getGpsLocation());
        holder.branchNameTextView.setText(model.getName() + "\t\t " + model.getAcceptedOrdersRange() + "-Km");
    }

    @Override
    public int getItemCount() {
        return BranchsList.size();
    }

    public void setList(List<Branch> newList) {
        BranchsList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView branchNameTextView , branchLocationTextView;
        public ViewHolder(View view) {
            super(view);
            branchLocationTextView = view.findViewById(R.id.branchLocationTextView);
            branchNameTextView = view.findViewById(R.id.branchNameTextView);
        }
    }
}