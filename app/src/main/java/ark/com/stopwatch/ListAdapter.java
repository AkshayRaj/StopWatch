package ark.com.stopwatch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/*

*/

public class ListAdapter extends BaseAdapter {
    private List<String> mLapList;
    LayoutInflater mInflater;
    private Context mContext;

    public ListAdapter(Context context, List<String> lapList) {
        mContext = context;
        mLapList = lapList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mLapList.size();
    }

    @Override
    public String getItem(int position) {
        return mLapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder categoryViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lap_list, parent, false);
            categoryViewHolder = new CategoryViewHolder(convertView);
            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        String name = getItem(position);
        categoryViewHolder.lapTime.setText(name);
        return convertView;

    }

    private class CategoryViewHolder {
        TextView lapTime;

        public CategoryViewHolder(View item) {
            lapTime = (TextView) item.findViewById(R.id.item_lap);
            lapTime.setTextColor(Color.BLACK);
        }
    }
}
