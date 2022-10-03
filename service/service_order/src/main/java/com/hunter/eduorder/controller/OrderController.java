package com.hunter.eduorder.controller;


import com.hunter.commonUtils.JwtUtils;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduorder.service.OrderService;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/eduOrder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    //1.生成订单

    @PostMapping("/createOrder/{courseId}")
    public ResultVo createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) {
            throw new ConsumeException(20001, "请先进行登录");
        }
        String orderNo = orderService.createOrder(courseId,userId);

        return ResultVo.ok().data("orderId",orderNo);
    }

}

