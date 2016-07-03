package com.example.bluetooth4chat.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetooth4chat.R;
import com.example.bluetooth4chat.mode.Msg;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Msg> items = new ArrayList<Msg>();

    public ChatAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Msg.TYPE_RECEIVED) {
            View view = inflater.inflate(R.layout.item_left, parent, false);
            LeftHolder leftHolder = new LeftHolder(view);
            return leftHolder;
        } else if (viewType == Msg.TYPE_SENT) {
            View view = inflater.inflate(R.layout.item_right, parent, false);
            RightHolder rightHolder = new RightHolder(view);
            return rightHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RightHolder) {
            RightHolder rightHolder = (RightHolder) holder;
            if (items != null) {
                rightHolder.chatContent.setText(items.get(position).getContent());
                rightHolder.chatImage.setImageResource(items.get(position).getImageId());
            }
        } else if (holder instanceof LeftHolder) {
            LeftHolder leftHolder = (LeftHolder) holder;
            if (items != null) {
                leftHolder.chatContent.setText(items.get(position).getContent());
                leftHolder.chatImage.setImageResource(items.get(position).getImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public void addMsg(Msg mMsg) {
        items.add(mMsg);
        notifyItemChanged(items.size());
    }

    public class RightHolder extends RecyclerView.ViewHolder {
        private TextView chatContent;
        private ImageView chatImage;
        public RightHolder(View itemView) {
            super(itemView);
            chatContent = (TextView) itemView.findViewById(R.id.right_msg);
            chatImage = (ImageView) itemView.findViewById(R.id.imageRight);
        }
    }

    public class LeftHolder extends RecyclerView.ViewHolder {
        private TextView chatContent;
        private ImageView chatImage;
        public LeftHolder(View itemView) {
            super(itemView);
            chatContent = (TextView) itemView.findViewById(R.id.left_msg);
            chatImage = (ImageView) itemView.findViewById(R.id.imageLeft);
        }
    }

}
