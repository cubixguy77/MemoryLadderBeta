package memorization;

public interface GridEvent {

    interface Grid {
        void scrollToTop();
        void scrollDown();
        int getCursorStart(int position);
        int getCursorEnd(int position);
        int getLastVisiblePosition();
        void setNumColumns(int numColumns);
        void refresh();
    }

    interface Navigation {
        interface UserNavigationEvents {
            void onPrevCell();
            void onNextCell();
        }

        interface NavigationView {
            void onDisablePrev();
            void onDisableNext();
            void onEnablePrev();
            void onEnableNext();
        }
    }

    interface Keyboard {
        interface UserKeyboardActions {
            /* Action Buttons */
            void onNextRow();
            void onSubmitRow();
            void onSubmitAllRows();

            /* Number Keypad Buttons */
            void onKeyPress(char digit);
            void onBackSpace();
        }
    }
}
