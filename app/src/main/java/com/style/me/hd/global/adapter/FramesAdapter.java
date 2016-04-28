package com.style.me.hd.global.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.style.me.hd.R;
import com.style.me.hd.presenter.HomePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class FramesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] colors;
    private HomePresenter homePresenter;

    public FramesAdapter(HomePresenter homePresenter, String[] colors) {
        this.homePresenter = homePresenter;
        this.colors = colors;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filters_list_item, parent, false);
        return new CommonHolder(view);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CommonHolder holder = (CommonHolder) viewHolder;
        int color = Color.parseColor(colors[position]);
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .endConfig()
                .round();
        holder.fabImage.setImageDrawable(builder.build("" + position, color));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.onFrameClick(Color.parseColor(colors[holder.getAdapterPosition()]));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                homePresenter.onBackgroundColor(Color.parseColor(colors[holder.getAdapterPosition()]));
                return true;
            }
        });
    }

    @Override public int getItemCount() {
        return colors.length;
    }

    public String[] getColors() {
        return colors;
    }

    class CommonHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.fabImage)
        ImageView fabImage;

        CommonHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
