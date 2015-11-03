package com.yellowcong.dao.impl;

import com.yellowcong.dao.BaseDao;
import com.yellowcong.model.Pager;
import com.yellowcong.model.SystemContext;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoImpl<T>
  extends HibernateDaoSupport
  implements BaseDao<T>
{
  private Class<T> clazz;
  
  private Class<T> getClazz()
  {
    if (this.clazz == null)
    {
      ParameterizedType type = (ParameterizedType)getClass()
        .getGenericSuperclass();
      this.clazz = ((Class)type.getActualTypeArguments()[0]);
    }
    return this.clazz;
  }
  
  @Resource(name="sessionFactory")
  private void setSuperSessionFactory(SessionFactory sessionFactory)
  {
    super.setSessionFactory(sessionFactory);
  }
  
  public T add(T t)
  {
    getHibernateTemplate().save(t);
    return t;
  }
  
  public void delete(int id)
  {
    super.getHibernateTemplate().delete(load(id));
  }
  
  public void update(T t)
  {
    super.getHibernateTemplate().update(t);
  }
  
  public T load(int id)
  {
    return (T)super.getHibernateTemplate().get(getClazz(), Integer.valueOf(id));
  }
  
  public Object executeQuery(String hql, Object[] args)
  {
    return executeQuery(hql, args, null);
  }
  
  private Query setParameter(String hql, Object[] args)
  {
    Query query = getSession().createQuery(hql);
    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        query.setParameter(i, args[i]);
      }
    }
    return query;
  }
  
  public Object executeQuery(String hql, Object args)
  {
    return executeQuery(hql, new Object[] { args });
  }
  
  public Object executeQuery(String hql)
  {
    return executeQuery(hql, null, null);
  }
  
  public void executeUpdate(String hql, Object[] args)
  {
    Query query = setParameter(hql, args);
    query.executeUpdate();
  }
  
  public void executeUpdate(String hql, Object args)
  {
    executeQuery(hql, new Object[] { args });
  }
  
  public void executeUpdate(String hql)
  {
    executeUpdate(hql, null);
  }
  
  public List<T> list(String hql, Object[] args)
  {
    return list(hql, args, null);
  }
  
  public List<T> list(String hql)
  {
    return list(hql, null, null);
  }
  
  public List<T> list(String hql, Object args)
  {
    return list(hql, new Object[] { args });
  }
  
  private String initSortHql(String hql)
  {
    String sort = SystemContext.getSort();
    String order = SystemContext.getOrder();
    if ((sort != null) && (!"".equals(sort)))
    {
      hql = hql + " order by " + sort;
      if ("desc".equals(order)) {
        hql = hql + " desc ";
      }
    }
    else
    {
      SystemContext.setOrder("desc");
      SystemContext.setSort("id");
    }
    return hql;
  }
  
  private void setAlias(Map<String, Object> alias, Query query)
  {
    if (alias != null)
    {
      Set<String> keys = alias.keySet();
      for (String key : keys)
      {
        Object obj = alias.get(key);
        if ((obj instanceof Collection)) {
          query.setParameterList(key, (Collection)obj);
        } else {
          query.setParameter(key, obj);
        }
      }
    }
  }
  
  private void setParameter(Object[] args, Query query)
  {
    if ((args != null) && (args.length > 0)) {
      for (int i = 0; i < args.length; i++) {
        query.setParameter(i, args[i]);
      }
    }
  }
  
  public List<T> list(String hql, Object[] args, Map<String, Object> alias)
  {
    hql = initSortHql(hql);
    
    Query query = getSession().createQuery(hql);
    
    setAlias(alias, query);
    
    setParameter(args, query);
    return query.list();
  }
  
  private int getRowCount(String hql, Object[] args, Map<String, Object> alias)
  {
    String countHql = "select count(*) " + hql.substring(hql.indexOf("from"));
    countHql.replaceAll("fetch", "");
    
    int rowCount = Integer.parseInt(
      executeQuery(countHql, args, alias).toString());
    return rowCount;
  }
  
  private int getSQLRowCount(String hql, Object[] args, Map<String, Object> alias)
  {
    String countHql = "select count(*) " + hql.substring(hql.indexOf("from"));
    countHql.replaceAll("fetch", "");
    
    SQLQuery query = getSession().createSQLQuery(countHql);
    setAlias(alias, query);
    setParameter(args, query);
    Integer rowCount = Integer.valueOf(Integer.parseInt(query.uniqueResult().toString()));
    return rowCount.intValue();
  }
  
  public List<T> list(String hql, Map<String, Object> alias)
  {
    return list(hql, null, alias);
  }
  
  public Pager<T> queryByPager(String hql, Object[] args)
  {
    return queryByPager(hql, args, null);
  }
  
  public Pager<T> queryByPager(String hql, Object args)
  {
    return queryByPager(hql, new Object[] { args });
  }
  
  public Pager<T> queryByPager(String hql)
  {
    return queryByPager(hql, null, null);
  }
  
  public Pager<T> queryByPager(String hql, Object[] args, Map<String, Object> alias)
  {
    int rowCount = getRowCount(hql, args, alias);
    hql = initSortHql(hql);
    Query query = getSession().createQuery(hql);
    
    Integer pageSize = SystemContext.getPageSize();
    Integer pageNow = SystemContext.getPageNow();
    if ((pageSize == null) || (pageSize.intValue() <= 0)) {
      pageSize = Integer.valueOf(10);
    }
    if ((pageNow == null) || (pageNow.intValue() <= 0)) {
      pageNow = Integer.valueOf(1);
    }
    setAlias(alias, query);
    setParameter(args, query);
    query.setFirstResult((pageNow.intValue() - 1) * pageSize.intValue()).setMaxResults(pageSize.intValue());
    List<T> data = query.list();
    
    Pager<T> pager = new Pager();
    pager.setData(data);
    pager.setPageNow(pageNow.intValue());
    pager.setPageSize(pageSize.intValue());
    pager.setPageCount((rowCount - 1) / pageSize.intValue() + 1);
    pager.setRowCount(rowCount);
    
    return pager;
  }
  
  public Pager<T> queryByPager(String hql, Map<String, Object> alias)
  {
    return queryByPager(hql, null, alias);
  }
  
  public List<? extends Object> listBySql(String sql, Object[] args, Class<?> clazz, boolean hasEntity)
  {
    return listBySql(sql, args, null, clazz, hasEntity);
  }
  
  public List<? extends Object> listBySql(String sql, Object args, Class<?> clazz, boolean hasEntity)
  {
    return listBySql(sql, new Object[] { args }, null, clazz, 
      hasEntity);
  }
  
  public List<? extends Object> listBySql(String sql, Class<?> clazz, boolean hasEntity)
  {
    return listBySql(sql, null, null, clazz, hasEntity);
  }
  
  public List<? extends Object> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clazz, boolean hasEntity)
  {
    sql = initSortHql(sql);
    
    SQLQuery query = getSession().createSQLQuery(sql);
    setAlias(alias, query);
    setParameter(args, query);
    if (hasEntity) {
      query.addEntity(clazz);
    } else {
      query.setResultTransformer(Transformers.aliasToBean(clazz));
    }
    return query.list();
  }
  
  public List<? extends Object> listBySql(String sql, Map<String, Object> alias, Class<?> clazz, boolean hasEntity)
  {
    return listBySql(sql, null, alias, clazz, hasEntity);
  }
  
  public Pager<? extends Object> queryPagerBySql(String sql, Object[] args, Class<?> clazz, boolean hasEntity)
  {
    return queryPagerBySql(sql, args, null, clazz, hasEntity);
  }
  
  public Pager<? extends Object> queryPagerBySql(String sql, Object args, Class<?> clazz, boolean hasEntity)
  {
    return queryPagerBySql(sql, new Object[] { args }, null, clazz, hasEntity);
  }
  
  public Pager<? extends Object> queryPagerBySql(String sql, Class<?> clazz, boolean hasEntity)
  {
    return queryPagerBySql(sql, null, null, clazz, hasEntity);
  }
  
  public Pager<? extends Object> queryPagerBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clazz, boolean hasEntity)
  {
    int rowCount = getSQLRowCount(sql, args, alias);
    
    sql = initSortHql(sql);
    SQLQuery query = getSession().createSQLQuery(sql);
    
    setAlias(alias, query);
    
    setParameter(args, query);
    
    Integer pageSize = SystemContext.getPageSize();
    Integer pageNow = SystemContext.getPageNow();
    if ((pageSize == null) || (pageSize.intValue() <= 0)) {
      pageSize = Integer.valueOf(10);
    }
    if ((pageNow == null) || (pageNow.intValue() <= 0)) {
      pageNow = Integer.valueOf(1);
    }
    query.setMaxResults(pageSize.intValue()).setFirstResult((pageNow.intValue() - 1) * pageSize.intValue());
    if (hasEntity) {
      query.addEntity(clazz);
    } else {
      query.setResultTransformer(Transformers.aliasToBean(clazz));
    }
    List<T> data = query.list();
    
    Pager<T> pager = new Pager();
    pager.setData(data);
    pager.setRowCount(rowCount);
    pager.setPageCount((rowCount - 1) / pageSize.intValue() + 1);
    pager.setPageSize(pageSize.intValue());
    pager.setPageNow(pageNow.intValue());
    
    return pager;
  }
  
  public Pager<? extends Object> queryPagerBySql(String sql, Map<String, Object> alias, Class<?> clazz, boolean hasEntity)
  {
    return queryPagerBySql(sql, null, alias, clazz, hasEntity);
  }
  
  public Object executeQuery(String hql, Object[] args, Map<String, Object> alias)
  {
    Query query = getSession().createQuery(hql);
    setAlias(alias, query);
    setParameter(args, query);
    return query.uniqueResult();
  }
  
  public Object executeQuery(String hql, Map<String, Object> alias)
  {
    return executeQuery(hql, null, alias);
  }
  
  public Object executeQueryBySQL(String sql, Class<?> clazz, boolean hasEntity)
  {
    return executeQueryBySQL(sql, null, null, clazz, hasEntity);
  }
  
  public Object executeQueryBySQL(String sql, Object args, Class<?> clazz, boolean hasEntity)
  {
    return executeQueryBySQL(sql, new Object[] { args }, null, clazz, hasEntity);
  }
  
  public Object executeQueryBySQL(String sql, Object[] args, Class<?> clazz, boolean hasEntity)
  {
    return executeQueryBySQL(sql, args, null, clazz, hasEntity);
  }
  
  public Object executeQueryBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clazz, boolean hasEntity)
  {
    SQLQuery query = getSession().createSQLQuery(sql);
    setAlias(alias, query);
    setParameter(args, query);
    if (hasEntity) {
      query.addEntity(clazz);
    } else {
      query.setResultTransformer(Transformers.aliasToBean(clazz));
    }
    return query.uniqueResult();
  }
  
  public Object executeQueryBySQL(String sql, Map<String, Object> alias, Class<?> clazz, boolean hasEntity)
  {
    return executeQueryBySQL(sql, null, alias, clazz, hasEntity);
  }
  
  public void addObj(Object obj)
  {
    getHibernateTemplate().save(obj);
  }

@Override
public int count() {
		return Integer.parseInt(this.getSession().createQuery("select count(*) from "+this.getClazz().getName()).uniqueResult()+"");

}
}
