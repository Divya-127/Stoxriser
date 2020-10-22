package com.shahdivya.stoxriser.ui.searchStocks;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shahdivya.stoxriser.ItemClicked;
import com.shahdivya.stoxriser.MainActivity;
import com.shahdivya.stoxriser.R;
import com.shahdivya.stoxriser.Retrofit.INodeJS;
import com.shahdivya.stoxriser.Retrofit.RetroFitClient;
import com.shahdivya.stoxriser.StockAdapter;
import com.shahdivya.stoxriser.Stocks;
import com.shahdivya.stoxriser.data.StockPricesData;
import com.shahdivya.stoxriser.models.MultipleResource;
import com.shahdivya.stoxriser.models.shareHoldersParams;
import com.shahdivya.stoxriser.models.userDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class searchStocksFragment extends Fragment {
    RecyclerView recyclerView;
    StockAdapter adapter;
    INodeJS api;
    Integer stockB;
    Integer userId;
    Double userBalance;
    Integer userShares;
    Random random = new Random();
    ArrayList<Stocks> stocks = new ArrayList<>();
    ArrayList<String> stockName = new ArrayList<>();
    ArrayList<Integer> stockId = new ArrayList<>();
    ArrayList<Integer> stockNo = new ArrayList<>();
    ArrayList<Integer> stockGiven = new ArrayList<>();
    ArrayList<Double> sharePrices = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sharesvalue, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = requireView().findViewById(R.id.stocksView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Retrofit retrofit = RetroFitClient.getInstance();
        api = retrofit.create(INodeJS.class);
        sharePrices.add(0,StockPricesData.sharePrices0[0]);
        sharePrices.add(1,StockPricesData.sharePrices1[1]);
        sharePrices.add(2,StockPricesData.sharePrices2[2]);
        sharePrices.add(3,StockPricesData.sharePrices3[3]);
        sharePrices.add(4,StockPricesData.sharePrices4[4]);

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
        Call<List<MultipleResource>> multipleResourceCall = api.getstockInfo();
        multipleResourceCall.enqueue(new Callback<List<MultipleResource>>() {
            @Override
            public void onResponse(Call<List<MultipleResource>> call, Response<List<MultipleResource>> response) {
                if (response.isSuccessful())
                {
                    assert response.body() != null;
                    Log.d("TAG",response.body().toString());
                    for (MultipleResource resource:response.body())
                    {
                        stockId.add(resource.getCID());
                        stockName.add(resource.getCNAME());
                        stockNo.add(resource.getNOOFSHARESAVAIL());
                        stockGiven.add(resource.getTOTALNOOFSHARES());
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
                                    stocks.clear();
                                    for (int i=0;i<stockName.size();i++) {
                                        Stocks stocks1;
                                        stocks1 = new Stocks(stockName.get(i), sharePrices.get(i));
                                        stocks.add(stocks1);
                                    }
                                    adapter.resetData(stocks);

                                }
                            });
                        }
                    };
                    t.schedule(timerTask,0,1000);

                    for (int i=0;i<stockName.size();i++) {
                        Stocks stocks1;
                        stocks1 = new Stocks(stockName.get(i), sharePrices.get(i));
                        stocks.add(stocks1);
                    }
                    adapter = new StockAdapter(stocks, requireContext(), new ItemClicked() {
                        @Override
                        public void onClick(final int position, View view) {
                            LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.buy_stocks,null,false);
                            final TextView stockNames = v.findViewById(R.id.stockName);
                            final EditText stockBuy = v.findViewById(R.id.buy_id);
                            stockNames.setText(stockName.get(position));
                            new AlertDialog.Builder(requireContext())
                                    .setView(v)
                                    .setTitle("\t\t\t\t\t\tBuy\t\t\t")
                                    .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            stockB = Integer.parseInt(stockBuy.getText().toString());
                                            if (Integer.parseInt(stockBuy.getText().toString())<stockNo.get(position))
                                            {
                                                 updateBal(userId,userShares+stockB,userBalance-(stockB*sharePrices.get(position)),position);
                                            }else {
                                                Toast.makeText(requireContext(),"Buy Limit has exceeded",Toast.LENGTH_SHORT).show();
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
                }else {
                    Toast.makeText(requireContext(),response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MultipleResource>> call, Throwable t)
            {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBal(Integer id, Integer u_shares, Double u_balance, final int i)
    {
        Call<shareHoldersParams> userHolders = api.updateBal(id,u_balance,u_shares);
        userHolders.enqueue(new Callback<shareHoldersParams>() {
            @Override
            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                if (!response.isSuccessful())
                {
                    Log.d("Error",response.message());
                }else {
                    shareHoldersParams holder =response.body();
                    if (holder.getStatus().equals("You don't have sufficient balance!"))
                    {
                        Toast.makeText(requireContext(), holder.getStatus(),Toast.LENGTH_SHORT).show();
                    }else {
                        insertIntoHolders(stockId.get(i), stockName.get(i), MainActivity.personInfo.getId(), sharePrices.get(i), stockB, stockNo.get(i));
                        Toast.makeText(requireContext(),holder.getStatus(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<shareHoldersParams> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void insertIntoHolders(final Integer sid, String shareName, Integer cid, Double sharePrice, final Integer shareNo, final Integer stockInit )
    {
        Call<shareHoldersParams> holdersParamsCall = api.insertShareHolders(sid,shareName,cid,sharePrice,shareNo);
        holdersParamsCall.enqueue(new Callback<shareHoldersParams>() {
            @Override
            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                if (!response.isSuccessful())
                {
                    Log.i("TAG","Error :"+response.code());
                }else {
                    shareHoldersParams body = response.body();
                    Call<shareHoldersParams> updateCall = api.updateShares(sid,stockInit-shareNo);
                    updateCall.enqueue(new Callback<shareHoldersParams>() {
                        @Override
                        public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                            if (!response.isSuccessful())
                            {
                                Log.e("TAG","Error"+response.code());
                            }else {
                                shareHoldersParams body1 = response.body();
                                Log.d("TAG",body1.getStatus());
                            }
                        }

                        @Override
                        public void onFailure(Call<shareHoldersParams> call, Throwable t) {
                            Log.d("TAG",t.getMessage());
                        }
                    });
                    Toast.makeText(requireContext(),body.getStatus(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<shareHoldersParams> call, Throwable t) {
                Log.e("TAG",t.getMessage());
            }
        });
    }
}