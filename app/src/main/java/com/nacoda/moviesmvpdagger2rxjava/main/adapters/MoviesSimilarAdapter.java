package com.nacoda.moviesmvpdagger2rxjava.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.nacoda.moviesmvpdagger2rxjava.Config.IMAGE_URL;


public class MoviesSimilarAdapter extends RecyclerView.Adapter<MoviesSimilarAdapter.ViewHolder> {

    private Context context;
    private SimilarListDao similarListDao;
    private Utils utils;
    private Gliding gliding;

    public MoviesSimilarAdapter(Context context, SimilarListDao similarListDao, Utils utils, Gliding gliding, OnItemClickListener listener) {
        this.context = context;
        this.similarListDao = similarListDao;
        this.utils = utils;
        this.gliding = gliding;
        this.listener = listener;
    }

    private final OnItemClickListener listener;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_similar, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String genres = utils.getGenres(similarListDao.getResults().get(position).getGenre_ids());

        holder.listMoviesTitleTextView.setText(similarListDao.getResults().get(position).getTitle());
        holder.listMoviesGenresTextView.setText(genres);

        Glide.with(context).load(IMAGE_URL + similarListDao.getResults().get(position).getPoster_path())
                .crossFade()
                .centerCrop()
                .into(holder.listMoviesPosterImageView);

        holder.click(similarListDao.getResults().get(position), listener);

    }

    public interface OnItemClickListener {
        void onClick(SimilarApiDao Item);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.list_movies_title_text_view)
        TextView listMoviesTitleTextView;
        @InjectView(R.id.list_movies_genres_text_view)
        TextView listMoviesGenresTextView;
        @InjectView(R.id.list_movies_poster_image_view)
        ImageView listMoviesPosterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }

        public void click(final SimilarApiDao similarApiDao, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(similarApiDao);
                }
            });
        }
    }
}
