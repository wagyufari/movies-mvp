package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.databinding.ListMoviesBinding;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private MoviesListDao moviesListDao;
    private final OnItemClickListener listener;

    public MoviesAdapter(Context context, MoviesListDao moviesListDao, OnItemClickListener listener) {
        this.context = context;
        this.moviesListDao = moviesListDao;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListMoviesBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.list_movies,
                parent, false);

        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String IMAGE_URL = "http://image.tmdb.org/t/p/w780/";

        String genres = Arrays.toString(moviesListDao.getResults().get(position).getGenre_ids())
                .replace("28", "Action")
                .replace("12", "Adventure")
                .replace("16", "Animation")
                .replace("35", "Comedy")
                .replace("80", "Crime")
                .replace("99", "Documentary")
                .replace("18", "Drama")
                .replace("14", "Fantasy")
                .replace("27", "Horror")
                .replace("10402", "Music")
                .replace("9648", "Mystery")
                .replace("10749", "Romance")
                .replace("878", "Science Fiction")
                .replace("10770", "TV Movie")
                .replace("10752", "War")
                .replace("37", "Western")
                .replace("10751", "Family")
                .replace("53", "Thriller")
                .replace("[", "")
                .replace("]", "");

        Movies m = new Movies();

        m.setTitle(moviesListDao.getResults().get(position).getTitle());
        m.setGenres(genres);
        m.setPoster_path(IMAGE_URL + moviesListDao.getResults().get(position).getPoster_path());

        holder.binding.setMovie(m);

        holder.click(moviesListDao.getResults().get(position), listener);

    }

    public interface OnItemClickListener {
        void onClick(MoviesApiDao Item);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.error_poster).into(imageView);
    }

    @Override
    public int getItemCount() {
        return moviesListDao.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListMoviesBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

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
