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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.anliu2.bMapSpider.common.BMapConstant;
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
	private static final ResponseHandler<PlaceResponse> PLACE_BEAN_CONVERT = new ResponseHandler<PlaceResponse>() {
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
	};

	public static void main(final String[] args) throws ParseException, IOException {
		// System.out.println(queryPlaceRound4Json("停车站", "31.213263",
		// "121.508101", "300", "0"));
		// System.out.println(queryPlaceRound("停车站", "31.213263", "121.508101",
		// "300", "0"));
		// System.out.println(queryPlaceRegion4Json("停车站", "上海", "0"));
		// System.out.println(queryPlaceRegion("停车站", "上海", "0"));
		// System.out.println(queryPlaceBounds4Json("停车站", "31.213263",
		// "121.508101", "31.313263", "121.608101", "0"));
		Log log = LogFactory.getLog(PlaceQueryUtils.class);
		log.info("asdf");
		System.out.println(queryPlaceBounds4Json("停车", BMapConstant.CENTER_LAT_DECIMAL.toString(),
				BMapConstant.CENTER_LNG_DECIMAL.toString(),
				BMapConstant.CENTER_LAT_DECIMAL.add(BMapConstant.RUN_STEP_DECIMAL).toString(),
				BMapConstant.CENTER_LNG_DECIMAL.add(BMapConstant.RUN_STEP_DECIMAL).toString(), "0"));

//		System.out.println(queryPlaceRegion4Json("停车", "上海", "30"));
	}

	public static PlaceResponse queryPlaceRound(final String cond, final String lat, final String lng,
			final String radius, final String pageNo) throws ClientProtocolException, IOException {
		final String url = concatRoundUrl(cond, lat, lng, radius, pageNo);
		return HttpReqUtils.processUrl(url, PLACE_BEAN_CONVERT);
	}

	public static String queryPlaceRound4Json(final String cond, final String lat, final String lng,
			final String radius, final String pageNo) throws ClientProtocolException, IOException {
		final String url = concatRoundUrl(cond, lat, lng, radius, pageNo);
		return HttpReqUtils.processUrl(url, HttpReqUtils.STRING_CONVERT);
	}

	public static PlaceResponse queryPlaceRegion(final String cond, final String region, final String pageNo)
			throws ClientProtocolException, IOException {
		final String url = concatRegionUrl(cond, region, pageNo);
		return HttpReqUtils.processUrl(url, PLACE_BEAN_CONVERT);
	}

	public static String queryPlaceRegion4Json(final String cond, final String region, final String pageNo)
			throws ClientProtocolException, IOException {
		final String url = concatRegionUrl(cond, region, pageNo);
		System.out.println(url);
		return HttpReqUtils.processUrl(url, HttpReqUtils.STRING_CONVERT);
	}

	public static PlaceResponse queryPlaceBounds(final String cond, final String leftDownLat, final String leftDownLng,
			final String rightUpLat, final String rightUpLng, final String pageNo) throws ClientProtocolException,
			IOException {
		final String url = concatBoundsUrl(cond, leftDownLat, leftDownLng, rightUpLat, rightUpLng, pageNo);
		return HttpReqUtils.processUrl(url, PLACE_BEAN_CONVERT);
	}

	public static String queryPlaceBounds4Json(final String cond, final String leftDownLat, final String leftDownLng,
			final String rightUpLat, final String rightUpLng, final String pageNo) throws ClientProtocolException,
			IOException {
		final String url = concatBoundsUrl(cond, leftDownLat, leftDownLng, rightUpLat, rightUpLng, pageNo);
		return HttpReqUtils.processUrl(url, HttpReqUtils.STRING_CONVERT);
	}

	private static String concatBoundsUrl(String cond, String leftDownLat, String leftDownLng, String rightUpLat,
			String rightUpLng, String pageNo) throws UnsupportedEncodingException {
		final StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/place/v2/search?");
		urlBuilder.append("&q=");
		urlBuilder.append(URLEncoder.encode(cond, "UTF-8"));
		urlBuilder.append("&bounds=");
		urlBuilder.append(leftDownLat + "," + leftDownLng + "," + rightUpLat + "," + rightUpLng);
		urlBuilder.append("&output=json");
		urlBuilder.append("&ak=");
		urlBuilder.append(BMapConstant.USER_AK);
		urlBuilder.append("&page_num=");
		urlBuilder.append(pageNo);
		urlBuilder.append("&page_size=");
		urlBuilder.append(BMapConstant.PAGE_SIZE);
		return urlBuilder.toString();
	}

	private static String concatRoundUrl(final String cond, final String lat, final String lng, final String radius,
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
		urlBuilder.append(BMapConstant.USER_AK);
		urlBuilder.append("&page_num=");
		urlBuilder.append(pageNo);
		urlBuilder.append("&page_size=");
		urlBuilder.append(BMapConstant.PAGE_SIZE);
		return urlBuilder.toString();
	}

	private static String concatRegionUrl(String cond, String region, String pageNo)
			throws UnsupportedEncodingException {
		final StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/place/v2/search?");
		urlBuilder.append("&q=");
		urlBuilder.append(URLEncoder.encode(cond, "UTF-8"));
		urlBuilder.append("&region=");
		urlBuilder.append(URLEncoder.encode(region, "UTF-8"));
		urlBuilder.append("&output=json");
		urlBuilder.append("&ak=");
		urlBuilder.append(BMapConstant.USER_AK);
		urlBuilder.append("&page_num=");
		urlBuilder.append(pageNo);
		urlBuilder.append("&page_size=");
		urlBuilder.append(BMapConstant.PAGE_SIZE);
		return urlBuilder.toString();
	}
}
