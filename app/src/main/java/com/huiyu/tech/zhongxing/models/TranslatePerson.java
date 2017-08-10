package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-08-01.
 */

public class TranslatePerson {
    /**
     * m : 操作成功!
     * c : 0
     * d : [{"name":"湖北省总公司","child":[{"name":"公司领导","child":[{"name":"21313","userId":"153d83537c9543e9b5a1606dd66483eb"}]},{"name":"综合部"},{"name":"市场部"},{"name":"技术部"},{"name":"研发部","child":[{"name":"杨磊","userId":"d7e96defb93d4f6b9a932c5a8a2c854e"}]},{"name":"测试部"},{"name":"武汉市分公司","child":[{"name":"公司领导"},{"name":"综合部"},{"name":"市场部"},{"name":"技术部"},{"name":"武昌区分公司","child":[{"name":"公司领导"},{"name":"综合部"},{"name":"市场部"},{"name":"技术部"}]},{"name":"洪山区分公司","child":[{"name":"公司领导"},{"name":"综合部"},{"name":"市场部"},{"name":"技术部"}]},{"name":"东湖高新区分公司","child":[{"name":"公司领导"},{"name":"综合部"},{"name":"市场部"},{"name":"技术部"}]}]}]}]
     */
    private String name;
    private String userId;
    public List<TranslatePerson> child;

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public List<TranslatePerson> getChild() {
        return child;
    }
}

