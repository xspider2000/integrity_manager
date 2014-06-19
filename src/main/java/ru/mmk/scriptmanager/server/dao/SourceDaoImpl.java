package ru.mmk.scriptmanager.server.dao;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.mmk.scriptmanager.server.domain.Source;

@Repository
public class SourceDaoImpl extends GenericDaoHibernateImpl<Source> implements SourceDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private TreeNodeDao treeNodeDao;

	@Override
	public void setSourceAsMaster(Integer sourceId, Integer nodeId) {
		Set<Source> siblingSourceSet = treeNodeDao.getById(nodeId).getSources();
		setAllSiblingSourcesAsNonMaster(sourceId, siblingSourceSet);
		setAsMaster(sourceId, siblingSourceSet);
	}

	private void setAllSiblingSourcesAsNonMaster(Integer sourceId, Set<Source> siblingSources) {
		for (Source source : siblingSources) {
			if (possibleToSetAsNonMaster(source, sourceId)) {
				source.setMaster(false);
				sessionFactory.getCurrentSession().update(source);
			}
		}
	}

	private boolean possibleToSetAsNonMaster(Source source, Integer sourceId) {
		return source.isMaster() && !source.getId().equals(sourceId);
	}

	private void setAsMaster(Integer sourceId, Set<Source> siblingSourceSet) {
		for (Source siblingSource : siblingSourceSet) {
			if (siblingSource.getId().equals(sourceId)) {
				siblingSource.setMaster(true);
				sessionFactory.getCurrentSession().update(siblingSource);
				break;
			}
		}
	}

}