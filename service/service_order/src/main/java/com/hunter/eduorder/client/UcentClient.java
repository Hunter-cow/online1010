package com.hunter.eduorder.client;

import com.hunter.commonUtils.UcenterMemberVo;
import com.hunter.eduorder.client.Impl.UcentClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-ucenter", fallback = UcentClientImpl.class)
public interface UcentClient {
    @PostMapping("/eduUcenter/member/getUserInfoById/{id}")
    UcenterMemberVo getUserInfoById(@PathVariable String id);
}
