package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.activity.DetailTourAct;
import com.neos.touristbook.view.adapter.TourAdapter;
import com.neos.touristbook.view.base.BaseDialog;
import com.neos.touristbook.view.event.OnActionCallback;
import com.neos.touristbook.view.event.OnCommonCallback;

import java.util.ArrayList;
import java.util.List;

import static com.neos.touristbook.view.adapter.TourAdapter.KEY_CLICK_ITEM;

public class SearchDialog extends BaseDialog<TourPresenter> implements OnActionCallback, TourCallback {
    private EditText edtSearch;
    private ImageView ivClear;
    private RecyclerView rvTour;
    private TourAdapter adapter;
    private List<Tour> mList;

    public SearchDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected void initView() {
        initSearchView();
        initListView();
    }

    private void initSearchView() {
        edtSearch = findViewById(R.id.edt_search, this);
        ivClear = findViewById(R.id.iv_clear, this);
        edtSearch.addTextChangedListener(new OnCommonCallback() {
            @Override
            public void afterTextChanged(Editable text) {
                if (text.toString().isEmpty()) {
                    ivClear.setVisibility(View.INVISIBLE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }
        });
        findViewById(R.id.tv_cancel, this);
        edtSearch.setFocusable(false);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void initListView() {
        mList = new ArrayList<>();
        rvTour = (RecyclerView) findViewById(R.id.rv_tour);
        rvTour.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new TourAdapter(mList, getContext());
        adapter.setmCallback(this);
        adapter.isPreview(false);
        rvTour.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_search:
                edtSearch.setFocusable(true);
                edtSearch.setFocusableInTouchMode(true);
                edtSearch.requestFocus();
                break;

            case R.id.tv_cancel:
                hideSoftKeyBoard();
                edtSearch.getText().clear();
                break;

            case R.id.iv_clear:
                edtSearch.getText().clear();
                break;

        }
    }

    private void hideSoftKeyBoard() {
        try {
            edtSearch.setFocusable(false);
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performSearch() {
        if (edtSearch.getText().toString().isEmpty()) {
            return;
        }
        mPresenter.searchTour(edtSearch.getText().toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_search;
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals(KEY_CLICK_ITEM)) {
            Tour tour = (Tour) data;
            showDetailTour(tour);
        }
    }
    private void showDetailTour(Tour tour) {
        Intent intent = new Intent(getContext(), DetailTourAct.class);
        intent.putExtra("data", tour);
        getContext().startActivity(intent);
    }


    @Override
    public void onResultTourList(List<Tour> tourList) {
        mList.clear();
        mList.addAll(tourList);
        adapter.notifyDataSetChanged();
    }
}
