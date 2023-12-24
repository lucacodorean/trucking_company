package app.dto;

import lombok.NonNull;
import lombok.Data;

@Data
public class CSVRequest {
    @NonNull private String fileName;
    @NonNull private String className;
}
