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

public class ProductGenerator implements IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Connection connection = session.connection();
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select coalesce(max((substring(recordID,2) + 0)),0) as Id from Product");
			while(rs.next()) {
				Random rand = new Random();
				int num = rs.getInt(1) + rand.nextInt(6) + 1;
				return "P" + Integer.valueOf(num).toString();
			}
		}catch (Exception e) {
			throw new AppException("Error while generating the product Id");
		}
		return null;
	}

}
