package me.retrodaredevil.solarthing.solcast;

import me.retrodaredevil.solarthing.serviceutil.HeaderRequestInterceptor;
import okhttp3.OkHttpClient;

public final class SolcastOkHttpUtil {
	private SolcastOkHttpUtil(){ throw new UnsupportedOperationException(); }

	public static OkHttpClient.Builder configure(OkHttpClient.Builder builder, String apiKey){
		return builder
				.addInterceptor(new HeaderRequestInterceptor("Authorization", "Bearer " + apiKey));
	}
}
