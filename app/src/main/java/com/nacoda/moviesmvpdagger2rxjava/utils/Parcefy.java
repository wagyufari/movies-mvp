package com.nacoda.moviesmvpdagger2rxjava.utils;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarApiDao;

/**
 * Created by Mayburger on 10/5/17.
 */

public class Parcefy {
    public ParcelableMovies fillMoviesParcelable(MoviesApiDao item, String genres) {
        ParcelableMovies parcelableMovies = new ParcelableMovies(
                item.getPoster_path(),
                item.getBackdrop_path(),
                item.getTitle(),
                item.getRelease_date(),
                item.getId(),
                item.getOverview(),
                genres,
                item.getVote_average(),
                item.getVote_count()
        );
        return parcelableMovies;
    }

    public ParcelableMovies fillSimilarParcelable(SimilarApiDao item, String genres) {
        ParcelableMovies parcelableMovies = new ParcelableMovies(
                item.getPoster_path(),
                item.getBackdrop_path(),
                item.getTitle(),
                item.getRelease_date(),
                item.getId(),
                item.getOverview(),
                genres,
                item.getVote_average(),
                item.getVote_count()
        );
        return parcelableMovies;
    }

    public ParcelableMovies fillParcelableList(MoviesListDao item, String genres, int position) {
        ParcelableMovies parcelableMovies = new ParcelableMovies(
                item.getResults().get(position).getPoster_path(),
                item.getResults().get(position).getBackdrop_path(),
                item.getResults().get(position).getTitle(),
                item.getResults().get(position).getRelease_date(),
                item.getResults().get(position).getId(),
                item.getResults().get(position).getOverview(),
                genres,
                item.getResults().get(position).getVote_average(),
                item.getResults().get(position).getVote_count()
        );
        return parcelableMovies;
    }
}
