package converter;

public enum TecnicaPromptEnum {

    FEW_SHOT(0, "Few-shot.md"),
    CHAIN_OF_THOUGHT(1, "Chain-of-thought.md"),
    GENERATE_KNOWLEDGE(2, "Generate-knowledge.md"),
    PROMPT_CHAINING(3, "Prompt-chaining.md"),
    CUSTOM_PROMPT(4, "Custom-prompt.md");

    private final int id;
    private final String fileName;

    TecnicaPromptEnum(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public static String getFileNameById(int id) {
        return switch (id) {
            case 0 -> FEW_SHOT.getFileName();
            case 1 -> CHAIN_OF_THOUGHT.getFileName();
            case 2 -> GENERATE_KNOWLEDGE.getFileName();
            case 3 -> PROMPT_CHAINING.getFileName();
            case 4 -> CUSTOM_PROMPT.getFileName();
            default -> "";
        };
    }
}
