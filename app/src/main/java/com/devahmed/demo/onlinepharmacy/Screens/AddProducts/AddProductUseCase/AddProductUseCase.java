package com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProductUseCase extends BaseObservableMvcView<AddProductUseCase.Listener> {
    private final Activity context;

    private final String FIREBASE_PATH = "Products";
    private final String FIRESTORAGE_PATH = "IMAGES";
    public interface Listener {
        void onProductAddedSuccessfully();
        void onProductFailedToAdd();
        void onInputError(String message);
    }

    public AddProductUseCase(Activity context) {
        this.context = context;
    }

    public void addNewProduct(final String name  , final String price , Uri pickedImage , final String subCategory
            ,final boolean isOffer){

            if(!isValid(name)){
                notifyInputError("Product name is not valid");
                return;
            }
            if(pickedImage == null){
                notifyInputError("You have to pick image first");
                return;
            }
            if(price.isEmpty()){
                notifyInputError("Enter the price");
                return;
            }
            try{
                if(!(((Integer)Integer.parseInt(price)) instanceof Integer)){
                    notifyInputError("Price is not valid");
                    return;
                }
            }catch (Exception e){
                notifyInputError("Enter valid price");
                return;
            }

        System.out.println("Hello");
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(FIRESTORAGE_PATH);
            final StorageReference imageFilePath = storageReference.child(pickedImage.getLastPathSegment());
            imageFilePath.putFile(pickedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageDownloadLink = uri.toString();
                            Product product = new Product(name , Integer.parseInt(price) , imageDownloadLink , subCategory);
                            //add post to firebase database
                            addPostToFirebase(product);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //some thing goes wrong while uploading post
                            notifyFailure();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    notifyFailure();
                }
            });
        }


    private void addPostToFirebase(Product product) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FIREBASE_PATH).push();
        //get post unique ID & update post key
        String key = myRef.getKey();
        product.setId(key);
        //add post data to firebase database
        myRef.setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                notifySuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyFailure();
            }
        });
    }

    boolean isValid(String text){
        if(!text.isEmpty() || !text.trim().equals("")){
            if( (text.length() >= 4) ){
                return true;
            }
        }
        return false;
    }

    private void notifyFailure() {
        for(Listener listener : getmListeners()){
            listener.onProductFailedToAdd();
        }
    }

    private void notifySuccess(){
        for(Listener listener : getmListeners()){
            listener.onProductAddedSuccessfully();
        }
    }

    private void notifyInputError(String message){
        for(Listener listener : getmListeners()){
            listener.onInputError(message);
        }
    }
}
