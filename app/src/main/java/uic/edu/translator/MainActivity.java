package uic.edu.translator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText MyInputText;
    Button MyTranslateButton;
    TextView MyOutputText;
    public static String outputText;
    private ImageButton btnSpeak;
    private ImageButton speechOut;
    Spinner fromLanguage;
    Spinner toLanguage;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech t1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_main);

        MyInputText = (EditText)findViewById(R.id.toTranslate);
        MyTranslateButton = (Button)findViewById(R.id.translate);
        MyOutputText = (TextView)findViewById(R.id.translatedText);

        MyTranslateButton.setOnClickListener(MyTranslateButtonOnClickListener);
         fromLanguage = (Spinner) findViewById(R.id.spinner2);
         toLanguage = (Spinner)findViewById(R.id.spinner);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // hide the action bar


        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        speechOut=(ImageButton) findViewById(R.id.speechOut);

        speechOutInitialiser();


        speechOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speechOutInitialiser();
            }
        });
    }
    private void speechOutInitialiser(){
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(GTranslate.findLanguage(toLanguage.getSelectedItem().toString()));
                    Set s = t1.getAvailableLanguages();


                    String toSpeak = MyOutputText.getText().toString();
                    if(!toSpeak.equals("Translated Text will be displayed here"))
                    {  Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();



                    int x = t1.isLanguageAvailable(GTranslate.findLanguage(toLanguage.getSelectedItem().toString()));
                    Float f = new Float("0.90");
                    t1.setSpeechRate(f);
                    t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);}
                }
            }
        });

    }
    private Button.OnClickListener MyTranslateButtonOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            String InputString;
            String OutputString = null;
            InputString = MyInputText.getText().toString();
            String inputLang = fromLanguage.getSelectedItem().toString();
            String outputLang = toLanguage.getSelectedItem().toString();
            try {
                 OutputString = GTranslate.translatedText(InputString, inputLang ,outputLang);
                while(MyOutputText.getText().equals("Translated Text will be displayed here"))
                MyOutputText.setText(outputText);
            } catch (Exception ex) {
                ex.printStackTrace();
                OutputString = "Error";
            }

            MyOutputText.setText(OutputString);

        }

    };
    private void promptSpeechInput() {
        String lang = GTranslate.findLanguageCode(fromLanguage.getSelectedItem().toString());
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{lang});

        ;
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something to Translate");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Speech Not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    MyInputText.setText(result.get(0));
                }
                break;
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}