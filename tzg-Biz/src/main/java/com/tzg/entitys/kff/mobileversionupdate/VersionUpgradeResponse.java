package com.tzg.entitys.kff.mobileversionupdate;

import java.io.Serializable;


public class VersionUpgradeResponse implements Serializable{

    private static final long serialVersionUID = -1335146363116823021L;

    /**
     * 版本号
     */
    private String ver;

    /**
     * 0不需更新，1需要更新
     */
    private Integer upgrade;

    /**
     * 0普通更新，1强制更新
     */
    private Integer force;

    /**
     * 升级APK地址
     */
    private String upgradeUrl;

    /**
     * 强制升级APK地址
     */
    private String guideUrl;

    /**
     * 更新内容描述
     */
    private String upExplain;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Integer getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public String getUpExplain() {
        return upExplain;
    }

    public void setUpExplain(String upExplain) {
        this.upExplain = upExplain;
    }
}

