package speednumbers.mastersofmemory.com.presentation;

import android.app.Fragment;
import android.widget.Toast;

import speednumbers.mastersofmemory.com.presentation.injection.HasComponent;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {
  /**
   * Shows a {@link Toast} message.
   *
   * @param message An string representing a message to be shown.
   */
  protected void showToastMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }
}