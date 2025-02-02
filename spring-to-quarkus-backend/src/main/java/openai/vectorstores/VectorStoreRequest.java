package openai.vectorstores;

import java.util.List;

public class VectorStoreRequest {

    VectorStoreRequest() {
    }

    VectorStoreRequest(String name, List<String> file_ids) {
        this.name = name;
        this.file_ids = file_ids;
    }

    private String name;

    private List<String> file_ids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFile_ids() {
        return file_ids;
    }

    public void setFile_ids(List<String> file_ids) {
        this.file_ids = file_ids;
    }
}
