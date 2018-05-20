package com.example.nietzche.test3;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listen_Fragment extends Fragment {
    private SeekBar seekBar;
    private TextView tvTime;
    private TextView tvDuration;
    private MediaPlayer player;
    private Button btn_play;
    private ListView listView;
    private Timer timer;
    private int[] resource;

    @Override
    public void onDestroyView() {
        if(player!=null){
            player.release();
            timer.cancel();
        }
        super.onDestroyView();
    }

    private Button btn_stop;
    int duration;
    private Handler handler;

    public Listen_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listen, container, false);
        tvTime = view.findViewById(R.id.rl).findViewById(R.id.tv_current);
        tvDuration = view.findViewById(R.id.rl).findViewById(R.id.tv_duration);
        seekBar = view.findViewById(R.id.rl).findViewById(R.id.seekbar);
        btn_play = view.findViewById(R.id.play);
        btn_stop = view.findViewById(R.id.stop);
        listView=view.findViewById(R.id.lv_listen);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        player.release();
                        player=MediaPlayer.create(getActivity(),R.raw.listen_1);
                        player.start();
                        break;
                    case 1:
                        player.release();
                        player=MediaPlayer.create(getActivity(),R.raw.listen_2);
                        player.start();
                        break;
                    case 2:
                        player.release();
                        player=MediaPlayer.create(getActivity(),R.raw.listen_3);
                        player.start();
                        break;
                    default:break;
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        int current = msg.arg1;
                        tvTime.setText(current / 60 + ":" + current % 60);
                        seekBar.setProgress(current);
//                        if(player.)
                        break;
                }
            }
        };


        player = MediaPlayer.create(getActivity(), R.raw.listen_1);
        duration = player.getDuration() / 1000;
        tvDuration.setText(duration / 60 + ":" + duration % 60);

        seekBar.setMax(duration);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    player.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                player.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                seekBar.setProgress(0);
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message m = new Message();
                m.what = 1;
                m.arg1 = player.getCurrentPosition() / 1000;
                handler.sendMessage(m);
            }
        };
        timer.schedule(task, 0, 1000);
        return view;
    }

}
