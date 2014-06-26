/**
 * 
 */
package com.anliu2.bMapSpider.spider.impl;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.anliu2.bMapSpider.common.BMapConstant;
import com.anliu2.bMapSpider.place.model.PlaceResponse;
import com.anliu2.bMapSpider.spider.inf.PersistenceCallback;
import com.anliu2.bMapSpider.spider.inf.Spider;
import com.anliu2.bMapSpider.util.PlaceQueryUtils;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-26
 * @modify_time: 2014-6-26
 */
public class PlaceSpiderImpl implements Spider<PlaceResponse> {
	private static final Log LOGGER = LogFactory.getLog(PlaceSpiderImpl.class);

	@Override
	public void sprawl(String cond, PersistenceCallback<PlaceResponse> callback) {
		BigDecimal latDecimal = BMapConstant.START_LAT_DECIMAL;
		BigDecimal lngDecimal = BMapConstant.START_LNG_DECIMAL;
		while (latDecimal.compareTo(BMapConstant.END_LAT_DECIMAL) < 0) {
			String lat = latDecimal.toString();
			latDecimal = latDecimal.add(BMapConstant.RUN_STEP_DECIMAL);
			String latAddStep = latDecimal.toString();
			while (lngDecimal.compareTo(BMapConstant.END_LNG_DECIMAL) < 0) {
				String lng = lngDecimal.toString();
				lngDecimal = lngDecimal.add(BMapConstant.RUN_STEP_DECIMAL);
				String lngAddStep = lngDecimal.toString();

				try {
					queryAndSave(cond, lat, lng, latAddStep, lngAddStep, callback);
				} catch (Exception e) {
					LOGGER.error("error bounds :" + lat + "," + lng + "," + latAddStep + "," + lngAddStep, e);
				}
			}
			lngDecimal = BMapConstant.START_LNG_DECIMAL;
		}
	}

	private static void queryAndSave(String cond, String lat, String lng, String latAddStep, String lngAddStep,
			PersistenceCallback<PlaceResponse> callback) throws ClientProtocolException, IOException {
		PlaceResponse response = PlaceQueryUtils.queryPlaceBounds(cond, lat, lng, latAddStep, lngAddStep, "0");
		if (!"0".equals(response.getTotal())) {
			callback.persist(response);
			Integer allData = Integer.valueOf(response.getTotal());
			int pageCount = (allData - 1) / BMapConstant.PAGE_SIZE + 1;
			if (pageCount > BMapConstant.MAX_PAGE_NUM) {
				LOGGER.error(lat + "," + lng + ":page > 37");
			}
			for (int i = 1; i < pageCount; i++) {
				PlaceResponse resp4Page = PlaceQueryUtils.queryPlaceBounds(cond, lat, lng, latAddStep, lngAddStep,
						String.valueOf(i));
				callback.persist(resp4Page);
			}
		}
	}
}
