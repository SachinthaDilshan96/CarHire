package carhire.layered.service.custom;

import carhire.layered.dto.HireDto;
import carhire.layered.entity.HireEntity;
import carhire.layered.service.SuperService;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public interface HireService extends SuperService {
        int addHire(HireDto hireDto) throws Exception;
        HireDto getHire(int id) throws Exception;
        ArrayList<HireDto> getAllHires() throws Exception;
        ArrayList<HireDto> getAllOverdueHires(LocalDate localDate) throws Exception;
        ArrayList<HireDto> getAllHiresToBeReturned() throws Exception;
        int markAsReturned(HireDto hireDto) throws Exception;
}
