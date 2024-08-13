package swiftescaper.backend.swiftescaper.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swiftescaper.backend.swiftescaper.apiPayload.ApiResponse;
import swiftescaper.backend.swiftescaper.apiPayload.code.status.SuccessStatus;
import swiftescaper.backend.swiftescaper.service.accident.AccidentService;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;

@RestController
@RequestMapping("/api/accident")
@RequiredArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    @PostMapping("/")
    public ApiResponse<String> postAccident(@RequestBody AccidentRequestDto.AccidentDto accidentDto) {
        accidentService.controlAccident(accidentDto);

        return ApiResponse.of(SuccessStatus.NOTIFICATION_SUCCESS, SuccessStatus.NOTIFICATION_SUCCESS.getMessage());
    }
}
