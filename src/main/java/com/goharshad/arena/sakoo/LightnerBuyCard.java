package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;

public class LightnerBuyCard extends AppCompatActivity implements View.OnClickListener {

    private TextView papersLeftCount;
    private RelativeLayout lightner_buy_50_layer, lightner_buy_100_layer, lightner_buy_200_layer;
    private LightnerDatabase lightnerDatabase;

    static final String TAG = "";

    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU_PREMIUM = "";

    // Does the user have the premium upgrade?
    boolean mIsPremium = false;

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 1234;

    // The helper object
    IabHelper mHelper;

    @Override
    protected void onResume() {
        super.onResume();
        setPapersleftcount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // use lastNum=lightnerDatabase.getTotalWordsNumTableEveryday();
            case R.id.lightner_buy_new_50_layer :
                //if (paid) then use lightnerDatabase.updateTotalWordsNumTableEveryday(lastNum,lastNum+50);
                break;
            case R.id.lightner_buy_new_100_layer:
                //if (paid) then use lightnerDatabase.updateTotalWordsNumTableEveryday(lastNum,lastNum+100);
                break;
            case R.id.lightner_buy_new_200_layer:
                //if (paid) then use lightnerDatabase.updateTotalWordsNumTableEveryday(lastNum,lastNum+200);
                break;
            //use setPapersleftcount();
        }
    }

    private class QueryInventoryFinishedListener implements IabHelper.QueryInventoryFinishedListener {

        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            } else {
                Log.d(TAG, "Query inventory was successful.");
                // does the user have the premium upgrade?
                mIsPremium = inv.hasPurchase(SKU_PREMIUM);

                // update UI accordingly
                Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    }

    private class OnIabPurchaseFinishedListener implements IabHelper.OnIabPurchaseFinishedListener{

        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            } else if (info.getSku().equals(SKU_PREMIUM)) {
                // give user access to premium content and update the UI
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightner_buy_card);
        lightnerDatabase=new LightnerDatabase(LightnerBuyCard.this,"laytner",null,1);
        initToolbar();
        fidnViews();
        setfonts();
        setPapersleftcount();

        lightner_buy_50_layer.setOnClickListener(this);
        lightner_buy_100_layer.setOnClickListener(this);
        lightner_buy_200_layer.setOnClickListener(this);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.lightner_buy_new_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);
    }

    private void setfonts() {
        setFont(R.id.lightner_buy_new_txt_1);
        setFont(R.id.lightner_buy_new_txt_2);
        setFont(R.id.lightner_buy_new_txt_3);
        setFont(R.id.lightner_buy_new_txt_4);
        setFont(R.id.buy_word_papers_left_count);
        setFont(R.id.activity_lightner_buy_new_name_1);
        setFont(R.id.activity_lightner_buy_new_name_2);
    }

    private void setFont(@IdRes int id) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView = (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    private void fidnViews() {
        papersLeftCount = (TextView) findViewById(R.id.buy_word_papers_left_count);
        lightner_buy_50_layer = (RelativeLayout) findViewById(R.id.lightner_buy_new_50_layer);
        lightner_buy_100_layer = (RelativeLayout) findViewById(R.id.lightner_buy_new_100_layer);
        lightner_buy_200_layer = (RelativeLayout) findViewById(R.id.lightner_buy_new_200_layer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    private void setPapersleftcount(){
        int totalPaper=lightnerDatabase.getTotalWordsNumTableEveryday();
        papersLeftCount.setText(totalPaper+"");
    }
}
