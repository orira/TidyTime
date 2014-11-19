package com.baws.tidytime.service;

import com.baws.tidytime.dto.ChoreDto;

import java.util.List;

/**
 * Created by wadereweti on 21/07/14.
 */
public interface ChoreService extends Service {
    void createChores(List<ChoreDto> chores);
}
