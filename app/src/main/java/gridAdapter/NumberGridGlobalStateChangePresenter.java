package gridAdapter;

import android.os.Bundle;

import memorization.Bus;
import memorization.GameStateListener;
import memorization.GridData;
import memorization.GridEvent;
import memorization.NumberMemoryModel;
import recall.RecallData;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

class NumberGridGlobalStateChangePresenter implements GameStateListener {

    private GridEvent.Recall.Grid adapter;
    private NumberMemoryModel model;

    NumberGridGlobalStateChangePresenter(GridEvent.Recall.Grid adapter, NumberMemoryModel model) {
        this.adapter = adapter;
        this.model = model;
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {

        GridData memoryData;
        RecallData recallData;
        int highlightPosition;

        if (savedInstanceState != null) {
            memoryData = Bus.memoryData;
            recallData = Bus.recallData;
            highlightPosition = savedInstanceState.getInt("NumberGridMemoryAdapter.highlightPosition");
        }
        else {
            memoryData = new GridData(challenge);
            memoryData.loadData();
            recallData = new RecallData(challenge);
            highlightPosition = 1;
        }

        model.setMemoryData(memoryData);
        model.setRecallData(recallData);
        model.setHighlightPosition(highlightPosition);

        adapter.setNumGridColumns(memoryData.numCols);
        adapter.refresh();
    }

    @Override
    public void onMemorizationStart() {
        adapter.refresh();
    }

    @Override
    public void onTimeExpired() {
    }

    @Override
    public void onTransitionToRecall() {
        model.setHighlightPosition(1);
        adapter.refresh();
        adapter.scrollToTop();
    }

    @Override
    public void onRecallComplete(Result result) {
    }

    @Override
    public void onPlayAgain() {
    }

    @Override
    public void onShutdown() {
    }
}