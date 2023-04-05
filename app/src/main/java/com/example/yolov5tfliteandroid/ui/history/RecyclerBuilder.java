package com.example.yolov5tfliteandroid.ui.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;

import java.util.ArrayList;
import java.util.List;

class RecyclerBuilder extends RecyclerView.Adapter<RecyclerBuilder.MyViewHolder> {
    private final Context context;
    private List<ItemProperty> itemProperties; //item属性集合

    public RecyclerBuilder(Context context) {
        this.context = context;
        this.itemProperties=new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemTitle.setText(itemProperties.get(position).getTime1());
        //holder.itemTitle2.setText(itemProperties.get(position).getTime2());
        Glide.with(context)
                .load(itemProperties.get(position).getImagePath())
                .into(holder.ivHistoryItem);
//        是否是编辑状态，是的话显示选中按钮，反之隐藏
        if (isEditStatus){
            holder.itemSelect.setVisibility(View.VISIBLE);
//            按钮是否为选中状态
            if (itemProperties.get(position).isSelect()){
                holder.itemSelect.setImageResource(R.drawable.selectfill);
            }
            else {
                holder.itemSelect.setImageResource(R.drawable.select);
            }
        }
        else {
            holder.itemSelect.setVisibility(View.GONE);
        }
//        itemClick
        holder.itemView.setOnClickListener(view -> {
            if (onRecyclerViewItemClick != null){
                onRecyclerViewItemClick.OnItemClick(holder.getAdapterPosition(), itemProperties);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemProperties.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        //private final TextView itemTitle2;
        private final ImageView itemSelect;
        private final CardView cardView;
        private ImageView ivHistoryItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle1);
            //itemTitle2=itemView.findViewById(R.id.itemTitle2);
            itemSelect = itemView.findViewById(R.id.itemSelect);
            ivHistoryItem = itemView.findViewById(R.id.iv_history_item);
            cardView=itemView.findViewById(R.id.cardview);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this.cardView.getContext(), R.color.white));
        }
    }

    //    获取item属性集合
    public List<ItemProperty> getItemProperties() {
        return itemProperties;
    }



    //刷新列表数据
    @SuppressLint("NotifyDataSetChanged")
    public void notifyList(List<ItemProperty> itemProperties){
        this.itemProperties = itemProperties;
        notifyDataSetChanged();
    }

    private boolean isEditStatus; //是否是编辑状态
    //    设置编辑模式
    @SuppressLint("NotifyDataSetChanged")
    public void setEditMode(boolean isEditStatus){
        this.isEditStatus = isEditStatus;
        notifyDataSetChanged();//刷新列表
    }

    //    响应itemClick
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick){
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    private OnRecyclerViewItemClick onRecyclerViewItemClick; //itemClick

    interface OnRecyclerViewItemClick{
        void OnItemClick(int position, List<ItemProperty> itemProperties);
    }
}
