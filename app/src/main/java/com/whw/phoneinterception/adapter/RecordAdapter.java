package com.whw.phoneinterception.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.whw.phoneinterception.R;
import com.whw.phoneinterception.myinterface.OnItemClickListener;
import com.whw.phoneinterception.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wuhaiwen on 2017/8/15.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    List<String> data = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    ButtonListener listener;

    OnItemClickListener onItemClickListener = null;

    public RecordAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 创建视图
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.record_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecordAdapter.MyViewHolder holder, int position) {
//        holder.tv_phone_num.setText(data.get(position));
        listener = new ButtonListener();
        listener.setPosition(position);
        holder.ibt_menu.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ibt_menu)
        ImageButton ibt_menu;

        @Bind(R.id.tv_phone_num)
        TextView tv_phone_num;
        public MyViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            ibt_menu.setOnClickListener(new ButtonListener());
//            ibt_menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //onItemClickListener.OnItemClick(view,getAdapterPosition());
//                    Toast.makeText(context, "hah", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    class ButtonListener implements ImageButton.OnClickListener {
        int position;

        private void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            PopupMenu menu = new PopupMenu(context, v);
            menu.inflate(R.menu.button_menu);
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_dial_back:
                            //获得电话号码然后用意图启动拨号界面，不会直接拨出去
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            context.startActivity(intent);
                            break;
                        case R.id.action_add_black_list:

                            break;
                        case R.id.action_delete:
                            break;
                        case R.id.action_add_contacts_list:

                            break;
                        case R.id.action_send_message:

                            ToastUtil.show(context, "发送成功"+position);

                            break;

                    }
                    return true;
                }
            });
            menu.show();
        }
    }
}
