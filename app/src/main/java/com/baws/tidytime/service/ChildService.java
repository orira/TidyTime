package com.baws.tidytime.service;

import com.baws.tidytime.dto.ChildDto;

/**
 * Created by wadereweti on 5/08/14.
 */
public interface ChildService extends Service {
    void createChild(ChildDto dto);
}
