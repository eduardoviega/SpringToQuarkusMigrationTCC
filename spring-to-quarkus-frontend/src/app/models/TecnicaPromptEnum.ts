export enum TecnicaPromptEnum {
    FEW_SHOT = 0,
    CHAIN_OF_THOUGHT = 1,
    GENERATE_KNOWLEDGE = 2,
    PROMPT_CHAINING = 3,
    CUSTOM_PROMPT = 4
}

export const TecnicaPromptTextEnum = {
    [TecnicaPromptEnum.FEW_SHOT]: "Few Shot",
    [TecnicaPromptEnum.CHAIN_OF_THOUGHT]: "Chain of Thought",
    [TecnicaPromptEnum.GENERATE_KNOWLEDGE]: "Generate Knowledge",
    [TecnicaPromptEnum.PROMPT_CHAINING]: "Prompt Chaining",
    [TecnicaPromptEnum.CUSTOM_PROMPT]: "Custom Prompt"
};