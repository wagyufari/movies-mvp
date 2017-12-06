package com.nacoda.moviesmvpdagger2rxjava.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.nacoda.moviesmvpdagger2rxjava.Constants;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db.FavoritesModel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<FavoritesModel> favoritesModelList;
    private View.OnLongClickListener longClickListener;
    private final OnItemClickListener listener;
    private Context context;

    public FavoritesAdapter(List<FavoritesModel> favoritesModelList, View.OnLongClickListener longClickListener, OnItemClickListener listener, Context context) {
        this.favoritesModelList = favoritesModelList;
        this.longClickListener = longClickListener;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_favorites, parent, false));
    }

    public interface OnItemClickListener {
        void onClick(FavoritesModel item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FavoritesModel favoritesModel = favoritesModelList.get(position);

        holder.listFavoritesTitleTextView.setText(favoritesModel.getTitle());
        holder.listFavoritesGenresTextView.setText(favoritesModel.getGenres());
        holder.listFavoritesReleaseDateTextView.setText(favoritesModel.getRelease_date());

        Glide.with(context).load(Constants.IMAGE_URL + favoritesModel.getPoster_path())
                .crossFade()
                .centerCrop()
                .into(holder.listFavoritesPosterImageView);

        holder.itemView.setTag(favoritesModel);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.click(favoritesModel, listener);
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

        @BindView(R.id.list_favorites_title_text_view)
        TextView listFavoritesTitleTextView;
        @BindView(R.id.list_favorites_genres_text_view)
        TextView listFavoritesGenresTextView;
        @BindView(R.id.list_favorites_release_date_text_view)
        TextView listFavoritesReleaseDateTextView;
        @BindView(R.id.list_favorites_poster_image_view)
        ImageView listFavoritesPosterImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void click(final FavoritesModel item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}