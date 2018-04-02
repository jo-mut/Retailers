package com.app.retailers.api.yelp.retailers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by J.EL on 10/11/2017.
 */

public class RetailsRecyclerAdapter extends RecyclerView.Adapter
        <RetailsRecyclerAdapter.RetailerViewHolder>{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Retailer> mRetailers = new ArrayList<>();
    private Retailer retailer;
    private Context mContext;

    public RetailsRecyclerAdapter(Context context, ArrayList<Retailer> retailers) {
        mContext = context;
        mRetailers = retailers;
    }

    @Override
    public RetailsRecyclerAdapter.RetailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retails_layout, parent, false);
        RetailerViewHolder viewHolder = new RetailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RetailsRecyclerAdapter.RetailerViewHolder holder, int position) {
        holder.bindRetailers(mRetailers.get(position));
        final String image = mRetailers.get(position).getImageUrl();
        Log.d("image", image);
    }

    @Override
    public int getItemCount() {
        return mRetailers.size();
    }

    public class RetailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.retailerNameTextView)TextView mRetailerNameTextView;
        @Bind(R.id.categoryTextView)TextView mCategoryTextView;
        @Bind(R.id.ratingTextView)TextView mRatingTextView;
        @Bind(R.id.retailerImageView)ImageView mRetailerImageView;
        private Context mContext;


        public RetailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRetailers(Retailer retailer){
            Picasso.with(mContext)
                    .load(retailer.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mRetailerImageView);

            mRetailerNameTextView.setText(retailer.getName());
            mCategoryTextView.setText(retailer.getCategories().get(0));
            mRatingTextView.setText("Rating" + retailer.getRating() + "/5");

        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RetailerDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("retailers", Parcels.wrap(mRetailers));
            mContext.startActivity(intent);
        }

    }
}
