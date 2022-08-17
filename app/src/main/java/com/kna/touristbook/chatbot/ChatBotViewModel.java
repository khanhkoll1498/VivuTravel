package com.kna.touristbook.chatbot;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ChatBotViewModel extends ViewModel {
    private BaseSchedulerProvider baseSchedulerProvides = SchedulerProvider.getInstance();
    private MutableLiveData<String> botAnswer = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<String> getBotAnswer() {
        return botAnswer;
    }

    public void getAnswer(String question) {
        Disposable disposable = TravelAppClient.getInstance().getApiService()
                .getAnswer(new BodyRequest(question, false))
                .subscribeOn(baseSchedulerProvides.io())
                .observeOn(baseSchedulerProvides.ui())
                .subscribe(answerResponse ->
                        botAnswer.setValue(answerResponse.getDataAnswerResponse()
                                .getIntentAnswerResponse().get(0).getLabel())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
