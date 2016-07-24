package speednumbers.mastersofmemory.challenges.domain.model;

public class ChallengeCardDataModel {
    private final int numDigits;

    public ChallengeCardDataModel(int numDigits) {
        this.numDigits = numDigits;
    }

    public int getNumDigits() {
        return numDigits;
    }
}
