package ru.mmk.scriptmanager.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.mmk.scriptmanager.server.domain.LogMessage;

@Repository
public class LogMessageDaoImpl extends GenericDaoHibernateImpl<LogMessage> implements LogMessageDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMessage> listByDate(Integer nodeId, Date dateFrom, Date dateTo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LogMessage.class);
		criteria.add(Restrictions.eq("nodeId", nodeId));
		if (dateFrom != null)
			criteria.add(Restrictions.ge("date", dateFrom));
		if (dateTo != null)
			criteria.add(Restrictions.le("date", dateTo));
		return criteria.list();
	}
}
