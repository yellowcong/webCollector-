package com.wyp.utils;

/**
 * @author 过往记忆
 * blog: www.wypblog.com
 * Create Data: 2012-8-9
 * Email: wyphao.2007@163.com
 * 
 * 版权所有，翻版不究，但在修改本程序的时候必须加上这些注释！
 * 仅用于学习交流之用
 */
public class Pair<T1, T2> {

	private T1 first;
	private T2 second;

	/**
	 * 
	 */
	public Pair() {
		// TODO Auto-generated constructor stub
		first = null;
		second = null;
	}

	/**
	 * @param first
	 *            first part
	 * @param second
	 *            second part
	 */
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public Pair(Pair<T1, T2> pair) {
		this.first = pair.getFirst();
		this.second = pair.getSecond();
	}

	public Pair<T1, T2> make_pair(T1 first, T2 second) {
		Pair<T1, T2> pair = new Pair<T1, T2>(first, second);
		return pair;
	}

	/**
	 * @return the first
	 */
	public T1 getFirst() {
		return first;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(T1 first) {
		this.first = first;
	}

	/**
	 * @return the second
	 */
	public T2 getSecond() {
		return second;
	}

	/**
	 * @param second
	 *            the second to set
	 */
	public void setSecond(T2 second) {
		this.second = second;
	}

}
