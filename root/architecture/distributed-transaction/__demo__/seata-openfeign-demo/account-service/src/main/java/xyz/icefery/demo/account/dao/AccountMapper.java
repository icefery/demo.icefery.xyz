package xyz.icefery.demo.account.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.account.entity.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {}
