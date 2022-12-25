package com.example.poetrytour.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.poetrytour.R;


import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

public class MessageListsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<MessageItem> lists;
    private Context context;

    public MessageListsAdapter(Context context,List<MessageItem> lists){
        this.inflater=LayoutInflater.from(context);
        this.lists=lists;
        this.context=context;
    }

    @Override
    public int getCount() {
      return lists.size();
    }

    @Override
    public MessageItem getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView;
        View view;
        if (convertView==null){
            view= inflater.inflate(R.layout.message_item,parent,false);
            itemView= new ItemView(view);
            view.setTag(itemView);
        }else{
            view=convertView;
            itemView=(ItemView) view.getTag();
        }
        itemView.init(getItem(position));
        return view;
    }

    class ItemView{
        private View view;
        private TextView name,time,message;
        private ImageView image;
        private BGABadgeTextView num;

        ItemView(View view){
            this.view=view;
            this.name=view.findViewById(R.id.message_user_name_item);
            this.time=view.findViewById(R.id.message_latest_time_item);
            this.message=view.findViewById(R.id.message_context_item);
            this.image=view.findViewById(R.id.message_avatar_item);
            this.num =view.findViewById(R.id.message_unread_num_item);
        }


        public void init( MessageItem messageItem){

            name.setText(messageItem.getName());
            time.setText(messageItem.getTime());
            message.setText(messageItem.getMessage());
            Glide.with(context).load(messageItem.getImage()).into(image);
            if(messageItem.getNum()>0) {
                num.showCirclePointBadge();
                num.showTextBadge("" + messageItem.getNum());
            }

        }

    }
}
