package com.nacoda.moviesmvpdagger2rxjava.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private Context context;
    private TrailersListDao trailersListDao;
    private Gliding gliding;
    private final OnItemClickListener listener;

    public TrailersAdapter(Context context, TrailersListDao trailersListDao, Gliding gliding, OnItemClickListener listener) {
        this.context = context;
        this.trailersListDao = trailersListDao;
        this.gliding = gliding;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trailers, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        gliding.GlideBackdrop(context,
                "http://i.ytimg.com/vi/" + trailersListDao.getResults().get(position).getKey() + "/mqdefault.jpg",
                holder.listTrailersImageView);


        holder.click(trailersListDao.getResults().get(position), listener);
    }

    public interface OnItemClickListener {
        void onClick(TrailersApiDao Item);
    }

    @Override
    public int getItemCount() {
        return trailersListDao.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_trailers_image_view)
        ImageView listTrailersImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void click(final TrailersApiDao trailersApiDao, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(trailersApiDao);
                }
            });
        }
    }
}
