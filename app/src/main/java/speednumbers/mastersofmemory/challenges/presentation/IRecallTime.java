package speednumbers.mastersofmemory.challenges.presentation;

import timer.TimeUtils;

public interface IRecallTime {
    interface View {
        void recallTimerEnable();
        void recallTimerDisable();
        void showRecallTimePicker(TimeUtils currentTimeModel);
        void hideTimePicker();
        void updateRecallTime(TimeUtils newTimeModel);
    }

    interface UserActions {
        void onRecallTimerEnable();
        void onRecallTimerDisable();
        void onRecallTimeClick();
        void onRecallTimeUpdated(TimeUtils newTimeModel);
    }

    interface Model {
        void enableRecallTimer(boolean isEnabled);
        void setRecallTime(TimeUtils newTimeModel);
        TimeUtils getRecallTime();
    }
}
