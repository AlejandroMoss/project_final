package com.bancolombia.proyect.infrastructure.adapters.cashouts;

import com.bancolombia.proyect.domain.model.Cashouts.Cashout;
import com.bancolombia.proyect.domain.model.Cashouts.gateways.CashoutRepository;
import com.bancolombia.proyect.infrastructure.adapters.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public class CashoutAdapter extends ReactiveAdapterOperations<Cashout,CashoutsData,Long,ICashoutDataRepository> implements CashoutRepository {
    private ICashoutDataRepository repository;
    protected CashoutAdapter(ICashoutDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper,  d -> mapper.map(d, Cashout.class));

    }

    @Override
    public Mono<Cashout> saveCashouts(Cashout cashout) {
        CashoutsData cashoutsData = convertToData(cashout);

        return repository.save(cashoutsData)
                .map(saveCashotsData -> {
                    Cashout cashout1 = new Cashout();
                    cashout1.setId(saveCashotsData.getId());
                    cashout1.setUserId(saveCashotsData.getUserId());
                    cashout1.setAmount(saveCashotsData.getAmount());
                    return cashout1;
                });
    }
    public CashoutsData convertToData(Cashout cashout) {
        CashoutsData cashoutsData = new CashoutsData();
        cashoutsData.setUserId(cashout.getUserId());
        cashoutsData.setAmount(cashout.getAmount());
        return cashoutsData;
    }
}
