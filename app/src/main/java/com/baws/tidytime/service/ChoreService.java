package com.baws.tidytime.service;

import com.baws.tidytime.dto.ChoreDto;

/**
 * Created by wadereweti on 21/07/14.
 */
public interface ChoreService extends Service {
    void createChore(ChoreDto dto);
}
