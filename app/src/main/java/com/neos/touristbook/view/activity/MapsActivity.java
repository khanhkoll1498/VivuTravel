package com.neos.touristbook.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.neos.touristbook.R;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.view.base.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private Tour tour;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        tour = (Tour) getIntent().getSerializableExtra("data");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maps;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (tour.getLocations() == null) {
            return;
        }

        List<LatLng> latLngs = new ArrayList();
        for (int i = 0; i < tour.getLocations().size(); i++) {
            View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_maps, null);
            TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
            numTxt.setText("" + (i + 1));

            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> address = geocoder.getFromLocationName(tour.getLocations().get(i), 1);
                Log.d("TAG", "onMapReady: ");
                LatLng l2 = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                latLngs.add(l2);
                MarkerOptions m = new MarkerOptions()
                        .position(l2)
                        .title(address.get(0).getFeatureName()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker)));
                googleMap.addMarker(m);
                if (i == 0) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l2, 12.0f));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < latLngs.size(); i++) {
            if (i != 0) {
                googleMap.addPolyline(new PolylineOptions().add(
                        latLngs.get(i - 1), latLngs.get(i)
                ).width(10).color(Color.RED));
            }
        }
        googleMap.addPolyline(new PolylineOptions().add(
                latLngs.get(latLngs.size() - 1), latLngs.get(0)
        ).width(10).color(Color.RED));
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

}
