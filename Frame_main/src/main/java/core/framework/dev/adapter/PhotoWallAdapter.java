package core.framework.dev.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import core.framework.dev.R;
import lyh.library.cm.adapter.recycleview.MultiItemTypeAdapter;
import lyh.library.cm.adapter.recycleview.base.ItemViewDelegate;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.component.banner.SlideBanner;
import lyh.library.cm.display.BitmapLoader;
import lyh.library.cm.utils.BitmapUtil;

/**
 * Created by lyh on 2016/11/2.
 */

public class PhotoWallAdapter extends MultiItemTypeAdapter<String> {

    public String[] urls = {
            "http://pic35.nipic.com/20131121/2531170_145358633000_2.jpg",
            "http://img.taopic.com/uploads/allimg/121017/234940-12101FR22825.jpg",
            "http://pic47.nipic.com/20140901/6608733_145238341000_2.jpg",
            "http://img1.qq.com/gamezone/pics/14806/14806970.jpg"
    };

    private int index = 0;

    public PhotoWallAdapter(Context context, List<String> datas) {
        super(context, datas);
        addItemViewDelegate(new Item_Common());
        addItemViewDelegate(new Item_short());
    }

    public class Item_Common implements ItemViewDelegate<String> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_meizhi_long;
        }

        @Override
        public boolean isForViewType(String item, int position) {
            return position % 2 == 0;
        }

        @Override
        public void convert(ViewHolder holder, String section, int position) {
            Glide.with(mContext)
                    .load(urls[index >= urls.length ? index = 0 : index++])
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                              @Override
                              public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                  ImageView iamge = holder.getView(R.id.iv_content);
                                  iamge.setImageBitmap(resource);

                                  TextView tvTitle = holder.getView(R.id.tv_title);
                                  Palette.generateAsync(resource, (palette) -> {
                                      Palette.Swatch swatch = palette.getVibrantSwatch();
                                      if (null != swatch) {
                                          tvTitle.setBackgroundColor(swatch.getRgb());
                                      }
                                  });
                              }
                          }
                    );
        }
    }

    public class Item_short implements ItemViewDelegate<String> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_meizhi_short;
        }

        @Override
        public boolean isForViewType(String item, int position) {
            return position % 2 != 0;
        }

        @Override
        public void convert(ViewHolder holder, String section, int position) {

            Glide.with(mContext)
                    .load(urls[index >= urls.length ? index = 0 : index++])
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                              @Override
                              public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                  ImageView iamge = holder.getView(R.id.iv_content);
                                  iamge.setImageBitmap(resource);

                                  TextView tvTitle = holder.getView(R.id.tv_title);
                                  Palette.generateAsync(resource, (palette) -> {
                                      Palette.Swatch swatch = palette.getVibrantSwatch();
                                      if (null != swatch) {
                                          tvTitle.setBackgroundColor(swatch.getRgb());
                                      }
                                  });
                              }
                          }
                    );
        }
    }
}