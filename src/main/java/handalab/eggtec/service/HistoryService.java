package handalab.eggtec.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import handalab.eggtec.dto.MessageDTO;
import handalab.eggtec.dto.request.history.CsvFilterDTO;
import handalab.eggtec.dto.request.history.HistoryFilterDTO;
import handalab.eggtec.dto.request.history.HistoryPostDTO;
import handalab.eggtec.dto.response.history.*;
import handalab.eggtec.mapper.HistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//transactional, autowired
@Service
@Slf4j
public class HistoryService {
    private final HistoryMapper historyMapper;
    public HistoryService(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }


    public List<TotalSummaryDTO> totalSummary() {
        return historyMapper.getTotalSummary();
    }


    public List<SummaryDTO> summaryByDate(Integer id, HistoryFilterDTO filter) {
        List<SummaryDTO> result = historyMapper.getSummary(id, filter);

        return result.stream()
                .peek(dto-> dto.setDate(dto.getDate().split("T")[0]))
                .collect(Collectors.toList());
    }

    public List<LastDTO> last() {
        List<LastDTO> result = historyMapper.getLast();

        return result.stream()
                .peek(dto -> dto.setLastDate(dto.getLastDate().split("T")[0]))
                .collect(Collectors.toList());
    }

    public List<TotalDTO> total() {
        List<TotalDTO> result = historyMapper.getTotal();
        return result;
    }

    private String mkdir(String csvPath) throws IOException {
        // path
        String totalPath = Paths.get("").toAbsolutePath().toString() + File.separator + "csv" + File.separator + csvPath + File.separator;
        Path directoryPath = Paths.get(totalPath);

        // mkdir
        try {
            Files.createDirectories(directoryPath); // 디렉터리가 없으면 생성
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new IOException(e); // 디렉터리 생성 실패 시 종료
        }
        return totalPath;
    }

    private File getUniqueFilePath(String totalPath, String fileName) {
        File csvFile = new File(totalPath + File.separator + fileName);
        int count = 1;

        while(csvFile.exists()) {
            csvFile = new File(totalPath + File.separator + fileName + " (" + count + ")");
            count++;
        }
        return csvFile;
    }

    public MessageDTO createCsv(Integer id, CsvFilterDTO info) throws IOException {
        List<CsvDTO> csvDTO = historyMapper.getCsvData(id, info); // query execution

        // mkdir
        String csvPath = info.getPath() + "csv";
        String totalPath = mkdir(csvPath);

        // fileName by case
        String fileName;
        if(info.getStartDate()==null || info.getEndDate()==null) { // 오늘 날짜
            String[] date = LocalDate.now().toString().split("-");
             fileName =  String.join("", date) + "_recipe" + id;
        }else{ // start~end
            String[] s_date = info.getStartDate().toString().split("-");
            String[] e_date = info.getEndDate().toString().split("-");
            fileName =  String.join("", s_date) + "_" + String.join("", e_date) + "_recipe" + id;
        }


        // get unique file name
        File csvFile = getUniqueFilePath(totalPath, fileName);

        // save csv // data: csvDTO, path: csvFile
        try {
            CsvMapper csvMapper = new CsvMapper();
            csvMapper.registerModule(new JavaTimeModule());
            CsvSchema schema = csvMapper.schemaFor(CsvDTO.class).withHeader();
            ObjectWriter writer = csvMapper.writer(schema);

            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8)){
                writer.writeValues(outputStreamWriter).writeAll(csvDTO); // query 결과
            }
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new IOException(e);
        }

        String separatorRegex = File.separator.equals("\\") ? "\\\\" : File.separator;
        String[] fileNameParts = csvFile.toString().split(separatorRegex);
        return new MessageDTO(fileNameParts[fileNameParts.length - 2] + File.separator + fileNameParts[fileNameParts.length - 1]);
    }

    public Integer createHistory(HistoryPostDTO history) {
        Integer result = historyMapper.postHistory(history);
//        log.info(result.toString());
        return result;
    }
}
