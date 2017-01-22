package memorization;

import android.os.Bundle;

import java.util.ArrayList;

import recall.PositionChangeListener;
import recall.RecallData;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import timer.TimerPlayPauseListener;

public class Bus implements GameStateListener, GridEvent.Memory.UserEvents, GridEvent.Memory.ViewEvents, GridEvent.Recall.UserEvents, GridEvent.Recall.ViewEvents, PositionChangeListener, SaveInstanceStateListener, TimerPlayPauseListener {

    private static Bus instance = null;
    private ArrayList<Object> observers;

    public static Challenge challenge;
    static GridData memoryData;
    static RecallData recallData;

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
    public void onPrevRecallCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.ViewEvents) {
                ((GridEvent.Recall.ViewEvents) observer).onPrevRecallCell();
            }
        }
    }

    @Override
    public void onNextRecallCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.ViewEvents) {
                ((GridEvent.Recall.ViewEvents) observer).onNextRecallCell();
            }
        }
    }

    @Override
    public void onRowFilled() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.ViewEvents) {
                ((GridEvent.Recall.ViewEvents) observer).onRowFilled();
            }
        }
    }

    @Override
    public void onNextRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.UserEvents) {
                ((GridEvent.Recall.UserEvents) observer).onNextRow();
            }
        }
    }

    @Override
    public void onSubmitRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.UserEvents) {
                ((GridEvent.Recall.UserEvents) observer).onSubmitRow();
            }
        }
    }

    @Override
    public void onSubmitAllRows() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.UserEvents) {
                ((GridEvent.Recall.UserEvents) observer).onSubmitAllRows();
            }
        }
    }

    @Override
    public void onKeyPress(char digit) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.UserEvents) {
                ((GridEvent.Recall.UserEvents) observer).onKeyPress(digit);
            }
        }
    }

    @Override
    public void onBackSpace() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall.UserEvents) {
                ((GridEvent.Recall.UserEvents) observer).onBackSpace();
            }
        }
    }


    @Override
    public void onPrevMemoryCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.UserEvents) {
                ((GridEvent.Memory.UserEvents) observer).onPrevMemoryCell();
            }
        }
    }

    @Override
    public void onNextMemoryCell() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.UserEvents) {
                ((GridEvent.Memory.UserEvents) observer).onNextMemoryCell();
            }
        }
    }

    @Override
    public void onDisablePrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.ViewEvents) {
                ((GridEvent.Memory.ViewEvents) observer).onDisablePrev();
            }
        }
    }

    @Override
    public void onDisableNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.ViewEvents) {
                ((GridEvent.Memory.ViewEvents) observer).onDisableNext();
            }
        }
    }

    @Override
    public void onEnablePrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.ViewEvents) {
                ((GridEvent.Memory.ViewEvents) observer).onEnablePrev();
            }
        }
    }

    @Override
    public void onEnableNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.ViewEvents) {
                ((GridEvent.Memory.ViewEvents) observer).onEnableNext();
            }
        }
    }

    @Override
    public void onPositionChange(int newPosition) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof PositionChangeListener) {
                ((PositionChangeListener) observer).onPositionChange(newPosition);
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
