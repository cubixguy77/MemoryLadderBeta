package speednumbers.mastersofmemory.com.presentation;

public class ChallengeCardNumbersPresenter extends ChallengeCardPresenter implements IChallengeCardNumbers.Presenter {
    IChallengeCardNumbers.View view;
    IChallengeCardNumbers.Model model;

    public ChallengeCardNumbersPresenter(IChallengeCardNumbers.View view, IChallengeCardNumbers.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSelectDigitsPerGroup(int digitsPerGroup) {
        if (digitsPerGroup == model.getDigitsPerGroup())
            return;

        //view.deselectDigitsPerGroup(model.getDigitsPerGroup());
        model.setDigitsPerGroup(digitsPerGroup);
        //view.selectDigitsPerGroup(model.getDigitsPerGroup());
    }
}
