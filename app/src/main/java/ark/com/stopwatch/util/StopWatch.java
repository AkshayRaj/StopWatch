package ark.com.stopwatch.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

import ark.com.stopwatch.OnTickListener;

/**
 * Created by Akshayraj on 11/25/15.
 */
public class StopWatch extends TimerTask{
    public static final long TIMER_PERIOD = 100;//millis
    public static final long TIMER_DELAY = 10;//millis
    private long mTotalTime;
    private long mLapTime;
    DateFormat mDateFormat = new SimpleDateFormat("mm:ss.SS");
    OnTickListener mOnTickListener;

    public StopWatch(OnTickListener onTickListener){
        mOnTickListener = onTickListener;
    }

    public void start(){
        mTotalTime = System.currentTimeMillis();
        mLapTime = mTotalTime;
    }

    public void stop(){

    }

    public void lapRestart(){
        mLapTime = System.currentTimeMillis();
    }

    public void reset(){
        start();
    }

    public String getLapTime() {
        return String.valueOf(System.currentTimeMillis() - mLapTime);
    }

    public String getTotalTime() {
        return String.valueOf(System.currentTimeMillis() - mTotalTime);
    }

    /**
     * The task to run should be specified in the implementation of the {@code run()}
     * method.
     */
    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        mOnTickListener.onTick(mDateFormat.format(System.currentTimeMillis() - mLapTime),
                mDateFormat.format(System.currentTimeMillis() - mTotalTime));
    }
}
