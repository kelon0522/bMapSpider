/**
 * 
 */
package com.anliu2.bMapSpider.common;

import java.math.BigDecimal;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-24
 * @modify_time: 2014-6-24
 */
public class BMapConstant {
	// public static final String USER_AK = "2C9xgOeiUGd0mZsXXiAS44rw";
	// public static final String WHITE_URL = "http://www.anliu2.com";

	public static final int PAGE_SIZE = 20;
	public static final int MAX_PAGE_NUM = 750 / PAGE_SIZE;

	public static final String USER_AK = "E4805d16520de693a3fe707cdc962045";
	public static final String USER_WHITE_URL = "http://developer.baidu.com";

	// 嘉善县政府
	public static final BigDecimal START_LAT_DECIMAL = new BigDecimal("30.836934831326");
	public static final BigDecimal START_LNG_DECIMAL = new BigDecimal("120.93244835327");
	// 嘉善县政府+1.1
	public static final BigDecimal END_LAT_DECIMAL = new BigDecimal("31.936934831326");
	public static final BigDecimal END_LNG_DECIMAL = new BigDecimal("122.03244835327");

	// 来福士广场
	public static final BigDecimal CENTER_LAT_DECIMAL = new BigDecimal("31.23824938476");
	public static final BigDecimal CENTER_LNG_DECIMAL = new BigDecimal("121.48325839028");

	public static final BigDecimal RUN_STEP_DECIMAL = new BigDecimal("0.001");

}
