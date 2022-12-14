package com.kna.touristbook.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.eyalbira.loadingdots.LoadingDots;
import com.kna.touristbook.R;
import com.kna.touristbook.chatbot.ChatBotViewModel;
import com.kna.touristbook.event.TourCallback;
import com.kna.touristbook.model.Message;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.presenter.TourPresenter;
import com.kna.touristbook.view.adapter.ChatAdapter;
import com.kna.touristbook.view.adapter.PlanAdapter;
import com.kna.touristbook.view.adapter.PreviewAdapter;
import com.kna.touristbook.view.base.BaseActivity;
import com.kna.touristbook.view.dialog.BookTourDialog;
import com.kna.touristbook.view.event.OnActionCallback;
import com.rd.PageIndicatorView;
import com.ymb.ratingbar_lib.RatingBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.kna.touristbook.view.dialog.BookTourDialog.KEY_GO_HOME;

public class DetailTourAct extends BaseActivity<TourPresenter> implements OnActionCallback, TourCallback {
    private Tour tour;
    private TextView tvTitle;
    private ImageView ivFavorite;
    private TextView tvAddress, tvPrice2, tvPrice, tvDes;
    private RecyclerView rvPlan;
    private PageIndicatorView pageIndicatorView;
    private TextView tvNumStar, tvNumRate;
    private RatingBar rating;
    private LinearLayout lnRate;


    private EditText edtMessage;
    private RecyclerView rvMessage;
    private List<Message> mList;
    private ChatAdapter adapter;
    private LoadingDots typing;
    private ChatBotViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.act_detail_tour;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(ChatBotViewModel.class);
        viewModel.getBotAnswer().observe(this, s -> {
            typing.setVisibility(View.GONE);
            mPresenter.reply(s, tour);
        });
        mapView();
        tour = (Tour) getIntent().getSerializableExtra("data");
        mPresenter.saveRecentTour(tour);
        mPresenter.getRateStar(tour);
        updateUI();

    }

    @Override
    public void onResultRate(Float numStar, long numRate) {
        rating.setEnabled(false);
        if (numRate == 0) {
            return;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        lnRate.setVisibility(View.VISIBLE);
        tvNumStar.setText(df.format(numStar));
        rating.setRating(numStar);
        tvNumRate.setText("(" + numRate + ") l?????t ????nh gi??");
    }

    private void mapView() {
        lnRate = findViewById(R.id.rl_rate);
        NestedScrollView nestedContent = findViewById(R.id.nested_content);
        tvTitle = findViewById(R.id.tv_title);
        tvAddress = findViewById(R.id.tv_address);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice2 = findViewById(R.id.tv_price2);
        tvDes = findViewById(R.id.tv_description);
        rvPlan = findViewById(R.id.rv_plan);
        tvNumStar = findViewById(R.id.tv_num_star);
        tvNumRate = findViewById(R.id.tv_num_rate);
        rating = findViewById(R.id.rating);
        ivFavorite = findViewById(R.id.iv_favorite, this);
        findViewById(R.id.iv_back, this);
        findViewById(R.id.tv_book_tour, this);
        findViewById(R.id.iv_maps, this);
        nestedContent.scrollTo(0, 0);
        initChat();
    }

    private void initChat() {
        mList = new ArrayList<>();
        findViewById(R.id.iv_chat, this);
        findViewById(R.id.iv_close, this);
        typing = findViewById(R.id.typing);
        edtMessage = findViewById(R.id.edt_message);
        rvMessage = findViewById(R.id.rv_chat);
        findViewById(R.id.iv_send, this);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(mList, this);
        rvMessage.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_favorite:
                mPresenter.checkFavorite(tour);
                break;
            case R.id.tv_book_tour:
                bookTour();
                break;
            case R.id.iv_send:
                sendMessage(edtMessage.getText().toString());
                break;
            case R.id.iv_chat:
                findViewById(R.id.rl_chat_view).setVisibility(View.VISIBLE);
                mList.clear();
                typing.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        typing.setVisibility(View.GONE);
                        mList.add(new Message(String.format("VivuTravel xin ch??o! B???n ??ang quan t??m ?????n %s ????ng kh??ng?", tour.getTitle()), 0));
                        mList.add(new Message("Ch??ng t??i c?? th??? gi??p g?? cho b???n?", 0));
                    }
                }, 1000);
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_close:
                findViewById(R.id.rl_chat_view).setVisibility(View.GONE);
                break;
            case R.id.iv_maps:
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("data", tour);
                startActivity(intent);
                break;
        }
    }

    private void sendMessage(String msg) {
        if (msg.isEmpty()) {
            return;
        }
        typing.setVisibility(View.VISIBLE);
        mList.add(new Message(msg, 1));
        adapter.notifyDataSetChanged();
        rvMessage.smoothScrollToPosition(mList.size());
        edtMessage.setText("");
        viewModel.getAnswer(msg);
    }

    @Override
    public void onReply(String botData) {
        mList.add(new Message(botData, 0));
        rvMessage.smoothScrollToPosition(mList.size() - 1);
        adapter.notifyDataSetChanged();
    }

    private void bookTour() {
        BookTourDialog dialog = new BookTourDialog(this, R.style.AppTheme);
        dialog.setTour(tour);
        dialog.setmCallback(this);
        dialog.show();
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals(KEY_GO_HOME)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void initPreView() {
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(tour.getImageList().size());

        ViewPager vpPreview = (ViewPager) findViewById(R.id.vp_preview);
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < tour.getImageList().size(); i++) {
            imageList.add(tour.getImageList().get(i).getImage());
        }
        PreviewAdapter adapter = new PreviewAdapter(getSupportFragmentManager(), imageList);
        vpPreview.setAdapter(adapter);

        vpPreview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void updateUI() {
        initPreView();
        tvTitle.setText(tour.getTitle());
        tvAddress.setText(tour.getAddress());
        tvPrice.setText(tour.getPrice());
        tvPrice2.setText(tour.getPrice());
        tvDes.setText(tour.getDescription());
        for (int i = 0; i < tour.getPlans().size(); i++) {
            String str = tour.getPlans().get(i).getContent();
            String header = str.substring(0, str.indexOf(".") + 1);
            String content = str.substring(str.indexOf(".") + 1, str.length() - 1);
            tour.getPlans().get(i).setContent(content);
            tour.getPlans().get(i).setHeader(header);
        }
        mPresenter.detackFavorite(tour);
        initListPlan();
    }

    private void initListPlan() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvPlan.setLayoutManager(new LinearLayoutManager(DetailTourAct.this));
                PlanAdapter adapter = new PlanAdapter(tour.getPlans(), DetailTourAct.this);
                rvPlan.setAdapter(adapter);
            }
        }, 1000);

    }

    @Override
    public void isFavorite(boolean b) {
        if (b) {
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_unfarvorite);
        }
    }
}
