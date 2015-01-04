package com.yueqiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueqiu.R;
import com.yueqiu.bean.SearchCoauchSubFragmentCoauchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scguo on 14/12/30.
 *
 * 用于SearchActivity当中的教练子Fragment当中的ListView的bean
 *
 */
public class SearchCoauchSubFragmentListAdapter extends BaseAdapter
{
    private List<SearchCoauchSubFragmentCoauchBean> mBeanList;
    private LayoutInflater mInflater;

    public SearchCoauchSubFragmentListAdapter(Context context, ArrayList<SearchCoauchSubFragmentCoauchBean> beanList)
    {
        this.mBeanList = beanList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mBeanList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.search_coauch_fragment_listitem_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mPhoto = (ImageView) convertView.findViewById(R.id.img_coauch_subfragment_listitem_photo);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.tv_coauch_subfragment_listitem_name);
            viewHolder.mDistance = (TextView) convertView.findViewById(R.id.tv_coauch_subfragment_listitem_distance);
            viewHolder.mKinds = (TextView) convertView.findViewById(R.id.tv_coauch_subfragment_listitem_kinds);
            viewHolder.mLevel = (TextView) convertView.findViewById(R.id.tv_coauch_subfragment_listitem_level);
            viewHolder.mGender = (TextView) convertView.findViewById(R.id.tv_coauch_subfragment_listitem_gender);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SearchCoauchSubFragmentCoauchBean bean = mBeanList.get(position);

        // then, inflate the layout of this list view item
        viewHolder.mLevel.setText(bean.getUserLevel());
        viewHolder.mKinds.setText(bean.getmBilliardKind());
        viewHolder.mDistance.setText(bean.getUserDistance());
        viewHolder.mGender.setText(bean.getUserGender());
        viewHolder.mName.setText(bean.getUserName());
        // TODO: this is the static data, and we should change it to the dynamic data by using the VolleyImageView
        // TODO: or something else that has the same functionality
        viewHolder.mPhoto.setImageResource(R.drawable.ic_launcher);

        return convertView;
    }

    private static class ViewHolder
    {
        public ImageView mPhoto;
        private TextView mName, mGender, mLevel, mKinds, mDistance;
    }


}























































































































































