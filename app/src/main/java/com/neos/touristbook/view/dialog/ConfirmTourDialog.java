package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.neos.touristbook.R;
import com.neos.touristbook.model.TourOrder;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.base.BaseDialog;

public class ConfirmTourDialog extends BaseDialog {
    private TextView tvConfirm;

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
        findViewById(R.id.iv_back, this);

        edtName.setFocusable(false);
        edtEmail.setFocusable(false);
        edtAddress.setFocusable(false);
        edtPhone.setFocusable(false);
        edtNumPerson.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.iv_back){
            dismiss();
        }else  if (v.getId()==R.id.tv_book_tour){
            CommonUtils.getInstance().toast("Xác nhận tour");
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_book_tour;
    }

    public void setTourOrder(TourOrder tourOrder) {
        edtName.setText(tourOrder.getName());
        edtEmail.setText(tourOrder.getEmail());
        edtAddress.setText(tourOrder.getAddress());
        edtPhone.setText(tourOrder.getPhone());
        edtNumPerson.setText(tourOrder.getNumPerson() + "");
    }
}
