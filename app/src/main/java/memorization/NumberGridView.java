package memorization;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NumberGridView extends GridView implements GameStateListener {

    private GridData data;
    private NumberGridAdapter adapter;
    private GameStateListener gameEventListener; // send events triggered by grid to this
    private GameStateDispatch dispatcher;

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onNextClick() {
        adapter.onHighlightNext();
    }

    public void setGameStateLifeCycleListener(GameStateDispatch dispatcher) {
        this.gameEventListener = dispatcher;
        this.dispatcher = dispatcher;
        dispatcher.subscribe(this);

        data = new GridData(500, 2);
        data.loadData();
        adapter = new NumberGridAdapter(getContext(), data);
        setAdapter(adapter);
        data.setAdapter(adapter);
        dispatcher.subscribe(adapter);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onMemorizationStart() {

    }

    @Override
    public void onTimeExpired() {

    }

    @Override
    public void onTransitionToRecall() {

    }
}
