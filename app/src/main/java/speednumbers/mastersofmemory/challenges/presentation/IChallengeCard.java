package speednumbers.mastersofmemory.challenges.presentation;

public interface IChallengeCard extends IMemTime.View, IRecallTime.View {
    interface View {
        void play();
        void delete();
    }

    interface Presenter extends IMemTime.Presenter, IRecallTime.UserActions {
        void onPlayClick();
        void onExpand();
        void onContract();
    }

    interface Model extends IMemTime.Model, IRecallTime.Model {
    }

    interface ViewModel {
        void setExpanded(boolean isExpanded);
        boolean isExpanded();
        void setSortOrder(int sortOrder);
        int getSortOrder();
    }
}