package com.beastmouth.auto.imaotai.service;

import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiItemEntity;

import java.util.List;

public interface IMaoTaiItemService {
    void refreshItem(List<IMaoTaiItemEntity> itemEntities);

    List<IMaoTaiItemEntity> all();
}
