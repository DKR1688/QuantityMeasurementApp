package com.quantity_measurement_app.repository;

import com.quantity_measurement_app.entity.QuantityMeasurementEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/quantitydb";
    private static final String USER = "root";
    private static final String PASSWORD = "deepak@1234";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        String sql = "INSERT INTO quantity_measurement_entity(operation, operand1, operand2, result) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getOperation());
            ps.setString(2, entity.getOperand1());
            ps.setString(3, entity.getOperand2());
            ps.setString(4, entity.getResult());

            ps.executeUpdate();

            System.out.println("Measurement saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        String sql = "SELECT operation, operand1, operand2, result FROM quantity_measurement_entity";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                        rs.getString("operation"),
                        rs.getString("operand1"),
                        rs.getString("operand2"),
                        rs.getString("result")
                );

                list.add(entity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}