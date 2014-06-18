/**
 * 
 */
package com.anliu2.bMapSpider.place.model;

import java.io.Serializable;
import java.util.List;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-18
 * @modify_time: 2014-6-18
 */
public class PlaceResponse implements Serializable {
	private static final long serialVersionUID = 2333383722257247117L;
	private String status;
	private String message;
	private String total;
	private List<PlaceInfo> results;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<PlaceInfo> getResults() {
		return results;
	}

	public void setResults(List<PlaceInfo> results) {
		this.results = results;
	}
}
