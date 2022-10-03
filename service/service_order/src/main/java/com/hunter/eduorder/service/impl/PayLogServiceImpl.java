package com.hunter.eduorder.service.impl;

import com.hunter.eduorder.entity.PayLog;
import com.hunter.eduorder.mapper.PayLogMapper;
import com.hunter.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-27
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
