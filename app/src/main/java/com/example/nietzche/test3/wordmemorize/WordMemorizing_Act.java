package com.example.nietzche.test3.wordmemorize;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nietzche.test3.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WordMemorizing_Act extends AppCompatActivity{
    private MyList wordlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_memorizing);
        Fragment_WordList fra_wordlist=new Fragment_WordList();
        Bundle b=new Bundle();
        b.putSerializable("wordlist",getWordList());
        fra_wordlist.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.act_memorize,fra_wordlist).commit();
    }

    public MyList getWordList() {
        if (wordlist == null) {
            int tag = getIntent().getFlags();
            XmlResourceParser parser=null;
            switch (tag) {
                case 1:
                    parser = getResources().getXml(R.xml.custom_1);
                    break;
                case 2:
                    parser = getResources().getXml(R.xml.custom_2);
                    break;
                case 3:
                    parser = getResources().getXml(R.xml.custom_3);
                    break;
                case 4:
                    parser = getResources().getXml(R.xml.custom_4);
                    break;
                case 5:
                    parser = getResources().getXml(R.xml.custom_5);
                    break;
                case 6:
                    parser = getResources().getXml(R.xml.custom_6);
                    break;
                case 7:
                    parser = getResources().getXml(R.xml.custom_7);
                    break;
                case 8:
                    parser = getResources().getXml(R.xml.custom_8);
                    break;

                default:
                    break;
            }
            wordlist = new MyList();
            try {
                for (int event = parser.getEventType(); event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
                    if (event == XmlPullParser.TEXT) {

                        wordlist.add(parser.getText());
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wordlist;
    }



}
