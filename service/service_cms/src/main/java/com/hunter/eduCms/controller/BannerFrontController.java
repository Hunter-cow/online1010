package com.hunter.eduCms.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduCms.entity.CrmBanner;
import com.hunter.eduCms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduCms/bannerFront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有的banner
    @GetMapping("/getAllBanner")
    public ResultVo getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return ResultVo.ok().data("list", list);
    }


}
