package handalab.eggtec.log;

import java.util.Map;

import org.springframework.stereotype.Component;

import handalab.eggtec.mapper.ErrorMapper;

@Component
public class Logger {
    private final ErrorMapper errorMapper;
    public Logger(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }
    // error save -> DB
    public int errLog(String type, Map<String, String> errs){ // type: 에러 발생 메소드 이름 (type: db column name)
        StringBuilder s = new StringBuilder();
        for(String e : errs.values()){
            s.append(e).append(" ");
        }
        s.append("\n");
        return errorMapper.errLog(type, s.toString());
    }

    public int errLog(String type, String e){
        return errorMapper.errLog(type, e);
    }
}