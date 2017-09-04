package com.whw.phoneinterception.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whw.phoneinterception.Constant;
import com.whw.phoneinterception.R;
import com.whw.phoneinterception.bean.Contacts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wuhaiwen on 2017/8/31.
 */

public class ContactsAdapter extends BaseAdapter {
    ArrayList<Contacts> data;
    Context context;
    LayoutInflater inflater;
    ContentResolver resolver;
    Bitmap bitmap;

    public ContactsAdapter(ArrayList<Contacts> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        resolver = context.getContentResolver();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.contacts);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_contacts_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contacts contacts = data.get(position);
        viewHolder.contacts_name.setText(contacts.getName());
        if (contacts.getPhoto_id() > 0) {
            Uri uri = null;
            if (contacts.getPhoto_uri().endsWith("/photo")) {
                uri = Uri.parse(contacts.getPhoto_uri().substring(0, contacts.getPhoto_uri().indexOf("/photo")));
            } else
                uri = Uri.parse(contacts.getPhoto_uri());
            try {
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                Bitmap contactPhoto = BitmapFactory.decodeStream(input);
                viewHolder.contacts_photo.setImageBitmap(contactPhoto);
            } catch (IllegalArgumentException e) {
                viewHolder.contacts_photo.setImageBitmap(bitmap);
            }
//            Log.d(Constant.TAG, contacts.getName() + " " + uri.toString() + " " + contacts.getPhoto_uri());
        } else
            viewHolder.contacts_photo.setImageBitmap(bitmap);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.photo_contacts)
        ImageView contacts_photo;
        @Bind(R.id.contacts_name)
        TextView contacts_name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
