/**
 * 
 */
package com.anliu2.bMapSpider.spider.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.anliu2.bMapSpider.place.model.PlaceResponse;
import com.anliu2.bMapSpider.spider.inf.PersistenceCallback;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-26
 * @modify_time: 2014-6-26
 */
public class PlacePersistenceCallbackWithFileImpl implements PersistenceCallback<PlaceResponse> {
	private static final Log LOGGER = LogFactory.getLog(PlacePersistenceCallbackWithFileImpl.class);
	private static final String RANDOM_FOLDER = UUID.randomUUID().toString();
	private static int FILE_NO = 1;

	/**
	 * 文件存储根目录
	 */
	private String rootPath;

	public PlacePersistenceCallbackWithFileImpl(String rootPath) {
		this.rootPath = rootPath;
	}

	@Override
	public void persist(PlaceResponse response) {
		String respString = JSONObject.fromObject(response).toString();
		String pathName = rootPath + RANDOM_FOLDER;
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
			LOGGER.error("write file error!", e);
		} finally {
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
					LOGGER.error("close file error!", e);
				}
			}
		}
	}

}
