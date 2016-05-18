package speednumbers.mastersofmemory.com.presentation;


public interface IChallengeSettingsModel extends IMemTime.Model, IRecallTime.Model {

    interface ViewModel {
        void setExpanded(boolean isExpanded);
        boolean isExpanded();
        void setSortOrder(int sortOrder);
        int getSortOrder();
    }
}


