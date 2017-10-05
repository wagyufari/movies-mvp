package com.nacoda.moviesmvpdagger2rxjava.main.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.nacoda.moviesmvpdagger2rxjava.URL.IMAGE_URL;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private Context context;
    private TrailersListDao trailersListDao;
    private Service service;
    private final OnItemClickListener listener;

    public TrailersAdapter(Context context, TrailersListDao trailersListDao, OnItemClickListener listener, Service service) {
        this.context = context;
        this.trailersListDao = trailersListDao;
        this.listener = listener;
        this.service = service;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trailers, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(context).load("http://i.ytimg.com/vi/"+trailersListDao.getResults().get(position).getKey()+"/mqdefault.jpg").asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.listTrailersImageView.setBackground(drawable);
                }
            }
        });

        holder.click(trailersListDao.getResults().get(position), listener);
    }

    public interface OnItemClickListener {
        void onClick(TrailersListDao Item);
    }

    @Override
    public int getItemCount() {
        return trailersListDao.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.list_trailers_image_view)
        ImageView listTrailersImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }

        public void click(final TrailersApiDao trailersApiDao, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(trailersListDao);
                }
            });
        }
    }
}
