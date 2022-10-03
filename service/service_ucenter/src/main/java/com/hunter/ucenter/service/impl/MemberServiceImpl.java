package com.hunter.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.commonUtils.JwtUtils;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import com.hunter.ucenter.entity.Member;
import com.hunter.ucenter.entity.Vo.LoginVo;
import com.hunter.ucenter.entity.Vo.RegisterVo;
import com.hunter.ucenter.mapper.MemberMapper;
import com.hunter.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.ucenter.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-22
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(password)) {
            throw new ConsumeException(20001, "手机号或密码为空");
        }
        //判断手机号
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getMobile, phone);
        Member member = baseMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new ConsumeException(20001, "手机号不存在");
        }
        //判断密码
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new ConsumeException(20001, "密码错误");
        }
        //判断是否禁用
        if (member.getIsDisabled()) {
            throw new ConsumeException(20001, "用户已被禁用");
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();//昵称
        String mobile = registerVo.getMobile();//手机号
        String password = registerVo.getPassword();//密码
        String code = registerVo.getCode();//验证码
        //判断
        if (StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new ConsumeException(20001, "注册失败");
        }
        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new ConsumeException(20001, "验证码错误！！");
        }
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getMobile, mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count >= 1) {
            throw new ConsumeException(20001, "手机号不能重发");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/65423f14-49a9-4092-baf5-6d0ef9686a85.png");
        baseMapper.insert(member);

    }

    @Override
    public Member getMemberByOpenId(String openid) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getOpenid, openid);
        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }
}
