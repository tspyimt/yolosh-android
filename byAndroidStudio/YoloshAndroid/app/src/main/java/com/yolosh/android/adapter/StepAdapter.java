package com.yolosh.android.adapter;//package com.yolosh.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yolosh.android.R;

import java.util.List;

/**
 * Adapter for GridView in collection page
 */
public class StepAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> dataList;

    public StepAdapter(List<String> dataset, Context ctx) {
        super(ctx, R.layout.layout_step_item, dataset);
        this.mContext = ctx;
        this.dataList = dataset;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        StepHolder holder = new StepHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_step_item, null);

            TextView textView1 = (TextView) view.findViewById(R.id.id_text1);
            TextView textView2 = (TextView) view.findViewById(R.id.id_text2);

            holder.textView1 = textView1;
            holder.textView2 = textView2;

            view.setTag(holder);
        } else {
            holder = (StepHolder) view.getTag();
        }
        holder.textView1.setText(position + 1 + "");
        holder.textView2.setText(dataList.get(position));

        return view;
    }

    /* *********************************
     * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/
    private static class StepHolder {
        public TextView textView1;
        public TextView textView2;
    }
}