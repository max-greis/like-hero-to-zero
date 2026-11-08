package iu.ipwa02.likeherotozero.repository;

import iu.ipwa02.likeherotozero.entity.EmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface EmissionRecordRepository extends JpaRepository<EmissionRecord, Long> {

    Optional<EmissionRecord> findFirstByCountryIdOrderByYearDesc(Long countryId);

    List<EmissionRecord> findByCountryIdOrderByYearAsc(Long countryId);

    Optional<EmissionRecord> findByCountryIdAndYear(Long countryId, Integer year);

    @EntityGraph(attributePaths = "country")
    List<EmissionRecord> findAllByOrderByCountryNameAscYearDesc();
}