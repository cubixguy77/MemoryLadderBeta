package memorization;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.GridView;

import recall.RecallCell;

public class NumberGridView extends GridView {

    private int scrollDistance;
    private final int scrollDuration = 500;

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGameStateLifeCycleListener() {
        Bus.getBus().subscribe(this);
        NumberGridAdapter adapter = new NumberGridAdapter(getContext());
        adapter.setGridView(this);
        setAdapter(adapter);
    }

    public int getCursorStart(int position) {
        return ((RecallCell) getChildAt(position)).getSelectionStart();
    }

    public int getCursorEnd(int position) {
        return ((RecallCell) getChildAt(position)).getSelectionEnd();
    }

    public void scrollGrid() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                smoothScrollBy(getScrollDistance(), scrollDuration);
            }
        });
    }

    public void scrollToTop() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                smoothScrollToPosition(1);
                setSelection(1);
            }
        });

    }

    private int getScrollDistance() {
        if (scrollDistance <= 0) {
            scrollDistance = getVerticalSpacing() + getChildAt(getFirstVisiblePosition()).getHeight();
        }

        return scrollDistance;
    }
}
