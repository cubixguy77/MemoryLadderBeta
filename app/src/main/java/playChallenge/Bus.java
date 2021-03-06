package playChallenge;

import android.os.Bundle;

import java.util.ArrayList;

import playChallenge.timer.ITimer;
import playChallenge.writtenNumbersChallenge.memorization.GridData;
import playChallenge.writtenNumbersChallenge.memorization.SpeedNumbers;
import playChallenge.writtenNumbersChallenge.recall.RecallData;
import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;

public class Bus implements GameStateListener, SpeedNumbers.Navigation.UserNavigationEvents, SpeedNumbers.Navigation.NavigationView, SpeedNumbers.Keyboard.UserKeyboardActions, SaveInstanceStateListener, ITimer.TimerPlayPauseListener {

    private static Bus instance = null;
    private ArrayList<Object> observers;

    public static Challenge challenge;
    public static GridData memoryData;
    public static RecallData recallData;

    public static GameState gameState;
    public static Result result;

    private Bus() {
        observers = new ArrayList<>();
    }

    /* Returns an instance of Bus
     * Bus is a Singleton and an instance will be created the first time this method is called
     * The instance will be destroyed on device rotation or on play again, but the static fields will retain their values
     */
    public static Bus getBus() {
        if(Bus.instance == null) {
            Bus.instance = new Bus();
        }
        return Bus.instance;
    }

    public void subscribe(Object observer) {
        if (observer != null && !observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    public boolean hasObservers() {
        return !observers.isEmpty();
    }

    public static void unsubscribeAll() {
        System.out.println("Bus.unSubscribeAll()");
        if (Bus.instance != null) {
            Bus.instance.observers.clear();
            Bus.instance = null;            
        }
    }

    public static void destroy() {
        Bus.challenge = null;
        Bus.memoryData = null;
        Bus.recallData = null;
    }
    
    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        Bus.challenge = challenge;

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onLoad(challenge, savedInstanceState);
            }
        }
    }

    @Override
    public void onMemorizationStart() {
        gameState = GameState.MEMORIZATION;
        Bus.result = new Result();

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onMemorizationStart();
            }
        }
    }

    @Override
    public void onTimeExpired() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onTimeExpired();
            }
        }
    }

    @Override
    public void onTransitionToRecall() {
        gameState = GameState.RECALL;

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onTransitionToRecall();
            }
        }
    }

    @Override
    public void onRecallComplete(Result result) {
        gameState = GameState.REVIEW;

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onRecallComplete(result);
            }
        }
    }

    @Override
    public void onPlayAgain() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onPlayAgain();
            }
        }
    }

    @Override
    public void onShutdown() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onShutdown();
            }
        }
    }

    @Override
    public void onNextRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Keyboard.UserKeyboardActions) {
                ((SpeedNumbers.Keyboard.UserKeyboardActions) observer).onNextRow();
            }
        }
    }

    @Override
    public void onSubmitRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Keyboard.UserKeyboardActions) {
                ((SpeedNumbers.Keyboard.UserKeyboardActions) observer).onSubmitRow();
            }
        }
    }

    @Override
    public void onSubmitAllRows() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Keyboard.UserKeyboardActions) {
                ((SpeedNumbers.Keyboard.UserKeyboardActions) observer).onSubmitAllRows();
            }
        }
    }

    @Override
    public void onKeyPress(char digit) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Keyboard.UserKeyboardActions) {
                ((SpeedNumbers.Keyboard.UserKeyboardActions) observer).onKeyPress(digit);
            }
        }
    }

    @Override
    public void onBackSpace() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Keyboard.UserKeyboardActions) {
                ((SpeedNumbers.Keyboard.UserKeyboardActions) observer).onBackSpace();
            }
        }
    }


    @Override
    public void onPrevCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Navigation.UserNavigationEvents) {
                ((SpeedNumbers.Navigation.UserNavigationEvents) observer).onPrevCell();
            }
        }
    }

    @Override
    public void onNextCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Navigation.UserNavigationEvents) {
                ((SpeedNumbers.Navigation.UserNavigationEvents) observer).onNextCell();
            }
        }
    }

    @Override
    public void onDisablePrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Navigation.NavigationView) {
                ((SpeedNumbers.Navigation.NavigationView) observer).onDisablePrev();
            }
        }
    }

    @Override
    public void onDisableNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Navigation.NavigationView) {
                ((SpeedNumbers.Navigation.NavigationView) observer).onDisableNext();
            }
        }
    }

    @Override
    public void onEnablePrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Navigation.NavigationView) {
                ((SpeedNumbers.Navigation.NavigationView) observer).onEnablePrev();
            }
        }
    }

    @Override
    public void onEnableNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SpeedNumbers.Navigation.NavigationView) {
                ((SpeedNumbers.Navigation.NavigationView) observer).onEnableNext();
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SaveInstanceStateListener) {
                ((SaveInstanceStateListener) observer).onRestoreInstanceState(inState);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SaveInstanceStateListener) {
                ((SaveInstanceStateListener) observer).onSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void startTimer() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof ITimer.TimerPlayPauseListener) {
                ((ITimer.TimerPlayPauseListener) observer).startTimer();
            }
        }
    }

    @Override
    public void pauseTimer() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof ITimer.TimerPlayPauseListener) {
                ((ITimer.TimerPlayPauseListener) observer).pauseTimer();
            }
        }
    }

    @Override
    public void cancelTimer() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof ITimer.TimerPlayPauseListener) {
                ((ITimer.TimerPlayPauseListener) observer).cancelTimer();
            }
        }
    }
}
