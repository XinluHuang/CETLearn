package com.example.nietzche.test3;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nietzche.test3.dict.CollinsDict;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Findword_Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private Button button;
    private EditText editText;
    private TextView textview;
    private SQLiteDatabase database;
    private CollinsDict collins;

    public Findword_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findword, container, false);
        copyDB();
        collins=new CollinsDict(getActivity().getFilesDir().getParent() + "/databases/collins.db");
        linearLayout = view.findViewById(R.id.line1);
        textview = view.findViewById(R.id.scrollview).findViewById(R.id.textview);
        editText = linearLayout.findViewById(R.id.edittext);
        button = linearLayout.findViewById(R.id.button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                findword();
                break;
        }
    }

    private void copyDB() {
        File mkdir = new File(getActivity().getFilesDir().getParent(), "databases");
        if (!mkdir.exists()) {
            mkdir.mkdirs();
        }
        File destination = new File(mkdir, "collins.db");
        FileOutputStream fos = null;
        InputStream resinput = null;
        if (!destination.exists()) {
            try {
                fos = new FileOutputStream(destination);
                resinput = getActivity().getAssets().open("collins.db");
                byte[] bytes = new byte[2048];
                int len = 0;
                while ((len = resinput.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (IOException e) {
                editText.setText(e.toString());
            } finally {
                try {
                    fos.close();
                    resinput.close();
                } catch (IOException e) {
                    editText.setText(e.toString());
                }
            }
        }
    }

    private void findword() {
        if(!collins.find(editText.getText().toString())) return;
        textview.setText(Html.fromHtml(collins.outInHtml()));
    }
}
