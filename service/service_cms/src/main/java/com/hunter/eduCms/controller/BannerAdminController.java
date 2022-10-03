package com.hunter.eduCms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduCms.entity.CrmBanner;
import com.hunter.eduCms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-20
 */
@RestController
@RequestMapping("/eduCms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询
    @GetMapping("/pageBanner/{page}/{limit}")
    public ResultVo pageBanner(@PathVariable Long page, @PathVariable Long limit) {

        Page<CrmBanner> bannerPage = new Page<>(page, limit);

        crmBannerService.page(bannerPage, null);

        return ResultVo.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    //添加banner
    @PostMapping("/addBanner")
    public ResultVo addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return ResultVo.ok();
    }

    @DeleteMapping("/remove/{id}")
    public ResultVo removeBanner(@PathVariable("id") Long id) {
        crmBannerService.removeById(id);
        return ResultVo.ok();
    }

    @PostMapping("/update")
    public ResultVo updateBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return ResultVo.ok();
    }

    @GetMapping("/get/{id}")
    public ResultVo getBanner(@PathVariable Long id) {
        CrmBanner banner = crmBannerService.getById(id);

        return ResultVo.ok().data("item", banner);
    }


}

