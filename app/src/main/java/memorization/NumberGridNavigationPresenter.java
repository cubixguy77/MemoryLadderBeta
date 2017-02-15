package memorization;

import memorization.SpeedNumbers.Navigation;

public class NumberGridNavigationPresenter implements Navigation.UserNavigationEvents {

    private SpeedNumbers.Grid grid;
    private NumberMemoryModel model;

    public NumberGridNavigationPresenter(SpeedNumbers.Grid grid, NumberMemoryModel model) {
        this.grid = grid;
        this.model = model;
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onPrevCell() {
        int highlightPosition = model.getHighlightPosition();
        GridData memoryData = model.getMemoryData();

        highlightPosition--;

        if (highlightPosition < 1) {
            highlightPosition = 1;
            Bus.getBus().onDisablePrev();
        }
        else if (memoryData.isRowMarker(highlightPosition)) {
            highlightPosition--;
        }
        else if (highlightPosition == memoryData.getNumCells() - 2) {
            Bus.getBus().onEnableNext();
        }

        model.setHighlightPosition(highlightPosition);
        grid.refresh();
    }

    @Override
    public void onNextCell() {
        int highlightPosition = model.getHighlightPosition();
        GridData memoryData = model.getMemoryData();

        highlightPosition++;

        /* Case: Arriving at the final grid cell */
        if (highlightPosition >= memoryData.getMaxValidHighlightPosition()) {
            highlightPosition = memoryData.getMaxValidHighlightPosition();
            Bus.getBus().onDisableNext();
        }

        /* Case: Carrying over to a new line */
        else if (memoryData.isRowMarker(highlightPosition)) {
            highlightPosition++;
            if (this.isLastRow(highlightPosition)) {
                grid.scrollDown();
            }
        }

        /* Case: Moved from first cell to second */
        else if (highlightPosition == 2) {
            Bus.getBus().onEnablePrev();
        }

        model.setHighlightPosition(highlightPosition);
        grid.refresh();
    }

    private boolean isLastRow(int position) {
        GridData memoryData = model.getMemoryData();
        return (memoryData.getRowNumber(position) >= memoryData.getRowNumber(grid.getLastVisiblePosition()) - 1);
    }
}