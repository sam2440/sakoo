package com.goharshad.arena.sakoo;

import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Team extends AppCompatActivity {

    private RecyclerView rlv;
    private RecyclerView.LayoutManager layoutManager;
    private TeamListAdapter adapter;
    private List<TeamMember> team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        initToolbar();
        rlv = (RecyclerView) findViewById(R.id.team_1_rlv);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TeamListAdapter(this, getTeam());
        rlv.setLayoutManager(layoutManager);
        rlv.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.team_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.team_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    public List<TeamMember> getTeam() {
        team=new ArrayList<>();
        team.add(null);
        team.add(new TeamMember(R.drawable.najani,"محمد حسین ناجانی","مدیر و کارشناس ارشد ارتباطات"));
        team.add(new TeamMember(R.drawable.mardani,"امیر مردانه","مدیر تبلیغات و طراحی"));
        team.add(new TeamMember(R.drawable.reza_sadeqian_poor,"رضا صادقیان پور","مشاور ارشد و مدیر مسائل آموزشی"));
        team.add(new TeamMember(R.drawable.user,"محسن مرادی","کارشناس ارتباطات و تبلیغات"));
        team.add(null);
        team.add(new TeamMember(R.drawable.rastg,"محمدرضا رستگاران","گروه برنامه نویسی آرنا"));
        team.add(new TeamMember(R.drawable.seps1,"سپهر صمدی","گروه برنامه نویسی آرنا"));
        team.add(new TeamMember(R.drawable.user,"شهرزاد شعشعانی","گروه برنامه نویسی آرنا"));
        team.add(new TeamMember(R.drawable.maleki,"امیر ملکی","گروه برنامه نویسی آرنا"));
        return team;
    }
}
