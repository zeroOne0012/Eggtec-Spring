package handalab.eggtec.mapper;

import handalab.eggtec.dto.response.setting.SelectedDataDTO;
import handalab.eggtec.dto.response.setting.SelectedDTO;
import handalab.eggtec.dto.response.setting.SettingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {
    SettingDTO getSetting();

    SelectedDTO getSelected();

    SelectedDataDTO getInitialize(@Param("exposure") String exposure);

    SettingDTO updateSetting(@Param("setting") SettingDTO setting);
}
