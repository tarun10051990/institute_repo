import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api';
import { AssessmentService } from '../../services/assessment';
import { Category, Session } from '../../models/interfaces';

@Component({
  selector: 'app-assessment',
  imports: [CommonModule],
  templateUrl: './assessment.html',
  styleUrl: './assessment.scss',
})
export class Assessment implements OnInit {
  categories: Category[] = [];
  currentSession: Session | null = null;
  loading = true;
  starting = false;

  categoryIcons: Record<string, string> = {
    ORIENTATION: '&#9881;',
    INTEREST: '&#9733;',
    PERSONALITY: '&#9786;',
    APTITUDE: '&#9889;',
    EQ: '&#9829;',
  };

  categoryColors: Record<string, string> = {
    ORIENTATION: '#7c3aed',
    INTEREST: '#008cff',
    PERSONALITY: '#ec4899',
    APTITUDE: '#ffb800',
    EQ: '#10b981',
  };

  constructor(
    private apiService: ApiService,
    private assessmentService: AssessmentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.apiService.getCategories().subscribe({
      next: (cats) => {
        this.categories = cats;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  startAssessment(): void {
    this.starting = true;
    this.assessmentService.startSession().subscribe({
      next: (session) => {
        this.currentSession = session;
        this.starting = false;
        if (this.categories.length > 0) {
          this.router.navigate(['/test', session.id, this.categories[0].id]);
        }
      },
      error: () => (this.starting = false),
    });
  }

  startCategory(categoryId: number): void {
    if (this.currentSession) {
      this.router.navigate(['/test', this.currentSession.id, categoryId]);
    } else {
      this.starting = true;
      this.assessmentService.startSession().subscribe({
        next: (session) => {
          this.currentSession = session;
          this.starting = false;
          this.router.navigate(['/test', session.id, categoryId]);
        },
        error: () => (this.starting = false),
      });
    }
  }

  getIcon(code: string): string {
    return this.categoryIcons[code] || '&#9670;';
  }

  getColor(code: string): string {
    return this.categoryColors[code] || '#7c3aed';
  }
}
