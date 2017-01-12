package toolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import memorization.Bus;
import memorization.GameStateListener;
import memorization.SaveInstanceStateListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.R;

public class ToolbarView extends android.support.v7.widget.Toolbar implements GameStateListener, SaveInstanceStateListener {

    private ActionBar supportActionBar;

    private MenuItem submitMemButton;
    private MenuItem submitRecallButton;
    private MenuItem submitReplayButton;

    private boolean restoreTitle = true;

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
        context.setSupportActionBar(this);
        this.supportActionBar = context.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_action_close);
        }
    }

    public void subscribe() {
        Bus.getBus().subscribe(this);
    }

    public boolean onCreateOptionsMenu(Menu menu, Context context) {
        System.out.println("onCreateOptionsMenu");
        ((AppCompatActivity) context).getMenuInflater().inflate(R.menu.menu_memorization, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("onPrepareOptionsMenu");
        submitMemButton = menu.findItem(R.id.action_submit_memorization);
        submitRecallButton = menu.findItem(R.id.action_submit_recall);
        submitReplayButton = menu.findItem(R.id.action_replay);

        /* This will be set to false when the device is rotated
         * onRestoreInstanceState will restore the saved title for us instead in that case
         */
        if (restoreTitle) {
            refreshToolbarTitleText();
        }
        refreshToolbarIcon();
        return true;
    }

    private void refreshToolbarTitleText() {
        switch (Bus.gameState) {
            case PRE_MEMORIZATION: supportActionBar.setTitle("Memorization"); break;
            case MEMORIZATION: supportActionBar.setTitle("Memorization"); break;
            case RECALL: supportActionBar.setTitle("Recall"); break;
            case REVIEW: supportActionBar.setTitle("Results - idk how to restore digits yet"); break;
            default: supportActionBar.setTitle("Error Restoring Toolbar");
        }
    }

    private void refreshToolbarIcon() {
        switch (Bus.gameState) {
            case PRE_MEMORIZATION:
                submitMemButton.setVisible(true);
                submitMemButton.setEnabled(false);
                submitMemButton.getIcon().setAlpha(130);
                submitRecallButton.setVisible(false);
                submitReplayButton.setVisible(false);
                break;
            case MEMORIZATION:
                submitMemButton.setVisible(true);
                submitMemButton.setEnabled(true);
                submitMemButton.getIcon().setAlpha(255);
                submitRecallButton.setVisible(false);
                submitReplayButton.setVisible(false);
                break;
            case RECALL:
                submitRecallButton.setVisible(true);
                submitMemButton.setVisible(false);
                submitReplayButton.setVisible(true);
                break;
            case REVIEW:
                submitReplayButton.setVisible(true);
                submitMemButton.setVisible(false);
                submitRecallButton.setVisible(false);
                break;
            default: break;
        }
    }


    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            System.out.println("onLoad(Toolbar): with restore");
            supportActionBar.setTitle(savedInstanceState.getString("Toolbar.Title"));
            restoreTitle = false;
            //supportActionBar.invalidateOptionsMenu();
        }
    }

    @Override
    public void onMemorizationStart() {
        setTitle("Memorization");
        refreshToolbarIcon();
    }

    @Override
    public void onTimeExpired() {

    }

    @Override
    public void onTransitionToRecall() {
        setTitle("Recall");
        refreshToolbarIcon();
    }

    @Override
    public void onRecallComplete(Result result) {
        setTitle("Results - " + result.getNumDigitsTotal() + " Digits");
        refreshToolbarIcon();
    }

    @Override
    public void onPlayAgain() {

    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("Toolbar.Title", getTitle().toString());
    }
}
