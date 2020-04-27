package com.neos.touristbook.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neos.touristbook.event.RegisterCallBack;
import com.neos.touristbook.model.User;
import com.neos.touristbook.utils.CommonUtils;

public class RegisterPresenter extends BasePresenter<RegisterCallBack> {
    private FirebaseAuth mAuth;

    public RegisterPresenter(RegisterCallBack mCallback) {
        super(mCallback);
        mAuth = FirebaseAuth.getInstance();
    }

    public void checkValidation(String email, String pass, String confirmPass) {
        if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            mCallback.error("Mời nhập đầy đủ thông tin tài khoản");
            return;
        }
        if (!email.contains("@")) {
            mCallback.error("Tài khoản không hợp lệ");
            return;
        }

        if (pass.length() < 6) {
            mCallback.error("Mật khẩu phải có lớn hơn 6 kí tự");
            return;
        }

        if (!pass.equals(confirmPass)) {
            mCallback.error("Mật khâủ không trùng nhau");
            return;
        }

        register(email, pass);
    }

    private void register(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUser(user);
                        } else {
                            mCallback.registerFail();
                        }
                    }
                });
    }

    protected void saveUser(FirebaseUser data) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        User user = new User(data.getUid(), data.getEmail(), "");
        myRef.child("user").child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mCallback.registerSuccess();
                    return;
                }
            }
        });
    }
}
