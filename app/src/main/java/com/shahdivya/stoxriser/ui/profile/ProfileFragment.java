package com.shahdivya.stoxriser.ui.profile;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shahdivya.stoxriser.MainActivity;
import com.shahdivya.stoxriser.R;
import com.shahdivya.stoxriser.Retrofit.INodeJS;
import com.shahdivya.stoxriser.Retrofit.RetroFitClient;
import com.shahdivya.stoxriser.data.StockPricesData;
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

public class ProfileFragment extends Fragment {
    INodeJS api;
    ArrayList<String> stockName = new ArrayList<>();
    ArrayList<Double> stockCost = new ArrayList<>();
    ArrayList<Integer> noOfShares = new ArrayList<>();
    ArrayList<Integer> stockId = new ArrayList<>();
    Double totalInvestedValue = 0.0;
    Integer userId;
    Double userBalance;
    Integer userShares;
    ArrayList<Double> sharePrices = new ArrayList<>();
    Random random = new Random();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        final TextView userName,moneyInvested,totalSharesValue,balance;
        balance = requireView().findViewById(R.id.balance);
        userName = requireView().findViewById(R.id.name);
        moneyInvested = requireView().findViewById(R.id.moneyInvested);
        totalSharesValue = requireView().findViewById(R.id.totalSharesValue);
        super.onActivityCreated(savedInstanceState);
        Retrofit retrofit = RetroFitClient.getInstance();
        api = retrofit.create(INodeJS.class);
        userName.setText(MainActivity.personInfo.getName());

//        sharePrices.add(356.93);
//        sharePrices.add(456.89);
//        sharePrices.add(345.67);
//        sharePrices.add(567.88);
//        sharePrices.add(400.56);
//userbalance
        Call<List<userDetails>> userCall = api.getUserDetails(MainActivity.personInfo.getId());
        userCall.enqueue(new Callback<List<userDetails>>() {
            @Override
            public void onResponse(Call<List<userDetails>> call, Response<List<userDetails>> response) {
                if (response.isSuccessful())
                {
                    userId = response.body().get(0).getUserId();
                    userBalance = response.body().get(0).getBalance();
                    //moneyInvested.setText(Double.toString(Math.round((20000-userBalance)*100)/100.0));
                    balance.setText("Balance Available "+Double.toString(userBalance));
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
        final Call<List<userStocks>> stocksCall = api.getStocks(MainActivity.personInfo.getId());
        stocksCall.enqueue(new Callback<List<userStocks>>() {
            @Override
            public void onResponse(Call<List<userStocks>> call, Response<List<userStocks>> response) {
                List<userStocks> stocksList = response.body();
                for (userStocks stocks:stocksList)
                {
                    stockId.add(stocks.getsID());
                    stockName.add(stocks.getSNAME());
                    stockCost.add(stocks.getCOSTPRICE());
                    noOfShares.add(stocks.getNOOFSHARES());
                }
                double invest = 0;
                for (int i=0;i<stockCost.size();i++)
                {
                    invest = invest + (stockCost.get(i)*noOfShares.get(i));
                }
                moneyInvested.setText("Money Invested "+Double.toString(Math.round((invest)*100)/100.0));
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
                                for (int i=0;i<stockCost.size();i++)
                                {
                                    totalInvestedValue = (sharePrices.get(stockId.get(i)-1)*noOfShares.get(i));
                                }
                                totalSharesValue.setText("Current Shares Price "+Double.toString(Math.round(totalInvestedValue*100)/100.0));
                            }
                        });
                    }
                };
                t.schedule(timerTask,0,1000);

            }
            @Override
            public void onFailure(Call<List<userStocks>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}