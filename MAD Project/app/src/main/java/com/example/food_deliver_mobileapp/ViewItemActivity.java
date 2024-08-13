package com.example.food_deliver_mobileapp;

import static com.example.food_deliver_mobileapp.DBHandler.TABLE_ITEM;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class ViewItemActivity extends AppCompatActivity {

    DBHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    ListView lv;

    int shopId;
    ArrayList<ItemModal> modalArrayList = new ArrayList<ItemModal>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_item);

        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", -1);

        dbHandler = new DBHandler(this);
        findid();
        displayData();

    }

    private void displayData() {
        sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ITEM + " WHERE shop_id=?", new String[]{String.valueOf(shopId)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String price = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String category = cursor.getString(5);
            String availability = cursor.getString(6);
            int shopID = cursor.getInt(7);

            // Corrected to add data to ItemModal
            modalArrayList.add(new ItemModal(id, name, description, price, category, availability, image, shopID));
        }
        Custom1 adapter = new Custom1(this, R.layout.single_item_data, modalArrayList);
        lv.setAdapter(adapter);
    }


    private void findid() {
        lv = findViewById(R.id.lv2);
    }

    private class Custom1 extends BaseAdapter {

        private Context context;
        private int layout;
        private ArrayList<ItemModal> modalArrayList;  // Use ItemModal instead of ShopModal

        public Custom1(Context context, int layout, ArrayList<ItemModal> modalArrayList) {
            this.context = context;
            this.layout = layout;
            this.modalArrayList = modalArrayList;
        }

        private class ViewHolder {
            TextView txtItemName, txtItemDescription, txtItemPrice, txtItemCategory, txtItemAvailability;
            ImageView itemImageView;
            CardView cardView;

            Button btnItemEdit,btnItemDelete;
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
                holder.txtItemName = convertView.findViewById(R.id.item_name_view);  // Update with correct ID
                holder.txtItemDescription = convertView.findViewById(R.id.item_description_view);
                holder.txtItemPrice = convertView.findViewById(R.id.item_price_view);
                holder.txtItemCategory = convertView.findViewById(R.id.item_category_view);
                holder.txtItemAvailability = convertView.findViewById(R.id.item_availability_view);
                holder.itemImageView = convertView.findViewById(R.id.item_image_view);
                holder.cardView = convertView.findViewById(R.id.cardview2);
                holder.btnItemEdit = convertView.findViewById(R.id.btn_item_edit);
                holder.btnItemDelete = convertView.findViewById(R.id.btn_item_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ItemModal modal = modalArrayList.get(position);  // Correct to use ItemModal

            holder.txtItemName.setText(modal.getName());
            holder.txtItemDescription.setText(modal.getDescription());
            holder.txtItemPrice.setText(modal.getPrice());
            holder.txtItemCategory.setText(modal.getCategory());
            holder.txtItemAvailability.setText(modal.getAvailability());

            byte[] image = modal.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.itemImageView.setImageBitmap(bitmap);

            holder.btnItemEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditItemActivity.class);
                    intent.putExtra("item_id", modal.getId()); // Pass the item ID
                    context.startActivity(intent);
                }
            });

            holder.btnItemDelete.setOnClickListener(v -> {
                boolean isDeleted = dbHandler.deleteItem(modal.getId());
                if (isDeleted) {
                    Toast.makeText(ViewItemActivity.this, "Item has been deleted", Toast.LENGTH_SHORT).show();

                    recreate();
                } else {
                    Toast.makeText(ViewItemActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }


}