package com.nacoda.moviesmvpdagger2rxjava.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.nacoda.moviesmvpdagger2rxjava.Config.IMAGE_URL;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private MoviesListDao moviesListDao;
    private Utils utils;
    private Gliding gliding;
    private final OnItemClickListener listener;

    public MoviesAdapter(Context context, MoviesListDao moviesListDao, Utils utils, Gliding gliding, OnItemClickListener listener) {
        this.context = context;
        this.moviesListDao = moviesListDao;
        this.utils = utils;
        this.gliding = gliding;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String genres = utils.getGenres(moviesListDao.getResults().get(position).getGenre_ids());

        holder.listMoviesTitleTextView.setText(moviesListDao.getResults().get(position).getTitle());
        holder.listMoviesGenresTextView.setText(genres);
        gliding.GlidePoster(context,IMAGE_URL + moviesListDao.getResults().get(position).getPoster_path(),holder.listMoviesPosterImageView);
        holder.click(moviesListDao.getResults().get(position), listener);

    }

    public interface OnItemClickListener {
        void onClick(MoviesApiDao Item);
    }

    @Override
    public int getItemCount() {
        return moviesListDao.getResults().size();
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

        public void click(final MoviesApiDao moviesApiDao, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(moviesApiDao);
                }
            });
        }
    }
}