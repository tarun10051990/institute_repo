import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../services/admin';
import {
  AdminSession,
  AdminUser,
  MbtiProfileAdmin,
  MbtiQuestionAdmin,
} from '../../models/interfaces';

type Tab = 'users' | 'reports' | 'questions' | 'content';

@Component({
  selector: 'app-admin-dashboard',
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.scss',
})
export class AdminDashboard implements OnInit {
  activeTab: Tab = 'users';

  users: AdminUser[] = [];
  sessions: AdminSession[] = [];
  questions: MbtiQuestionAdmin[] = [];
  profiles: MbtiProfileAdmin[] = [];

  loading = false;
  message = '';

  editingQuestion: MbtiQuestionAdmin | null = null;
  editingProfile: MbtiProfileAdmin | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  setTab(tab: Tab): void {
    this.activeTab = tab;
    this.message = '';
    if (tab === 'users') this.loadUsers();
    if (tab === 'reports') this.loadSessions();
    if (tab === 'questions') this.loadQuestions();
    if (tab === 'content') this.loadProfiles();
  }

  // ---- Users ----
  loadUsers(): void {
    this.loading = true;
    this.adminService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  deleteUser(user: AdminUser): void {
    if (!confirm(`Delete user ${user.email}? This removes their sessions and results.`)) return;
    this.adminService.deleteUser(user.id).subscribe({
      next: () => {
        this.message = `Deleted ${user.email}`;
        this.loadUsers();
      },
      error: (err) => (this.message = err?.error?.error || 'Could not delete user'),
    });
  }

  // ---- Reports / sessions ----
  loadSessions(): void {
    this.loading = true;
    this.adminService.getSessions().subscribe({
      next: (sessions) => {
        this.sessions = sessions;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  downloadPdf(session: AdminSession): void {
    this.adminService.downloadPdf(session.sessionId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `report-${session.sessionCode}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => (this.message = 'Could not download PDF (session may be incomplete)'),
    });
  }

  // ---- MBTI questions ----
  loadQuestions(): void {
    this.loading = true;
    this.adminService.getMbtiQuestions().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  newQuestion(): void {
    this.editingQuestion = {
      dimension: 'EI',
      questionText: '',
      displayOrder: this.questions.length + 1,
      active: true,
      options: [
        { label: 'A', text: '', letter: 'E' },
        { label: 'B', text: '', letter: 'I' },
      ],
    };
  }

  editQuestion(q: MbtiQuestionAdmin): void {
    this.editingQuestion = JSON.parse(JSON.stringify(q));
  }

  cancelQuestion(): void {
    this.editingQuestion = null;
  }

  saveQuestion(): void {
    if (!this.editingQuestion) return;
    const q = this.editingQuestion;
    const obs = q.id
      ? this.adminService.updateMbtiQuestion(q.id, q)
      : this.adminService.createMbtiQuestion(q);
    obs.subscribe({
      next: () => {
        this.message = 'Question saved';
        this.editingQuestion = null;
        this.loadQuestions();
      },
      error: (err) => (this.message = err?.error?.error || 'Could not save question'),
    });
  }

  deleteQuestion(q: MbtiQuestionAdmin): void {
    if (!q.id || !confirm('Delete this MBTI question?')) return;
    this.adminService.deleteMbtiQuestion(q.id).subscribe({
      next: () => {
        this.message = 'Question deleted';
        this.loadQuestions();
      },
      error: (err) => (this.message = err?.error?.error || 'Could not delete question'),
    });
  }

  // ---- MBTI content / profiles ----
  loadProfiles(): void {
    this.loading = true;
    this.adminService.getMbtiProfiles().subscribe({
      next: (profiles) => {
        this.profiles = profiles;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  editProfile(p: MbtiProfileAdmin): void {
    this.editingProfile = JSON.parse(JSON.stringify(p));
  }

  cancelProfile(): void {
    this.editingProfile = null;
  }

  saveProfile(): void {
    if (!this.editingProfile) return;
    const p = this.editingProfile;
    this.adminService.updateMbtiProfile(p.id, p).subscribe({
      next: () => {
        this.message = `Saved ${p.typeCode} content`;
        this.editingProfile = null;
        this.loadProfiles();
      },
      error: (err) => (this.message = err?.error?.error || 'Could not save profile'),
    });
  }

  // textarea <-> string[] helpers
  linesToText(lines: string[] | undefined): string {
    return (lines || []).join('\n');
  }

  textToLines(value: string, target: 'strengths' | 'weaknesses' | 'careers'): void {
    if (!this.editingProfile) return;
    this.editingProfile[target] = value.split('\n').map((s) => s.trim()).filter((s) => s);
  }
}
