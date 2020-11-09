package com.example.l_cure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class PopupActivity extends Activity {
    private int number;
    TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //데이터 가져오기
        Intent intent = getIntent();
        number = intent.getIntExtra("number",0);

        if(number==1){
            setContentView(R.layout.session01_popup);
        }else if(number==2){
            setContentView(R.layout.session02_popup);
        }else if(number==3){
            setContentView(R.layout.session03_popup);
        }else if(number==4){
            setContentView(R.layout.session04_popup);
        }else if(number==5){
            setContentView(R.layout.session05_popup);
        }else if(number==6){
            setContentView(R.layout.session06_popup);
        }else if(number==7){
            setContentView(R.layout.rightanswer_popup);
        }else if(number==8){
            setContentView(R.layout.wronganswer_popup);
        }
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        if(number==1){
            Intent intent = new Intent(getApplicationContext(), SelectSyllableCountActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }else if(number==2){
            Intent intent = new Intent(getApplicationContext(), SelectWordSynthesisActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}