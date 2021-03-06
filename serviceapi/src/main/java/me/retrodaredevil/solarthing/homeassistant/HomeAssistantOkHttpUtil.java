package me.retrodaredevil.solarthing.homeassistant;

import me.retrodaredevil.solarthing.serviceutil.HeaderRequestInterceptor;
import okhttp3.OkHttpClient;

public final class HomeAssistantOkHttpUtil {
	private HomeAssistantOkHttpUtil(){ throw new UnsupportedOperationException(); }

	public static OkHttpClient.Builder configure(OkHttpClient.Builder builder, String longLivedAccessToken){
		return builder
				.addInterceptor(new HeaderRequestInterceptor("Authorization", "Bearer " + longLivedAccessToken));
	}
}
