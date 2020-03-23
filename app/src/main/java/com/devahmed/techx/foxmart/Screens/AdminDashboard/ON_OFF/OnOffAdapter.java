package com.devahmed.techx.foxmart.Screens.AdminDashboard.ON_OFF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnOffAdapter extends RecyclerView.Adapter<OnOffAdapter.ViewHolder> {

    private List<Branch> BranchsList;

    public OnOffAdapter(List<Branch> BranchsList) {
        this.BranchsList = BranchsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_on_off, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Branch model = BranchsList.get(position);

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

        public ViewHolder(View view) {
            super(view);

        }
    }
}