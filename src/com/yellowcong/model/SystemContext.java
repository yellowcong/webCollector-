package com.yellowcong.model;

public class SystemContext
{
  private static ThreadLocal<Integer> pageSize = new ThreadLocal();
  private static ThreadLocal<Integer> pageNow = new ThreadLocal();
  /**
   * 排序字段
   */
  private static ThreadLocal<String> sort = new ThreadLocal();
  /**
   * 排序方法   desc  asc 
   */
  private static ThreadLocal<String> order = new ThreadLocal();
  private static ThreadLocal<String> realPath = new ThreadLocal();
  
  public static Integer getPageSize()
  {
    return (Integer)pageSize.get();
  }
  
  public static void setPageSize(Integer _pageSize)
  {
    pageSize.set(_pageSize);
  }
  
  public static Integer getPageNow()
  {
    return (Integer)pageNow.get();
  }
  
  public static void setPageNow(Integer _pageNow)
  {
    pageNow.set(_pageNow);
  }
  
  public static String getSort()
  {
    return (String)sort.get();
  }
  /**
   * 设定排序字段
   * @param _sort
   */
  public static void setSort(String _sort)
  {
    sort.set(_sort);
  }
  
  public static String getOrder()
  {
    return (String)order.get();
  }
  /**
   * 设定排序方式  asc desc
   * @param _order
   */
  public static void setOrder(String _order)
  {
    order.set(_order);
  }
  
  public static void removePageSize()
  {
    pageSize.remove();
  }
  
  public static void removePageNow()
  {
    pageNow.remove();
  }
  
  public static void removePageOrder()
  {
    order.remove();
  }
  
  public static void removePageSort()
  {
    sort.remove();
  }
  
  public static String getRealPath()
  {
    return (String)realPath.get();
  }
  
  public static void setRealPath(String _realPath)
  {
    realPath.set(_realPath);
  }
  
  public static void removeRealPath()
  {
    realPath.remove();
  }
}
