package com.hunter.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.commonUtils.CourseWebVoOrder;
import com.hunter.commonUtils.ResultVo;
import com.hunter.commonUtils.UcenterMemberVo;
import com.hunter.eduorder.client.EduClient;
import com.hunter.eduorder.client.UcentClient;
import com.hunter.eduorder.entity.Order;
import com.hunter.eduorder.mapper.OrderMapper;
import com.hunter.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcentClient ucentClient;

    @Autowired
    private OrderService orderService;

    //生成订单
    @Override
    public String createOrder(String courseId, String userId) {
        //通过远程调用获取用户信息
        UcenterMemberVo userInfo = ucentClient.getUserInfoById(userId);
        //通过远程调用获取课程信息
        CourseWebVoOrder courseInfo = eduClient.getCourseInfoOrder(courseId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setMemberId(userId);
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());
        order.setTotalFee(courseInfo.getPrice());
        order.setPayType(1);
        order.setStatus(0);
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }

    //根据订单号查询订单信息
    @GetMapping("/getOrderInfo/{orderNo}")
    public ResultVo getOrderInfo(@PathVariable("orderNo") String orderNo) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);
        Order order = orderService.getOne(queryWrapper);
        return ResultVo.ok().data("orderInfo", order);
    }
}
