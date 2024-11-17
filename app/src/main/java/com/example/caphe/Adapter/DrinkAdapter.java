package com.example.caphe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caphe.Menu;
import com.example.caphe.Model.Drinks;
import com.example.caphe.Order;
import com.example.caphe.R;
import com.example.caphe.SettingDrink;
import com.example.caphe.SettingMenu;

import java.io.InputStream;
import java.util.List;

public class DrinkAdapter extends BaseAdapter {

    private Context context;
    private List<Drinks> drinkList;

    public DrinkAdapter(Context context, List<Drinks> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemdrink, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.drinkImage = convertView.findViewById(R.id.drinkImage1);
            viewHolder.drinkName = convertView.findViewById(R.id.drinkName);
            viewHolder.drinkPrice = convertView.findViewById(R.id.drinkPrice);
            viewHolder.btnAdd = convertView.findViewById(R.id.btnAdd);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu cho đồ uống
        Drinks drink = drinkList.get(position);
        viewHolder.drinkName.setText(drink.getName());
        viewHolder.drinkPrice.setText(String.valueOf(drink.getPrice()));


        // Cập nhật hình ảnh cho đồ uống bằng AsyncTask (tải ảnh từ URL)
        String imageUrl = drink.getImageResource();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new ImageLoadTask(imageUrl, viewHolder.drinkImage).execute();
        } else {
            viewHolder.drinkImage.setImageResource(R.drawable.macdinh); // Ảnh mặc định nếu không có URL
        }

        // Xử lý sự kiện khi nhấn vào nút "Thêm"
        viewHolder.btnAdd.setOnClickListener(v -> {
            if (context instanceof Menu) {
                Intent intent = new Intent(context, Order.class);
                intent.putExtra("drink_id", drink.getId());
                context.startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn vào item trong GridView
        convertView.setOnClickListener(view -> {
            if (context instanceof SettingMenu) {
                Intent intent = new Intent(context, SettingDrink.class);
                intent.putExtra("id", drink.getId());
                intent.putExtra("name", drink.getName());
                intent.putExtra("information", drink.getInformation());
                intent.putExtra("price", drink.getPrice());
                intent.putExtra("imageResource", drink.getImageResource());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    // ViewHolder để tái sử dụng các view, giúp tăng hiệu suất
    static class ViewHolder {
        ImageView drinkImage;
        TextView drinkName, drinkPrice, sosao;
        Button btnAdd;
    }

    // Phương thức cập nhật dữ liệu khi danh sách đồ uống thay đổi
    public void updateData(List<Drinks> newDrinkList) {
        this.drinkList = newDrinkList;
        notifyDataSetChanged();
    }

    // AsyncTask để tải hình ảnh từ URL
    private static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                // Tải ảnh từ URL
                InputStream inputStream = new java.net.URL(url).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);  // Cập nhật ImageView với ảnh tải về
            }
        }
    }
}
