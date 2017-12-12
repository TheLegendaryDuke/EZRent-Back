package com.aoranzhang.ezrentback.service;

import com.pubnub.api.PubNub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    @Autowired
    private PubNub pubNub;

}
