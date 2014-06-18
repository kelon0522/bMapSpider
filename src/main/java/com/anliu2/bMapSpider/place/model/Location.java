/**
 * 
 */
package com.anliu2.bMapSpider.place.model;

import java.io.Serializable;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-17
 * @modify_time: 2014-6-17
 */
public class Location implements Serializable {
	private static final long serialVersionUID = 4569331314098241578L;
	private String lat;
	private String lng;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
