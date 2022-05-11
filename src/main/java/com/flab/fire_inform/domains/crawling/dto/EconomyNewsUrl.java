package com.flab.fire_inform.domains.crawling.dto;

public enum EconomyNewsUrl {
    FINANCE("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=259"),
    STOCK("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=258"),
    INDUSTRY("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=261"),
    VENTURE("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=771"),
    ESTATE("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=260"),
    GLOBAL("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=262"),
    LIFE("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=310"),
    ORDINARY("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=263");
    private final String url;
    EconomyNewsUrl(String url){
        this.url =url;
    }
    public String getUrl(){
        return url;
    }

}
