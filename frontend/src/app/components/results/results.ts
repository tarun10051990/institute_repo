import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData } from 'chart.js';
import { AssessmentService } from '../../services/assessment';
import { AssessmentResult } from '../../models/interfaces';

@Component({
  selector: 'app-results',
  imports: [CommonModule, RouterLink, BaseChartDirective],
  templateUrl: './results.html',
  styleUrl: './results.scss',
})
export class Results implements OnInit {
  result: AssessmentResult | null = null;
  loading = true;
  sessionId = 0;

  barChartData: ChartData<'bar'> = { labels: [], datasets: [] };
  barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    plugins: {
      legend: { display: false },
      title: { display: true, text: 'Category-wise Score Distribution', font: { size: 16 } },
    },
    scales: {
      y: { beginAtZero: true, max: 100, title: { display: true, text: 'Score (%)' } },
    },
  };

  pieChartData: ChartData<'pie'> = { labels: [], datasets: [] };
  pieChartOptions: ChartConfiguration<'pie'>['options'] = {
    responsive: true,
    plugins: {
      legend: { position: 'bottom' },
      title: { display: true, text: 'Assessment Score Distribution', font: { size: 16 } },
    },
  };

  radarChartData: ChartData<'radar'> = { labels: [], datasets: [] };
  radarChartOptions: ChartConfiguration<'radar'>['options'] = {
    responsive: true,
    plugins: {
      legend: { display: false },
      title: { display: true, text: 'Competency Profile', font: { size: 16 } },
    },
    scales: {
      r: { beginAtZero: true, max: 100 },
    },
  };

  chartColors = ['#2962ff', '#00b894', '#fd79a8', '#ff9f43', '#6c5ce7'];

  constructor(
    private route: ActivatedRoute,
    private assessmentService: AssessmentService
  ) {}

  ngOnInit(): void {
    this.sessionId = Number(this.route.snapshot.paramMap.get('sessionId'));
    this.assessmentService.getResults(this.sessionId).subscribe({
      next: (result) => {
        this.result = result;
        this.setupCharts();
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  setupCharts(): void {
    if (!this.result) return;
    const labels = this.result.categoryScores.map((s) => s.categoryName);
    const scores = this.result.categoryScores.map((s) => s.percentage);

    this.barChartData = {
      labels,
      datasets: [
        {
          data: scores,
          backgroundColor: this.chartColors,
          borderRadius: 8,
        },
      ],
    };

    this.pieChartData = {
      labels,
      datasets: [
        {
          data: scores,
          backgroundColor: this.chartColors,
        },
      ],
    };

    this.radarChartData = {
      labels,
      datasets: [
        {
          data: scores,
          backgroundColor: 'rgba(41, 98, 255, 0.2)',
          borderColor: '#2962ff',
          pointBackgroundColor: '#2962ff',
        },
      ],
    };
  }

  downloadPdf(): void {
    this.assessmentService.downloadPdfReport(this.sessionId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `career-report-${this.result?.sessionCode || this.sessionId}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
    });
  }

  getScoreLevel(percentage: number): string {
    if (percentage >= 75) return 'Strong';
    if (percentage >= 50) return 'Moderate';
    return 'Developing';
  }

  getScoreLevelClass(percentage: number): string {
    if (percentage >= 75) return 'level-strong';
    if (percentage >= 50) return 'level-moderate';
    return 'level-developing';
  }
}
