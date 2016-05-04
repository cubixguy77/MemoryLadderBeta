package speednumbers.mastersofmemory.com.memoryladder_speednumbers;

public interface IChallengeCardNumbers {
    interface View extends IChallengeCard.View {
        void selectDigitsPerGroup(int digitsPerGroup);
        void deselectDigitsPerGroup(int digitsPerGroup);
    }

    interface Presenter extends IChallengeCard.Presenter {
        void onSelectDigitsPerGroup(int digitsPerGroup);
    }

    interface Model extends IChallengeCard.Model, IChallengeCard.ViewModel {
        void setNumDigits(int numDigits);
        int getNumDigits();
        void setDigitsPerLine(int numDigitsPerLine);
        int getDigitsPerLine();
        void setNumLines(int numLines);
        int getNumLines();
        void setDigitsPerGroup(int digitsPerGroup);
        int getDigitsPerGroup();
    }
}
