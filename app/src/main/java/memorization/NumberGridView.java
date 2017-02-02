package memorization;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.GridView;

import gridAdapter.NumberGridAdapter;
import recall.RecallCell;

public class NumberGridView extends GridView {

    private int scrollDistance;
    private final int scrollDuration = 500;

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        NumberGridAdapter adapter = new NumberGridAdapter(getContext());
        adapter.setGridView(this);
        setAdapter(adapter);
    }

    public void subscribe() {
        Bus.getBus().subscribe(this);
    }

    public int getCursorStart(int position) {
        RecallCell recallCell = (RecallCell) getChildAt(getAdjustedPosition(position));
        return recallCell == null ? 0 : recallCell.getSelectionStart();
    }

    public int getCursorEnd(int position) {
        RecallCell recallCell = (RecallCell) getChildAt(getAdjustedPosition(position));
        return recallCell == null ? 0 : recallCell.getSelectionEnd();
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
            for (int i = 0; i <= getLastVisiblePosition() - getFirstVisiblePosition(); i++) {
                if (getChildAt(i) != null) {
                    scrollDistance = getVerticalSpacing() + getChildAt(i).getHeight(); //scrollDistance = getVerticalSpacing() + getChildAt(getFirstVisiblePosition()).getHeight();
                    return scrollDistance;
                }
            }

            return 0;
        }

        return scrollDistance;
    }

    private int getAdjustedPosition(int position) {
        return position - getFirstVisiblePosition();
    }
}
