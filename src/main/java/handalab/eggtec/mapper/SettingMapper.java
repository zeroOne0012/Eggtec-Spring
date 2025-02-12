package handalab.eggtec.mapper;

import handalab.eggtec.dto.setting.SelectedDataDTO;
import handalab.eggtec.dto.setting.SelectedDTO;
import handalab.eggtec.dto.setting.SettingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {
    SettingDTO getSetting();

    SelectedDTO getSelected();

    SelectedDataDTO getInitialize(@Param("exposure") String exposure);

    SettingDTO updateSetting(@Param("setting") SettingDTO setting);
}
