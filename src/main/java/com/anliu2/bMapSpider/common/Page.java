/**
 * 
 */
package com.anliu2.bMapSpider.common;

import java.io.Serializable;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-18
 * @modify_time: 2014-6-18
 */
public class Page implements Serializable {
	private static final long serialVersionUID = 1824551657784456943L;
	private String start;
	private String max;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
}
