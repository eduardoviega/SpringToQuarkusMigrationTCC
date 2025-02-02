import { Injectable } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  constructor(private ngxSpinnerService: NgxSpinnerService) { }

  showLoading() {
    this.ngxSpinnerService.show(undefined, {
      bdColor: "rgba(0, 0, 0, 0.8)",
      size: "medium",
      color: "#9bc8cd",
      fullScreen: true
    });
  }

  hideLoading() {
    this.ngxSpinnerService.hide();
  }
}
