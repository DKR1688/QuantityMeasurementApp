package com.quantity_measurement_app.repository;

import com.quantity_measurement_app.entity.QuantityMeasurementEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {
	private static final String JDBC_URL = "jdbc:h2:~/quantitydb";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public QuantityMeasurementDatabaseRepository() {
		createTableIfNotExists();
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
	}

	private void createTableIfNotExists() {
		String sql = """
				CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
				    id INT AUTO_INCREMENT PRIMARY KEY,
				    operation VARCHAR(50),
				    input1 VARCHAR(50),
				    input2 VARCHAR(50),
				    result VARCHAR(50)
				)
				""";

		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {

		String sql = "INSERT INTO quantity_measurement_entity(operation,input1,input2,result) VALUES (?,?,?,?)";

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, entity.getOperation());
			ps.setString(2, entity.getOperand1());
			ps.setString(3, entity.getOperand2());
			ps.setString(4, entity.getResult());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {

		List<QuantityMeasurementEntity> list = new ArrayList<>();

		String sql = "SELECT operation,input1,input2,result FROM quantity_measurement_entity";

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				list.add(new QuantityMeasurementEntity(rs.getString("operation"), rs.getString("input1"),
						rs.getString("input2"), rs.getString("result")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
}