package carhire.layered.service.custom;

import carhire.layered.dto.HireDto;
import carhire.layered.service.SuperService;

public interface HireService extends SuperService {
        int addHire(HireDto hireDto) throws Exception;
}
