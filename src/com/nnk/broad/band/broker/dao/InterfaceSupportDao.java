package com.nnk.broad.band.broker.dao;

import java.util.List;

import com.nnk.broad.band.broker.entity.InterfaceSupportVo;

public interface InterfaceSupportDao {

	List<InterfaceSupportVo> list(String sql);
}
