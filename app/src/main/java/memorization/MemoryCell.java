package memorization;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.TextView;

import speednumbers.mastersofmemory.com.presentation.R;

public class MemoryCell extends TextView {

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
        setTextColor(Color.RED);
        setBackgroundResource(0);
    }

    public void setAsDataCell() {
        setTextColor(Color.BLACK);
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
