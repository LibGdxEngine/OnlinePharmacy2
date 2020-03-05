package com.devahmed.demo.onlinepharmacy.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devahmed.demo.onlinepharmacy.R;

public class UtilsDialog {

    private static Dialog Dialog;
    private Activity activity;

    public UtilsDialog(Activity activity) {
        this.activity = activity;
    }

    public void showFullImageDialog(String imageUrl){
        //Dialog Init
        Dialog = new Dialog(activity);
        Dialog.setContentView(R.layout.full_image_dialog);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog.findViewById(R.id.container).setAnimation(AnimationUtils.loadAnimation(activity , R.anim.fade_scale_animation));
        Dialog.setCancelable(true);
        ImageView fullViewImage = Dialog.findViewById(R.id.fullViewImage);
        Glide.with(activity)
                .load(imageUrl)
                .placeholder(R.drawable.images_placeholder)
                .into(fullViewImage);
        Dialog.show();
    }
}
