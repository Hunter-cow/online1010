package com.hunter.vodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class testVod {
    public static void main(String[] args) {
    //获取凭证
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tESr2STJBE3ijgd2AvN", "VzsJUIDFjztHhQncNlzjw90oxcxJ3k");
        GetVideoPlayAuthRequest getVideoPlayAuthRequest = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse getVideoPlayAuthResponse = new GetVideoPlayAuthResponse();
        getVideoPlayAuthRequest.setVideoId("985fcf9781f24b7eab0c44a87fce07a4");
        try {
            getVideoPlayAuthResponse = client.getAcsResponse(getVideoPlayAuthRequest);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("playAuth:"+getVideoPlayAuthResponse.getPlayAuth());

    }

    //根据id获取视频url
    public static void getPlayUrl() {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tESr2STJBE3ijgd2AvN", "VzsJUIDFjztHhQncNlzjw90oxcxJ3k");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("985fcf9781f24b7eab0c44a87fce07a4");
        try {
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
