package speednumbers.mastersofmemory.challenges.domain.model;


import speednumbers.mastersofmemory.challenges.presentation.IMemTime;
import speednumbers.mastersofmemory.challenges.presentation.IRecallTime;

public interface IChallengeSettingsModel extends IMemTime.Model, IRecallTime.Model {

    interface ViewModel {
        void setExpanded(boolean isExpanded);
        boolean isExpanded();
        void setSortOrder(int sortOrder);
        int getSortOrder();
    }
}


