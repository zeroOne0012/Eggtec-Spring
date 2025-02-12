package handalab.eggtec.exception;
import handalab.eggtec.mapper.ErrorMapper;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;



@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorMapper errorMapper;

    public GlobalExceptionHandler(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    // error save -> DB
    private int errLog(String type, Map<String, String> res){ // type: 에러 발생 메소드 (type: db column name)
        StringBuilder s = new StringBuilder();
//        for(Map.Entry<String, String> e : res.entrySet()){
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

//    private String getOccurred(StackTraceElement[] stackTrace) {
//        String methodName = stackTrace.length > 0 ? stackTrace[0].getMethodName() : "Unknown Method";
//        String className = stackTrace.length > 0 ? stackTrace[0].getClassName() : "Unknown Class";
//
//        return String.format("Exception occurred in %s.%s",
//                className, methodName);
//    }

    // responseDTO return, errLog()
    private Map<String, String> makeResponse(Exception e, boolean isDbConnection) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getClass().getSimpleName());
        response.put("message", e.getMessage());
        if (isDbConnection) errLog(getOccurred(e.getStackTrace()), response);
        return response;
    }


    // 400; 잘못된 요청 파라미터 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleArgumentMismatch(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.BAD_REQUEST);
    }

    // 400; 모든 예외 처리 (기본)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.BAD_REQUEST);
    }

    // 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(Exception e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // 500; 데이터베이스 연결 오류 (JDBC Connection 문제)
    /*
        a.
        가장 최상위 Exception을 등록해야 하는 것으로 보임

        b.
        @ExceptionHandler()에 여러 개의 exception 등록 가능
        {
            @ExceptionHandler({CannotCreateTransactionException.class, Exception.class})
            public ResponseEntity<Map<String, String>> handleExceptions(Exception e) { ... }
        }
     */
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseConnectionError(CannotCreateTransactionException e) {
        return new ResponseEntity<>(makeResponse(e, false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    @ExceptionHandler(CannotGetJdbcConnectionException.class)
//    public ResponseEntity<Map<String, String>> handleDatabaseConnectionError(CannotGetJdbcConnectionException e) {
//        return new ResponseEntity<>(makeResponse(e, false), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(ConnectException.class)
//    public ResponseEntity<Map<String, String>> handleDatabaseConnectionError(ConnectException e) {
//        return new ResponseEntity<>(makeResponse(e, false), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(MyBatisSystemException.class)
//    public ResponseEntity<Map<String, String>> handleDatabaseConnectionError(MyBatisSystemException e) {
//        return new ResponseEntity<>(makeResponse(e, false), HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    // 500; MyBatis / JPA 쿼리 실행 오류
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseError(DataAccessException e) {
        return new ResponseEntity<>(makeResponse(e, true), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    // 404 ... not works
//    @ExceptionHandler(NoHandlerFoundException.class)
////    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleNotFoundError(NoHandlerFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("요청하신 URL을 찾을 수 없습니다: " + e.getRequestURL());
//    }

}
