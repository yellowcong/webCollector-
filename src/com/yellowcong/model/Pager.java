package com.yellowcong.model;

import java.util.List;

public class Pager<T>
{
  private int pageSize;
  private int pageNow;
  private int pageCount;
  private int rowCount;
  private List<T> data;
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getPageNow()
  {
    return this.pageNow;
  }
  
  public void setPageNow(int pageNow)
  {
    this.pageNow = pageNow;
  }
  
  public int getPageCount()
  {
    return this.pageCount;
  }
  
  public void setPageCount(int pageCount)
  {
    this.pageCount = pageCount;
  }
  
  public int getRowCount()
  {
    return this.rowCount;
  }
  
  public void setRowCount(int rowCount)
  {
    this.rowCount = rowCount;
  }
  
  public List<T> getData()
  {
    return this.data;
  }
  
  public void setData(List<T> data)
  {
    this.data = data;
  }
}
