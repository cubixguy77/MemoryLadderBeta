package memorization;

public interface GridEvent {

    interface Memory {

        interface UserEvents {
            void onPrevMemoryCell();
            void onNextMemoryCell();
        }

        interface ViewEvents {
            void onDisablePrev();
            void onDisableNext();
            void onEnablePrev();
            void onEnableNext();
        }
    }

    interface Recall {

        interface UserEvents {
            void onNextRow();
            void onSubmitRow();
            void onSubmitAllRows();

            void onKeyPress(char digit);
            void onBackSpace();
        }

        interface ViewEvents {
            void onPrevRecallCell();
            void onNextRecallCell();
            void onRowFilled();
        }

    }

}
