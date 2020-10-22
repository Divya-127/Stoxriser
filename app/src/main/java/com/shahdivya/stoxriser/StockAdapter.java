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

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockHolder>
{
    ArrayList<Stocks> stocks;
    Context context;
    ItemClicked itemClicked;
    ViewGroup parent;

    public StockAdapter(ArrayList<Stocks> stocks, Context context,ItemClicked itemClicked) {
        this.stocks = stocks;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.stock_holder,parent,false);
        return new StockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        holder.shareN.setText(stocks.get(position).getShareName());
        holder.shareP.setText((stocks.get(position).getSharePrice()).toString());
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
    public void resetData(ArrayList<Stocks> stocks) {
        this.stocks = stocks;
        this.notifyDataSetChanged();
    }
    class StockHolder extends RecyclerView.ViewHolder
    {
        TextView shareP,shareN;
        Button buy;
        public StockHolder(@NonNull View itemView) {
            super(itemView);
            shareP = itemView.findViewById(R.id.share_price);
            shareN = itemView.findViewById(R.id.share_name);
            buy = itemView.findViewById(R.id.buy);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked.onClick(getAdapterPosition(),v);
                }
            });
        }

    }
}
