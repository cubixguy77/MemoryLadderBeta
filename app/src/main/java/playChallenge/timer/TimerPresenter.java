package playChallenge.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import playChallenge.Bus;
import playChallenge.GameState;
import playChallenge.GameStateListener;
import playChallenge.SaveInstanceStateListener;
import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;
import selectChallenge.viewChallengeCard.NumberChallenge;

class TimerPresenter implements ITimer.TimerUpdateListener, GameStateListener, SaveInstanceStateListener, ITimer.TimerPlayPauseListener {

    private ITimer.View view;
    private TimerModel model;

    TimerPresenter(ITimer.View view) {
        this.view = view;
    }

    /**** SaveInstanceStateListener *****/
    @Override
    public void onRestoreInstanceState(Bundle inState) {}
    @Override
    public void onSaveInstanceState(Bundle outState) { if (model != null) model.save(outState); }
    /*----------------------------------*/


    /**** Presenter *****/
    @Override
    public void onTimeUpdate(long millisRemaining, long millisElapsed) {
        if (model == null || !model.getTimerSettingModel().timerEnabled) {
            return;
        }

        if (model.getTimerSettingModel().countDirection == CountDirection.UP) {
            view.displayTime(millisElapsed / 1000);
        }
        else {
            view.displayTime(millisRemaining / 1000);
        }
    }

    @Override
    public void onTimeCountdownComplete() {
        Bus.result.setMemTime((int) model.getOriginalTimeLimitInMillis() / 1000);
        Bus.getBus().onTimeExpired();
    }
    /*---------------------------------------------*/


    /**************GameStateListener****************/
    @Override
    public void onLoad(final Challenge challenge, final Bundle savedInstanceState) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                TimerSettingModel timerSettings = new TimerSettingModel(NumberChallenge.getMemTimerSetting(challenge));

                if (Bus.gameState == GameState.PRE_MEMORIZATION || Bus.gameState == GameState.MEMORIZATION) {
                    model = new TimerModel(timerSettings, savedInstanceState, TimerPresenter.this);
                    onTimeUpdate(model.getMillisRemaining(), model.getMillisElapsed());
                }
                else {
                    view.hide();
                    model = null;
                }
            }
        });
    }

    @Override
    public void onMemorizationStart() {
        startTimer();
    }

    @Override
    public void onTimeExpired() {}

    @Override
    public void onTransitionToRecall() {
        if (Bus.result.getMemTime() <= 0) {
            Bus.result.setMemTime((float) model.getMillisElapsed() / 1000);
        }

        cancelTimer();
        view.hide();
        view = null;
    }

    @Override
    public void onRecallComplete(Result result) {}

    @Override
    public void onPlayAgain() {}

    @Override
    public void onShutdown() {
        if (model != null) {
            model.cancelTimer();
        }

        view = null;
    }
    /*-------------- End GameStateListener ---------------------------*/



    /************ TimerPlayPauseListener ************************/
    @Override
    public void startTimer() {
        /* This cannot be called immediately because, on restoring from orientation change, the timer may not have been initialized yet */
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (model != null) {
                    model.startTimer();
                }
            }
        });
    }

    @Override
    public void pauseTimer() {
        if (model != null) {
            model.pauseTimer();
        }
    }

    @Override
    public void cancelTimer() {
        if (model != null) {
            model.cancelTimer();
        }
    }
    /*-----------------------------------------------------------------------*/
}
