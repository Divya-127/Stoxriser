package com.shahdivya.stoxriser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyStocksAdapter extends RecyclerView.Adapter<MyStocksAdapter.StockDetailsHolder>
{
    ArrayList<StockDetails> stockDetails;
    Context context;
    ItemClicked itemClicked;
    ViewGroup parent;

    public MyStocksAdapter(ArrayList<StockDetails> stockDetails, Context context,ItemClicked itemClicked) {
        this.stockDetails = stockDetails;
        this.context = context;
        this.itemClicked = itemClicked;
    }


    @NonNull
    @Override
    public StockDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);
        return new StockDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockDetailsHolder holder, int position) {
        holder.shareN.setText(stockDetails.get(position).getShareName());
        holder.shareP.setText((stockDetails.get(position).getSharePrice()).toString());
        holder.shareNumber.setText(stockDetails.get(position).getNoOfShares().toString());
    }

    @Override
    public int getItemCount() {
        return stockDetails.size();
    }
    public void resetData(ArrayList<StockDetails> stockDetails) {
        this.stockDetails = stockDetails;
        this.notifyDataSetChanged();
    }

    class StockDetailsHolder extends RecyclerView.ViewHolder
    {
        TextView shareP,shareN,shareNumber;
        Button sell;
        public StockDetailsHolder(@NonNull View itemView) {
            super(itemView);
            shareP = itemView.findViewById(R.id.sharePrice);
            shareN = itemView.findViewById(R.id.shareName);
            shareNumber = itemView.findViewById(R.id.noofShares);
            sell = itemView.findViewById(R.id.sell);
            sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked.onClick(getAdapterPosition(),v);
                }
            });
        }
    }
}