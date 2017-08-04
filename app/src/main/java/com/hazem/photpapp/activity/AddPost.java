package com.hazem.photpapp.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hazem.photpapp.R;
import com.hazem.photpapp.model.Post;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

public class AddPost extends AppCompatActivity {

    //init the views
    private LinearLayout selectPhotoParent;
    ImageView addedImage;
    TextView postTV;
    EditText postDiscET;


    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private StorageReference mStorageRef;

    //for upload the image
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        initViews();
        sendData();
    }

    /**
     * here to init the views from the xml and the firebase variable
     */
    private void initViews() {
        postTV = (TextView) findViewById(R.id.postTV);
        postDiscET = (EditText) findViewById(R.id.postDiscET);

        selectPhotoParent = (LinearLayout) findViewById(R.id.selectPhotoParent);
        addedImage = (ImageView) findViewById(R.id.addedImage);

        selectPhotoParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.on(getSupportFragmentManager(), new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                Log.d("google", "here in the image ");
                                if (r.getError() == null) {
                                    addedImage.setImageBitmap(r.getBitmap());
                                    image = r.getBitmap();
                                } else {
                                    Toast.makeText(AddPost.this, "error occur try again", Toast.LENGTH_LONG).show();
                                    //Handle possible errors
                                    //TODO: do what you have to do with r.getError();
                                }
                            }
                        });
            }
        });

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //init the storage
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }


    /**
     * here we are going to add new post
     */
    private void sendData() {

        postTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //make a progress dialoge
                //todo clear this and make a simple loading view
                final ProgressDialog progressDialog = new ProgressDialog(AddPost.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Connect to all world plz wait !");
                progressDialog.show();

                //create new model and add it to the forebase real time database
                final Post post = new Post();

                //first upload the image

                StorageReference tripsRef = mStorageRef.child("images/" + random() + ".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = tripsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        String imageUrl = taskSnapshot.getDownloadUrl().toString();
                        post.setImage(imageUrl);
                        //ad the rest of the post attrubite
                        post.setId(mFirebaseUser.getUid() + random());
                        post.setUserId(mFirebaseUser.getUid());
                        post.setUserName(mFirebaseUser.getDisplayName());
                        post.setUserImage(mFirebaseUser.getPhotoUrl().toString());
                        post.setDisc(postDiscET.getText().toString() + " ");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String currentDateandTime = sdf.format(new Date());
                        post.setTime(currentDateandTime);

                        mFirebaseDatabaseReference.child("posts")
                                .child(post.getId()).setValue(post);
                        finish();
                        Toast.makeText(AddPost.this, "posted to the world ", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }


    /**
     * to get ids for the firebase
     *
     * @return random string
     */
    protected String random() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
