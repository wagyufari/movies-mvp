package com.nacoda.moviesmvpdagger2rxjava.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appolica.flubber.Flubber;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.utils.AnimationHelper;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nacoda.moviesmvpdagger2rxjava.Constants.IMAGE_URL;


public class MoviesMainAdapter extends RecyclerView.Adapter<MoviesMainAdapter.ViewHolder> {

    private Context context;
    private MoviesListDao moviesListDao;
    private Utils utils;
    private Gliding gliding;

    public MoviesMainAdapter(Context context, MoviesListDao moviesListDao, Utils utils, Gliding gliding, OnItemClickListener listener) {
        this.context = context;
        this.moviesListDao = moviesListDao;
        this.utils = utils;
        this.gliding = gliding;
        this.listener = listener;
    }

    private final OnItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies_main, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(IMAGE_URL + moviesListDao.getResults().get(position).getPoster_path()).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.listMoviesMainPosterCard.setForeground(drawable);
                }
            }
        });

        String genres = utils.getGenres(moviesListDao.getResults().get(position).getGenre_ids());

        holder.listMoviesMainTitleTextView.setText(moviesListDao.getResults().get(position).getTitle());
        holder.listMoviesMainGenreTextView.setText(genres);
        holder.listMoviesMainYearTextView.setText(moviesListDao.getResults().get(position).getRelease_date().substring(0, 4));

        holder.click(moviesListDao.getResults().get(position), holder.listMoviesMainPosterCard, listener);
        AnimationHelper.onSlideAnim(holder.listMoviesMainContentLinear);

    }

    public interface OnItemClickListener {
        void onClick(MoviesApiDao Item, CardView detailImage);
    }

    @Override
    public int getItemCount() {
        return (moviesListDao.getResults().size() / 2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_movies_main_poster_card)
        CardView listMoviesMainPosterCard;
        @BindView(R.id.list_movies_main_title_text_view)
        TextView listMoviesMainTitleTextView;
        @BindView(R.id.list_movies_main_genre_text_view)
        TextView listMoviesMainGenreTextView;
        @BindView(R.id.list_movies_main_year_text_view)
        TextView listMoviesMainYearTextView;
        @BindView(R.id.list_movies_main_content_linear)
        LinearLayout listMoviesMainContentLinear;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void click(final MoviesApiDao moviesApiDao, final CardView detailImage, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(moviesApiDao, detailImage);
                }
            });
        }
    }
}
