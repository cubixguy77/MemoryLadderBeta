package memorization;

import java.util.ArrayList;

import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class Bus implements GameStateListener, GridEvent.Memory.UserEvents, GridEvent.Memory.ViewEvents, GridEvent.Recall {

    private static Bus instance = null;
    private ArrayList<Object> observers;


    public static GameState gameSate;

    private Bus() {
        observers = new ArrayList<>();
    }

    public static Bus getBus() {
        if(instance == null) {
            instance = new Bus();
        }
        return instance;
    }

    public void subscribe(Object observer) {
        if (observer != null) {
            this.observers.add(observer);
        }
    }

    public void unsubscribeAll() {
        observers = null;
        instance = null;
    }


    @Override
    public void onLoad(Challenge challenge) {
        gameSate = GameState.PRE_MEMORIZATION;

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onLoad(challenge);
            }
        }
    }

    @Override
    public void onMemorizationStart() {
        gameSate = GameState.MEMORIZATION;

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
        gameSate = GameState.RECALL;

        for (Object observer : observers) {
            if (observer != null && observer instanceof GameStateListener) {
                ((GameStateListener) observer).onTransitionToRecall();
            }
        }
    }

    @Override
    public void onRecallComplete(Result result) {
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
    public void onNextRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall) {
                ((GridEvent.Recall) observer).onNextRow();
            }
        }
    }

    @Override
    public void onSubmitRow() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Recall) {
                ((GridEvent.Recall) observer).onSubmitRow();
            }
        }
    }









    @Override
    public void onPrev() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.UserEvents) {
                ((GridEvent.Memory.UserEvents) observer).onPrev();
            }
        }
    }

    @Override
    public void onNext() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof GridEvent.Memory.UserEvents) {
                ((GridEvent.Memory.UserEvents) observer).onNext();
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
}
