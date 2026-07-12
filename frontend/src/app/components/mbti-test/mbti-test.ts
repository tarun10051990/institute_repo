import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api';
import { MbtiQuestion, MbtiResult } from '../../models/interfaces';

@Component({
  selector: 'app-mbti-test',
  imports: [CommonModule],
  templateUrl: './mbti-test.html',
  styleUrl: './mbti-test.scss',
})
export class MbtiTest implements OnInit {
  questions: MbtiQuestion[] = [];
  currentIndex = 0;
  answers: Record<number, string> = {};
  loading = true;
  submitting = false;
  started = false;
  result: MbtiResult | null = null;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getMbtiQuestions().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  get currentQuestion(): MbtiQuestion | null {
    return this.questions[this.currentIndex] || null;
  }

  get progress(): number {
    if (this.questions.length === 0) return 0;
    return ((this.currentIndex + 1) / this.questions.length) * 100;
  }

  get answeredCount(): number {
    return Object.keys(this.answers).length;
  }

  get allAnswered(): boolean {
    return this.answeredCount === this.questions.length && this.questions.length > 0;
  }

  startTest(): void {
    this.started = true;
  }

  selectOption(questionId: number, letter: string): void {
    this.answers[questionId] = letter;
    if (this.currentIndex < this.questions.length - 1) {
      setTimeout(() => this.nextQuestion(), 250);
    }
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

  submit(): void {
    if (!this.allAnswered) return;
    this.submitting = true;
    const payload = Object.entries(this.answers).map(([questionId, letter]) => ({
      questionId: Number(questionId),
      letter,
    }));
    this.apiService.submitMbti(payload).subscribe({
      next: (result) => {
        this.result = result;
        this.submitting = false;
        window.scrollTo({ top: 0, behavior: 'smooth' });
      },
      error: () => (this.submitting = false),
    });
  }

  restart(): void {
    this.result = null;
    this.answers = {};
    this.currentIndex = 0;
    this.started = false;
  }
}
