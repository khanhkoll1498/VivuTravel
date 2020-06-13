package com.neos.touristbook.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neos.touristbook.event.AccountCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.model.User;
import com.neos.touristbook.utils.CommonUtils;

public class AccoutPresenter extends BasePresenter<AccountCallback> {
    public AccoutPresenter(AccountCallback mCallback) {
        super(mCallback);
    }

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public void updateProfile(User user) {
        myRef.child("user").child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mCallback.onResultUser(null);
                }
            }
        });
    }

    public void loadProfile() {
        myRef.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mCallback.onResultUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
