package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.model.TourOrder;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.base.BaseDialog;
import com.neos.touristbook.view.event.OnActionCallback;

import static com.neos.touristbook.view.dialog.ConfirmTourDialog.kEY_BOOK_SC;

public class BookTourDialog extends BaseDialog<TourPresenter> implements OnActionCallback, TourCallback {
    public static final String KEY_GO_HOME = "KEY_GO_HOME";
    private Tour tour;
    private EditText edtNumPerson, edtName, edtEmail, edtAddress, edtPhone;
    private TourOrder tourOrder;
    private TextView tvPrice;

    public BookTourDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
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
        tvPrice = findViewById(R.id.tv_price);

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
                if (edtName.getText().toString().isEmpty()
                        || edtEmail.getText().toString().isEmpty()
                        || edtAddress.getText().toString().isEmpty()
                        || edtPhone.getText().toString().isEmpty()
                        || edtNumPerson.getText().toString().isEmpty()) {
                    CommonUtils.getInstance().toast("Vui lòng điền đầy đủ thông tin");
                    hideLoading();
                    return;
                }
                tourOrder = new TourOrder(System.currentTimeMillis(), tour, edtName.getText().toString(),
                        edtAddress.getText().toString(),
                        edtEmail.getText().toString(), edtPhone.getText().toString(),
                        Integer.parseInt(edtNumPerson.getText().toString().trim()));
                ConfirmTourDialog dialog = new ConfirmTourDialog(getContext(), R.style.AppTheme);
                dialog.setTourOrder(tourOrder);
                dialog.setmCallback(BookTourDialog.this);
                dialog.show();
                hideLoading();
            }
        }, 2000);

    }


    public void setTour(Tour tour) {
        this.tour = tour;
        tvPrice.setText(tour.getPrice());
    }


    @Override
    public void callback(String key, Object data) {
        if (key.equals(kEY_BOOK_SC)) {
            CommonUtils.getInstance().toast("Đã Thanh toán thành công !");
            mPresenter.saveBookedTour(tourOrder);
            dismiss();
        }
    }
}
