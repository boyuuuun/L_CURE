package com.example.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SelectPhonemicSynthesisActivity extends AppCompatActivity {
    Button back, setting;
    Button one, two, three;
    Button board;
    TextView text;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list = new ArrayList<>();;
    private int quizCount = 1;
    String imgName;
    Hangul hangul = new Hangul();
    private String answer_word;
    private int word_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phonemic_synthesis);

        // words 데이터베이스 load
        initLoadDB();

        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() < 2) {
                new_word_list.add(word_list.get(i));
            }
        }

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);

        // get random word
        random_word();

        // click board button
        board = findViewById(R.id.board);
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            // 음성 인식
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "말하세요");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                startActivityForResult(intent, 1);
            }
        });
    }

    private void random_word() {
        Random random = new Random();
        int index = random.nextInt(new_word_list.size());

        String word = new_word_list.get(index).getWord();       // 단어
        String[] question  = hangul.hangulToJaso(word).split("");
        one.setText(question[1]);
        two.setText(question[2]);
        three.setText(question[3]);
        answer_word = word;
        word_index = index;
    }

    public void showNext(){
        quizCount++;
        new_word_list.remove(word_index);
        random_word();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
                ArrayList<String> inputs = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); // input answer 리스트
                String input ;
                boolean correct = false;
                int chance = 3; // input 후보 개수
                for (int i=0; i<chance; i++) {
                    input = inputs.get(i);
                    if (input.equals(answer_word.getWord())) {
                        correct = true;
                        break;
                    }
                }

                if (correct) {
                    // correct answer
                    if(quizCount==5) {
                        Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                        startActivityForResult(intent,5000);
                        intent = new Intent(getApplicationContext(), PopupActivity.class);
                        intent.putExtra("number", 7);
                        intent.putExtra("imgName", answer_word.getImg_name());
                        startActivityForResult(intent,5000);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                        intent.putExtra("number", 7);
                        intent.putExtra("imgName", answer_word.getImg_name());
                        startActivityForResult(intent,5000);

                        showNext();
                    }
                } else{
                    // wrong answer
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 8);
                    startActivityForResult(intent,5000);
                }

            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    private void initLoadDB() {
        DataAdapter mDbHelper = new DataAdapter(this.getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        word_list = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }
}
