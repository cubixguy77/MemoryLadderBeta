package toolbar;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import memorization.Bus;
import memorization.GameStateListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.R;

public class ToolbarView extends android.support.v7.widget.Toolbar implements GameStateListener {

    private MenuItem submitMemButton;
    private MenuItem submitRecallButton;
    private MenuItem submitReplayButton;

    public ToolbarView(Context context) {
        super(context);
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(AppCompatActivity context) {
        Bus.getBus().subscribe(this);

        context.setSupportActionBar(this);
        ActionBar supportActionBar = context.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_action_close);
            supportActionBar.setTitle("Memorization");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu, Context context) {
        ((AppCompatActivity) context).getMenuInflater().inflate(R.menu.menu_memorization, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        submitMemButton = menu.findItem(R.id.action_submit_memorization);
        submitRecallButton = menu.findItem(R.id.action_submit_recall);
        submitReplayButton = menu.findItem(R.id.action_replay);
        setRecallIcon(false);
        return true;
    }

    private void setRecallIcon(boolean enabled) {
        if (enabled) {
            submitMemButton.setEnabled(true);
            submitMemButton.getIcon().setAlpha(255);
        }
        else {
            submitMemButton.setEnabled(false);
            submitMemButton.getIcon().setAlpha(130);
        }
    }


    @Override
    public void onLoad(Challenge challenge) {

    }

    @Override
    public void onMemorizationStart() {
        setTitle("Memorization");
        setRecallIcon(true);
    }

    @Override
    public void onTimeExpired() {

    }

    @Override
    public void onTransitionToRecall() {
        submitMemButton.setVisible(false);
        submitRecallButton.setVisible(true);
        setTitle("Recall");
    }

    @Override
    public void onRecallComplete(Result result) {
        setTitle("Results - " + result.getNumDigitsAttempted() + " Digits");
        submitRecallButton.setVisible(false);
        submitReplayButton.setVisible(true);
    }

    @Override
    public void onPlayAgain() {

    }
}
