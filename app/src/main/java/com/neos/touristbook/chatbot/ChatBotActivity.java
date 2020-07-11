package com.neos.touristbook.chatbot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.neos.touristbook.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ChatBotActivity extends AppCompatActivity {
    private Button buttonSubmit;
    private EditText editQuery;
    private BaseSchedulerProvider baseSchedulerProvides = SchedulerProvider.getInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editQuery = findViewById(R.id.editQuery);
        buttonSubmit.setOnClickListener(v -> {
            Disposable disposable = TravelAppClient.getInstance().getApiService().getAnswer(new BodyRequest(editQuery.getText().toString(), false))
                    .subscribeOn(baseSchedulerProvides.io())
                    .observeOn(baseSchedulerProvides.ui())
                    .subscribe(answerResponse ->
                            Toast.makeText(getApplicationContext(),
                                    answerResponse.getDataAnswerResponse().getIntentAnswerResponse().get(0).getLabel(),
                                    Toast.LENGTH_SHORT).show());
            compositeDisposable.add(disposable);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
