package com.hunter.ucenter.service;

import com.hunter.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.ucenter.entity.Vo.LoginVo;
import com.hunter.ucenter.entity.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-22
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Member getMemberByOpenId(String openid);
}
