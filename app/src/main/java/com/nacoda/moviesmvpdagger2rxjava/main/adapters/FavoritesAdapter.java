package com.nacoda.moviesmvpdagger2rxjava.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nacoda.moviesmvpdagger2rxjava.Config;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesModel;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<FavoritesModel> favoritesModelList;
    private View.OnLongClickListener longClickListener;
    private Context context;

    public FavoritesAdapter(List<FavoritesModel> favoritesModelList, View.OnLongClickListener longClickListener, Context context) {
        this.favoritesModelList = favoritesModelList;
        this.longClickListener = longClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_favorites, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FavoritesModel favoritesModel = favoritesModelList.get(position);

        holder.listFavoritesTitleTextView.setText(favoritesModel.getTitle());
        holder.listFavoritesGenresTextView.setText(favoritesModel.getGenres());

        Glide.with(context).load(Config.IMAGE_URL + favoritesModel.getPoster_path())
                .crossFade()
                .centerCrop()
                .into(holder.listFavoritesPosterImageView);

        holder.itemView.setTag(favoritesModel);
        holder.itemView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return favoritesModelList.size();
    }

    public void addItems(List<FavoritesModel> favoritesModelList) {
        this.favoritesModelList = favoritesModelList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.list_favorites_title_text_view)
        TextView listFavoritesTitleTextView;
        @InjectView(R.id.list_favorites_genres_text_view)
        TextView listFavoritesGenresTextView;
        @InjectView(R.id.list_favorites_poster_image_view)
        ImageView listFavoritesPosterImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}