import { Component, Input } from '@angular/core';
import { CareerTestButton } from '../career-test-button/career-test-button';
import { CareerTodayLogo } from '../career-today-logo/career-today-logo';

@Component({
  selector: 'app-career-test-card',
  imports: [CareerTestButton, CareerTodayLogo],
  templateUrl: './career-test-card.html',
  styleUrl: './career-test-card.scss',
})
export class CareerTestCard {
  @Input() title = 'Discover Your Career Path';
  @Input() description =
    'Find the career path that matches your interests, strengths and goals.';
  @Input() ctaLabel = 'Take Career Test';
  @Input() compact = false;

  readonly highlights = [
    { icon: '\u26A1', text: 'Takes ~20 minutes' },
    { icon: '\uD83D\uDCCA', text: 'Instant insights' },
    { icon: '\uD83C\uDF93', text: 'Built for students' },
  ];
}
