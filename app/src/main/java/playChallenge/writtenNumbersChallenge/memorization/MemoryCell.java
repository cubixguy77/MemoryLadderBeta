package playChallenge.writtenNumbersChallenge.memorization;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;

import speednumbers.mastersofmemory.com.presentation.R;

public class MemoryCell extends android.support.v7.widget.AppCompatTextView {

    private boolean isSelected;

    public MemoryCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(30);
        setGravity(Gravity.CENTER);

        int sizeInPixels = (int) getResources().getDimension(R.dimen.number_grid_cellSize);
        setLayoutParams(new AbsListView.LayoutParams(sizeInPixels, sizeInPixels));

        int textSize = (int) getResources().getDimension(R.dimen.memory_cell_textSize);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        isSelected = false;
    }

    public void setAsRowMarker() {
        setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        setBackgroundResource(0);
        setVisibility(View.VISIBLE);
    }

    public void setAsDataCell() {
        setTextColor(Color.BLACK);
        setVisibility(View.VISIBLE);
    }

    public void setAsHiddenDataCell() {
        setAsDataCell();
        setText("__");
    }

    public void setSelected(boolean isSelected) {

        this.isSelected = isSelected;

        if (this.isSelected) {
            setTextColor(Color.WHITE);
            setBackgroundResource(R.drawable.shape_rounded_corners);
        }
        else {
            setTextColor(Color.BLACK);
            setBackgroundResource(0);
        }

    }
}
