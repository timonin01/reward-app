package org.javaguru.reward.calculation.rest.tariff;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.domain.Tariff;
import org.javaguru.reward.calculation.service.repositories.TariffRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/tariff")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetTariffController {

    private final TariffRepository tariffRepository;

    @GetMapping(path = "/{id}",
            produces = "application/json")
    public GetTariffResponse getTariffResponse(@PathVariable Long id){
        TariffDTO tariffDTO = searchTariffInDB(id);
        return new GetTariffResponse(tariffDTO);
    }

    private TariffDTO searchTariffInDB(Long id){
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if(tariff.isPresent()){
            return createTariffDTO(tariff.get());
        }
        throw new IllegalArgumentException("Reward not found by id " + id);
    }

    private TariffDTO createTariffDTO(Tariff tariff){
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setId(tariff.getId());
        tariffDTO.setAmount(tariff.getAmount());
        tariffDTO.setJobType(tariff.getJobType());
        return tariffDTO;
    }


}
