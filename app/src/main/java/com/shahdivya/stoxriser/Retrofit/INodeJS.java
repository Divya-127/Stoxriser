package com.shahdivya.stoxriser.Retrofit;

import com.shahdivya.stoxriser.models.MultipleResource;
import com.shahdivya.stoxriser.models.loginParams;
import com.shahdivya.stoxriser.models.registerParams;
import com.shahdivya.stoxriser.models.shareHoldersParams;
import com.shahdivya.stoxriser.models.userDetails;
import com.shahdivya.stoxriser.models.userStocks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface INodeJS
{
    @POST("register")
    @FormUrlEncoded
    Call<registerParams> registerUser(@Field("email") String email,
                                      @Field("name") String name,
                                      @Field("password") String password);
    @FormUrlEncoded
    @POST("login")
    Call<loginParams> loginUser(@Field("email") String email,
                                @Field("password") String password);
    @GET("stocks")
    Call<List<MultipleResource>> getstockInfo();

    @FormUrlEncoded
    @POST("holders")
    Call<shareHoldersParams> insertShareHolders(@Field("sid") Integer sid,
                                                @Field("sharesname") String sharesname,
                                                @Field("cid") Integer cid,
                                                @Field("cost") Double cost,
                                                @Field("noofshares") Integer noofshares);
    @FormUrlEncoded
    @PUT("updateData")
    Call<shareHoldersParams> updateShares(@Field("cid") Integer cid,
                                          @Field("noofshares") Integer noofshares);

    @GET("userDetails/{id}")
    Call<List<userDetails>> getUserDetails (@Path("id") Integer id);

    @FormUrlEncoded
    @PUT("updateBal")
    Call<shareHoldersParams> updateBal(@Field("sid") Integer sid,
                                       @Field("userbal") Double userBalance,
                                       @Field("usershares") Integer userShares);
    @GET("users/{id}")
    Call<List<userStocks>> getStocks(@Path("id") Integer id);

    @FormUrlEncoded
    @PUT("updateHolders")
    Call<shareHoldersParams> updateHolderData(@Field("costprice") Double costprice,
                                              @Field("noofshares") Integer noofshares,
                                              @Field("sid") Integer sid,
                                              @Field("cid") Integer cid);

    @DELETE("stockholderData/{cid}/{sid}")
    Call<shareHoldersParams> deleteHolderData(@Path("cid") Integer cid,
                                              @Path("sid") Integer sid);
    @FormUrlEncoded
    @POST("createUserBal")
    Call<shareHoldersParams> insertBal(@Field("id") Integer id,
                                       @Field("usershares") Integer shares,
                                       @Field("userbal") Double balance);
}
