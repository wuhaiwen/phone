package com.whw.phoneinterception.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
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
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_contacts_item, parent, false);
        ContactsAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contacts contacts = data.get(position);
        holder.contacts_name.setText(contacts.getName());
        if (contacts.getPhoto_id() > 0) {
            Uri uri = null;
            if (contacts.getPhoto_uri().endsWith("/photo")) {
                uri = Uri.parse(contacts.getPhoto_uri().substring(0, contacts.getPhoto_uri().indexOf("/photo")));
            } else
                uri = Uri.parse(contacts.getPhoto_uri());
            try {
                Picasso.with(context).load(uri).into(holder.contacts_photo);
            } catch (IllegalArgumentException e) {
                Log.d(Constant.TAG,  "Exception" + uri.toString() + " " + contacts.getPhoto_uri());
                Picasso.with(context).load(uri).into(holder.contacts_photo);
            }
//            Log.d(Constant.TAG, contacts.getName() + " " + uri.toString() + " " + contacts.getPhoto_uri());
        } else
            holder.contacts_photo.setImageBitmap(bitmap);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        RecyclerView.ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.layout_contacts_item, parent, false);
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        Contacts contacts = data.get(position);
//        viewHolder.contacts_name.setText(contacts.getName());
//        if (contacts.getPhoto_id() > 0) {
//            Uri uri = null;
//            if (contacts.getPhoto_uri().endsWith("/photo")) {
//                uri = Uri.parse(contacts.getPhoto_uri().substring(0, contacts.getPhoto_uri().indexOf("/photo")));
//            } else
//                uri = Uri.parse(contacts.getPhoto_uri());
//            try {
//                Picasso.with(context).load(uri).into(viewHolder.contacts_photo);
//            } catch (IllegalArgumentException e) {
//                Log.d(Constant.TAG,  "Exception" + uri.toString() + " " + contacts.getPhoto_uri());
//                Picasso.with(context).load(uri).into(viewHolder.contacts_photo);
//            }
////            Log.d(Constant.TAG, contacts.getName() + " " + uri.toString() + " " + contacts.getPhoto_uri());
//        } else
//            viewHolder.contacts_photo.setImageBitmap(bitmap);
//        return convertView;
//    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.photo_contacts)
        ImageView contacts_photo;
        @Bind(R.id.contacts_name)
        TextView contacts_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
