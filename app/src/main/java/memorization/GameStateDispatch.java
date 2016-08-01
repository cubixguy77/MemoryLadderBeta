package memorization;

import java.util.ArrayList;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class GameStateDispatch implements  GameStateListener {

    ArrayList<GameStateListener> observers;
    public static GameState gameSate;

    public GameStateDispatch() {
        observers = new ArrayList<>();
    }

    public void subscribe(GameStateListener observer) {
        if (observer != null) {
            this.observers.add(observer);
        }
    }

    @Override
    public void onLoad(Challenge challenge) {
        gameSate = GameState.PRE_MEMORIZATION;

        for (GameStateListener observer : observers) {
            if (observer != null) {
                observer.onLoad(challenge);
            }
        }
    }

    @Override
    public void onMemorizationStart() {
        gameSate = GameState.MEMORIZATION;

        for (GameStateListener observer : observers) {
            if (observer != null) {
                observer.onMemorizationStart();
            }
        }
    }

    @Override
    public void onTimeExpired() {


        for (GameStateListener observer : observers) {
            if (observer != null) {
                observer.onTimeExpired();
            }
        }
    }

    @Override
    public void onTransitionToRecall() {
        gameSate = GameState.RECALL;

        for (GameStateListener observer : observers) {
            if (observer != null) {
                observer.onTransitionToRecall();
            }
        }
    }

    @Override
    public void onNextRow() {
        for (GameStateListener observer : observers) {
            if (observer != null) {
                observer.onNextRow();
            }
        }
    }

    @Override
    public void onSubmitRow() {
        for (GameStateListener observer : observers) {
            if (observer != null) {
                observer.onSubmitRow();
            }
        }
    }
}
