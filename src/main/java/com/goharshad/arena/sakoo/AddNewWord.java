package com.goharshad.arena.sakoo;


import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewWord extends AppCompatActivity implements View.OnClickListener {

    private TextView remain_paper;
    private EditText word,meaning;
    private Button finish,nextPaper;
    private LightnerDatabase lightnerDatabase;
    private int table1_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
        findViews();
        initToolbar();
        lightnerDatabase=new LightnerDatabase(AddNewWord.this,"laytner",null,1);
        table1_num=lightnerDatabase.getTotalWordsNumTableEveryday();
        setRemainPaperNum();
        setFonts(R.id.add_new_word_txt_header_1);
        setFonts(R.id.add_new_word_txt_header_2);
        setFonts(R.id.add_word_meaning_et);
        setFonts(R.id.add_word_word_et);
        setFonts(R.id.add_word_next_paper);
        setFonts(R.id.add_word_finish);
        setFonts(R.id.textView3);
        setFonts(R.id.activity_lightner_add_new_name_1);
        setFonts(R.id.activity_lightner_add_new_name_2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRemainPaperNum();
    }

    private void initToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.laytner_new_word_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);
    }

    private void setFonts(@IdRes int id) {
        View view=findViewById(id);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/IRAN-SANS.TTF");
        if (id==R.id.add_new_word_txt_header_1 || id==R.id.add_new_word_txt_header_2 ||
                id==R.id.textView3 || id==R.id.activity_lightner_add_new_name_1 || id==R.id.activity_lightner_add_new_name_2)
            ((TextView) view).setTypeface(typeface);
        else if (id==R.id.add_word_next_paper || id==R.id.add_word_finish)
            ((Button) view).setTypeface(typeface);
        else if (id==R.id.add_word_meaning_et || id==R.id.add_word_word_et)
            ((EditText) view).setTypeface(typeface);
    }

    private void findViews(){
        remain_paper= (TextView) findViewById(R.id.add_word_remain_papers);
        word= (EditText) findViewById(R.id.add_word_word_et);
        meaning= (EditText) findViewById(R.id.add_word_meaning_et);
        finish= (Button) findViewById(R.id.add_word_finish);
        nextPaper= (Button) findViewById(R.id.add_word_next_paper);

        finish.setOnClickListener(this);
        nextPaper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_word_finish:
                finish();
            case R.id.add_word_next_paper:
                addNewWord();
                break;
        }
    }

    private void addNewWord(){
        if(word.getText().toString().isEmpty()){
            Toast.makeText(this, "ابتدا کلمه ی مورد نظر خود را وارد کنید.", Toast.LENGTH_SHORT).show();
        }
        else if(meaning.getText().toString().isEmpty()){
            Toast.makeText(this, "ابتدا معنی کلمه ی مورد نظر خود را وارد کنید.", Toast.LENGTH_SHORT).show();
        }
        else{
            lightnerDatabase.insert("lay_everyday",word.getText().toString(),meaning.getText().toString());
            word.setHint("تایپ کنید...");
            meaning.setHint("تایپ کنید...");
            word.setText("");
            meaning.setText("");
            lightnerDatabase.updateTotalWordsNumTableEveryday(table1_num+"",(table1_num-1)+"");
            table1_num--;
            remain_paper.setText(getPersianNum(table1_num));
        }
    }
    private String getGrade(int i){
        switch (i){
            case 0:
                return "۰";
            case 1:
                return "۱";
            case 2:
                return "۲";
            case 3:
                return "۳";
            case 4:
                return "۴";
            case 5:
                return "۵";
            case 6:
                return "۶";
            case 7:
                return "۷";
            case 8:
                return "۸";
            case 9:
                return "۹";
        }
        return "۰";
    }
    private StringBuilder getPersianNum(int x){
        String s="";
        if(x==0){
            s="۰";
        }
        else{
            while(x!=0){
                s+=getGrade(x%10);
                x=x/10;
            }
        }
        StringBuilder sb=new StringBuilder();
        sb.append(s);
        sb=sb.reverse();
        return sb;
    }
    private void setRemainPaperNum(){
        remain_paper.setText(getPersianNum(table1_num));
    }
}
