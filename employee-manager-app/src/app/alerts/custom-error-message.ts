import { HttpErrorResponse } from "@angular/common/http";

export function CustomErrorMessage(httpErrorResponse: HttpErrorResponse) {
  if (httpErrorResponse.error instanceof Error) {
    alert('An error occurred:'+ httpErrorResponse.error.message);
  } else {
    alert(`${JSON.stringify(httpErrorResponse.error)}`);
  }
}