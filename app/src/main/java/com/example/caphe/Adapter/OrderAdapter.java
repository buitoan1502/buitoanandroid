package com.example.caphe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.caphe.Model.Drinks;
import com.example.caphe.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Drinks> {

    private LayoutInflater inflater;

    public OrderAdapter(Context context, List<Drinks> drinksList) {
        super(context, 0, drinksList);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sử dụng ViewHolder để tái sử dụng view
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_order, parent, false);
            holder = new ViewHolder();
            holder.tenDoUong = convertView.findViewById(R.id.tenDoUong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy đối tượng đồ uống hiện tại và hiển thị tên
        Drinks drink = getItem(position);
        if (drink != null) {
            holder.tenDoUong.setText(drink.getName());
        }

        return convertView;
    }

    // ViewHolder pattern để cải thiện hiệu suất
    private static class ViewHolder {
        TextView tenDoUong;
    }
}
