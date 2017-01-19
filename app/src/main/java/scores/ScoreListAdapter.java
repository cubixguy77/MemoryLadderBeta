package scores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import speednumbers.mastersofmemory.challenges.MyApplication;
import speednumbers.mastersofmemory.com.presentation.R;

class ScoreListAdapter extends ArrayAdapter<Score>
{
    ScoreListAdapter(Context context, int resource, List<Score> items)
    {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.viewgroup_score_list_item, parent, false);
        }

        Score p = getItem(position);

        if (p != null) {
            TextView rank = (TextView) v.findViewById(R.id.rank);
            TextView score = (TextView) v.findViewById(R.id.score);
            TextView time = (TextView) v.findViewById(R.id.time);

            if (rank != null) {
                rank.setText(String.format("#%s", p.getRank()));
            }

            if (score != null) {
                score.setText(String.format("%s digits", p.getScore()));
            }

            if (time != null) {
                time.setText(String.format("%s seconds", p.getMemTime()));
            }

            LinearLayout layout = (LinearLayout) v.findViewById(R.id.list_item_layout);
            layout.setBackgroundColor(MyApplication.getAppContext().getResources().getColor(R.color.leaderboard_row_background_standard));
            layout.setLayoutParams(new android.widget.AbsListView.LayoutParams(android.widget.AbsListView.LayoutParams.MATCH_PARENT, MyApplication.getAppContext().getResources().getDimensionPixelSize(R.dimen.score_list_row_height)));
        }

        return v;
    }
}
