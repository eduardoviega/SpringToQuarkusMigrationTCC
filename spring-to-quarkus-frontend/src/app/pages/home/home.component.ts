import { Component } from '@angular/core';
import { ConfigService } from '../../services/config.service';
import { CommonModule } from '@angular/common';
import { TecnicaPromptEnum, TecnicaPromptTextEnum } from '../../models/TecnicaPromptEnum';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MarkdownComponent } from 'ngx-markdown';

type ArquivoSelecionado = {
  file: File,
  content: string
}

type PromptTechnique = {
  techniqueEnum: TecnicaPromptEnum;
  techniqueName: string;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, MatSelectModule, MatFormFieldModule, MarkdownComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  selectedFiles: File[] = [];
  migratedFiles: File[] = [];

  selectedFile: ArquivoSelecionado | undefined = undefined;
  migratedFile: ArquivoSelecionado | undefined = undefined;

  promptTechniques: PromptTechnique[] = [
    { techniqueEnum: TecnicaPromptEnum.FEW_SHOT, techniqueName: TecnicaPromptTextEnum[TecnicaPromptEnum.FEW_SHOT] },
    { techniqueEnum: TecnicaPromptEnum.CHAIN_OF_THOUGHT, techniqueName: TecnicaPromptTextEnum[TecnicaPromptEnum.CHAIN_OF_THOUGHT] },
    { techniqueEnum: TecnicaPromptEnum.GENERATE_KNOWLEDGE, techniqueName: TecnicaPromptTextEnum[TecnicaPromptEnum.GENERATE_KNOWLEDGE] },
    { techniqueEnum: TecnicaPromptEnum.PROMPT_CHAINING, techniqueName: TecnicaPromptTextEnum[TecnicaPromptEnum.PROMPT_CHAINING] },
    { techniqueEnum: TecnicaPromptEnum.CUSTOM_PROMPT, techniqueName: TecnicaPromptTextEnum[TecnicaPromptEnum.CUSTOM_PROMPT] }
  ];
  selectedPromptTechnique: PromptTechnique = this.promptTechniques[0];

  constructor(private configService: ConfigService) { }

  onFileChange(event: any) {
    let files = event.target.files;
    if (files.length > 0) {
      this.selectedFiles = Array.from(files);
    }
  }

  onSubmit() {
    if (this.selectedFiles.length > 0) {
      const formData = new FormData();
      this.selectedFiles.forEach((file) => formData.append('files', file, file.name));
      this.migratedFiles = [];
      this.migratedFile = undefined;

      this.configService.migrateProject(formData, this.selectedPromptTechnique.techniqueEnum).subscribe(response => {
        try {
          let responseFilesObject = JSON.parse(response.choices[0].message.content);
          this.processOpenAIResponse(responseFilesObject);
        } catch (error) {
          console.error("Erro ao tratar dados da migração:", error);
        }
      });
    }
  }

  processOpenAIResponse(response: any) {
    let fileNameList: Array<string> = this.selectedFiles.map((file) => file.name);
    fileNameList.push("README.md");

    fileNameList.forEach(fileName => {
      const fileContent = response[fileName];
      const file = new File([fileContent], fileName, { type: 'application/octet-stream' });
      this.migratedFiles.push(file);
    });

    return response;
  }

  selectFile(file: File, isMigratedFile: boolean) {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      if (isMigratedFile) {
        let conteudoJaSelecionado = this.migratedFile?.file == file;
        this.migratedFile = conteudoJaSelecionado ? undefined : { file, content: e.target.result };
      }
      else {
        let conteudoJaSelecionado = this.selectedFile?.file == file;
        this.selectedFile = conteudoJaSelecionado ? undefined : { file, content: e.target.result };
      }
    };
    reader.readAsText(file);
  }

}
