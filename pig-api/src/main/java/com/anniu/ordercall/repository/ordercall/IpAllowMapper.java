package com.anniu.ordercall.repository.ordercall;

import com.anniu.ordercall.model.ordercall.IpAllow;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IpAllowMapper extends Mapper<IpAllow> {
}