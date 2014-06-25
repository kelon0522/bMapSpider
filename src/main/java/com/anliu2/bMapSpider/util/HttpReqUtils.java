/**
 * 
 */
package com.anliu2.bMapSpider.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.anliu2.bMapSpider.common.BMapConstant;

/**
 * @project: bMapSpider
 * @description: 
 * @author: lei.yu
 * @create_time: 2014-6-24
 * @modify_time: 2014-6-24
 */
public class HttpReqUtils {
	public static final ResponseHandler<String> STRING_CONVERT = new ResponseHandler<String>() {
		@Override
		public String handleResponse(final HttpResponse hResponse) throws ClientProtocolException, ParseException,
				IOException {
			final int status = hResponse.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				final HttpEntity entity = hResponse.getEntity();
				if (entity == null) {
					return null;
				}
				return EntityUtils.toString(hResponse.getEntity());
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		}
	};

	public static <T> T processUrl(String url, ResponseHandler<T> handler) throws ClientProtocolException, IOException {
		final HttpGet hRequest = createHttpGet(url);
		final CloseableHttpClient client = createHttpClient();
		try {
			return client.execute(hRequest, handler);
		} finally {
			client.close();
		}
	}

	private static CloseableHttpClient createHttpClient() {
		final RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		final CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		return client;
	}

	private static HttpGet createHttpGet(final String urlBuilder) {
		final HttpGet hRequest = new HttpGet(urlBuilder);
		hRequest.setHeader("Referer", BMapConstant.USER_WHITE_URL);
		return hRequest;
	}
}
