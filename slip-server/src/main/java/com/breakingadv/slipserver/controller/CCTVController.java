package com.breakingadv.slipserver.controller;

import com.breakingadv.slipserver.service.CCTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CCTVController {
    private final CCTVService cctvService;

}
