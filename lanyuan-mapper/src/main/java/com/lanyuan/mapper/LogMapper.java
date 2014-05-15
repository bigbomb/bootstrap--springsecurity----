package com.lanyuan.mapper;

import com.lanyuan.base.BaseMapper;
import com.lanyuan.entity.Log;

public interface LogMapper extends BaseMapper<Log>{
	public long count(Log log);
}
