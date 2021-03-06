package playChallenge.writtenNumbersChallenge.numberGrid;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import playChallenge.Bus;
import playChallenge.GameState;
import playChallenge.writtenNumbersChallenge.memorization.SpeedNumbers;
import playChallenge.writtenNumbersChallenge.memorization.MemoryCell;

class NumberGridMemoryAdapter extends NumberGridRecallAdapter {

    NumberGridMemoryAdapter(SpeedNumbers.Grid grid)
    {
        super(grid);
        new NumberGridNavigationPresenter(grid, getModel());
    }

    /* View Construction */

    private View getViewPreMemorization(View convertView, Context context) {
        MemoryCell view;
        if (convertView == null || !(convertView instanceof MemoryCell)) {
            view = new MemoryCell(context, null);
        }
        else
            view = (MemoryCell) convertView;

        view.setAsHiddenDataCell();
        return view;
    }

    private View getViewMemorization(int position, View convertView, Context context) {
        MemoryCell view;

        if (convertView == null || !(convertView instanceof MemoryCell)) {
            view = new MemoryCell(context, null);
            view.setGravity(Gravity.CENTER);
        }
        else
            view = (MemoryCell) convertView;

        view.setVisibility(View.VISIBLE);
        view.setAsDataCell();
        view.setText(getModel().getMemoryData().getText(position));

        if (position == getModel().getHighlightPosition()) {
            view.setSelected(true);
        }
        else {
            view.setSelected(false);
        }

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* The parent can handle the following views:
         * Row Marker
         * Empty Cell (leftovers on final row)
         * Keyboard Cell
         * Review Cell
         */
        View view = super.getView(position, convertView, parent);

        if (view != null) {
            return view;
        }

        if (Bus.gameState == GameState.PRE_MEMORIZATION) {
            return getViewPreMemorization(convertView, context);
        }

        if (Bus.gameState == GameState.MEMORIZATION) {
            return getViewMemorization(position, convertView, context);
        }

        return null;
    }

    /* End View Construction */
}