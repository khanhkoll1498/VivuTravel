package com.neos.touristbook.presenter;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neos.touristbook.App;
import com.neos.touristbook.R;
import com.neos.touristbook.event.LoginCallBack;
import com.neos.touristbook.model.Account;
import com.neos.touristbook.model.User;
import com.neos.touristbook.utils.CommonUtils;

public class LoginPresenter extends BasePresenter<LoginCallBack> {
    private static final int GOOGLE_SIGN = 1;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    public LoginPresenter(LoginCallBack mCallback) {
        super(mCallback);
    }


    public void loginGoogle() {
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(App.getInstance().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(App.getInstance(), googleSignInOptions);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mCallback.onStartLoginGG(signInIntent);
    }

    public void loginFaceBook() {

    }


    public void excuteLoginGG(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (Exception e) {
            CommonUtils.getInstance().log(e.toString());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUser(user);
                        } else {
                            mCallback.loginFailed();
                        }
                    }
                });
    }

    protected void saveUser(FirebaseUser data) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        User user = new User(data.getUid(), "", data.getPhotoUrl().toString(), data.getDisplayName(), "");
        myRef.child("user").child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mCallback.loginSuccess();
                }
            }
        });
    }

    public void loginWithAccount(Account account) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mCallback.loginSuccess();
                        } else {
                            mCallback.loginFailed();
                        }
                    }
                });

    }
}
