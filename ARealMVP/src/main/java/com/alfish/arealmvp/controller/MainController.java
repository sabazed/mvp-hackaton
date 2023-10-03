package com.alfish.arealmvp.controller;

import com.alfish.arealmvp.enums.RequestRejectReason;
import com.alfish.arealmvp.message.*;
import com.alfish.arealmvp.model.Statue;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.service.CloudService;
import com.alfish.arealmvp.service.RequestManager;
import com.alfish.arealmvp.service.StatueService;
import com.alfish.arealmvp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statues")
public class MainController {

    private final StatueService statueService;
    private final UserService userService;
    private final RequestManager requestManager;

    @Autowired
    public MainController(StatueService statueService, UserService userService, RequestManager requestManager) {
        this.statueService = statueService;
        this.userService = userService;
        this.requestManager = requestManager;
    }

    @GetMapping("/")
    @ResponseBody
    public GetStatuesResponse getAllStatues(@RequestParam("username") String username /*@RequestBody GetStatuesRequest request*/) {
        User user = userService.getUser(username);
        return requestManager.process(user, new GetStatuesRequest(username));
    }

    @GetMapping("/{statueName}/")
    @ResponseBody
    public GetCloudsResponse getStatueClouds(@RequestParam("username") String username, @PathVariable String statueName, @RequestParam String isInitial /*@RequestBody GetCloudsRequest request*/) {
        User user = userService.getUser(username);
        Statue statue = statueService.getStatueByName(statueName);
        boolean initial = Boolean.parseBoolean(isInitial);
        GetCloudsRequest request = new GetCloudsRequest(username, statueName, initial);
        GetCloudsResponse response =  requestManager.process(user, statue, request);
        if (response.getRejectReason() == null) {
            if (initial) {
                userService.addView(user);
                statueService.addView(statue);
            }
            else {
                userService.addInteraction(user);
                statueService.addInteraction(statue);
            }
        }
        return response;
    }

    @PostMapping("/{statueName}/")
    @ResponseBody
    public NewCloudResponse addStatueCloud(@PathVariable String statueName, @RequestBody NewCloudRequest request) {
        User user = userService.getUser(request.getUsername());
        Statue statue = statueService.getStatueByName(statueName);
        NewCloudResponse response = requestManager.process(user, statue, request);
        if (response.getRejectReason() == null) {
            userService.addWrite(user);
            statueService.addWrite(statue);
        }
        return response;
    }

}
