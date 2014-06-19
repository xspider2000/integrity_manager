package ru.mmk.scriptmanager.server.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDaoHibernateImpl<Entity> implements GenericDao<Entity> {

	private Class<Entity> entityType;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernateImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		entityType = (Class<Entity>) pt.getActualTypeArguments()[0];
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void add(Entity entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void update(Entity entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public void remove(Integer id) {
		@SuppressWarnings("unchecked")
		Entity entity = (Entity) sessionFactory.getCurrentSession().load(entityType, id);
		if (entity != null) {
			sessionFactory.getCurrentSession().delete(entity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> list() {
		return sessionFactory.getCurrentSession().createQuery("from " + entityType.getName()).list();
	}

	@Override
	public Entity getById(Integer id) {
		@SuppressWarnings("unchecked")
		Entity entity = (Entity) sessionFactory.getCurrentSession().get(entityType, id);
		return entity;
	}
}
