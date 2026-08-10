import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api';
import { Category } from '../../models/interfaces';
import { CareerTestCard } from '../career-test/career-test-card/career-test-card';

@Component({
  selector: 'app-programs',
  imports: [RouterLink, CommonModule, CareerTestCard],
  templateUrl: './programs.html',
  styleUrl: './programs.scss',
})
export class Programs implements OnInit {
  categories: Category[] = [];
  loading = true;

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

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getCategories().subscribe({
      next: (cats) => {
        this.categories = cats;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  getIcon(code: string): string {
    return this.categoryIcons[code] || '&#9670;';
  }

  getColor(code: string): string {
    return this.categoryColors[code] || '#7c3aed';
  }
}
