package com.example.android.myrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Cek jika on item click listener ada
            if (mOnItemClickListener != null) {
                // Create ViewHolder dri recyclerview item
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                // Implement method in interface
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            // Cek jika item click listener itu ada
            if (mOnItemClickListener != null) {
                // Set Click Listener ke view
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }
        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {
        }
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        // Set recyclerview global variable value based on parameter
        mRecyclerView = recyclerView;
        // Pasang tag di recyclerview items
        mRecyclerView.setTag(R.id.item_click_support, this);
        // Attach state change listener to recyclerview
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    // Tambahkan ItemClickSupport ke RecyclerView
    public static ItemClickSupport addTo(RecyclerView view) {
        // Buat ItemClickSupport dengan memanggil tag di sebuah view
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        // Cek jika support itu ada
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    // Set on item click listener value based on parameter {@link MainActivity}
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}