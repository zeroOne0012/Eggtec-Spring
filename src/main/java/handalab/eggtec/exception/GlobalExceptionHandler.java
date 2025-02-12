package handalab.eggtec.exception;
import handalab.eggtec.mapper.ErrorMapper;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;



@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorMapper errorMapper;

    public GlobalExceptionHandler(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    // DB에 error save
    private int errLog(String type, Map<String, String> res){ // type: 에러 발생 메소드 (type: db column name)
        StringBuilder s = new StringBuilder();
//        for(Map.Entry<String, String> e : res.entrySet()){
        for(String e : res.values()){
            s.append(e).append(" ");
        }
        s.append("\n");
        return errorMapper.errLog(type, s.toString());
    }

    private String getOccurred(StackTraceElement[] stackTrace) {
        String methodName = stackTrace.length > 0 ? stackTrace[0].getMethodName() : "Unknown Method";
//        String className = stackTrace.length > 0 ? stackTrace[0].getClassName() : "Unknown Class";

//        return String.format("Exception occurred in %s.%s",
//                className, methodName);
        return methodName;
    }




    // 데이터베이스 연결 오류 (JDBC Connection 문제)
    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseConnectionError(CannotGetJdbcConnectionException e) {
        Map<String, String> response = new HashMap<>();
//        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Database Connection Error");
//        response.put("message", "데이터베이스 연결에 실패했습니다.");
        response.put("message", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // MyBatis / JPA 쿼리 실행 오류
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseError(DataAccessException e) {
        Map<String, String> response = new HashMap<>();
//        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Database Query Error");
//        response.put("message", "데이터를 불러오는 중 문제가 발생했습니다.");
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 잘못된 요청 파라미터 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleArgumentMismatch(MethodArgumentTypeMismatchException e) {
        Map<String, String> response = new HashMap<>();
//        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid Request Parameter");
//        response.put("message", "요청 형식이 잘못되었습니다.");
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 모든 예외 처리 (기본)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        Map<String, String> response = new HashMap<>();
//        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", e.getClass().getSimpleName());
        response.put("message", e.getMessage());
//        response.put("from", getOccurred(e.getStackTrace()));
        errLog(getOccurred(e.getStackTrace()), response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(Exception e) {
        Map<String, String> response = new HashMap<>();
//        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
//        response.put("message", "서버 내부 오류가 발생했습니다.");
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }




//    // 404 ... not works
//    @ExceptionHandler(NoHandlerFoundException.class)
////    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleNotFoundError(NoHandlerFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("요청하신 URL을 찾을 수 없습니다: " + e.getRequestURL());
//    }

}
