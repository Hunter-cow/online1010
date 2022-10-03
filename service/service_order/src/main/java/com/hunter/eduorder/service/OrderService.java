package com.hunter.eduorder.service;

import com.hunter.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-27
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String userId);
}
