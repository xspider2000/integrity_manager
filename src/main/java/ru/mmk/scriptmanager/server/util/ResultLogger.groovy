package ru.mmk.scriptmanager.server.util

import groovy.sql.Sql;


class ResultLogger {
	private  def jdbcProps = new Properties();

	public ResultLogger() {
		def stream = getClass().getClassLoader().getResourceAsStream("jdbc.properties")
		jdbcProps.load(stream)
	}

	public warn(String message, Integer nodeId){
		def sql = getSql()
		sql.execute("INSERT INTO log(date, priority, message, node_id) VALUES (now(), 'WARN', $message, $nodeId)")
	}

	public info(String message, Integer nodeId){
		def sql = getSql()
		sql.execute("INSERT INTO log(date, priority, message, node_id) VALUES (now(), 'INFO', $message, $nodeId)")
	}

	private Sql getSql() {
		def url = jdbcProps.getProperty('jdbc.databaseurl')
		def user = jdbcProps.getProperty('jdbc.username')
		def password = jdbcProps.('jdbc.password')
		def driver = jdbcProps.('jdbc.driverClassName')
		def sql = Sql.newInstance(url, user, password, driver)

		return sql;
	}
}
