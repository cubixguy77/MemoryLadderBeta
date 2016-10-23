package speednumbers.mastersofmemory.challenges.presentation.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import injection.components.ChallengeListComponent;
import injection.components.DaggerChallengeListComponent;
import injection.modules.ChallengeListModule;
import memorization.NumberMemoryActivity;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.IAddChallengeListener;
import speednumbers.mastersofmemory.challenges.presentation.IChallengeSelectionListener;
import speednumbers.mastersofmemory.challenges.presentation.fragments.ChallengeListFragment;
import speednumbers.mastersofmemory.com.presentation.R;

public class ChallengeListActivity extends BaseActivity implements IChallengeSelectionListener {
    private ChallengeListComponent challengeListComponent;
    private long gameKey = 1;

    @BindView(R.id.tool_bar_challenge_list) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
        ButterKnife.bind(this);
        this.initializeInjector();

        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        this.getSupportActionBar().setTitle("Select a Challenge");

        if (savedInstanceState == null) {
            addFragment(R.id.ChallengeListScroller, new ChallengeListFragment());
        }
    }

    private void initializeInjector() {
        this.challengeListComponent = DaggerChallengeListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .challengeListModule(new ChallengeListModule(gameKey))
                .build();
    }

    @Override
    public ChallengeListComponent getComponent() {
        return challengeListComponent;
    }

    @Override
    public void onChallengeSelected(Challenge challenge) {
        Intent myIntent = new Intent(ChallengeListActivity.this, NumberMemoryActivity.class);
        myIntent.putExtra("ChallengeKey", challenge.getChallengeKey());
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_challenge_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_challenge) {
            showAddChallengeDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddChallengeDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("New Challenge");
        alert.setMessage("Number of Digits");

        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        edittext.setMaxLines(1);
        edittext.setMaxWidth(100);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(5);
        edittext.setFilters(FilterArray);

        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        alert.setView(edittext);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int value = Integer.parseInt(edittext.getText().toString());
                IAddChallengeListener challengeListFragment = (ChallengeListFragment) getSupportFragmentManager().findFragmentByTag("ChallengeListFragment");
                challengeListFragment.onChallengeAdd(value);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });

        alert.show();
    }
}
