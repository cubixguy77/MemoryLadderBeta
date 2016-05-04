package speednumbers.mastersofmemory.com.memoryladder_speednumbers;

public interface IMemTime {
    interface View {
        void memTimerEnable();
        void memTimerDisable();
        void showMemTimePicker(TimeModel currentTimeModel);
        void hideTimePicker();
        void updateMemTime(TimeModel newTimeModel);
    }

    interface Presenter {
        void onMemTimerEnable();
        void onMemTimerDisable();
        void onMemTimeClick();
        void onMemTimeUpdated(TimeModel newTimeModel);
    }

    interface Model {
        void enableMemTimer(boolean isEnabled);
        void setMemTime(TimeModel newTimeModel);
        TimeModel getMemTime();
    }
}
