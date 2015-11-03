package com.yellowcong.dao;

import com.yellowcong.model.Pager;
import java.util.List;
import java.util.Map;

public abstract interface BaseDao<T>
{
  public abstract T add(T paramT);
  
  public abstract void addObj(Object paramObject);
  
  public abstract void delete(int paramInt);
  
  public abstract void update(T paramT);
  
  public abstract T load(int paramInt);
  
  public abstract Object executeQuery(String paramString, Object[] paramArrayOfObject);
  
  public abstract Object executeQuery(String paramString, Object paramObject);
  
  public abstract Object executeQuery(String paramString);
  
  public abstract Object executeQuery(String paramString, Object[] paramArrayOfObject, Map<String, Object> paramMap);
  
  public abstract Object executeQuery(String paramString, Map<String, Object> paramMap);
  
  public abstract Object executeQueryBySQL(String paramString, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Object executeQueryBySQL(String paramString, Object paramObject, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Object executeQueryBySQL(String paramString, Object[] paramArrayOfObject, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Object executeQueryBySQL(String paramString, Object[] paramArrayOfObject, Map<String, Object> paramMap, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Object executeQueryBySQL(String paramString, Map<String, Object> paramMap, Class<?> paramClass, boolean paramBoolean);
  
  public abstract void executeUpdate(String paramString, Object[] paramArrayOfObject);
  
  public abstract void executeUpdate(String paramString, Object paramObject);
  
  public abstract void executeUpdate(String paramString);
  
  public abstract List<T> list(String paramString, Object[] paramArrayOfObject);
  
  public abstract List<T> list(String paramString, Object paramObject);
  
  public abstract List<T> list(String paramString);
  
  public abstract List<T> list(String paramString, Object[] paramArrayOfObject, Map<String, Object> paramMap);
  
  public abstract List<T> list(String paramString, Map<String, Object> paramMap);
  
  public abstract Pager<T> queryByPager(String paramString, Object[] paramArrayOfObject);
  
  public abstract Pager<T> queryByPager(String paramString, Object paramObject);
  
  public abstract Pager<T> queryByPager(String paramString);
  
  public abstract Pager<T> queryByPager(String paramString, Object[] paramArrayOfObject, Map<String, Object> paramMap);
  
  public abstract Pager<T> queryByPager(String paramString, Map<String, Object> paramMap);
  
  public abstract List<? extends Object> listBySql(String paramString, Object[] paramArrayOfObject, Class<?> paramClass, boolean paramBoolean);
  
  public abstract List<? extends Object> listBySql(String paramString, Object paramObject, Class<?> paramClass, boolean paramBoolean);
  
  public abstract List<? extends Object> listBySql(String paramString, Class<?> paramClass, boolean paramBoolean);
  
  public abstract List<? extends Object> listBySql(String paramString, Object[] paramArrayOfObject, Map<String, Object> paramMap, Class<?> paramClass, boolean paramBoolean);
  
  public abstract List<? extends Object> listBySql(String paramString, Map<String, Object> paramMap, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Pager<? extends Object> queryPagerBySql(String paramString, Object[] paramArrayOfObject, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Pager<? extends Object> queryPagerBySql(String paramString, Object paramObject, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Pager<? extends Object> queryPagerBySql(String paramString, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Pager<? extends Object> queryPagerBySql(String paramString, Object[] paramArrayOfObject, Map<String, Object> paramMap, Class<?> paramClass, boolean paramBoolean);
  
  public abstract Pager<? extends Object> queryPagerBySql(String paramString, Map<String, Object> paramMap, Class<?> paramClass, boolean paramBoolean);
  /**
   * 获取数据条数
   * @return
   */
  public int count();
}
