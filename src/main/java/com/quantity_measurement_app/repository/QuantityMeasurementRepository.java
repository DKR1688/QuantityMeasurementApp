package com.quantity_measurement_app.repository;

import com.quantity_measurement_app.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
	//it find all measurements by operation type
	List<QuantityMeasurementEntity> findByOperation(String operation);
	List<QuantityMeasurementEntity> findByMeasurementType(String measurementType);
	List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);
	List<QuantityMeasurementEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT q FROM QuantityMeasurementEntity q WHERE q.operation = :operation AND q.errorFlag = false")
	List<QuantityMeasurementEntity> findByOperationWithoutErrors(@Param("operation") String operation);

	long countByOperationAndErrorFlagFalse(String operation);
	List<QuantityMeasurementEntity> findByErrorFlagTrue();
	List<QuantityMeasurementEntity> findByOperationAndMeasurementType(String operation, String measurementType);

	long deleteByCreatedAtBefore(LocalDateTime date);
}
