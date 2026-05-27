import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api';
import { AssessmentService } from '../../services/assessment';
import { Category, Question } from '../../models/interfaces';

@Component({
  selector: 'app-test-taking',
  imports: [CommonModule],
  templateUrl: './test-taking.html',
  styleUrl: './test-taking.scss',
})
export class TestTaking implements OnInit {
  sessionId = 0;
  categoryId = 0;
  category: Category | null = null;
  questions: Question[] = [];
  currentIndex = 0;
  selectedOptions: Record<number, number> = {};
  loading = true;
  submitting = false;
  completing = false;
  allCategories: Category[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService,
    private assessmentService: AssessmentService
  ) {}

  ngOnInit(): void {
    this.sessionId = Number(this.route.snapshot.paramMap.get('sessionId'));
    this.categoryId = Number(this.route.snapshot.paramMap.get('categoryId'));

    this.apiService.getCategories().subscribe({
      next: (cats) => (this.allCategories = cats),
    });

    this.apiService.getCategoryById(this.categoryId).subscribe({
      next: (cat) => (this.category = cat),
    });

    this.apiService.getQuestionsByCategory(this.categoryId).subscribe({
      next: (questions) => {
        this.questions = questions;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  get currentQuestion(): Question | null {
    return this.questions[this.currentIndex] || null;
  }

  get progress(): number {
    if (this.questions.length === 0) return 0;
    return ((this.currentIndex + 1) / this.questions.length) * 100;
  }

  get answeredCount(): number {
    return Object.keys(this.selectedOptions).length;
  }

  selectOption(questionId: number, optionId: number): void {
    this.selectedOptions[questionId] = optionId;
    this.submitting = true;
    this.assessmentService
      .submitAnswer(this.sessionId, questionId, optionId)
      .subscribe({
        next: () => {
          this.submitting = false;
          if (this.currentIndex < this.questions.length - 1) {
            setTimeout(() => this.nextQuestion(), 300);
          }
        },
        error: () => (this.submitting = false),
      });
  }

  nextQuestion(): void {
    if (this.currentIndex < this.questions.length - 1) {
      this.currentIndex++;
    }
  }

  prevQuestion(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    }
  }

  goToQuestion(index: number): void {
    this.currentIndex = index;
  }

  finishCategory(): void {
    const currentCatIndex = this.allCategories.findIndex(
      (c) => c.id === this.categoryId
    );
    if (currentCatIndex < this.allCategories.length - 1) {
      const nextCat = this.allCategories[currentCatIndex + 1];
      this.router.navigate(['/test', this.sessionId, nextCat.id]);
      this.categoryId = nextCat.id;
      this.currentIndex = 0;
      this.selectedOptions = {};
      this.loading = true;
      this.category = null;

      this.apiService.getCategoryById(this.categoryId).subscribe({
        next: (cat) => (this.category = cat),
      });
      this.apiService.getQuestionsByCategory(this.categoryId).subscribe({
        next: (questions) => {
          this.questions = questions;
          this.loading = false;
        },
      });
    } else {
      this.completing = true;
      this.assessmentService.completeSession(this.sessionId).subscribe({
        next: () => {
          this.completing = false;
          this.router.navigate(['/results', this.sessionId]);
        },
        error: () => (this.completing = false),
      });
    }
  }

  isLastCategory(): boolean {
    const idx = this.allCategories.findIndex((c) => c.id === this.categoryId);
    return idx === this.allCategories.length - 1;
  }
}
