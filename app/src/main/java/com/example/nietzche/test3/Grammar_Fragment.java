package com.example.nietzche.test3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Grammar_Fragment extends Fragment {

    private WebView webView;
    public Grammar_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_grammar_fragment, container, false);
        webView=view.findViewById(R.id.grammar_webview);
        webView.loadUrl("file:///android_asset/grammar099.htm");
        return view;
    }

}
