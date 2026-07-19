import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api';
import { Category } from '../../models/interfaces';

@Component({
  selector: 'app-programs',
  imports: [RouterLink, CommonModule],
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
    ORIENTATION: '#2962ff',
    INTEREST: '#00b894',
    PERSONALITY: '#fd79a8',
    APTITUDE: '#ff9f43',
    EQ: '#6c5ce7',
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
    return this.categoryColors[code] || '#2962ff';
  }
}
