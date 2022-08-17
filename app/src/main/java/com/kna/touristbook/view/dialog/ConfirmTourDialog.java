package com.kna.touristbook.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kna.touristbook.R;
import com.kna.touristbook.model.TourOrder;
import com.kna.touristbook.view.base.BaseDialog;

public class ConfirmTourDialog extends BaseDialog {
    private TextView tvConfirm;
    public static String kEY_BOOK_SC = "kEY_BOOK_SC";
    private TextView tvPrice;

    public ConfirmTourDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    private EditText edtNumPerson, edtName, edtEmail, edtAddress, edtPhone;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtNumPerson = (EditText) findViewById(R.id.edt_num_person);
        tvConfirm = (TextView) findViewById(R.id.tv_book_tour, this);
        tvConfirm.setText("Thanh toán");
        findViewById(R.id.iv_home).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_title)).setText("Xác nhận thông tin");
        findViewById(R.id.iv_back, this);
        tvPrice = (TextView) findViewById(R.id.tv_price);

        edtName.setFocusable(false);
        edtEmail.setFocusable(false);
        edtAddress.setFocusable(false);
        edtPhone.setFocusable(false);
        edtNumPerson.setFocusable(false);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            dismiss();
        } else if (v.getId() == R.id.tv_book_tour) {
            showLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideLoading();
                    mCallback.callback(kEY_BOOK_SC, null);
                    dismiss();
                }
            }, 2000);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_confirm_tour;
    }

    public void setTourOrder(TourOrder tourOrder) {
        edtName.setText(tourOrder.getName());
        edtEmail.setText(tourOrder.getEmail());
        edtAddress.setText(tourOrder.getAddress());
        edtPhone.setText(tourOrder.getPhone());
        edtNumPerson.setText(tourOrder.getNumPerson() + "");
        tvPrice.setText(tourOrder.getTotalPrice());
    }

}