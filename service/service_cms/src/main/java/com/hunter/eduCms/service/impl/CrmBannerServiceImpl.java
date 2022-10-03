package com.hunter.eduCms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.eduCms.entity.CrmBanner;
import com.hunter.eduCms.mapper.CrmBannerMapper;
import com.hunter.eduCms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-20
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        LambdaQueryWrapper<CrmBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CrmBanner::getId);
        queryWrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
