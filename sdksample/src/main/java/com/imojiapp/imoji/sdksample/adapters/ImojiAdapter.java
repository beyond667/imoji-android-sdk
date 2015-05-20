package com.imojiapp.imoji.sdksample.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.imojiapp.imoji.sdk.Imoji;
import com.imojiapp.imoji.sdk.ImojiApi;
import com.imojiapp.imoji.sdksample.R;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sajjadtabib on 5/4/15.
 */
public class ImojiAdapter extends ArrayAdapter<Imoji> {

    private LayoutInflater mInflater;

    public ImojiAdapter(Context context, int resource, List<Imoji> items) {
        super(context, resource, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.imoji_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Imoji item = getItem(position);
        ImojiApi.with(getContext()).loadThumb(item, null).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap b = Bitmap.createBitmap(source.getWidth() + 50, source.getHeight() + 50, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                for (int i = 0; i < 25; i++) {
                    c.drawBitmap(source, i, i, null);
                }
                source.recycle();
                return b;
            }

            @Override
            public String key() {
                return "stacked";
            }
        }).into(holder.mImojiIv);


        return convertView;
    }

    static class ViewHolder{

        @InjectView(R.id.iv_imoji)
        ImageView mImojiIv;

        public ViewHolder(View v){
            ButterKnife.inject(this, v);
        }
    }
}
