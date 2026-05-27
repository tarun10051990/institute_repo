import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';
import { AssessmentResult, Session } from '../models/interfaces';

@Injectable({
  providedIn: 'root',
})
export class AssessmentService {
  private apiUrl = 'http://localhost:8080/api/assessment';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: token ? `Bearer ${token}` : '',
    });
  }

  startSession(): Observable<Session> {
    return this.http.post<Session>(
      `${this.apiUrl}/start`,
      {},
      { headers: this.getHeaders() }
    );
  }

  submitAnswer(
    sessionId: number,
    questionId: number,
    selectedOptionId: number
  ): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(
      `${this.apiUrl}/sessions/${sessionId}/answer`,
      { questionId, selectedOptionId },
      { headers: this.getHeaders() }
    );
  }

  completeSession(sessionId: number): Observable<AssessmentResult> {
    return this.http.post<AssessmentResult>(
      `${this.apiUrl}/sessions/${sessionId}/complete`,
      {},
      { headers: this.getHeaders() }
    );
  }

  getResults(sessionId: number): Observable<AssessmentResult> {
    return this.http.get<AssessmentResult>(
      `${this.apiUrl}/sessions/${sessionId}/results`,
      { headers: this.getHeaders() }
    );
  }

  getSessionStatus(sessionId: number): Observable<Session> {
    return this.http.get<Session>(
      `${this.apiUrl}/sessions/${sessionId}/status`,
      { headers: this.getHeaders() }
    );
  }

  getUserSessions(): Observable<Session[]> {
    return this.http.get<Session[]>(`${this.apiUrl}/sessions`, {
      headers: this.getHeaders(),
    });
  }

  downloadPdfReport(sessionId: number): Observable<Blob> {
    return this.http.get(
      `${this.apiUrl}/sessions/${sessionId}/report/pdf`,
      {
        headers: this.getHeaders(),
        responseType: 'blob',
      }
    );
  }
}
