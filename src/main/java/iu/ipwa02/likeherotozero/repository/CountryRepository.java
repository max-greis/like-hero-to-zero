package iu.ipwa02.likeherotozero.repository;

import iu.ipwa02.likeherotozero.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAllByOrderByNameAsc();

    Optional<Country> findByIsoCode(String isoCode);
}