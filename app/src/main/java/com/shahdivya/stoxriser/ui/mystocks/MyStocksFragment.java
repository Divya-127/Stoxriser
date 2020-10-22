package com.shahdivya.stoxriser.ui.mystocks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shahdivya.stoxriser.ItemClicked;
import com.shahdivya.stoxriser.MainActivity;
import com.shahdivya.stoxriser.MyStocksAdapter;
import com.shahdivya.stoxriser.R;
import com.shahdivya.stoxriser.Retrofit.INodeJS;
import com.shahdivya.stoxriser.Retrofit.RetroFitClient;
import com.shahdivya.stoxriser.StockDetails;
import com.shahdivya.stoxriser.Stocks;
import com.shahdivya.stoxriser.data.StockPricesData;
import com.shahdivya.stoxriser.models.MultipleResource;
import com.shahdivya.stoxriser.models.shareHoldersParams;
import com.shahdivya.stoxriser.models.userDetails;
import com.shahdivya.stoxriser.models.userStocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyStocksFragment extends Fragment
{
    INodeJS api;
    Integer userId;
    Double userBalance;
    Integer userShares;
    Integer stockS;
    ArrayList<StockDetails> stockDetailsList = new ArrayList<>();
    ArrayList<Integer> stockId = new ArrayList<>();//stockId of share that user has
    ArrayList<String> stockName = new ArrayList<>();//stockName of share that user has
    ArrayList<Double> stockCost = new ArrayList<>();//stockCost of share that user has
    ArrayList<Integer> noOfShares = new ArrayList<>();//total No of stocks that user has
    ArrayList<Double> sharePrices = new ArrayList<>();// current Share Price of that stock
    ArrayList<Integer> stockNo = new ArrayList<>(); //noofSharesAvailable (total Shares ka table)
    MyStocksAdapter adapter;
    RecyclerView recyclerView;
    int intially;
    Random random = new Random();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mystocks, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Retrofit retrofit = RetroFitClient.getInstance();
        api = retrofit.create(INodeJS.class);
        recyclerView = requireView().findViewById(R.id.recycleStocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //StockNo from total shares
        Call<List<MultipleResource>> multipleResourceCall = api.getstockInfo();
        multipleResourceCall.enqueue(new Callback<List<MultipleResource>>() {
            @Override
            public void onResponse(Call<List<MultipleResource>> call, Response<List<MultipleResource>> response) {
                for (MultipleResource resource:response.body())
                {
                    stockNo.add(resource.getNOOFSHARESAVAIL());
                }
            }
            @Override
            public void onFailure(Call<List<MultipleResource>> call, Throwable t) {

            }
        });
        //To get User details
        Call<List<userDetails>> userCall = api.getUserDetails(MainActivity.personInfo.getId());
        userCall.enqueue(new Callback<List<userDetails>>() {
            @Override
            public void onResponse(Call<List<userDetails>> call, Response<List<userDetails>> response) {
                if (response.isSuccessful())
                {
                    userId = response.body().get(0).getUserId();
                    userBalance = response.body().get(0).getBalance();
                    userShares = response.body().get(0).getUShares();
                }else {
                    Log.d("TAG",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<userDetails>> call, Throwable t) {
                Log.d("TAG",t.getMessage());
            }
        });
        /*
        Intialising share Prices Current vaale
         */
        sharePrices.add(StockPricesData.sharePrices0[random.nextInt(60)]);
        sharePrices.add(StockPricesData.sharePrices1[random.nextInt(60)]);
        sharePrices.add(StockPricesData.sharePrices2[random.nextInt(60)]);
        sharePrices.add(StockPricesData.sharePrices3[random.nextInt(60)]);
        sharePrices.add(StockPricesData.sharePrices4[random.nextInt(60)]);
        Call<List<userStocks>> stocksCall = api.getStocks(MainActivity.personInfo.getId());
        stocksCall.enqueue(new Callback<List<userStocks>>() {
            @Override
            public void onResponse(Call<List<userStocks>> call, Response<List<userStocks>> response) {
                if (response.isSuccessful())
                {
                    List<userStocks> stocksList = response.body();
                    for (userStocks stocks:stocksList)
                    {
                        stockId.add(stocks.getsID());
                        stockName.add(stocks.getSNAME());
                        stockCost.add(stocks.getCOSTPRICE());
                        noOfShares.add(stocks.getNOOFSHARES());
                    }
                    TimerTask timerTask;
                    final Handler handler = new Handler();
                    Timer t = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sharePrices.clear();
                                    sharePrices.add(StockPricesData.sharePrices0[random.nextInt(60)]);
                                    sharePrices.add(StockPricesData.sharePrices1[random.nextInt(60)]);
                                    sharePrices.add(StockPricesData.sharePrices2[random.nextInt(60)]);
                                    sharePrices.add(StockPricesData.sharePrices3[random.nextInt(60)]);
                                    sharePrices.add(StockPricesData.sharePrices4[random.nextInt(60)]);
                                    stockDetailsList.clear();
                                    for (int i=0;i<stockName.size();i++)
                                    {
                                        StockDetails details;
                                        details = new StockDetails(stockName.get(i),sharePrices.get(stockId.get(i)-1),noOfShares.get(i));
                                        stockDetailsList.add(details);
                                    }
                                    adapter.resetData(stockDetailsList);
                                }
                            });
                        }
                    };
                    t.schedule(timerTask,0,1000);

                    for (int i=0;i<stockName.size();i++)
                    {
                        StockDetails details;
                        details = new StockDetails(stockName.get(i),sharePrices.get(i),noOfShares.get(i));
                        stockDetailsList.add(details);
                    }
                    adapter = new MyStocksAdapter(stockDetailsList, requireContext(), new ItemClicked() {
                        @Override
                        public void onClick(final int position, View view) {
                            LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.buy_stocks,null,false);
                            final TextView stockNames = v.findViewById(R.id.stockName);
                            final EditText stockBuy = v.findViewById(R.id.buy_id);
                            stockNames.setText(stockName.get(position));
                            //Shws a dialog box
                            new AlertDialog.Builder(requireContext())
                                    .setView(v)
                                    .setTitle("\t\t\t\t\t\tSell\t\t\t\t\t\t")
                                    .setPositiveButton("Sell", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            stockS = Integer.parseInt(stockBuy.getText().toString());
                                            if (stockS>noOfShares.get(position))
                                            {
                                                Toast.makeText(requireContext(),"You don't posses that many stocks!",Toast.LENGTH_SHORT).show();
                                            }else  if (stockS<noOfShares.get(position)) {
                                                intially = noOfShares.get(position);
                                                noOfShares.add(position,noOfShares.get(position)-stockS);
                                                userBalance = userBalance + (stockS*sharePrices.get(position));
                                                updateBal(userId,noOfShares.get(position),userBalance,position);
                                            }else {
                                                intially = noOfShares.get(position);
                                                userBalance = userBalance + (stockS*sharePrices.get(position));
                                                updateBal(userId,noOfShares.get(position)-stockS,userBalance,position);
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(requireContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<userStocks>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    private void updateBal(final Integer userId, final Integer shares, Double balance, final int position)
    {
        Call<shareHoldersParams> userHolders = api.updateBal(userId,balance,shares);
        userHolders.enqueue(new Callback<shareHoldersParams>() {
            @Override
            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response)
            {
                shareHoldersParams holder = response.body();
                Toast.makeText(requireContext(),holder.getStatus(),Toast.LENGTH_SHORT).show();
                if (holder.getStatus().equals("Balance Updated Successfully"))
                {
                    if (stockS<intially){
                        updateShareholders(stockCost.get(position),shares,stockId.get(position),userId,position);
                    }
                    else
                        deleteShareholders(stockId.get(position),userId);
                }
            }
            @Override
            public void onFailure(Call<shareHoldersParams> call, Throwable t)
            {
                Log.e("Error",t.getMessage());
            }
        });
    }

    private void deleteShareholders(final Integer sId, Integer userId)
    {
        Call<shareHoldersParams> deleteHolders = api.deleteHolderData(userId,sId);
        deleteHolders.enqueue(new Callback<shareHoldersParams>() {
            @Override
            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                if (!response.isSuccessful())
                {
                    Log.i("TAG","Error"+response.message());
                }else {
                    Log.d("TAG",noOfShares.toString());
                    shareHoldersParams body = response.body();
                    Call<shareHoldersParams> updateCall = api.updateShares(sId,stockNo.get(sId-1)+stockS);
                    updateCall.enqueue(new Callback<shareHoldersParams>() {
                        @Override
                        public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                            if (!response.isSuccessful())
                            {
                                Log.e("Error","Error"+response.code());
                            }else {
                                shareHoldersParams body1 = response.body();
                                Log.d("TAG",body1.getStatus());
                            }
                        }

                        @Override
                        public void onFailure(Call<shareHoldersParams> call, Throwable t) {
                            Log.e("Error",t.getMessage());
                        }
                    });
                    Toast.makeText(requireContext(),body.getStatus(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<shareHoldersParams> call, Throwable t) {

            }
        });
    }

    private void updateShareholders(Double stockCost, final Integer shares, final Integer stockId, final Integer userId, final Integer position)
    {
        Call<shareHoldersParams> updateHolders = api.updateHolderData(stockCost,shares,stockId,userId);
        updateHolders.enqueue(new Callback<shareHoldersParams>() {
            @Override
            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                    if (!response.isSuccessful())
                    {
                        Log.i("TAG","Error"+response.code());
                    }else {
                        for (int i=0;i<stockName.size();i++)
                        {
                            StockDetails details;
                            details = new StockDetails(stockName.get(i),sharePrices.get(i),shares);
                            stockDetailsList.add(i,details);
                            adapter.resetData(stockDetailsList);
                        }
                        shareHoldersParams body = response.body();
                        Call<shareHoldersParams> updateCall = api.updateShares(stockId,stockNo.get(stockId-1)+stockS);
                        updateCall.enqueue(new Callback<shareHoldersParams>() {
                            @Override
                            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                                if (!response.isSuccessful())
                                {
                                    Log.e("Error","Error"+response.code());
                                }else {
                                    int ans = stockNo.get(stockId-1);
                                    stockNo.set(stockId-1,ans+stockS);
                                    shareHoldersParams body1 = response.body();
                                    Log.d("TAG",body1.getStatus());
                                }
                            }

                            @Override
                            public void onFailure(Call<shareHoldersParams> call, Throwable t)
                            {
                                Log.e("Error",t.getMessage());
                            }
                        });
                        Toast.makeText(requireContext(),body.getStatus(),Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<shareHoldersParams> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
}