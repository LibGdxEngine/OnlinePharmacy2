package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.UsersControl;

import com.devahmed.tech4fun.ecommerce.Models.User;

import java.util.List;

public interface UserControlsMvc {

    interface Listener{
        void onUserItemClicked(User user);
        void onBlockBtnClicked(User user);
        void onUserInfoBtnClicked(User user);
        void onUserOrdersBtnClicked(User user);
        void onCallBtnClicked(User user);
    }

    void bindData(List<User> userList);
    void showProgressbar();
    void hideProgressbar();
    void showUserDialog(User user);
}
