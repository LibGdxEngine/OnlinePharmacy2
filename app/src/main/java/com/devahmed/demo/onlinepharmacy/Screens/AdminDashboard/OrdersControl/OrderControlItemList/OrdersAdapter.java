package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.OrdersControl.OrderControlItemList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Models.Order;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> implements OrderListItemViewMvc.Listener {

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void OnItemClicked(Order Order);
        void onShowBtnClicked(Order order);
        void onShareBtnClicked(Order order);
        void onStatusBtnClicked(Order order);
    }

    private List<Order> OrdersList;

    public OrdersAdapter(List<Order> OrdersList, OnItemClickListener listener) {
        this.OrdersList = OrdersList;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderListItemViewMvcImp mvcImp = new OrderListItemViewMvcImp(LayoutInflater.from(parent.getContext()), parent);
        mvcImp.registerListener(this);
        return new ViewHolder(mvcImp);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order model = OrdersList.get(position);
        holder.recyclerViewListItemMvcImp.bindData(model);
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public void setList(List<Order> newList) {
        OrdersList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final OrderListItemViewMvcImp recyclerViewListItemMvcImp;

        public ViewHolder(OrderListItemViewMvcImp viewMvcImp) {
            super(viewMvcImp.getRootView());
            recyclerViewListItemMvcImp = viewMvcImp;
        }
    }


    @Override
    public void onItemClicked(Order model) {
        mListener.OnItemClicked(model);
    }

    @Override
    public void onShowBtnClicked(Order order) {
        mListener.onShowBtnClicked(order);
    }

    @Override
    public void onShareBtnClicked(Order order) {
        mListener.onShareBtnClicked(order);
    }

    @Override
    public void onStatusBtnClicked(Order order) {
        mListener.onStatusBtnClicked(order);
    }

}