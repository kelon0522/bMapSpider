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
public class PlaceInfo implements Serializable {
	private static final long serialVersionUID = 9146622006968338594L;
	private String name;
	private Location location;
	private String address;
	private String telephone;
	private String street_id;
	private String uid;

	/**
	 * @return poi名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return poi经纬度坐标-纬度值
	 */
	public String getLocationLat() {
		return location.getLat();
	}

	/**
	 * poi经纬度坐标-经度值
	 */
	public String getLocationLng() {
		return location.getLng();
	}

	/**
	 * @return poi地址信息
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getStreet_id() {
		return street_id;
	}

	public void setStreet_id(String street_id) {
		this.street_id = street_id;
	}

	/**
	 * @return poi的唯一标示
	 */
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return poi电话信息
	 */
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
