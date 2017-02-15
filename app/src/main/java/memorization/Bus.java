package memorization;

import android.os.Bundle;

import java.util.ArrayList;

import recall.RecallData;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import timer.TimerPlayPauseListener;

public class Bus implements GameStateListener, GridEvent.Navigation.UserNavigationEvents, GridEvent.Navigation.NavigationView, GridEvent.Keyboard.UserKeyboardActions, SaveInstanceStateListener, TimerPlayPauseListener {

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

    boolean hasObservers() {
        return !observers.isEmpty();
    }

    static void unsubscribeAll() {
        System.out.println("Bus.unSubscribeAll()");
        if (Bus.instance != null) {
            Bus.instance.observers.clear();
            Bus.instance = null;            
        }
    }

    static void destroy() {
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
            if (observer != null && observer instanceof GridEvent.Keyboard.UserKeyboardActions) {
                ((GridEvent.Keyboard.UserKeyboardActions) observer).onNextRow();
            }
        }
    }

    @Override
    public void onSubmitRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Keyboard.UserKeyboardActions) {
                ((GridEvent.Keyboard.UserKeyboardActions) observer).onSubmitRow();
            }
        }
    }

    @Override
    public void onSubmitAllRows() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Keyboard.UserKeyboardActions) {
                ((GridEvent.Keyboard.UserKeyboardActions) observer).onSubmitAllRows();
            }
        }
    }

    @Override
    public void onKeyPress(char digit) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Keyboard.UserKeyboardActions) {
                ((GridEvent.Keyboard.UserKeyboardActions) observer).onKeyPress(digit);
            }
        }
    }

    @Override
    public void onBackSpace() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Keyboard.UserKeyboardActions) {
                ((GridEvent.Keyboard.UserKeyboardActions) observer).onBackSpace();
            }
        }
    }


    @Override
    public void onPrevCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Navigation.UserNavigationEvents) {
                ((GridEvent.Navigation.UserNavigationEvents) observer).onPrevCell();
            }
        }
    }

    @Override
    public void onNextCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Navigation.UserNavigationEvents) {
                ((GridEvent.Navigation.UserNavigationEvents) observer).onNextCell();
            }
        }
    }

    @Override
    public void onDisablePrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Navigation.NavigationView) {
                ((GridEvent.Navigation.NavigationView) observer).onDisablePrev();
            }
        }
    }

    @Override
    public void onDisableNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Navigation.NavigationView) {
                ((GridEvent.Navigation.NavigationView) observer).onDisableNext();
            }
        }
    }

    @Override
    public void onEnablePrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Navigation.NavigationView) {
                ((GridEvent.Navigation.NavigationView) observer).onEnablePrev();
            }
        }
    }

    @Override
    public void onEnableNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Navigation.NavigationView) {
                ((GridEvent.Navigation.NavigationView) observer).onEnableNext();
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
            if (observer != null && observer instanceof TimerPlayPauseListener) {
                ((TimerPlayPauseListener) observer).startTimer();
            }
        }
    }

    @Override
    public void pauseTimer() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof TimerPlayPauseListener) {
                ((TimerPlayPauseListener) observer).pauseTimer();
            }
        }
    }

    @Override
    public void stopTimer() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof TimerPlayPauseListener) {
                ((TimerPlayPauseListener) observer).stopTimer();
            }
        }
    }
}
