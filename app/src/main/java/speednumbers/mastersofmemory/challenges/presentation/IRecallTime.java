package speednumbers.mastersofmemory.challenges.presentation;

import timer.TimeModel;

public interface IRecallTime {
    interface View {
        void recallTimerEnable();
        void recallTimerDisable();
        void showRecallTimePicker(TimeModel currentTimeModel);
        void hideTimePicker();
        void updateRecallTime(TimeModel newTimeModel);
    }

    interface UserActions {
        void onRecallTimerEnable();
        void onRecallTimerDisable();
        void onRecallTimeClick();
        void onRecallTimeUpdated(TimeModel newTimeModel);
    }

    interface Model {
        void enableRecallTimer(boolean isEnabled);
        void setRecallTime(TimeModel newTimeModel);
        TimeModel getRecallTime();
    }
}
