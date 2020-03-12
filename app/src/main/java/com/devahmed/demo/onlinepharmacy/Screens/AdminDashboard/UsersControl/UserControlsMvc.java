package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.UsersControl;

import com.devahmed.demo.onlinepharmacy.Models.User;

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
