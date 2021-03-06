/*
 * Copyright 2017 Harsh Sharma
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.harsh.instatagsample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SomeOneAdapter extends RecyclerView.Adapter<SomeOneAdapter.ViewHolder> {

    private final Context mContext;
    private final List<SomeOne> mSomeOneList;
    private final SomeOneClickListener mSomeOneClickListener;

    public SomeOneAdapter(List<SomeOne> someOnes,
                          Context mContext,
                          SomeOneClickListener mSomeOneClickListener) {
        this.mSomeOneList = someOnes;
        this.mContext = mContext;
        this.mSomeOneClickListener = mSomeOneClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_row_some_one_to_be_tagged, parent, false);
        return new ViewHolder(view, mSomeOneClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SomeOne someOne = mSomeOneList.get(position);
        holder.setPosition(position);
        holder.setSomeOne(someOne);
        holder.setContext(mContext);
        holder.txtUserName.setText(someOne.getUserName());
        holder.txtFullName.setText(someOne.getFullName());

        Glide
                .with(mContext)
                .load(someOne.getUrl())
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_default_avatar)
                .into(holder.imgProfile);

    }

    @Override
    public int getItemCount() {
        if (mSomeOneList != null) {
            return mSomeOneList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SomeOneClickListener listener;
        private final RelativeLayout relativeLayout;
        private final ImageView imgProfile;
        private final TextView txtUserName;
        private final TextView txtFullName;
        private SomeOne someOne;
        private int position;
        private Context context;

        public void setPosition(int position) {
            this.position = position;
        }

        public void setSomeOne(SomeOne someOne) {
            this.someOne = someOne;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public ViewHolder(View itemView, SomeOneClickListener listener) {
            super(itemView);
            this.listener = listener;
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.someOneItem);
            imgProfile = (ImageView) itemView.findViewById(R.id.someOneProfileImage);
            txtUserName = (TextView) itemView.findViewById(R.id.someOneUserName);
            txtFullName = (TextView) itemView.findViewById(R.id.txtFullName);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.someOneItem:
                    listener.onSomeOneClicked(someOne, position);
                    break;
            }
        }
    }
}

