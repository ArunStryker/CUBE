package com.example.christhu.cube;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Menu extends AppCompatActivity {
    CardView cardView;
ImageView df;
    private TextToSpeech tts;
    private ArrayList<String> questions;
    private String name, surname, age, asName;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String PREFS = "prefs";
    private static final String NEW = "new";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String AS_NAME = "as_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        cardView=(CardView)findViewById(R.id.card_view1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=(new Intent(Menu.this,Lis.class));
                startActivity(intent);
            }
        });



        preferences = getSharedPreferences(PREFS,0);
        editor = preferences.edit();

        findViewById(R.id.md).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen();
            }
        });
        loadQuestions();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }


                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

    }

    private void loadQuestions(){
        questions = new ArrayList<>();
        questions.clear();
        questions.add("Hello, what is your name?");
        questions.add("What is your surname?");
        questions.add("How old are you?");
        questions.add("That's all I had, thank you ");
    }

    private void listen(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Menu.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String inSpeech = res.get(0);
                recognition(inSpeech);
            }
        }
    }

    private void recognition(String text){
        Log.e("Speech",""+text);
        String[] speech = text.split(" ");

        if(text.contains("what is your name")){
            String as_name = preferences.getString(AS_NAME,"");
            if(as_name.equals(""))
                speak("I am sara");
            else
                speak("My name is "+as_name);
        }


        if(text.contains("check offers availability")){
            String as_name = preferences.getString(AS_NAME,"");
            if(as_name.equals(""))
                speak("Pizza and burger");
            else
                speak("My name is "+as_name);
        }

        if(text.contains("who is the best child specialist doctor")){
            String as_name = preferences.getString(AS_NAME,"");
            if(as_name.equals(""))
                speak("Doctor chris");
            else
                speak("My name is "+as_name);
        }
        if(text.contains("where is doctor room")){
            String as_name = preferences.getString(AS_NAME,"");
            if(as_name.equals(""))
                speak("Go stright and third room");
            else
                speak("My name is "+as_name);
        }

    }
}
