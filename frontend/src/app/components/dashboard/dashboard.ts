import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';
import { AssessmentService } from '../../services/assessment';
import { AuthResponse, Session } from '../../models/interfaces';
import { CareerTestCard } from '../career-test/career-test-card/career-test-card';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink, CommonModule, CareerTestCard],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  user: AuthResponse | null = null;
  sessions: Session[] = [];
  loading = true;

  constructor(
    private authService: AuthService,
    private assessmentService: AssessmentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.assessmentService.getUserSessions().subscribe({
      next: (sessions) => {
        this.sessions = sessions;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  startNewAssessment(): void {
    this.router.navigate(['/assessment']);
  }

  viewResults(sessionId: number): void {
    this.router.navigate(['/results', sessionId]);
  }

  downloadReport(sessionId: number): void {
    this.assessmentService.downloadPdfReport(sessionId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `career-report-${sessionId}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
    });
  }

  getCompletedCount(): number {
    return this.sessions.filter((s) => s.status === 'COMPLETED').length;
  }

  getInProgressCount(): number {
    return this.sessions.filter((s) => s.status === 'IN_PROGRESS').length;
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'COMPLETED': return 'badge-success';
      case 'IN_PROGRESS': return 'badge-warning';
      default: return 'badge-primary';
    }
  }
}
