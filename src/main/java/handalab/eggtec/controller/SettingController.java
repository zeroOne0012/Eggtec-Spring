package handalab.eggtec.controller;

import handalab.eggtec.dto.setting.InitializeDTO;
import handalab.eggtec.dto.setting.SettingDTO;
import handalab.eggtec.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/apis/setting")
@RestController
public class SettingController {
    private final SettingService settingService;
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/")
    public ResponseEntity<SettingDTO> getSetting() {
        SettingDTO result = settingService.getSetting();
        return result!=null
                ? ResponseEntity.status(HttpStatus.OK).body(result)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/initialize")
    public ResponseEntity<InitializeDTO> getInitialize() {
        InitializeDTO result = settingService.getInitialize();
        return result!=null
                ? ResponseEntity.status(HttpStatus.OK).body(result)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PatchMapping("/")
    public ResponseEntity<SettingDTO> updateSetting(@RequestBody SettingDTO setting) {
        SettingDTO result = settingService.updateSetting(setting);
        return result!=null
                ? ResponseEntity.status(HttpStatus.OK).body(result)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
