/**
 * 
 */
package com.anliu2.bMapSpider.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.anliu2.bMapSpider.place.model.PlaceInfo;
import com.anliu2.bMapSpider.place.model.PlaceResponse;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-17
 * @modify_time: 2014-6-17
 */
public class PlaceQueryUtils {
	private static final String USER_AK = "2C9xgOeiUGd0mZsXXiAS44rw";
	private static final String WHITE_URL = "http://www.anliu2.com";

	// private static final String USER_AK = "E4805d16520de693a3fe707cdc962045";
	// private static final String WHITE_URL =
	// "http://developer.baidu.com/map/webservice-placeapi.htm";

	public static void main(final String[] args) throws ParseException, IOException {
		System.out.println(queryPlaceRound4Json("停车站", "31.213263", "121.508101", "300", "0"));
		System.out.println(queryPlaceRound("停车站", "31.213263", "121.508101", "300", "0"));
	}

	public static PlaceResponse queryPlaceRound(final String cond, final String lat, final String lng,
			final String radius, final String pageNo) throws ClientProtocolException, IOException {
		final StringBuilder urlBuilder = concatUrl(cond, lat, lng, radius, pageNo);
		final HttpGet hRequest = createHttpGet(urlBuilder);
		final CloseableHttpClient client = createHttpClient();
		try {
			return client.execute(hRequest, new ResponseHandler<PlaceResponse>() {
				@SuppressWarnings("unchecked")
				@Override
				public PlaceResponse handleResponse(final HttpResponse hResponse) throws ClientProtocolException,
						ParseException, IOException {
					final int status = hResponse.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						final HttpEntity entity = hResponse.getEntity();
						if (entity == null) {
							return null;
						}
						final String jsonString = EntityUtils.toString(entity);
						final JSONObject fromObject = JSONObject.fromObject(jsonString);
						final PlaceResponse bean = (PlaceResponse) JSONObject.toBean(fromObject, new PlaceResponse(),
								new JsonConfig());
						if ("0".equals(bean.getStatus()) && Integer.valueOf(bean.getTotal()) > 0) {
							final JSONArray jsonArray = fromObject.getJSONArray("results");
							bean.setResults(JSONArray.toList(jsonArray, new PlaceInfo(), new JsonConfig()));
						}
						return bean;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			});
		} finally {
			client.close();
		}
	}

	public static String queryPlaceRound4Json(final String cond, final String lat, final String lng,
			final String radius, final String pageNo) throws ClientProtocolException, IOException {
		final StringBuilder urlBuilder = concatUrl(cond, lat, lng, radius, pageNo);
		final HttpGet hRequest = createHttpGet(urlBuilder);

		final CloseableHttpClient client = createHttpClient();
		try {
			final CloseableHttpResponse hResponse = client.execute(hRequest);
			try {
				return EntityUtils.toString(hResponse.getEntity());
			} finally {
				hResponse.close();
			}
		} finally {
			client.close();
		}
	}

	private static StringBuilder concatUrl(final String cond, final String lat, final String lng, final String radius,
			final String pageNo) throws UnsupportedEncodingException {
		final StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/place/v2/search?");
		urlBuilder.append("&q=");
		urlBuilder.append(URLEncoder.encode(cond, "UTF-8"));
		urlBuilder.append("&location=");
		urlBuilder.append(lat + "," + lng);
		urlBuilder.append("&radius=");
		urlBuilder.append(radius);
		urlBuilder.append("&output=json");
		urlBuilder.append("&ak=");
		urlBuilder.append(USER_AK);
		urlBuilder.append("&page_num=");
		urlBuilder.append(pageNo);
		urlBuilder.append("&page_size=20");
		return urlBuilder;
	}

	private static CloseableHttpClient createHttpClient() {
		final RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		final CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		return client;
	}

	private static HttpGet createHttpGet(final StringBuilder urlBuilder) {
		final HttpGet hRequest = new HttpGet(urlBuilder.toString());
		hRequest.setHeader("Referer", WHITE_URL);
		return hRequest;
	}
}
