package handalab.eggtec.service;

import handalab.eggtec.dto.setting.InitializeDTO;
import handalab.eggtec.dto.setting.SelectedDataDTO;
import handalab.eggtec.dto.setting.SelectedDTO;
import handalab.eggtec.dto.setting.SettingDTO;
import handalab.eggtec.mapper.SettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SettingService {

    private final SettingMapper settingMapper;

    public SettingService(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
    }

    // GET /
    public SettingDTO getSetting() {
        return settingMapper.getSetting();
    }

    // GET /initialize
    public InitializeDTO getInitialize() {
        SelectedDTO selected = settingMapper.getSelected(); // query1
        String exposureTime = "exp_" + selected.getType().toLowerCase();
        SelectedDataDTO selectedData = settingMapper.getInitialize(exposureTime); // query2

        return new InitializeDTO(
                selected.getIdx(),
                selected.getWeight(),
                List.of(selectedData.getY1(),selectedData.getY2()),
                List.of(selectedData.getY3(),selectedData.getY4()),
                selectedData.getExposure(),
                selectedData.getOutputCnt()
        );
    }

    // PATCH /
    public SettingDTO updateSetting(SettingDTO setting) {
        return settingMapper.updateSetting(setting);
    }
}
