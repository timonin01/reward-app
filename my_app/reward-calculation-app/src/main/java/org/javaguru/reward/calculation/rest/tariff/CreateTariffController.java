package org.javaguru.reward.calculation.rest.tariff;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.rest.employee.CreateEmployeeRequest;
import org.javaguru.reward.calculation.rest.employee.CreateEmployeeResponse;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Tariff;
import org.javaguru.reward.calculation.service.repositories.TariffRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/test/tariff")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateTariffController {

    private final TariffRepository tariffRepository;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public CreateTariffResponse createTariff(@RequestBody CreateTariffRequest tariffRequest){
        Tariff tariff = saveTariffInDB(tariffRequest);
        TariffDTO tariffDTO = createTariffDTO(tariff);
        return new CreateTariffResponse(tariffDTO);
    }

    private Tariff saveTariffInDB(CreateTariffRequest tariffRequest){
        Tariff tariff = new Tariff();
        tariff.setAmount(BigDecimal.valueOf(tariffRequest.getAmount()));
        tariff.setJobType(JobType.valueOf(tariffRequest.getJobType()));
        tariffRepository.save(tariff);
        return tariff;
    }

    private TariffDTO createTariffDTO(Tariff tariff){
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setId(tariff.getId());
        tariffDTO.setAmount(tariff.getAmount());
        tariffDTO.setJobType(tariff.getJobType().name());
        return tariffDTO;
    }

}
