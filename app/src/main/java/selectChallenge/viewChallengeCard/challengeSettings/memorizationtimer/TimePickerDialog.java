package selectChallenge.viewChallengeCard.challengeSettings.memorizationtimer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import speednumbers.mastersofmemory.com.presentation.R;
import playChallenge.timer.TimeFormat;

class TimePickerDialog extends AlertDialog.Builder implements OnClickListener {

	private int initialNumSeconds;
	private int numSeconds;

	private CheckBox noLimitCheckbox;

    private ViewGroup timePickerLayout;

	private TextView Hours;
	private TextView Minutes;
	private TextView Seconds;
	
	private Button HoursUp;
	private Button HoursDown;
	private Button MinutesUp;
	private Button MinutesDown;
	private Button SecondsUp;
	private Button SecondsDown;
	
	private int hours;
	private int minutes;
	private int seconds;
	
	final private static int UP = 0;
	final private static int DOWN = 1;
	
	final private static int MAX_HOUR = 5;
	
	private OnMyDialogResultTime mDialogResult; // the callback
	
	TimePickerDialog(Context context, int numSeconds, String labelText) {
		super(context);
		setTitle(labelText);
		setCancelable(true);

		this.initialNumSeconds = numSeconds;
		this.numSeconds = numSeconds == -1 ? 0 : numSeconds;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.dialog_time_picker, null);
		this.setView(dialoglayout);

		initTimeVariables();	
		initButtons(dialoglayout);
		initText(dialoglayout);

		setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				mDialogResult.finish(TimePickerDialog.this.initialNumSeconds);
			}
		});

		setNegativeButton(getContext().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mDialogResult.finish(TimePickerDialog.this.initialNumSeconds);
			}
		});

		setPositiveButton(getContext().getString(R.string.Save), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mDialogResult.finish(TimePickerDialog.this.numSeconds);
			}
		});
	}
	
	private void initTimeVariables() {
		hours = TimeFormat.getHours(numSeconds);
		minutes = TimeFormat.getMinutes(numSeconds);
		seconds = TimeFormat.getSeconds(numSeconds);
	}
	
	interface OnMyDialogResultTime{
	       void finish(int result);
	}
	
	void setDialogResult(OnMyDialogResultTime dialogResult){
        mDialogResult = dialogResult;
    }
	
	private void initText(View layout) {
		Hours = (TextView) layout.findViewById(R.id.Hours);
		Minutes = (TextView) layout.findViewById(R.id.Minutes);
		Seconds = (TextView) layout.findViewById(R.id.Seconds);
		
		refreshTimeDisplay();

        if (numSeconds == 0) {
            noLimitCheckbox.setChecked(true);
        }
	}
	
	private void initButtons(View layout) {
        noLimitCheckbox = (CheckBox) layout.findViewById(R.id.noLimitCheckbox);
        noLimitCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timePickerLayout.setVisibility(View.GONE);
                    numSeconds = 0; // 0 seconds signifies no time limit
                }
                else {
                    timePickerLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        timePickerLayout = (ViewGroup) layout.findViewById(R.id.timePickerLayout);

		HoursUp = (Button) layout.findViewById(R.id.HoursUp);
		HoursUp.setOnClickListener(this);
		HoursDown = (Button) layout.findViewById(R.id.HoursDown);
		HoursDown.setOnClickListener(this);
		
		MinutesUp = (Button) layout.findViewById(R.id.MinutesUp);
		MinutesUp.setOnClickListener(this);
		MinutesDown = (Button) layout.findViewById(R.id.MinutesDown);
		MinutesDown.setOnClickListener(this);
		
		SecondsUp = (Button) layout.findViewById(R.id.SecondsUp);
		SecondsUp.setOnClickListener(this);
		SecondsDown = (Button) layout.findViewById(R.id.SecondsDown);
		SecondsDown.setOnClickListener(this);		
	}	
	
	private void refreshTimeDisplay() {
		String hours_string;
		String minutes_string;
		String seconds_string;
		
		hours_string = "0" + hours;
		
		if (minutes < 10)
			minutes_string = "0" + minutes;
		else
			minutes_string = Integer.toString(minutes);
		
		if (seconds < 10)
			seconds_string = "0" + seconds;
		else
			seconds_string = Integer.toString(seconds);
		
		Hours.setText(hours_string);
		Minutes.setText(minutes_string);
		Seconds.setText(seconds_string);

		numSeconds = (hours * 3600) + (minutes * 60) + seconds;
	}
	
	private void updateHours(int button) {
		if (button == UP && hours < MAX_HOUR) 
			hours++;		
		else if (button == DOWN && hours > 0)
			hours--;

		refreshTimeDisplay();
	}
	
	private void updateMinutes(int button) {
		if (button == UP && minutes < 59) 
			minutes++;
		else if (button == UP && minutes == 59)
			minutes = 0;
		else if (button == DOWN && minutes > 0)
			minutes--;
		else if (button == DOWN && minutes == 0)
			minutes = 59;

		refreshTimeDisplay();
	}
	
	private void updateSeconds(int button) {
		if (button == UP && seconds < 59) 
			seconds++;	
		else if (button == UP && seconds == 59)
			seconds = 0;
		else if (button == DOWN && seconds > 0)
			seconds--;
		else if (button == DOWN && seconds == 0)
			seconds = 59;
		refreshTimeDisplay();
	}
		
	@Override
	public void onClick(View v) {
		if (v == HoursUp)
			updateHours(UP);
		else if (v == HoursDown)
			updateHours(DOWN);
		else if (v == MinutesUp)
			updateMinutes(UP);
		else if (v == MinutesDown)
			updateMinutes(DOWN);
		else if (v == SecondsUp)
			updateSeconds(UP);
		else if (v == SecondsDown)
			updateSeconds(DOWN);
	}	
}