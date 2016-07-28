package memorization;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NumberGridAdapter extends BaseAdapter {

    private Context context;
    private GridData data;
    private int highlightPosition = 1;

    public NumberGridAdapter(Context context, GridData data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.getNumCells();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void onNextClick() {
        highlightPosition++;
        if (data.isRowMarker(highlightPosition)) {
            highlightPosition++;
        }

        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Cell view;
        if (convertView == null) {
            view = new Cell(context, null);
            view.setGravity(Gravity.CENTER);
        }
        else
            view = (Cell) convertView;

        boolean isViewRowMarker = data.isRowMarker(position);
        if (isViewRowMarker)
        {
            view.setAsRowMarker();
            view.setText(Integer.toString(data.getRowNumber(position)));
        }
        else
        {
            view.setAsDataCell();
            view.setText(data.getText(position));
        }

        if (isViewRowMarker == false) {
            if (position == highlightPosition) {
                view.setSelected(true);
            }
            else {
                view.setSelected(false);
            }
        }

        return view;
    }
}