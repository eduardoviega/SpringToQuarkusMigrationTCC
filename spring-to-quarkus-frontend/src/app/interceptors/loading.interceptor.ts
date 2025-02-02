import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoadingService } from '../services/loading.service';
import { finalize } from 'rxjs';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const spinnerService = inject(LoadingService);

  spinnerService.showLoading();
  return next(req).pipe(
    finalize(() => spinnerService.hideLoading())
  );
};
