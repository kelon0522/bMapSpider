/**
 * 
 */
package com.anliu2.bMapSpider.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import com.anliu2.bMapSpider.common.BMapConstant;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-24
 * @modify_time: 2014-6-24
 */
public class GeocodingUtils {

	public static String geocoding(String address) throws ClientProtocolException, IOException {
		final String url = concatGeocodingUrl(address);
		return HttpReqUtils.processUrl(url, HttpReqUtils.STRING_CONVERT);
	}

	private static String concatGeocodingUrl(String address) throws UnsupportedEncodingException {
		final StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/geocoder/v2/?");
		urlBuilder.append("ak=");
		urlBuilder.append(BMapConstant.USER_AK);
		urlBuilder.append("&address=");
		urlBuilder.append(URLEncoder.encode(address, "UTF-8"));
		urlBuilder.append("&city=");
		urlBuilder.append(URLEncoder.encode("上海", "UTF-8"));
		urlBuilder.append("&output=json");
		return urlBuilder.toString();
	}

	public static String reGeocoding(String lat, String lng) throws ClientProtocolException, IOException {
		final String url = concatReGeocodingUrl(lat, lng);
		return HttpReqUtils.processUrl(url, HttpReqUtils.STRING_CONVERT);
	}

	private static String concatReGeocodingUrl(String lat, String lng) {
		final StringBuilder urlBuilder = new StringBuilder("http://api.map.baidu.com/geocoder/v2/?");
		urlBuilder.append("ak=");
		urlBuilder.append(BMapConstant.USER_AK);
		urlBuilder.append("&location=");
		urlBuilder.append(lat + "," + lng);
		urlBuilder.append("&output=json");
		urlBuilder.append("&pois=0");
		return urlBuilder.toString();
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println(reGeocoding("30.836934986363", "120.93244835327"));
		System.out.println(reGeocoding("31.936934831326", "122.03244835327"));
		System.out.println(URLDecoder.decode(geocoding("嘉善县政府"), "UTF-8"));
		System.out.println(URLDecoder.decode("\u65e0\u76f8\u5173\u7ed3\u679c", "UTF-8"));
	}
}
