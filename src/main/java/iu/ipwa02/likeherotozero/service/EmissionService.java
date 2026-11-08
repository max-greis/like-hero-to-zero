package iu.ipwa02.likeherotozero.service;

import iu.ipwa02.likeherotozero.entity.Country;
import iu.ipwa02.likeherotozero.entity.EmissionRecord;
import iu.ipwa02.likeherotozero.repository.CountryRepository;
import iu.ipwa02.likeherotozero.repository.EmissionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import iu.ipwa02.likeherotozero.dto.EmissionChartPoint;
import iu.ipwa02.likeherotozero.dto.EmissionRecordForm;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmissionService {

    private final CountryRepository countryRepository;
    private final EmissionRecordRepository emissionRecordRepository;

    public EmissionService(
            CountryRepository countryRepository,
            EmissionRecordRepository emissionRecordRepository
    ) {
        this.countryRepository = countryRepository;
        this.emissionRecordRepository = emissionRecordRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    public Optional<Country> getCountryByIsoCode(String isoCode) {
        return countryRepository.findByIsoCode(isoCode);
    }

    public Optional<EmissionRecord> getLatestEmission(Long countryId) {
        return emissionRecordRepository.findFirstByCountryIdOrderByYearDesc(countryId);
    }

    public List<EmissionRecord> getEmissionHistory(Long countryId) {
        return emissionRecordRepository.findByCountryIdOrderByYearAsc(countryId);
    }

    public List<EmissionChartPoint> getEmissionChartData(Long countryId) {
        return emissionRecordRepository.findByCountryIdOrderByYearAsc(countryId)
                .stream()
                .map(record -> new EmissionChartPoint(
                        record.getYear(),
                        record.getEmissionsKt()
                ))
                .toList();
    }

    public List<EmissionRecord> getAllEmissionRecords() {
        return emissionRecordRepository.findAllByOrderByCountryNameAscYearDesc();
    }

    @Transactional
    public void createEmissionRecord(EmissionRecordForm form) {
        Country country = countryRepository.findById(form.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Selected country does not exist."));

        if (emissionRecordRepository.findByCountryIdAndYear(
                country.getId(), form.getYear()).isPresent()) {
            throw new IllegalArgumentException(
                    "An emission record already exists for this country and year."
            );
        }

        EmissionRecord record = new EmissionRecord(
                country,
                form.getYear(),
                form.getEmissionsKt()
        );

        emissionRecordRepository.save(record);
    }

    @Transactional
    public void updateEmissionRecord(Long recordId, EmissionRecordForm form) {
        EmissionRecord record = emissionRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Emission record not found."));

        Country country = countryRepository.findById(form.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Selected country does not exist."));

        emissionRecordRepository.findByCountryIdAndYear(country.getId(), form.getYear())
                .ifPresent(existingRecord -> {
                    if (!existingRecord.getId().equals(recordId)) {
                        throw new IllegalArgumentException(
                                "An emission record already exists for this country and year."
                        );
                    }
                });

        record.setCountry(country);
        record.setYear(form.getYear());
        record.setEmissionsKt(form.getEmissionsKt());

        emissionRecordRepository.save(record);
    }

    public EmissionRecordForm getEmissionRecordForEditing(Long recordId) {
        EmissionRecord record = emissionRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Emission record not found."));

        EmissionRecordForm form = new EmissionRecordForm();
        form.setCountryId(record.getCountry().getId());
        form.setYear(record.getYear());
        form.setEmissionsKt(record.getEmissionsKt());

        return form;
    }

    public boolean emissionRecordExists(Long recordId) {
        return emissionRecordRepository.existsById(recordId);
    }

    @Transactional
    public void deleteEmissionRecord(Long recordId) {
        EmissionRecord record = emissionRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Emission record not found."
                ));

        emissionRecordRepository.delete(record);
    }
}