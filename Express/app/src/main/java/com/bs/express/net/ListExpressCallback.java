package com.bs.express.net;

import com.bs.express.bean.ExpressInfo;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;



public abstract class ListExpressCallback extends Callback<ExpressInfo> {
    @Override
    public ExpressInfo parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        ExpressInfo expressInfo = new Gson().fromJson(string, ExpressInfo.class);
        return expressInfo;
    }


}

