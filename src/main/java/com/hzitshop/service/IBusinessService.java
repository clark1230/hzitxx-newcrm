package com.hzitshop.service;

import com.hzitshop.util.ServerResponse;

public interface IBusinessService {
    /**
     * 还原数据
      * @return
     */
    public ServerResponse recover(Integer[] customerIdArr);
}
