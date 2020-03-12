package com.devahmed.demo.onlinepharmacy.Utils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

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
        Dialog.findViewById(R.id.container).setAnimation(AnimationUtils.loadAnimation(activity , R.anim.products_image_fade_scale_animation));
        Dialog.setCancelable(true);
        ImageView fullViewImage = Dialog.findViewById(R.id.fullViewImage);

        Glide.with(activity)
                .load(imageUrl)
                .placeholder(R.drawable.images_placeholder)
                .into(fullViewImage);
        Dialog.show();
    }

    public void showFullImageDialog(CanvasView canvasView){
        //Dialog Init
        Dialog = new Dialog(activity);
        Dialog.setContentView(R.layout.bill_image_dialog);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Dialog.findViewById(R.id.container).setAnimation(AnimationUtils.loadAnimation(activity , R.anim.products_image_fade_scale_animation));
        Dialog.setCancelable(true);
        final LinearLayout linearLayout = Dialog.findViewById(R.id.container);
        linearLayout.addView(canvasView);
        Dialog.show();
        Dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                linearLayout.removeAllViews();
            }
        });
    }


}
