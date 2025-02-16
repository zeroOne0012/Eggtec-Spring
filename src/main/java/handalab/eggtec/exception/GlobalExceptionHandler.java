package handalab.eggtec.exception;
import handalab.eggtec.mapper.ErrorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorMapper errorMapper;
    public GlobalExceptionHandler(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    // error save -> DB
    private int errLog(String type, Map<String, String> res){ // type: 에러 발생 메소드 (type: db column name)
        StringBuilder s = new StringBuilder();
        for(String e : res.values()){
            s.append(e).append(" ");
        }
        s.append("\n");
        return errorMapper.errLog(type, s.toString());
    }

    // Exception.getStackTrace()에서 mothod 이름 반환
    private String getOccurred(StackTraceElement[] stackTrace) {
        return stackTrace.length > 0 ? stackTrace[0].getMethodName() : "Unknown Method";
    }

    // responseDTO 반환 및 에러 저장 or logging
    private Map<String, String> makeResponse(Exception e, boolean isDbConnection) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getClass().getSimpleName());
        response.put("message", e.getMessage());
        if (isDbConnection) errLog(getOccurred(e.getStackTrace()), response);
        else log.error("ERROR! " + (getOccurred(e.getStackTrace()) + ": " + e.getMessage()));
        return response;
    }

    // 400; 잘못된 요청 파라미터 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleArgumentMismatch(MethodArgumentTypeMismatchException e) throws IOException {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.BAD_REQUEST);
    }

    // 400; 기본 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.BAD_REQUEST);
    }

    // 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(Exception e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 500
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 500; 데이터베이스 연결 오류 (JDBC Connection 문제)
    // 가장 최상위 에러 등록함, 여러개 등록 가능
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseConnectionError(CannotCreateTransactionException e) {
        return new ResponseEntity<>(makeResponse(e, false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 500; MyBatis 쿼리 실행 오류
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseError(DataAccessException e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
