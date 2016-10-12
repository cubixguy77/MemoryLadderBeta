package review;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.TextView;

public class ReviewCell extends TextView {

    public ReviewCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(20);
        setLines(2);
        setGravity(Gravity.CENTER);
        setLayoutParams(new AbsListView.LayoutParams(190, 190));
    }

    public void setModel(String memoryString, String recallString) {
        boolean correct = memoryString.equals(recallString);

        if (correct) {
            SimpleSpanBuilder ssb = new SimpleSpanBuilder();
            ssb.append(recallString,new ForegroundColorSpan(Color.GREEN));
            ssb.append(System.getProperty("line.separator"));
            ssb.append(memoryString);
            setText(ssb.build());
        }
        else {
            /*
             * -1: Empty
             *  0: Wrong
             *  1: Right
             */
            int[] result = new int[memoryString.length()];

            for (int i=0; i<memoryString.length(); i++) {
                if (i < recallString.length()) // Recall character present
                    result[i] = memoryString.charAt(i) == recallString.charAt(i) ? 1 : 0;
                else
                    result[i] = -1;
            }

            SimpleSpanBuilder ssb = new SimpleSpanBuilder();

            for (int i=0; i<memoryString.length(); i++) {
                switch (result[i]) {
                    case -1: ssb.append("—",new ForegroundColorSpan(Color.RED)); break;
                    case  0: ssb.append(Character.toString(recallString.charAt(i)),new ForegroundColorSpan(Color.RED), new StrikethroughSpan()); break;
                    case  1: ssb.append(Character.toString(recallString.charAt(i)),new ForegroundColorSpan(Color.GREEN)); break;
                }
            }

            ssb.append(System.getProperty("line.separator"));
            ssb.append(memoryString);

            /*
            for (int i=0; i<memoryString.length(); i++) {
                switch (result[i]) {
                    case -1: ssb.append(Character.toString(memoryString.charAt(i))); break;
                    case  0: ssb.append(Character.toString(memoryString.charAt(i))); break;
                    case  1: ssb.append("\u0020"); break;
                }
            }
            */

            setText(ssb.build());
        }
    }
}
