package scores;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import speednumbers.mastersofmemory.com.presentation.R;
import timer.TimeUtils;

class ScoreListAdapter extends ArrayAdapter<Score>
{
    private Context context;

    ScoreListAdapter(Context context, int resource, List<Score> scores)
    {
        super(context, resource, scores);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.viewgroup_score_list_item, parent, false);
        }

        Score p = getItem(position);

        if (p != null) {
            TextView rank = (TextView) v.findViewById(R.id.rank);
            TextView score = (TextView) v.findViewById(R.id.score);
            TextView time = (TextView) v.findViewById(R.id.time);

            /* Create header row */
            if (position == 0) {
                if (rank != null) {
                    rank.setText(R.string.scoreList_Rank);
                    rank.setTypeface(null, Typeface.BOLD);
                }

                if (score != null) {
                    score.setText(R.string.scoreList_Score);
                    score.setTypeface(null, Typeface.BOLD);
                }

                if (time != null) {
                    time.setText(R.string.scoreList_Time);
                    time.setTypeface(null, Typeface.BOLD);
                }
            }

            /* Create standard list row */
            else {
                if (rank != null) {
                    rank.setText(String.format("%s", p.getRank()));
                    rank.setTypeface(null, Typeface.NORMAL);
                }

                if (score != null) {
                    score.setText(String.format("%s digits", p.getScore()));
                    score.setTypeface(null, Typeface.NORMAL);
                }

                if (time != null) {
                    time.setText(TimeUtils.getMemorizationTimeText(p.getMemTime()));
                    time.setTypeface(null, Typeface.NORMAL);
                }
            }
        }

        return v;
    }
}
