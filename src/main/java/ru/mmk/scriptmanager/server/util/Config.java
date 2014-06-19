package ru.mmk.scriptmanager.server.util;

import groovy.sql.Sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import ru.mmk.scriptmanager.server.domain.DataSource;
import ru.mmk.scriptmanager.server.service.DataSourceService;

public class Config {

	public Sql getConnection(String name) throws IOException,
			ClassNotFoundException, SQLException {
		if (name.equals("local")) {
			Properties jdbcProps = new Properties();
			jdbcProps.load(Config.class.getClassLoader().getResourceAsStream(
					"jdbc.properties"));

			String dbUrl = jdbcProps.getProperty("jdbc.testdatabaseurl");
			String username = jdbcProps.getProperty("jdbc.username");
			String password = jdbcProps.getProperty("jdbc.password");
			String driver = jdbcProps.getProperty("jdbc.driverClassName");
			return Sql.newInstance(dbUrl, username, password, driver);
		} else {
			DataSourceService serv = new DataSourceService() {

				@Override
				public List<DataSource> list() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public DataSource getByName(String name) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			DataSource ds = serv.getByName(name);
			String dbUrl = ds.getConnectionURL();
			String username = ds.getUserName();
			String password = ds.getPassword();
			String driver = ds.getDriverClass();
			return Sql.newInstance(dbUrl, username, password, driver);
		}
	}
}
