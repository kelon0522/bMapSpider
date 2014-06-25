/**
 * 
 */
package com.anliu2.bMapSpider.run;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.anliu2.bMapSpider.common.BMapConstant;
import com.anliu2.bMapSpider.place.model.PlaceResponse;
import com.anliu2.bMapSpider.util.PlaceQueryUtils;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-25
 * @modify_time: 2014-6-25
 */
public class Spider {
	private static final String QUERY_COND = "停车";

	private static final String RANDOM_FOLDER = UUID.randomUUID().toString();

	private static int FILE_NO = 1;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		long startTime = System.currentTimeMillis();
		process();
		long endTime = System.currentTimeMillis();
		System.out.println("run times： " + (endTime - startTime) / 1000 + "s");
	}

	private static void process() throws ClientProtocolException, IOException {
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

//				System.out.println(lat + "," + lng + "," + latAddStep + "," + lngAddStep);
				queryAndSave(latDecimal, lngDecimal, lat, lng, latAddStep, lngAddStep);
			}
			lngDecimal = BMapConstant.START_LNG_DECIMAL;
		}
	}

	/**
	 * @param latDecimal
	 * @param lngDecimal
	 * @param lat
	 * @param lng
	 * @param latAddStep
	 * @param lngAddStep
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static void queryAndSave(BigDecimal latDecimal, BigDecimal lngDecimal, String lat, String lng,
			String latAddStep, String lngAddStep) throws ClientProtocolException, IOException {
		PlaceResponse response = PlaceQueryUtils.queryPlaceBounds(QUERY_COND, lat, lng, latAddStep, lngAddStep,
				"0");
		if (!"0".equals(response.getTotal())) {
			saveResponseWithFile(response);
			Integer allData = Integer.valueOf(response.getTotal());
			int pageCount = (allData - 1) / BMapConstant.PAGE_SIZE + 1;
			if (pageCount > BMapConstant.MAX_PAGE_NUM) {
				System.err.println(latDecimal + "," + lngDecimal + ":page > 37");
			}
			for (int i = 1; i < pageCount; i++) {
				PlaceResponse resp4Page = PlaceQueryUtils.queryPlaceBounds(QUERY_COND, lat, lng, latAddStep,
						lngAddStep, String.valueOf(i));
				saveResponseWithFile(resp4Page);
			}
		}
	}

	private static void saveResponseWithFile(PlaceResponse resp) {
		String respString = JSONObject.fromObject(resp).toString();
		String pathName = "e:\\carinfo\\" + RANDOM_FOLDER;
		File path = new File(pathName);
		if (!(path.exists()) && !(path.isDirectory())) {
			path.mkdirs();
		}
		File file = new File(pathName + "/" + FILE_NO + ".dat");
		FILE_NO = FILE_NO + 1;
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			write.write(respString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
