package ark.com.stopwatch;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

import ark.com.stopwatch.util.StopWatch;

public class MainActivity extends Activity {
    EditText mLapTimeEditText;
    EditText mTotalTimeEditText;
    Button mStartButton;
    Button mStopButton;
    Button mLapButton;
    Button mResetButton;
    ListView mListView;
    StopWatch mStopWatch;
    static Timer mTimer;

    ArrayList<String> mLapList = new ArrayList<String>();
    ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLapTimeEditText = (EditText) findViewById(R.id.edittext_laptime);
        mTotalTimeEditText = (EditText) findViewById(R.id.edittext_totaltime);
        mStartButton = (Button) findViewById(R.id.startButton);
        mStopButton = (Button) findViewById(R.id.stopButton);
        mLapButton = (Button) findViewById(R.id.lapButton);
        mResetButton = (Button) findViewById(R.id.resetButton);
        mListView = (ListView) findViewById(R.id.lapTimeList);
        mListAdapter = new ListAdapter(getApplicationContext(), mLapList);
        mListView.setAdapter(mListAdapter);
        disableView();
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartButton();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopButton();
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetButton();
            }
        });

        mLapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLapButton();
            }
        });
    }

    private void onStartButton(){
        enableView();
        mStopWatch = new StopWatch(new OnTickListener() {
            @Override
            public void onTick(final String lapTime, final String totalTime) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLapTimeEditText.setText(lapTime);
                        mTotalTimeEditText.setText(totalTime);
                    }
                });
            }
        });
        Thread stopWatchThread = new Thread(new Runnable() {
            /**
             * Starts executing the active part of the class' code. This method is
             * called when a thread is started that has been created with a class which
             * implements {@code Runnable}.
             */
            @Override
            public void run() {
                mTimer = new Timer();
                mTimer.scheduleAtFixedRate(mStopWatch, StopWatch.TIMER_DELAY, StopWatch.TIMER_PERIOD);
            }
        });
        stopWatchThread.start();
        mStopWatch.start();
    }

    private void onStopButton(){
        addLap();
        mStopWatch.stop();
        mLapTimeEditText.setText("00:00.00");
        mTotalTimeEditText.setText("00:00.00");
        disableView();
        mTimer.cancel();
        mTimer.purge();
    }

    private void onLapButton(){
        addLap();
        mStopWatch.lapRestart();
    }

    private void addLap() {
        TextView latestLapTime = new TextView(getApplicationContext());
        latestLapTime.setText(mLapTimeEditText.getText());
        mLapList.add(mLapTimeEditText.getText().toString());
        mListAdapter.notifyDataSetChanged();
    }

    private void onResetButton(){
        mStopWatch.reset();
    }

    private void enableView(){
        mLapTimeEditText.setText("00:00.00");
        mTotalTimeEditText.setText("00:00.00");
        mStartButton.setEnabled(false);
        mStopButton.setEnabled(true);
        mLapButton.setEnabled(true);
        mResetButton.setEnabled(true);
    }

    private void disableView() {
        mLapTimeEditText.setText("00:00.00");
        mTotalTimeEditText.setText("00:00.00");
        mStartButton.setEnabled(true);
        mStopButton.setEnabled(false);
        mLapButton.setEnabled(false);
        mResetButton.setEnabled(false);
    }

}
