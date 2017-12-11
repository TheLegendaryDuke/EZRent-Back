package com.aoranzhang.ezrentback.data.entity;

public interface PersistentObject {
    String getId();
    void setId(String id);

    Integer getVersion();
    void setVersion(Integer version);
}
