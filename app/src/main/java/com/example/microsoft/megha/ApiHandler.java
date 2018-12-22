package com.example.microsoft.megha;

import org.json.JSONObject;

public interface ApiHandler {
   public void onApiSuccess(JSONObject successResponse);
   public void onApiFailure(JSONObject failureResponse);


}
