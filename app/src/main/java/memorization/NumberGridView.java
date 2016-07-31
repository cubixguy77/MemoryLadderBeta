package memorization;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NumberGridView extends GridView implements GameStateLifeCycle {

    private GridData data;
    private NumberGridAdapter adapter;

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onNextClick() {
        adapter.onNextClick();
    }

    @Override
    public void onLoad() {
        data = new GridData(500, 2);
        data.loadData();
        adapter = new NumberGridAdapter(getContext(), data);
        setAdapter(adapter);
        adapter.onLoad();
    }

    @Override
    public void onMemorizationStart() {
        adapter.onMemorizationStart();
    }

    @Override
    public void onTimeExpired() {
        adapter.onTimeExpired();
        this.onTransitionToRecall();
    }

    @Override
    public void onTransitionToRecall() {
        adapter.onTransitionToRecall();
    }
}
