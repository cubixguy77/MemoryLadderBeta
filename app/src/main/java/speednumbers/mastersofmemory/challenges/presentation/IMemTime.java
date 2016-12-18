package speednumbers.mastersofmemory.challenges.presentation;

import timer.TimeUtils;

public interface IMemTime {
    interface View {
        void memTimerEnable();
        void memTimerDisable();
        void showMemTimePicker(TimeUtils currentTimeModel);
        void hideTimePicker();
        void updateMemTime(TimeUtils newTimeModel);
    }

    interface Presenter {
        void onMemTimerEnable();
        void onMemTimerDisable();
        void onMemTimeClick();
        void onMemTimeUpdated(TimeUtils newTimeModel);
    }

    interface Model {
        void enableMemTimer(boolean isEnabled);
        void setMemTime(TimeUtils newTimeModel);
        TimeUtils getMemTime();
    }
}
