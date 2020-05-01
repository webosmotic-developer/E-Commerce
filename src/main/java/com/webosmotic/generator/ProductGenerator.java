package com.webosmotic.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.webosmotic.exception.AppException;

public class ProductGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		final Connection connection = session.connection();
		try {
			final Statement stm = connection.createStatement();
			final ResultSet rs = stm
					.executeQuery("select coalesce(max((substring(recordID,2) + 0)),0) as Id from Product");
			while (rs.next()) {
				final Random rand = new Random();
				final int num = rs.getInt(1) + rand.nextInt(6) + 1;
				return "P" + Integer.valueOf(num).toString();
			}
		} catch (final Exception e) {
			throw new AppException("Error while generating the product Id");
		}
		return null;
	}

}
