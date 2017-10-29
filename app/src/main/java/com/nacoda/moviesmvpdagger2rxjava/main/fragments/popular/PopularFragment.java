package com.nacoda.moviesmvpdagger2rxjava.main.fragments.popular;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.dagger.ApplicationComponent;
import com.nacoda.moviesmvpdagger2rxjava.dagger.DaggerApplicationComponent;
import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.NetworkModule;
import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.UtilityModule;
import com.nacoda.moviesmvpdagger2rxjava.main.DetailActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesMainAdapter;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Parcefy;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment implements PopularView {

    @Inject
    Service service;
    @Inject
    Utils utils;
    @Inject
    Gliding gliding;
    @Inject
    Parcefy parcefy;

    @BindView(R.id.fragment_movies_recycler_view)
    RecyclerView fragmentMoviesRecyclerView;
    @BindView(R.id.fragment_movies_swipe_refresh_layout)
    SwipeRefreshLayout fragmentMoviesSwipeRefreshLayout;
    @BindView(R.id.fragment_movies_layout)
    LinearLayout fragmentMoviesLayout;

    String total_pages;

    Unbinder unbinder;
    PopularPresenter presenter;

    @OnClick(R.id.fragment_movies_more_button)
    public void OnClickMore(){
        Intent more = new Intent(getActivity(), MoviesActivity.class);
        more.putExtra("total_pages", total_pages);
        more.putExtra("category", "now_playing");
        more.putExtra("page", 1);
        startActivity(more);
    }

    public PopularFragment() {

    }

    ApplicationComponent applicationComponent;

    /**
     * This onCreate override is used to initialize the dagger
     **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getActivity().getCacheDir(), "responses");
        applicationComponent = DaggerApplicationComponent.builder()
                .utilityModule(new UtilityModule())
                .networkModule(new NetworkModule(cacheFile))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    /**
     * onCreateView
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        initUnbinder(view);
        getApplicationComponent().inject(this);
        initRetrofit();
        return view;
    }

    /**
     * This method is used to initialize the unbinder which is used to unbind the Butterknife when the fragment is destroyed
     *
     * @param view The View for the Butterknife
     */
    private void initUnbinder(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * This method is used to initialize the Retrofit using the Presenter
     */
    private void initRetrofit() {
        presenter = new PopularPresenter(service, this);
        presenter.getPopularList(1);

        fragmentMoviesSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPopularList(1);
            }
        });
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void showWait() {
        fragmentMoviesSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void removeWait() {
        fragmentMoviesSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        utils.snackbar(fragmentMoviesLayout, "No Internet");
    }

    /**
     * Callback from the presenter from the initRetrofit
     **/
    @Override
    public void getPopularListSuccess(MoviesListDao moviesListDao) {

        total_pages = moviesListDao.getTotal_pages();

        MoviesMainAdapter adapter = new MoviesMainAdapter(getActivity(), moviesListDao, utils, gliding,
                new MoviesMainAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = utils.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = parcefy.fillMoviesParcelable(item, genres);

                        Intent detail = new Intent(getActivity(), DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                    }
                });
        fragmentMoviesRecyclerView.setAdapter(adapter);
        fragmentMoviesRecyclerView.setNestedScrollingEnabled(false);
        initLayoutManager(fragmentMoviesRecyclerView);
    }

    /**
     * Unbinds the Butterknife when fragment is destroyed
     **/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
