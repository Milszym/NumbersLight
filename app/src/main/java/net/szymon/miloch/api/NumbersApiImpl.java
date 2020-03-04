package net.szymon.miloch.api;

import net.szymon.miloch.api.data.NumberBaseDto;
import net.szymon.miloch.api.data.NumberDetailsDto;
import net.szymon.miloch.data.NumberBase;
import net.szymon.miloch.data.NumberDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class NumbersApiImpl implements NumbersApi {

    private NumbersApiService numbersApiService;

    @Inject
    public NumbersApiImpl(NumbersApiService numbersApiService) {
        this.numbersApiService = numbersApiService;
    }

    @NotNull
    @Override
    public Single<List<NumberBase>> getNumbers() {
        return this.numbersApiService.getNumbers()
                .map(new Function<List<NumberBaseDto>, List<NumberBase>>() {
                    @Override
                    public List<NumberBase> apply(List<NumberBaseDto> numbersDto) {
                        List<NumberBase> numberDataList = new ArrayList();
                        for (NumberBaseDto numberBaseDto : numbersDto) {
                            NumberBase numberData = new NumberBase(numberBaseDto);
                            numberDataList.add(numberData);
                        }
                        return numberDataList;
                    }
                });
    }

    @NotNull
    @Override
    public Single<NumberDetails> getNumber(@NotNull String numberName) {
        return this.numbersApiService.getNumber(numberName)
                .map(new Function<NumberDetailsDto, NumberDetails>() {
                    @Override
                    public NumberDetails apply(NumberDetailsDto numberBaseDto) {
                        return new NumberDetails(numberBaseDto);
                    }
                });
    }
}
