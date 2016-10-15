package memorization;

import android.os.Bundle;

import java.util.ArrayList;

import recall.PositionChangeListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class Bus implements GameStateListener, GridEvent.Memory.UserEvents, GridEvent.Memory.ViewEvents, GridEvent.Recall.UserEvents, GridEvent.Recall.ViewEvents, PositionChangeListener, SaveInstanceStateListener {

    private static Bus instance = null;
    private ArrayList<Object> observers;


    public static GameState gameState;

    private Bus() {
        observers = new ArrayList<>();
    }

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

    static void unsubscribeAll() {
        if (Bus.instance != null) {
            Bus.instance.observers.clear();
            Bus.instance = null;
        }
    }

    @Override
    public void onLoad(Challenge challenge) {
        gameState = GameState.PRE_MEMORIZATION;

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onLoad(challenge);
            }
        }
    }

    @Override
    public void onMemorizationStart() {
        gameState = GameState.MEMORIZATION;

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
    public void onKeyPress(int digit) {
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
}
