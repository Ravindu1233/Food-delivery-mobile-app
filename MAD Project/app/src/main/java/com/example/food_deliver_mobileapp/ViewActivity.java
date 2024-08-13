package com.example.food_deliver_mobileapp;

import static com.example.food_deliver_mobileapp.DBHandler.TABLE_SHOP;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    DBHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    ListView lv;
    ArrayList<com.example.food_deliver_mobileapp.ShopModal> modalArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view);

        dbHandler = new DBHandler(this);
        findid();
        displayData();
    }

    private void displayData() {
        sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SHOP, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String city = cursor.getString(3);
            String contact = cursor.getString(4);
            String email = cursor.getString(5);
            String open = cursor.getString(6);
            String close = cursor.getString(7);
            byte[] image = cursor.getBlob(8);

            modalArrayList.add(new com.example.food_deliver_mobileapp.ShopModal(id, name, address, city, contact, email, open, close, image));
        }
        Custom adapter = new Custom(this, R.layout.singledata, modalArrayList);
        lv.setAdapter(adapter);
    }

    private void findid() {
        lv = findViewById(R.id.lv);
    }

    private class Custom extends BaseAdapter {

        private Context context;
        private int layout;
        private ArrayList<com.example.food_deliver_mobileapp.ShopModal> modalArrayList;

        public Custom(Context context, int layout, ArrayList<com.example.food_deliver_mobileapp.ShopModal> modalArrayList) {
            this.context = context;
            this.layout = layout;
            this.modalArrayList = modalArrayList;
        }

        private class ViewHolder {
            TextView txtname, txtcity;
            ImageView imageView;
            CardView cardView;
        }

        @Override
        public int getCount() {
            return modalArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return modalArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(layout, parent, false);
                holder = new ViewHolder();
                holder.txtname = convertView.findViewById(R.id.name_view);
                holder.txtcity = convertView.findViewById(R.id.city_view);
                holder.imageView = convertView.findViewById(R.id.image_view);
                holder.cardView = convertView.findViewById(R.id.cardview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            com.example.food_deliver_mobileapp.ShopModal modal = modalArrayList.get(position);

            holder.txtname.setText(modal.getName());
            holder.txtcity.setText(modal.getCity());

            byte[] image = modal.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.imageView.setImageBitmap(bitmap);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailedViewActivity.class);
                    intent.putExtra("shop_id", modal.getId()); // Pass only the ID
                    context.startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
