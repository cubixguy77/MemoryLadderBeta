package memorization;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import recall.RecallCell;

public class NumberGridAdapter extends BaseAdapter implements GameStateLifeCycle {

    private Context context;
    private GridData data;
    private int highlightPosition = 1;
    private GameState gameState;
    private GridData recallData;

    public NumberGridAdapter(Context context, GridData data)
    {
        this.context = context;
        this.data = data;
        this.gameState = GameState.MEMORIZATION;
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

    private View getRowMarkerView(int position, View convertView) {
        Cell view;
        if (convertView == null) {
            view = new Cell(context, null);
        }
        else if (convertView instanceof RecallCell)
            view = new Cell(context, null);
        else
            view = (Cell) convertView;

        view.setAsRowMarker();
        view.setText(Integer.toString(data.getRowNumber(position)));

        return view;
    }

    public View getViewMemorization(int position, View convertView) {
        Cell view;
        if (convertView == null) {
            view = new Cell(context, null);
            view.setGravity(Gravity.CENTER);
        }
        else
            view = (Cell) convertView;

        view.setAsDataCell();
        view.setText(data.getText(position));

        if (position == highlightPosition) {
            view.setSelected(true);
        }
        else {
            view.setSelected(false);
        }

        return view;
    }

    public View getViewRecall(int position, View convertView) {
        RecallCell view;
        if (convertView == null) {
            view = new RecallCell(context, null);
        }
        else if (convertView instanceof Cell)
            view = new RecallCell(context, null);
        else
            view = (RecallCell) convertView;

        view.setPosition(position);
        view.setText(recallData.getText(position));
        view.addRecallTextWatcher(recallData);

        System.out.println("set view pos: " + position + "  to value " + recallData.getText(position));

        return view;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isViewRowMarker = data.isRowMarker(position);
        if (isViewRowMarker)
        {
            return getRowMarkerView(position, convertView);
        }

        if (gameState == GameState.PRE_MEMORIZATION) {
            return getViewPreMemorization(convertView);
        }

        if (gameState == GameState.MEMORIZATION) {
            return getViewMemorization(position, convertView);
        }

        if (gameState == GameState.RECALL) {
            return getViewRecall(position, convertView);
        }

        return null;
    }

    private View getViewPreMemorization(View convertView) {
        Cell view;
        if (convertView == null) {
            view = new Cell(context, null);
        }
        else
            view = (Cell) convertView;

        view.setAsHiddenDataCell();
        return view;
    }




    @Override
    public void onLoad() {
        gameState = GameState.PRE_MEMORIZATION;
        notifyDataSetChanged();
    }

    @Override
    public void onMemorizationStart() {
        gameState = GameState.MEMORIZATION;
        notifyDataSetChanged();
    }

    @Override
    public void onTimeExpired() {

    }

    @Override
    public void onTransitionToRecall() {
        System.out.println("Adapter: Transition to RECALL");
        gameState = GameState.RECALL;
        recallData = new GridData(500, 2);
        notifyDataSetChanged();
    }
}