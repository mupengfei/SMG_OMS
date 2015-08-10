package sh.odb.core.service.impl;

import java.util.List;

import sh.odb.core.dao.IBaseDAO;
import sh.odb.core.service.IBaseService;

public abstract class BaseServiceImpl<M extends java.io.Serializable, PK extends java.io.Serializable> implements IBaseService<M, PK> {

	protected IBaseDAO<M, PK> baseDAO;

	public abstract void setBaseDAO(IBaseDAO<M, PK> baseDAO);

	@Override
	public M save(M model) {
		baseDAO.save(model);
		return model;
	}

	@Override
	public void merge(M model) {
		baseDAO.merge(model);
	}

	@Override
	public void saveOrUpdate(M model) {
		baseDAO.saveOrUpdate(model);
	}

	@Override
	public void update(M model) {
		baseDAO.update(model);
	}

	@Override
	public void delete(PK id) {
		baseDAO.delete(id);
	}

	@Override
	public void deleteObject(M model) {
		baseDAO.deleteObject(model);
	}

	@Override
	public M get(PK id) {
		return baseDAO.get(id);
	}

	@Override
	public List<M> getByParam(String hql, Object... paramlist) {
		return baseDAO.getByParams(hql, paramlist);
	}

	@Override
	public List<M> listAll() {
		return baseDAO.listAll();
	}
}
