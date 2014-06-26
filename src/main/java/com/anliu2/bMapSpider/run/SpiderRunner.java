/**
 * 
 */
package com.anliu2.bMapSpider.run;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anliu2.bMapSpider.spider.impl.PlacePersistenceCallbackWithFileImpl;
import com.anliu2.bMapSpider.spider.impl.PlaceSpiderImpl;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-25
 * @modify_time: 2014-6-25
 */
public class SpiderRunner {

	private static final Log LOGGER = LogFactory.getLog(SpiderRunner.class);

	private static final String ROOT_PATH = "e:/carinfo/";
	private static final String QUERY_COND = "停车";
	
	public static void main(String[] args) {
		String cond = QUERY_COND;
		String rootPath = ROOT_PATH;
		if (args.length == 2) {
			cond = args[0];
			rootPath = args[1];
		}

		long startTime = System.currentTimeMillis();
		new PlaceSpiderImpl().sprawl(cond, new PlacePersistenceCallbackWithFileImpl(rootPath));
		long endTime = System.currentTimeMillis();
		LOGGER.info("run times： " + (endTime - startTime) / 1000 + "s");
	}
}
