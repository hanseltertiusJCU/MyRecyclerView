package com.example.android.myrecyclerview;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener{
    private int position;
    private OnItemClickCallback onItemClickCallback;
    // Constructor of custom item click listener
    CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        // Set value of global variable based on parameter
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    // When view got clicked, call method based on interface
    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);
    }
    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
