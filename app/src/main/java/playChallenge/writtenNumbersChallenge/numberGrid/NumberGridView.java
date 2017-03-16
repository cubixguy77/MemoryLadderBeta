package playChallenge.writtenNumbersChallenge.numberGrid;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.GridView;

import playChallenge.writtenNumbersChallenge.memorization.SpeedNumbers;
import playChallenge.writtenNumbersChallenge.recall.RecallCell;

public class NumberGridView extends GridView implements SpeedNumbers.Grid {

    private int scrollDistance;
    private final int scrollDuration = 500;
    private NumberGridMemoryAdapter adapter;

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    public void init() {
        adapter = new NumberGridMemoryAdapter(this);
        setAdapter(adapter);
    }

    @Override
    public int getCursorStart(int position) {
        RecallCell recallCell = (RecallCell) getChildAt(getAdjustedPosition(position));
        return recallCell == null ? 0 : recallCell.getSelectionStart();
    }

    @Override
    public int getCursorEnd(int position) {
        RecallCell recallCell = (RecallCell) getChildAt(getAdjustedPosition(position));
        return recallCell == null ? 0 : recallCell.getSelectionEnd();
    }

    @Override
    public void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                smoothScrollBy(getScrollDistance(), scrollDuration);
            }
        });
    }

    @Override
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
        /* Lazy load */
        if (scrollDistance <= 0) {
            for (int i = 0; i <= getLastVisiblePosition() - getFirstVisiblePosition(); i++) {
                if (getChildAt(i) != null) {
                    scrollDistance = getVerticalSpacing() + getChildAt(i).getHeight();
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