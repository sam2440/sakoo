package com.goharshad.arena.sakoo;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Support extends AppCompatActivity implements View.OnClickListener {

    private ImageButton tele,insta,web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initToolbar();
        findViews();
        tele.setOnClickListener(this);
        web.setOnClickListener(this);
        insta.setOnClickListener(this);
    }

    private void findViews() {
        tele= (ImageButton) findViewById(R.id.ic_support_tele);
        insta= (ImageButton) findViewById(R.id.ic_support_insta);
        web= (ImageButton) findViewById(R.id.ic_support_web);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent WebView = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(WebView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_support_web :
                goToUrl("http://saku.goharshad8.ir");
                break;
            case R.id.ic_support_tele :
                goToUrl("https://t.me/sakoo_group");
                break;
            case R.id.ic_support_insta :
                goToUrl("https://www.instagram.com/sakoo.group/");
                break;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.suptoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.support_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }


}
