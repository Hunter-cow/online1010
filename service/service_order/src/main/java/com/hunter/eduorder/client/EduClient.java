package com.hunter.eduorder.client;

import com.hunter.commonUtils.CourseWebVoOrder;
import com.hunter.eduorder.client.Impl.EduClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {
    @PostMapping("/eduService/courseFront/getCourseInfoOrder/{id}")
    CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}
