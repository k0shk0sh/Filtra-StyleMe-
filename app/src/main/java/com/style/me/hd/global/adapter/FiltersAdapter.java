package com.style.me.hd.global.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.style.me.hd.R;
import com.style.me.hd.global.filter.FilterModel;
import com.style.me.hd.presenter.HomePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved h566UniFi
 */
public class FiltersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FilterModel> filterModelList;
    private HomePresenter homePresenter;

    public FiltersAdapter(HomePresenter homePresenter, List<FilterModel> filterModelList) {
        this.homePresenter = homePresenter;
        this.filterModelList = filterModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filters_list_item, parent, false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CommonHolder holder = (CommonHolder) viewHolder;
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(position);
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .endConfig()
                .round();
        holder.fabImage.setImageDrawable(builder.build("" + position, color));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.onFilterClick(filterModelList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterModelList.size();
    }

    public List<FilterModel> getFilterModelList() {
        return filterModelList;
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
