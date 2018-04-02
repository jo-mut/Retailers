package com.app.retailers.api.yelp.retailers;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RetailerDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private RetailersPagerAdapter adapterViewPager;
    ArrayList<Retailer> retailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_detail);
        ButterKnife.bind(this);

        retailers = Parcels.unwrap(getIntent().getParcelableExtra("retailers"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new RetailersPagerAdapter(getSupportFragmentManager(), retailers);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
