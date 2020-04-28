package com.neos.touristbook.task;

import android.os.AsyncTask;

public class ExcuteTask extends AsyncTask<Object, Object, Object> {
    private OnAsyncCallBack mCallBack;

    public void setmCallBack(OnAsyncCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void onPreExecute() {
        mCallBack.onPreExcute();
    }

    @Override
    protected Object doInBackground(Object... data) {
        int key = (int) data[0];
        return mCallBack.excuteTask(this, key, data[1]);
    }

    public void updateData(Object... obj) {
        publishProgress(obj);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        mCallBack.updateUI((Integer) values[0], values[1]);
    }

    @Override
    protected void onPostExecute(Object o) {
        mCallBack.taskComplete(o);
    }

    public interface OnAsyncCallBack {
        Object excuteTask(ExcuteTask myTask, int key, Object data);

         default void taskComplete(Object value) {
        }

        default void updateUI(int key, Object value) {
        }

        default void onPreExcute() {
        }
    }


}
