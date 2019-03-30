//
//
//import android.os.Handler;
//import android.util.Log;
//import android.widget.Toast;
//
///**
// * Created by Arshad on 3/7/2019.
// */
//
//public class GlobalTimer{
//    private static GlobalTimer instance = null;
//    public boolean timerRunning = false;
//
//    public boolean getTimerRunning(){return timerRunning;}
//    public void setTimerRunning(boolean timer){ timerRunning=timer;}
//
//
//    public startTimer(){
//
//        if(!timerRunning){
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //Do something after 100ms
//                    Log.e(" test" , "timer started");
//
//                }
//            }, 10000); // should be 100 * 1000 * 60 * 10 for 10 min" />}
//        }
//
//    }
//
//
//    protected GlobalTimer() {
//        // Exists only to defeat instantiation.
//
//    }
//
//
//
//
//    public static GlobalTimer getInstance() {
//        if(instance == null) {
//            instance = new GlobalTimer();
//        }
//        return instance;
//
//    }
//
//
//
//
//}