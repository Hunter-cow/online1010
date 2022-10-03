package com.hunter.eduservice.client;

import com.hunter.commonUtils.ResultVo;
import com.hunter.commonUtils.UcenterMemberVo;
import com.hunter.eduservice.client.Impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(value = "service-ucenter", fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {
    @PostMapping("/eduUcenter/member/getUserInfoById/{id}")
    public UcenterMemberVo getUserInfoById(@PathVariable("id") String id);
}
