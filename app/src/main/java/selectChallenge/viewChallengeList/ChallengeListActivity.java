package selectChallenge.viewChallengeList;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import injection.components.ChallengeListComponent;
import injection.components.DaggerChallengeListComponent;
import injection.modules.ChallengeListModule;
import memorization.NumberMemoryActivity;
import selectChallenge.viewChallengeCard.Challenge;
import selectChallenge.viewChallengeCard.NumberChallenge;
import selectChallenge.addChallenge.IAddChallengeListener;
import speednumbers.mastersofmemory.com.presentation.R;

public class ChallengeListActivity extends BaseActivity implements IChallengeSelectionListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private ChallengeListComponent challengeListComponent;

    @BindView(R.id.tool_bar_challenge_list) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
        ButterKnife.bind(this);
        this.initializeInjector();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null)
            return;

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        this.getSupportActionBar().setTitle(getString(R.string.selectChallenge));

        if (savedInstanceState == null) {
            addFragment(R.id.ChallengeListScroller, new ChallengeListFragment());
        }
    }

    private void initializeInjector() {
        long gameKey = 1;
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
        recordAnalyticsEventChallengeSelected(challenge);
        startActivity(myIntent);
    }

    private void recordAnalyticsEventChallengeSelected(Challenge challenge) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, NumberChallenge.getNumDigitsSetting(challenge).getValue() + " Digits");
        params.putString(FirebaseAnalytics.Param.ITEM_ID, "Digits Per Group: " + NumberChallenge.getDigitsPerGroupSetting(challenge).getValue());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_challenge_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_add_challenge:
                showAddChallengeDialog();
                return true;

            case R.id.action_send_feedback:
                getUserFeedback();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddChallengeDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getResources().getString(R.string.newChallenge));
        alert.setMessage(getResources().getString(R.string.numberOfDigits));

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
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        alert.setView(edittext);

        alert.setPositiveButton(getString(R.string.Add), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = edittext.getText().toString();
                if (text.length() == 0)
                    return;

                int value = Integer.parseInt(text);
                if (value > 0) {
                    IAddChallengeListener challengeListFragment = (ChallengeListFragment) getSupportFragmentManager().findFragmentByTag("ChallengeListFragment");
                    challengeListFragment.onChallengeAdd(value);
                }
            }
        });

        alert.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });

        alert.show();
    }

    private void getUserFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "mastersofmemorycontact@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_EmailSubjectText));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(getString(R.string.feedback_EmailMessageBodyText)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "mastersofmemorycontact@gmail.com" });
            Email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_EmailSubjectText));
            Email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(getString(R.string.feedback_EmailMessageBodyText)));
            startActivity(Intent.createChooser(Email, getString(R.string.send_feedback)));
        }
    }
}
