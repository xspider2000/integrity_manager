package ru.mmk.scriptmanager.server.dao;

import org.springframework.stereotype.Repository;

import ru.mmk.scriptmanager.server.domain.Node;

@Repository
public class TreeNodeDaoImpl extends GenericDaoHibernateImpl<Node> implements TreeNodeDao {
}
