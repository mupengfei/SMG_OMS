package sh.odb.core.service;

import java.util.List;

/**
 * @author peeply
 * @version 创建时间：2012-8-2 下午02:32:03 类说明
 */
public interface IBaseService<M extends java.io.Serializable, PK extends java.io.Serializable> {

	public M save(M model);

	public void saveOrUpdate(M model);

	public void update(M model);

	public void merge(M model);

	public void delete(PK id);

	public void deleteObject(M model);

	public M get(PK id);

	public List<M> getByParam(String hql, Object... paramlist);

	public List<M> listAll();
}
