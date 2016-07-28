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
        ((NumberGridAdapter) getAdapter()).onNextClick();
    }

    @Override
    public void onLoad() {
        data = new GridData(500, 2);
        adapter = new NumberGridAdapter(getContext(), data);
        setAdapter(adapter);
    }

    @Override
    public void onMemorizationStart() {
        adapter.showData();
    }

    @Override
    public void onTimeExpired() {

    }

    @Override
    public void onTransitionToRecall() {

    }
}
