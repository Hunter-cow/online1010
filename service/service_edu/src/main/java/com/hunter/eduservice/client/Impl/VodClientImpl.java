package com.hunter.eduservice.client.Impl;

import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.client.VodClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class VodClientImpl implements VodClient {
    @Override
    public ResultVo uploadAliyunVideo(MultipartFile file) {
        return null;
    }

    @Override
    public ResultVo removeAliyunVideoById(String id) {
        return null;
    }

    @Override
    public ResultVo removeBatch(List<String> videoIdList) {
        return null;
    }

    @Override
    public ResultVo getPlayAuth(String id) {
        return null;
    }
}
