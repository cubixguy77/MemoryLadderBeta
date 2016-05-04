package speednumbers.mastersofmemory.com.memoryladder_speednumbers;

import speednumbers.mastersofmemory.com.memoryladder_speednumbers.TimeModel;

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
