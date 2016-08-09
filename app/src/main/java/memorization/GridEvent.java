package memorization;

public interface GridEvent {

    interface Memory {

        interface UserEvents {
            void onPrev();
            void onNext();
        }

        interface ViewEvents {
            void onDisablePrev();
            void onDisableNext();
            void onEnablePrev();
            void onEnableNext();
        }
    }

    interface Recall {
        void onNextRow();
        void onSubmitRow();
    }

}
