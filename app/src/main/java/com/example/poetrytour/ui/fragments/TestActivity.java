package com.example.poetrytour.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.poetrytour.R;
import com.example.poetrytour.tool.ContextTool;
import com.example.poetrytour.ui.adapter.MessageItem;
import com.example.poetrytour.ui.adapter.MessageListAdapter;
import com.example.poetrytour.ui.adapter.MessageListsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bingoogolapple.badgeview.annotation.BGABadge;

@BGABadge({
        View.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeView，不想用这个类的话就删了这一行
        ImageView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeImageView，不想用这个类的话就删了这一行
        TextView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingTextView，不想用这个类的话就删了这一行
        RadioButton.class// 对应 cn.bingoogolapple.badgeview.BGABadgeRadioButton，不想用这个类的话就删了这一行

})

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(150);
                openItem.setTitle("置顶");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(150);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        SwipeMenuListView listView=findViewById(R.id.message_list);
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        String[] data = new String[30];
        for (int i = 0; i < data.length; i++) {
            data[i] = "測试数据:" + i;
        }

        List<MessageItem> lists=new ArrayList<>();
        for(int i=0;i<6;i++){
            MessageItem messageItem=new MessageItem();
            messageItem.setImage("https://ts1.cn.mm.bing.net/th/id/R-C.29a84eb867bf75b5327e7df3b1a7e32c?rik=iW9zjAJwqTB%2fdA&riu=http%3a%2f%2ftupian.qqw21.com%2farticle%2fUploadPic%2f2019-7%2f201971622263482217.jpeg&ehk=W4G6YV7SJ1LFEFGJ3r%2bsC66stsnts%2bGu%2b7tsCcMPWGA%3d&risl=&pid=ImgRaw&r=0");
            messageItem.setName("测试"+i);
            messageItem.setMessage("消息"+i);
            messageItem.setTime("2022-10-6");
            Random r = new Random();
            if(i<3){
            messageItem.setNum(r.nextInt(10)+1);
            }else {
                messageItem.setNum(0);
            }
            lists.add(messageItem);
        }
        MessageListsAdapter adapter= new MessageListsAdapter(ContextTool.Companion.getContext(),lists);
        listView.setAdapter(adapter);
    }
}