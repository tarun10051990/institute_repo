import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';
import {
  AdminSession,
  AdminUser,
  AssessmentResult,
  MbtiProfileAdmin,
  MbtiQuestionAdmin,
} from '../models/interfaces';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin';

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

  private opts() {
    return { headers: this.getHeaders() };
  }

  // Users
  getUsers(): Observable<AdminUser[]> {
    return this.http.get<AdminUser[]>(`${this.apiUrl}/users`, this.opts());
  }

  deleteUser(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.apiUrl}/users/${id}`, this.opts());
  }

  // Sessions & reports
  getSessions(): Observable<AdminSession[]> {
    return this.http.get<AdminSession[]>(`${this.apiUrl}/sessions`, this.opts());
  }

  getSessionResults(sessionId: number): Observable<AssessmentResult> {
    return this.http.get<AssessmentResult>(`${this.apiUrl}/sessions/${sessionId}/results`, this.opts());
  }

  downloadPdf(sessionId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/sessions/${sessionId}/report/pdf`, {
      headers: this.getHeaders(),
      responseType: 'blob',
    });
  }

  // MBTI questions
  getMbtiQuestions(): Observable<MbtiQuestionAdmin[]> {
    return this.http.get<MbtiQuestionAdmin[]>(`${this.apiUrl}/mbti/questions`, this.opts());
  }

  createMbtiQuestion(q: MbtiQuestionAdmin): Observable<MbtiQuestionAdmin> {
    return this.http.post<MbtiQuestionAdmin>(`${this.apiUrl}/mbti/questions`, q, this.opts());
  }

  updateMbtiQuestion(id: number, q: MbtiQuestionAdmin): Observable<MbtiQuestionAdmin> {
    return this.http.put<MbtiQuestionAdmin>(`${this.apiUrl}/mbti/questions/${id}`, q, this.opts());
  }

  deleteMbtiQuestion(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.apiUrl}/mbti/questions/${id}`, this.opts());
  }

  // MBTI profiles (report content)
  getMbtiProfiles(): Observable<MbtiProfileAdmin[]> {
    return this.http.get<MbtiProfileAdmin[]>(`${this.apiUrl}/mbti/profiles`, this.opts());
  }

  updateMbtiProfile(id: number, p: MbtiProfileAdmin): Observable<MbtiProfileAdmin> {
    return this.http.put<MbtiProfileAdmin>(`${this.apiUrl}/mbti/profiles/${id}`, p, this.opts());
  }
}
