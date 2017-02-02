package gridAdapter;

import android.os.Bundle;

import memorization.Bus;
import memorization.GameStateListener;
import memorization.GridData;
import recall.RecallData;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

class NumberGridPresenter implements GameStateListener {

    private NumberGridAdapter adapter;

    private GridData memoryData;
    private RecallData recallData;
    private int highlightPosition;

    NumberGridPresenter(NumberGridAdapter adapter) {
        this.adapter = adapter;
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            memoryData = Bus.memoryData;
            recallData = Bus.recallData;
            highlightPosition = savedInstanceState.getInt("NumberGridAdapter.highlightPosition");
        }
        else {
            memoryData = new GridData(challenge);
            memoryData.loadData();
            recallData = new RecallData(challenge);
            highlightPosition = 1;
        }

        adapter.setMemoryData(memoryData);
        adapter.setRecallData(recallData);
        adapter.setHighlightPosition(highlightPosition);
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
        setHighlightPosition(1);
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

    private void setHighlightPosition(int highlightPosition) {
        this.highlightPosition = highlightPosition;
        adapter.setHighlightPosition(this.highlightPosition);
    }
}
