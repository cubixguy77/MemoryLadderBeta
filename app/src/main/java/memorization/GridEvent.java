package memorization;

public interface GridEvent {

    interface Memory {
        interface NavigationEvents {
            void onPrevCell();
            void onNextCell();
        }

        interface Grid {
            void scrollDown();
            int getLastVisiblePosition();
            void refresh();
        }

        interface NavigationView {
            void onDisablePrev();
            void onDisableNext();
            void onEnablePrev();
            void onEnableNext();
        }
    }

    interface Recall {
        interface UserEvents {
            /* Action Buttons */
            void onNextRow();
            void onSubmitRow();
            void onSubmitAllRows();

            /* Number Keypad Buttons */
            void onKeyPress(char digit);
            void onBackSpace();
        }

        interface Grid {
            void scrollToTop();
            void scrollDown();
            void setNumGridColumns(int numGridColumns);
            int getCursorStart();
            int getCursorEnd();
            int getLastVisiblePosition();
            void refresh();
        }
    }
}
