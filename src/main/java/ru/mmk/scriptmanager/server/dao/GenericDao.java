package ru.mmk.scriptmanager.server.dao;

import java.util.List;

public interface GenericDao<Entity> {

	public void add(Entity entity);

	public void update(Entity entity);

	public void remove(Integer id);

	public List<Entity> list();

	public Entity getById(Integer id);
}
