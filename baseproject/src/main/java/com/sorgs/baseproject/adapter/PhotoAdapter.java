package com.sorgs.baseproject.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sorgs.baseproject.R;
import com.sorgs.baseproject.base.MyAdapter;

import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/07/24
 * desc   : 图片选择适配器
 */
public final class PhotoAdapter extends MyAdapter<String> {

    private final List<String> mSelectPhoto;

    public PhotoAdapter(Context context, List<String> data) {
        super(context);
        mSelectPhoto = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    @Override
    protected RecyclerView.LayoutManager generateDefaultLayoutManager(Context context) {
        return new GridLayoutManager(context, 3);
    }

    final class ViewHolder extends MyAdapter.ViewHolder {
        ImageView mImageView = itemView.findViewById(R.id.iv_photo_image);
        CheckBox mCheckBox = itemView.findViewById(R.id.iv_photo_check);

        ViewHolder() {
            super(R.layout.item_photo);
        }

        @Override
        public void onBindView(int position) {
            Glide.with(getContext())
                 .load(getItem(position))
                 .into(mImageView);

            mCheckBox.setChecked(mSelectPhoto.contains(getItem(position)));
        }
    }
}