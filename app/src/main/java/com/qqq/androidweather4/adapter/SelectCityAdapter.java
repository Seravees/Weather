package com.qqq.androidweather4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qqq.androidweather4.R;

import java.util.List;
import java.util.Map;

/**
 * Created by qqq on 2017-01-03.
 */

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.SelectCityViewHolder> {
    List<Map<String, Object>> data;
    Context context;
    LayoutInflater inflater;

    public SelectCityAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SelectCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectCityViewHolder holder = new SelectCityViewHolder(inflater.inflate(R.layout.item_select_city, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final SelectCityViewHolder holder, int position) {
        holder.cityName.setText((String) data.get(position).get("cityName"));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SelectCityViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;

        public SelectCityViewHolder(View view) {
            super(view);
            cityName = (TextView) view.findViewById(R.id.select_city_recycler_view_city_name);
        }
    }
}
