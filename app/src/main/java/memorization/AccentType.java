package memorization;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import recall.RecallEntryView;

@IntDef(flag=true, value={RecallEntryView.ACCENT_NONE, RecallEntryView.ACCENT_ALL, RecallEntryView.ACCENT_CHARACTER})
@Retention(RetentionPolicy.SOURCE)
public @interface AccentType {}