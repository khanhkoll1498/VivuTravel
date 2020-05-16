package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.model.TourOrder;
import com.neos.touristbook.view.base.BaseDialog;

public class BookTourDialog extends BaseDialog {
    public static final String KEY_GO_HOME = "KEY_GO_HOME";
    private Tour tour;
    private EditText edtNumPerson, edtName, edtEmail, edtAddress, edtPhone;

    public BookTourDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_book_tour;
    }

    @Override
    protected void initView() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtNumPerson = (EditText) findViewById(R.id.edt_num_person);


        findViewById(R.id.iv_back, this);
        findViewById(R.id.iv_home, this);
        findViewById(R.id.tv_book_tour, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.iv_home:
                mCallback.callback(KEY_GO_HOME, null);
                break;
            case R.id.tv_book_tour:
                confirmTour();
                break;
        }
    }

    private void confirmTour() {
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TourOrder tourOrder = new TourOrder(tour, edtName.getText().toString(),
                        edtAddress.getText().toString(),
                        edtEmail.getText().toString(), edtPhone.getText().toString(),
                        Integer.parseInt(edtNumPerson.getText().toString().trim()));
                ConfirmTourDialog dialog = new ConfirmTourDialog(getContext(), R.style.AppTheme);
                dialog.setTourOrder(tourOrder);
                dialog.show();
                hideLoading();
            }
        }, 2000);

    }


    public void setTour(Tour tour) {
        this.tour = tour;
    }


}
