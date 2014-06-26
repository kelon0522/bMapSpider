/**
 * 
 */
package com.anliu2.bMapSpider.spider.inf;

/**
 * @project: bMapSpider
 * @description:
 * @author: lei.yu
 * @create_time: 2014-6-26
 * @modify_time: 2014-6-26
 */
public interface Spider<T> {
	public void sprawl(String cond, PersistenceCallback<T> callback);
}
