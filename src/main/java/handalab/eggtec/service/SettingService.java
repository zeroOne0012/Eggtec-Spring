package handalab.eggtec.service;

import handalab.eggtec.dto.response.setting.InitializeDTO;
import handalab.eggtec.dto.response.setting.SelectedDataDTO;
import handalab.eggtec.dto.response.setting.SelectedDTO;
import handalab.eggtec.dto.response.setting.SettingDTO;
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

    public SettingDTO getSetting() {
        return settingMapper.getSetting();
    }

    public InitializeDTO getInitialize() {
        SelectedDTO selected = settingMapper.getSelected();
        String exposureTime = "exp_" + selected.getType().toLowerCase();
        SelectedDataDTO selectedData = settingMapper.getInitialize(exposureTime);

        return new InitializeDTO(
                selected.getIdx(),
                selected.getWeight(),
                List.of(selectedData.getY1(),selectedData.getY2()),
                List.of(selectedData.getY3(),selectedData.getY4()),
                selectedData.getExposure(),
                selectedData.getOutputCnt()
        );
    }

    public SettingDTO updateSetting(SettingDTO setting) {
        return settingMapper.updateSetting(setting);
    }
}
