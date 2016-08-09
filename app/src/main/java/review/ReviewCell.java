package review;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.TextView;

public class ReviewCell extends TextView {

    public ReviewCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(30);
        setLines(2);
        setGravity(Gravity.CENTER);
        setLayoutParams(new AbsListView.LayoutParams(190, 190));
    }

    public void setModel(String memoryString, String recallString) {
        boolean correct = memoryString.equals(recallString);

        if (correct) {
            setText(memoryString + System.getProperty("line.separator"));
        }
        else {
            String s = memoryString + System.getProperty("line.separator") + recallString;
            setText(s);
        }
    }
}
