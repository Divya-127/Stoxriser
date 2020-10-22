package com.shahdivya.stoxriser.ui.searchStocks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class searchStocksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public searchStocksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}