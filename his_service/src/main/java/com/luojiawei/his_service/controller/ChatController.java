package com.luojiawei.his_service.controller;


import com.luojiawei.his_service.domain.dto.ChatForm;
import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/diagnosis/{diagnosisId}/qa/ask")
    public Result<Object> ask(@PathVariable("diagnosisId") Integer diagnosisId, @RequestBody ChatForm chatForm) {
        return Result.ok();
    }
}
